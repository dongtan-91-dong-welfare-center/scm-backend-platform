package com.dw.scm.product.entity;

import com.dw.scm.common.entity.BaseEntity;
import com.dw.scm.supplier.entity.Supplier;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Comment("제품 마스터 (원료/반제/자재/완제 공통)")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    @Comment("제품 ID (PK)")
    private Long id;

    @Column(name = "PRODUCT_CODE", length = 50, nullable = false, unique = true)
    @Comment("제품 코드(유니크)")
    private String productCode;

    @Column(name = "PRODUCT_NAME", length = 200, nullable = false)
    @Comment("제품명")
    private String productName;

    @Enumerated(EnumType.STRING)
    @Column(name = "PRODUCT_TYPE", length = 20, nullable = false)
    @Comment("제품 유형 (RAW, INTERM, MATERIAL, FINISHED)")
    private ProductType productType;

    @Column(name = "UNIT", length = 20, nullable = false)
    @Comment("기본 단위")
    private String unit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEFAULT_SUPPLIER_ID")
    @Comment("기본 공급업체 ID (FK)")
    private Supplier defaultSupplier;

    @Column(name = "LEAD_TIME_DAYS")
    @Comment("기본 리드타임(일)")
    private Integer leadTimeDays;

    @Column(name = "SAFETY_STOCK_QTY", precision = 18, scale = 3)
    @Comment("기본 안전재고량")
    private BigDecimal safetyStockQty;

    @Column(name = "LOT_SIZE", precision = 18, scale = 3)
    @Comment("기본 발주 LOT SIZE")
    private BigDecimal lotSize;

    @Column(name = "IS_ACTIVE", nullable = false, columnDefinition = "TINYINT(1) default 1")
    @Comment("사용 여부")
    private boolean isActive = true;
}
