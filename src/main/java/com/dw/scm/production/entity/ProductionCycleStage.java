package com.dw.scm.production.entity;

import com.dw.scm.common.entity.BaseEntity;
import com.dw.scm.product.entity.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Comment("생산사이클 단계 (원료→반제→자재→완제 흐름)")
public class ProductionCycleStage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    @Comment("생산 단계 ID (PK)")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CYCLE_ID", nullable = false)
    @Comment("생산사이클 ID (FK)")
    private ProductionCycle cycle;

    @Column(name = "STEP_SEQ", nullable = false)
    @Comment("단계 순번")
    private int stepSeq;

    @Column(name = "STEP_NAME", length = 100, nullable = false)
    @Comment("단계 명")
    private String stepName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INPUT_PRODUCT_ID")
    @Comment("입력 제품 ID")
    private Product inputProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OUTPUT_PRODUCT_ID")
    @Comment("산출 제품 ID")
    private Product outputProduct;

    @Column(name = "PROCESS_TIME_HOURS", precision = 10, scale = 2)
    @Comment("처리 시간(시간)")
    private BigDecimal processTimeHours;

    @Column(name = "YIELD_RATE", precision = 5, scale = 2)
    @Comment("수율(%)")
    private BigDecimal yieldRate;

    @Column(name = "NOTE", length = 500)
    @Comment("비고")
    private String note;
}
