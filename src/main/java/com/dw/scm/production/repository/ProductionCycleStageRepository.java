package com.dw.scm.production.repository;

import com.dw.scm.production.entity.ProductionCycleStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductionCycleStageRepository extends JpaRepository<ProductionCycleStage, Long> {
}
