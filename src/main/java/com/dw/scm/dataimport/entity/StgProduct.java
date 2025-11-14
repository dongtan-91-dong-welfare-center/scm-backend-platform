package com.dw.scm.dataimport.entity;

import com.dw.scm.common.entity.BaseEntity;
import com.dw.scm.product.entity.ProductType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Comment("제품 엑셀 업로드 스테이징 테이블")
public class StgProduct extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    @Comment("STG 제품 ID (PK)")
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

    @Column(name = "PRODUCT_CODE", length = 50)
    @Comment("제품 코드 (엑셀 원본값)")
    private String productCode;

    @Column(name = "PRODUCT_NAME", length = 200)
    @Comment("제품명")
    private String productName;

    @Enumerated(EnumType.STRING)
    @Column(name = "PRODUCT_TYPE", length = 20)
    @Comment("제품 유형 (RAW, INTERM, MATERIAL, FINISHED 등)")
    private ProductType productType;

    @Column(name = "UNIT", length = 20)
    @Comment("단위 (EA, KG 등)")
    private String unit;

    @Column(name = "DEFAULT_SUPPLIER_CODE", length = 50)
    @Comment("기본 공급업체 코드 (엑셀에서 코드값으로 입력)")
    private String defaultSupplierCode;

    @Column(name = "LEAD_TIME_DAYS")
    @Comment("리드타임(일)")
    private Integer leadTimeDays;

    @Column(name = "SAFETY_STOCK_QTY", precision = 18, scale = 3)
    @Comment("안전재고 수량")
    private BigDecimal safetyStockQty;

    @Column(name = "LOT_SIZE", precision = 18, scale = 3)
    @Comment("발주 LOT 사이즈")
    private BigDecimal lotSize;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", length = 20, nullable = false)
    @Comment("상태 (RAW, VALID, INVALID, IMPORTED)")
    private ImportStatus status = ImportStatus.RAW;

    @Column(name = "ERROR_MESSAGE", length = 1000)
    @Comment("검증 오류 메시지 (간단 요약)")
    private String errorMessage;
}
