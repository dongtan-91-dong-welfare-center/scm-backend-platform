package com.dw.scm.supplier.service;

import com.dw.scm.dataimport.entity.ImportJob;
import org.springframework.web.multipart.MultipartFile;

public interface SupplierImportService {
    /**
     * 공급업체 데이터 파일을 시스템에 등록하고 비동기적으로 가져오기 프로세스를 시작합니다.
     *
     * @param file 가져올 파일 (예: 엑셀)
     * @return 생성된 가져오기 작업(ImportJob)의 초기 상태
     */
    ImportJob startImport(MultipartFile file);

    /**
     * 지정된 ID의 가져오기 작업을 비동기적으로 처리합니다.
     * 이 메서드는 내부적으로 호출됩니다.
     *
     * @param jobId 처리할 ImportJob의 ID
     */
    void processImport(Long jobId);
}
