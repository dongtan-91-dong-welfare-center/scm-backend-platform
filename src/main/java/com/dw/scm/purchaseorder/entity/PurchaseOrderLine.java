package com.dw.scm.purchaseorder.entity;

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
@Comment("구매 주문 상세 테이블")
public class PurchaseOrderLine extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    @Comment("주문 상세 ID (PK)")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PO_ID", nullable = false)
    @Comment("구매주문 ID (FK)")
    private PurchaseOrder purchaseOrder;

    @Column(name = "LINE_NO", nullable = false)
    @Comment("라인 번호")
    private int lineNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    @Comment("제품 ID (FK)")
    private Product product;

    @Column(name = "ORDER_QTY", nullable = false, precision = 18, scale = 3)
    @Comment("주문 수량")
    private BigDecimal orderQty;

    @Column(name = "UNIT_PRICE", precision = 18, scale = 3)
    @Comment("단가")
    private BigDecimal unitPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", length = 20, nullable = false)
    @Comment("상태")
    private OrderStatus status = OrderStatus.OPEN;
}
