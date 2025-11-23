package com.dw.scm.dataimport.entity;

import com.dw.scm.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Setter
@Comment("공급업체 엑셀 업로드 스테이징 테이블")
public class StgVendor extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    @Comment("STG 공급업체 ID (PK)")
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

    @Column(name = "VENDOR_CODE", length = 50)
    @Comment("공급업체 코드 (엑셀 원본)")
    private String vendorCode;

    @Column(name = "VENDOR_NAME", length = 200)
    @Comment("공급업체 이름")
    private String vendorName;

    @Column(name = "CONTACT_NAME", length = 100)
    @Comment("담당자 이름")
    private String contactName;

    @Column(name = "CONTACT_PHONE", length = 50)
    @Comment("담당자 연락처")
    private String contactPhone;

    @Column(name = "CONTACT_EMAIL", length = 100)
    @Comment("담당자 이메일")
    private String contactEmail;

    @Column(name = "ADDRESS", length = 500)
    @Comment("공급업체 주소")
    private String address;

    @Column(name = "DEFAULT_LEAD_TIME_DAYS")
    @Comment("기본 리드타임(일)")
    private Integer defaultLeadTimeDays;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", length = 20, nullable = false)
    @Comment("상태 (RAW, VALID, INVALID, IMPORTED)")
    private ImportStatus status = ImportStatus.RAW;

    @Column(name = "ERROR_MESSAGE", length = 1000)
    @Comment("검증 오류 메시지")
    private String errorMessage;
}
