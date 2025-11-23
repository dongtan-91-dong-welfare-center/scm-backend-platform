package com.dw.scm.vendor.service.validator;

import com.dw.scm.dataimport.entity.ImportStatus;
import com.dw.scm.dataimport.entity.StgVendor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class VendorValidator {

    /**
     * 스테이징된 공급업체 데이터 목록을 검증합니다.
     *
     * @param stgVendors 검증할 스테이징 공급업체 목록
     */
    public void validate(List<StgVendor> stgVendors) {
        for (StgVendor stgVendor : stgVendors) {
            List<String> errors = new ArrayList<>();

            // 필수 값 검증: 공급업체 코드
            if (!StringUtils.hasText(stgVendor.getVendorCode())) {
                errors.add("공급업체 코드는 필수입니다.");
            }

            // 필수 값 검증: 공급업체 이름
            if (!StringUtils.hasText(stgVendor.getVendorName())) {
                errors.add("공급업체 이름은 필수입니다.");
            }

            // 추가적인 검증 로직 (예: 형식, 길이, 중복 등)
            // ...

            if (errors.isEmpty()) {
                stgVendor.setStatus(ImportStatus.VALID);
            } else {
                stgVendor.setStatus(ImportStatus.INVALID);
                stgVendor.setErrorMessage(String.join(", ", errors));
            }
        }
    }
}
