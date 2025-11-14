package com.dw.scm.supplier.entity;

import com.dw.scm.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Setter
@Comment("공급업체 마스터 테이블")
public class Supplier extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    @Comment("공급업체 ID (PK)")
    private Long id;

    @Column(name = "SUPPLIER_CODE", length = 50, nullable = false, unique = true)
    @Comment("공급업체 코드(유니크)")
    private String supplierCode;

    @Column(name = "SUPPLIER_NAME", length = 200, nullable = false)
    @Comment("공급업체 명")
    private String supplierName;

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

    @Column(name = "IS_ACTIVE", nullable = false, columnDefinition = "TINYINT(1) default 1")
    @Comment("사용 여부")
    private boolean isActive = true;
}
