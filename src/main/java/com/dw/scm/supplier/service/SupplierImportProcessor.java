package com.dw.scm.supplier.service;

import com.dw.scm.dataimport.entity.ImportJob;
import com.dw.scm.dataimport.entity.ImportStatus;
import com.dw.scm.dataimport.entity.StgSupplier;
import com.dw.scm.dataimport.repository.ImportJobRepository;
import com.dw.scm.dataimport.repository.StgSupplierRepository;
import com.dw.scm.dataimport.service.ImportJobService;
import com.dw.scm.supplier.service.parser.SupplierExcelParser;
import com.dw.scm.supplier.service.validator.SupplierValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SupplierImportProcessor {

    // ImportJob의 상태 변경은 이 클래스에서 직접 처리하므로, JobService의 일부 기능만 사용합니다.
    private final ImportJobService importJobService;
    private final SupplierService supplierService;
    private final SupplierExcelParser supplierExcelParser;
    private final SupplierValidator supplierValidator;
    private final StgSupplierRepository stgSupplierRepository;
    private final ImportJobRepository importJobRepository; // 최종 저장을 위해 직접 주입

    @Transactional
    public void process(ImportJob job) {
        try {
            // 1. 파싱
            job.setStatus(ImportStatus.PARSING);
            importJobRepository.save(job); // 상태 변경 즉시 반영

            List<StgSupplier> stgSuppliers = supplierExcelParser.parse(job.getFilePath(), job);
            stgSupplierRepository.saveAll(stgSuppliers);
            job.setStatus(ImportStatus.PARSED);
            importJobRepository.save(job);

            // 2. 검증
            job.setStatus(ImportStatus.VALIDATING);
            importJobRepository.save(job);

            supplierValidator.validate(stgSuppliers);
            stgSupplierRepository.saveAll(stgSuppliers);
            job.setStatus(ImportStatus.VALIDATED);
            importJobRepository.save(job);

            // 3. 데이터 반영 (Merge)
            long errorCount = stgSuppliers.stream().filter(s -> s.getStatus() == ImportStatus.INVALID).count();
            if (errorCount == 0) {
                // 오류가 없을 때만 실제 데이터 반영 로직 실행
                job.setStatus(ImportStatus.IMPORTING);
                importJobRepository.save(job);

                supplierService.mergeFromStaging(stgSuppliers);
                stgSupplierRepository.saveAll(stgSuppliers); // 'IMPORTED' 상태 저장

                job.setStatus(ImportStatus.COMPLETED);
            } else {
                // 검증 오류가 있으면 데이터 반영을 건너뛰고 실패 처리
                job.setStatus(ImportStatus.FAILED);
            }

            // 4. 최종 결과 업데이트
            updateJobResult(job, stgSuppliers);
            importJobRepository.save(job);

        } catch (Exception e) {
            // 프로세스 도중 예외 발생 시 실패 처리
            job.setStatus(ImportStatus.FAILED);
            job.setMessage("가져오기 처리 중 오류 발생: " + e.getMessage());
            importJobRepository.save(job);
        }
    }

    private void updateJobResult(ImportJob job, List<StgSupplier> stgSuppliers) {
        long totalRows = stgSuppliers.size();
        long successRows = stgSuppliers.stream().filter(s -> s.getStatus() == ImportStatus.IMPORTED).count();
        long errorRows = totalRows - successRows;

        job.setTotalRows((int) totalRows);
        job.setSuccessRows((int) successRows);
        job.setErrorRows((int) errorRows);

        if (job.getStatus() != ImportStatus.FAILED) {
            job.setMessage("가져오기 작업이 성공적으로 완료되었습니다.");
        } else {
            job.setMessage(errorRows + "개의 행에서 오류가 발견되었습니다.");
        }
    }
}
