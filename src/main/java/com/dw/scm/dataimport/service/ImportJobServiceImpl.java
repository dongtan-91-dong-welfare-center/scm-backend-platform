package com.dw.scm.dataimport.service;

import com.dw.scm.dataimport.entity.ImportJob;
import com.dw.scm.dataimport.entity.ImportStatus;
import com.dw.scm.dataimport.entity.StgSupplier;
import com.dw.scm.dataimport.repository.ImportJobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ImportJobServiceImpl implements ImportJobService {

    private final ImportJobRepository importJobRepository;

    @Override
    public ImportJob createNewJob(MultipartFile file, Path filePath) {
        ImportJob job = new ImportJob();
        job.setFileName(file.getOriginalFilename());
        job.setFilePath(filePath.toString());

        // 파일의 원본 이름에서 확장자를 추출하여 저장합니다.
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        job.setFileType(extension != null ? extension.toUpperCase() : "");

        job.setImportType("SUPPLIER");
        job.setStatus(ImportStatus.UPLOADED);
        return importJobRepository.save(job);
    }

    @Override
    public ImportJob getJob(Long jobId) {
        return importJobRepository.findById(jobId).orElse(null);
    }

    @Override
    public void updateStatus(ImportJob job, ImportStatus status) {
        job.setStatus(status);
        importJobRepository.save(job);
    }

    @Override
    public void updateJobStatus(ImportJob job, List<StgSupplier> stgSuppliers) {
        long totalRows = stgSuppliers.size();
        long successRows = stgSuppliers.stream().filter(s -> s.getStatus() == ImportStatus.VALID).count();
        long errorRows = totalRows - successRows;

        job.setTotalRows((int) totalRows);
        job.setSuccessRows((int) successRows);
        job.setErrorRows((int) errorRows);

        if (errorRows == 0) {
            job.setStatus(ImportStatus.VALIDATED);
            job.setMessage("모든 데이터가 성공적으로 검증되었습니다.");
        } else {
            job.setStatus(ImportStatus.FAILED);
            job.setMessage(errorRows + "개의 행에서 오류가 발견되었습니다.");
        }
        importJobRepository.save(job);
    }

    @Override
    public void failJob(ImportJob job, Exception e) {
        job.setStatus(ImportStatus.FAILED);
        job.setMessage("가져오기 처리 중 오류 발생: " + e.getMessage());
        importJobRepository.save(job);
    }
}
