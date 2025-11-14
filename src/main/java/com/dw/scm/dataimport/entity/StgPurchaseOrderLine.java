package com.dw.scm.dataimport.entity;

import com.dw.scm.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Setter
@Comment("구매주문 상세 엑셀 업로드 스테이징 테이블")
public class StgPurchaseOrderLine extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    @Comment("STG 구매주문 상세 ID (PK)")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IMPORT_JOB_ID", nullable = false)
    @Comment("업로드 배치 ID (FK → IMPORT_JOB.ID)")
    private ImportJob importJob;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IMPORT_SHEET_ID")
    @Comment("시트 ID (FK → IMPORT_SHEET.ID)")
    private ImportSheet importSheet;

    @Column(name = "ROW_NO", nullable = false)
    @Comment("엑셀 행 번호 (1부터)")
    private int rowNo;

    @Column(name = "PO_NUMBER", length = 50)
    @Comment("구매주문 번호 (헤더와 연결용)")
    private String poNumber;

    @Column(name = "LINE_NO")
    @Comment("라인 번호")
    private Integer lineNo;

    @Column(name = "PRODUCT_CODE", length = 50)
    @Comment("제품 코드 (엑셀 원본)")
    private String productCode;

    @Column(name = "ORDER_QTY_STR", length = 50)
    @Comment("주문 수량 (문자열로 우선 저장, 나중에 숫자로 변환)")
    private String orderQtyStr;

    @Column(name = "UNIT_PRICE_STR", length = 50)
    @Comment("단가 (문자열, 나중에 숫자로 변환)")
    private String unitPriceStr;

    @Column(name = "REMARKS", length = 500)
    @Comment("비고")
    private String remarks;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", length = 20, nullable = false)
    @Comment("상태 (RAW, VALID, INVALID, IMPORTED)")
    private ImportStatus status = ImportStatus.RAW;

    @Column(name = "ERROR_MESSAGE", length = 1000)
    @Comment("검증 오류 메시지")
    private String errorMessage;
}
