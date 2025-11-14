package com.dw.scm.dataimport.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Comment("엑셀 업로드 시 행(row) 단위 에러 로그 테이블")
public class ImportRowError {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    @Comment("에러 로그 ID (PK)")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IMPORT_JOB_ID", nullable = false)
    @Comment("업로드 배치 ID (FK → IMPORT_JOB.ID)")
    private ImportJob importJob;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IMPORT_SHEET_ID")
    @Comment("시트 ID (FK → IMPORT_SHEET.ID)")
    private ImportSheet importSheet;

    @Column(name = "TARGET_TABLE", length = 50, nullable = false)
    @Comment("대상 도메인 (PRODUCT, SUPPLIER, PURCHASE_ORDER 등)")
    private String targetTable;

    @Column(name = "ROW_NO", nullable = false)
    @Comment("엑셀 기준 행 번호 (1부터)")
    private int rowNo;

    @Column(name = "ERROR_CODE", length = 100)
    @Comment("에러 코드 (선택)")
    private String errorCode;

    @Column(name = "ERROR_MESSAGE", length = 1000, nullable = false)
    @Comment("에러 상세 메시지")
    private String errorMessage;

    @Column(name = "RAW_DATA", columnDefinition = "JSON")
    @Comment("해당 행의 원본 데이터(JSON 형태로 저장)")
    private String rawData;

    @Column(name = "CREATED_AT", nullable = false, updatable = false, columnDefinition = "DATETIME default CURRENT_TIMESTAMP")
    @Comment("생성 일시")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
