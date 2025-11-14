package com.dw.scm.receipt.entity;

import com.dw.scm.common.entity.BaseEntity;
import com.dw.scm.purchaseorder.entity.PurchaseOrder;
import com.dw.scm.supplier.entity.Supplier;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Comment("입고 헤더 테이블")
public class Receipt extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    @Comment("입고 ID (PK)")
    private Long id;

    @Column(name = "RECEIPT_NUMBER", length = 50, nullable = false, unique = true)
    @Comment("입고 번호(유니크)")
    private String receiptNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUPPLIER_ID", nullable = false)
    @Comment("공급업체 ID (FK)")
    private Supplier supplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PO_ID")
    @Comment("구매주문 ID (FK)")
    private PurchaseOrder purchaseOrder;

    @Column(name = "RECEIPT_DATE", nullable = false)
    @Comment("입고일")
    private LocalDate receiptDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", length = 20, nullable = false)
    @Comment("상태")
    private ReceiptStatus status = ReceiptStatus.CONFIRMED;

    @Column(name = "REMARKS", length = 500)
    @Comment("비고")
    private String remarks;
}
