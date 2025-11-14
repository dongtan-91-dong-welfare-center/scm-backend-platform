package com.dw.scm.dataimport.service;

import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;

public interface FileStorageService {
    /**
     * 파일을 지정된 경로에 저장합니다.
     *
     * @param file 저장할 파일
     * @return 저장된 파일의 전체 경로
     */
    Path store(MultipartFile file);
}
