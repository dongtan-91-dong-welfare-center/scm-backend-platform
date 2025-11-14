package com.dw.scm.dataimport.repository;

import com.dw.scm.dataimport.entity.StgSupplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StgSupplierRepository extends JpaRepository<StgSupplier, Long> {
}
