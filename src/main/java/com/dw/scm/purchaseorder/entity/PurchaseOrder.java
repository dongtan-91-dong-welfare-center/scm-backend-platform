package com.dw.scm.purchaseorder.entity;

import com.dw.scm.common.entity.BaseEntity;
import com.dw.scm.supplier.entity.Supplier;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Comment("구매 주문 헤더 테이블")
public class PurchaseOrder extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    @Comment("구매주문 ID (PK)")
    private Long id;

    @Column(name = "PO_NUMBER", length = 50, nullable = false, unique = true)
    @Comment("주문 번호(유니크)")
    private String poNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUPPLIER_ID", nullable = false)
    @Comment("공급업체 ID (FK)")
    private Supplier supplier;

    @Column(name = "ORDER_DATE", nullable = false)
    @Comment("주문일")
    private LocalDate orderDate;

    @Column(name = "EXPECTED_RECEIPT_DATE")
    @Comment("예상 입고일")
    private LocalDate expectedReceiptDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", length = 20, nullable = false)
    @Comment("상태(OPEN, PARTIAL, CLOSED)")
    private OrderStatus status = OrderStatus.OPEN;

    @Column(name = "CURRENCY_CODE", length = 10)
    @Comment("통화(KRW, USD 등)")
    private String currencyCode;

    @Column(name = "REMARKS", length = 500)
    @Comment("비고")
    private String remarks;
}
