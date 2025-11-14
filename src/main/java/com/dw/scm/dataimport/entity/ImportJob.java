package com.dw.scm.dataimport.entity;

import com.dw.scm.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Setter
@Comment("엑셀 업로드 배치(파일 단위) 이력 테이블")
public class ImportJob extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    @Comment("업로드 배치 ID (PK)")
    private Long id;

    @Column(name = "FILE_NAME", length = 255, nullable = false)
    @Comment("업로드된 파일명(원본명)")
    private String fileName;

    @Column(name = "FILE_PATH", length = 500, nullable = false)
    @Comment("서버에 저장된 파일 경로 또는 스토리지 키")
    private String filePath;

    @Column(name = "FILE_TYPE", length = 50, nullable = false)
    @Comment("파일 타입 (예: EXCEL)")
    private String fileType;

    @Column(name = "IMPORT_TYPE", length = 50)
    @Comment("업로드 유형 (예: PRODUCT, SUPPLIER, ORDER 등 비즈니스 구분)")
    private String importType;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", length = 20, nullable = false)
    @Comment("상태 (UPLOADED, PARSED, VALIDATED, COMPLETED, FAILED)")
    private ImportStatus status = ImportStatus.UPLOADED;

    @Column(name = "TOTAL_ROWS")
    @Comment("전체 행 수")
    private Integer totalRows;

    @Column(name = "SUCCESS_ROWS")
    @Comment("성공 처리된 행 수")
    private Integer successRows;

    @Column(name = "ERROR_ROWS")
    @Comment("에러 발생 행 수")
    private Integer errorRows;

    @Column(name = "MESSAGE", length = 1000)
    @Comment("요약 메시지 또는 실패 사유")
    private String message;

    @Column(name = "CREATED_BY")
    @Comment("업로드한 사용자 ID")
    private Long createdBy;
}
