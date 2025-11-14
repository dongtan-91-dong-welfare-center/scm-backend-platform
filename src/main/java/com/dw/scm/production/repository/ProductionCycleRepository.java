package com.dw.scm.production.repository;

import com.dw.scm.production.entity.ProductionCycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductionCycleRepository extends JpaRepository<ProductionCycle, Long> {
}
