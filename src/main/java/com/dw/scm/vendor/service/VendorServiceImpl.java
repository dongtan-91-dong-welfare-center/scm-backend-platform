package com.dw.scm.vendor.service;

import com.dw.scm.dataimport.entity.ImportStatus;
import com.dw.scm.dataimport.entity.StgVendor;
import com.dw.scm.vendor.entity.Vendor;
import com.dw.scm.vendor.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;

    @Override
    @Transactional
    public void mergeFromStaging(List<StgVendor> stgVendors) {
        for (StgVendor stgVendor : stgVendors) {
            // 검증에 실패한 데이터는 건너뜁니다.
            if (stgVendor.getStatus() != ImportStatus.VALID) {
                continue;
            }

            // 운영 테이블에 데이터가 있는지 코드로 확인
            Optional<Vendor> existingVendorOpt = vendorRepository.findByVendorCode(stgVendor.getVendorCode());

            Vendor vendor;
            if (existingVendorOpt.isPresent()) {
                // 데이터가 있으면 기존 엔티티를 업데이트 (Update)
                vendor = existingVendorOpt.get();
            } else {
                // 데이터가 없으면 새로운 엔티티를 생성 (Insert)
                vendor = new Vendor();
                vendor.setVendorCode(stgVendor.getVendorCode());
            }

            // 스테이징 데이터를 운영 엔티티로 매핑
            vendor.setVendorName(stgVendor.getVendorName());
            vendor.setContactName(stgVendor.getContactName());
            vendor.setContactPhone(stgVendor.getContactPhone());
            vendor.setContactEmail(stgVendor.getContactEmail());
            vendor.setAddress(stgVendor.getAddress());
            vendor.setDefaultLeadTimeDays(stgVendor.getDefaultLeadTimeDays());
            vendor.setActive(true); // 가져온 데이터는 활성 상태로 설정

            // 운영 테이블에 저장
            vendorRepository.save(vendor);

            // 스테이징 테이블의 상태를 'IMPORTED'로 변경
            stgVendor.setStatus(ImportStatus.IMPORTED);
        }
        // 변경된 스테이징 데이터 상태는 호출한 쪽(Processor)에서 일괄 저장합니다.
    }
}
