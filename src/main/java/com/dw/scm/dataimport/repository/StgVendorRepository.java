package com.dw.scm.dataimport.repository;

import com.dw.scm.dataimport.entity.StgVendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StgVendorRepository extends JpaRepository<StgVendor, Long> {
}
