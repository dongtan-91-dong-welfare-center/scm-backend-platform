package com.dw.scm.vendor.service;

import com.dw.scm.dataimport.entity.ImportJob;
import com.dw.scm.dataimport.service.FileStorageService;
import com.dw.scm.dataimport.service.ImportJobService;
import com.dw.scm.dataimport.service.validator.ImportFileValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class VendorImportServiceImpl implements VendorImportService {

    private final ImportFileValidator importFileValidator;
    private final FileStorageService fileStorageService;
    private final ImportJobService importJobService;
    private final VendorImportProcessor vendorImportProcessor;

    @Override
    @Transactional
    public ImportJob startImport(MultipartFile file) {
        // 1. 파일 자체에 대한 유효성 검증 (공통 Validator 사용)
        importFileValidator.validate(file);

        // 2. 파일 저장
        Path filePath = fileStorageService.store(file);

        // 3. ImportJob 생성 및 초기 상태 저장
        ImportJob job = importJobService.createNewJob(file, filePath);

        // 4. 비동기 처리 호출
        processImport(job.getId());

        return job;
    }

    @Override
    @Async
    public void processImport(Long jobId) {
        ImportJob job = importJobService.getJob(jobId);
        if (job != null) {
            vendorImportProcessor.process(job);
        }
    }
}
