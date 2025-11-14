package com.dw.scm.supplier.service;

import com.dw.scm.dataimport.entity.ImportStatus;
import com.dw.scm.dataimport.entity.StgSupplier;
import com.dw.scm.supplier.entity.Supplier;
import com.dw.scm.supplier.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    @Override
    @Transactional
    public void mergeFromStaging(List<StgSupplier> stgSuppliers) {
        for (StgSupplier stgSupplier : stgSuppliers) {
            // 검증에 실패한 데이터는 건너뜁니다.
            if (stgSupplier.getStatus() != ImportStatus.VALID) {
                continue;
            }

            // 운영 테이블에 데이터가 있는지 코드로 확인
            Optional<Supplier> existingSupplierOpt = supplierRepository.findBySupplierCode(stgSupplier.getSupplierCode());

            Supplier supplier;
            if (existingSupplierOpt.isPresent()) {
                // 데이터가 있으면 기존 엔티티를 업데이트 (Update)
                supplier = existingSupplierOpt.get();
            } else {
                // 데이터가 없으면 새로운 엔티티를 생성 (Insert)
                supplier = new Supplier();
                supplier.setSupplierCode(stgSupplier.getSupplierCode());
            }

            // 스테이징 데이터를 운영 엔티티로 매핑
            supplier.setSupplierName(stgSupplier.getSupplierName());
            supplier.setContactName(stgSupplier.getContactName());
            supplier.setContactPhone(stgSupplier.getContactPhone());
            supplier.setContactEmail(stgSupplier.getContactEmail());
            supplier.setAddress(stgSupplier.getAddress());
            supplier.setDefaultLeadTimeDays(stgSupplier.getDefaultLeadTimeDays());
            supplier.setActive(true); // 가져온 데이터는 활성 상태로 설정

            // 운영 테이블에 저장
            supplierRepository.save(supplier);

            // 스테이징 테이블의 상태를 'IMPORTED'로 변경
            stgSupplier.setStatus(ImportStatus.IMPORTED);
        }
        // 변경된 스테이징 데이터 상태는 호출한 쪽(Processor)에서 일괄 저장합니다.
    }
}
