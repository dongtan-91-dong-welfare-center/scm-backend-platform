package com.dw.scm.dataimport.entity;

import com.dw.scm.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Setter
@Comment("엑셀 시트별 업로드/파싱 이력 테이블")
public class ImportSheet extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    @Comment("시트 이력 ID (PK)")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IMPORT_JOB_ID", nullable = false)
    @Comment("업로드 배치 ID (FK → IMPORT_JOB.ID)")
    private ImportJob importJob;

    @Column(name = "SHEET_NAME", length = 100, nullable = false)
    @Comment("엑셀 시트명")
    private String sheetName;

    @Column(name = "TARGET_TABLE", length = 50, nullable = false)
    @Comment("매핑 대상 도메인 (PRODUCT, SUPPLIER, PURCHASE_ORDER 등)")
    private String targetTable;

    @Column(name = "TOTAL_ROWS")
    @Comment("해당 시트의 전체 행 수")
    private Integer totalRows;

    @Column(name = "SUCCESS_ROWS")
    @Comment("성공 처리된 행 수")
    private Integer successRows;

    @Column(name = "ERROR_ROWS")
    @Comment("에러 발생 행 수")
    private Integer errorRows;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", length = 20, nullable = false)
    @Comment("상태 (PARSED, VALIDATED, COMPLETED 등)")
    private ImportStatus status = ImportStatus.PARSED;
}
