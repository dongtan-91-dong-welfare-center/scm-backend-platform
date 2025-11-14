package com.dw.scm.supplier.service.validator;

import com.dw.scm.dataimport.entity.ImportStatus;
import com.dw.scm.dataimport.entity.StgSupplier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class SupplierValidator {

    /**
     * 스테이징된 공급업체 데이터 목록을 검증합니다.
     *
     * @param stgSuppliers 검증할 스테이징 공급업체 목록
     */
    public void validate(List<StgSupplier> stgSuppliers) {
        for (StgSupplier stgSupplier : stgSuppliers) {
            List<String> errors = new ArrayList<>();

            // 필수 값 검증: 공급업체 코드
            if (!StringUtils.hasText(stgSupplier.getSupplierCode())) {
                errors.add("공급업체 코드는 필수입니다.");
            }

            // 필수 값 검증: 공급업체 이름
            if (!StringUtils.hasText(stgSupplier.getSupplierName())) {
                errors.add("공급업체 이름은 필수입니다.");
            }

            // 추가적인 검증 로직 (예: 형식, 길이, 중복 등)
            // ...

            if (errors.isEmpty()) {
                stgSupplier.setStatus(ImportStatus.VALID);
            } else {
                stgSupplier.setStatus(ImportStatus.INVALID);
                stgSupplier.setErrorMessage(String.join(", ", errors));
            }
        }
    }
}
