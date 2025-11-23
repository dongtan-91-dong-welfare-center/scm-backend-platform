package com.dw.scm.vendor.repository;

import com.dw.scm.vendor.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {

    /**
     * 공급업체 코드로 공급업체 정보를 조회합니다.
     * 코드는 유니크하므로 단일 결과를 Optional로 반환합니다.
     *
     * @param vendorCode 조회할 공급업체 코드
     * @return Optional<Vendor>
     */
    Optional<Vendor> findByVendorCode(String vendorCode);
}
