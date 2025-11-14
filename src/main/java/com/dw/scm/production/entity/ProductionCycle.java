package com.dw.scm.production.entity;

import com.dw.scm.common.entity.BaseEntity;
import com.dw.scm.product.entity.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Comment("생산사이클 헤더(원료→반제→자재→완제 흐름 정의)")
public class ProductionCycle extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    @Comment("생산사이클 ID (PK)")
    private Long id;

    @Column(name = "CYCLE_CODE", length = 50, nullable = false, unique = true)
    @Comment("생산사이클 코드(유니크)")
    private String cycleCode;

    @Column(name = "CYCLE_NAME", length = 200, nullable = false)
    @Comment("생산사이클 명")
    private String cycleName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINISHED_PRODUCT_ID", nullable = false)
    @Comment("완제품 ID (FK)")
    private Product finishedProduct;

    @Column(name = "VERSION_NO", nullable = false, columnDefinition = "INT default 1")
    @Comment("버전 번호")
    private int versionNo = 1;

    @Column(name = "IS_ACTIVE", nullable = false, columnDefinition = "TINYINT(1) default 1")
    @Comment("사용 여부")
    private boolean isActive = true;

    @Column(name = "EFFECTIVE_FROM")
    @Comment("적용 시작일")
    private LocalDate effectiveFrom;

    @Column(name = "EFFECTIVE_TO")
    @Comment("적용 종료일")
    private LocalDate effectiveTo;

    @Column(name = "TOTAL_LEAD_TIME_DAYS")
    @Comment("전체 리드타임(일)")
    private Integer totalLeadTimeDays;

    @Column(name = "DESCRIPTION", length = 500)
    @Comment("설명")
    private String description;
}
