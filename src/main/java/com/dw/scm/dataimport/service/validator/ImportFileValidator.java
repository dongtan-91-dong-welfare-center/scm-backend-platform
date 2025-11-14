package com.dw.scm.dataimport.service.validator;

import com.dw.scm.common.exception.BusinessException;
import com.dw.scm.common.exception.ErrorCode;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * 데이터 가져오기 기능에서 사용되는 파일 자체의 유효성을 검증하는 공통 Validator입니다.
 */
@Component
public class ImportFileValidator {

    public void validate(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE, "가져올 파일이 비어있습니다.");
        }

        // 향후 파일 확장자, MIME 타입, 최대 크기 등 다양한 공통 파일 검증 로직을 이곳에 추가할 수 있습니다.
        // 예: if (!"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equals(file.getContentType())) { ... }
    }
}
