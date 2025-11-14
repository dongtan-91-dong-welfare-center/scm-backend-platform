package com.dw.scm.aichat.entity;

import com.dw.scm.product.entity.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Comment("AI가 생성한 보고서 이력 테이블")
public class AiReportHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    @Comment("AI 보고서 이력 ID (PK)")
    private Long id;

    @Column(name = "REPORT_TYPE", length = 50, nullable = false)
    @Comment("보고서 유형")
    private String reportType;

    @Column(name = "REPORT_TITLE", length = 200, nullable = false)
    @Comment("보고서 제목")
    private String reportTitle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TARGET_PRODUCT_ID")
    @Comment("대상 제품 ID (FK)")
    private Product targetProduct;

    @Column(name = "TARGET_FROM_DATE")
    @Comment("분석 시작일")
    private LocalDate targetFromDate;

    @Column(name = "TARGET_TO_DATE")
    @Comment("분석 종료일")
    private LocalDate targetToDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AI_CHAT_SESSION_ID")
    @Comment("연관된 AI 세션 ID (FK)")
    private AiChatSession aiChatSession;

    @Column(name = "REQUEST_USER_ID")
    @Comment("보고서 요청한 사용자")
    private Long requestUserId;

    @Column(name = "REPORT_SUMMARY", length = 1000)
    @Comment("요약")
    private String reportSummary;

    @Lob
    @Column(name = "REPORT_BODY", nullable = false)
    @Comment("보고서 본문")
    private String reportBody;

    @Column(name = "SNAPSHOT_JSON", columnDefinition = "JSON")
    @Comment("스냅샷 데이터(JSON)")
    private String snapshotJson;

    @Column(name = "CREATED_AT", nullable = false, updatable = false, columnDefinition = "DATETIME default CURRENT_TIMESTAMP")
    @Comment("생성 일시")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
