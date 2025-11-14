package com.dw.scm.supplier.repository;

import com.dw.scm.supplier.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    /**
     * 공급업체 코드로 공급업체 정보를 조회합니다.
     * 코드는 유니크하므로 단일 결과를 Optional로 반환합니다.
     *
     * @param supplierCode 조회할 공급업체 코드
     * @return Optional<Supplier>
     */
    Optional<Supplier> findBySupplierCode(String supplierCode);
}
