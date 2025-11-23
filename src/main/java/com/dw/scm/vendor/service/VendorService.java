package com.dw.scm.vendor.service;

import com.dw.scm.dataimport.entity.StgVendor;

import java.util.List;

/**
 * 공급업체(Vendor) 도메인의 핵심 비즈니스 로직을 처리하는 서비스 인터페이스입니다.
 */
public interface VendorService {

    /**
     * 스테이징 테이블의 검증된 데이터를 실제 운영 테이블로 반영(UPSERT)합니다.
     *
     * @param stgVendors 스테이징된 공급업체 데이터 목록
     */
    void mergeFromStaging(List<StgVendor> stgVendors);
}
