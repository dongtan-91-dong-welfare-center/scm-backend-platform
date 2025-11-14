package com.dw.scm.receipt.entity;

import com.dw.scm.common.entity.BaseEntity;
import com.dw.scm.product.entity.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Comment("입고 상세 테이블")
public class ReceiptLine extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    @Comment("입고 상세 ID (PK)")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RECEIPT_ID", nullable = false)
    @Comment("입고 ID (FK)")
    private Receipt receipt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    @Comment("제품 ID (FK)")
    private Product product;

    @Column(name = "RECEIVED_QTY", nullable = false, precision = 18, scale = 3)
    @Comment("입고 수량")
    private BigDecimal receivedQty;

    @Column(name = "UNIT_COST", precision = 18, scale = 3)
    @Comment("단가")
    private BigDecimal unitCost;

    @Column(name = "LOT_NO", length = 100)
    @Comment("LOT 번호")
    private String lotNo;

    @Column(name = "EXPIRY_DATE")
    @Comment("유통기한")
    private LocalDate expiryDate;
}
