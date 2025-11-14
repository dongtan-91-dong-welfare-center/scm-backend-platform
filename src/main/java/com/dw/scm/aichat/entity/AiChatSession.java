package com.dw.scm.aichat.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Comment("AI 대화 세션 관리 테이블")
public class AiChatSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    @Comment("AI 대화 세션 ID (PK)")
    private Long id;

    @Column(name = "USER_ID")
    @Comment("사용자 ID")
    private Long userId;

    @Column(name = "SESSION_TITLE", length = 200)
    @Comment("세션 제목")
    private String sessionTitle;

    @Column(name = "STATUS", length = 20, nullable = false, columnDefinition = "VARCHAR(20) default 'ACTIVE'")
    @Comment("세션 상태")
    private String status = "ACTIVE";

    @Column(name = "STARTED_AT", nullable = false, updatable = false, columnDefinition = "DATETIME default CURRENT_TIMESTAMP")
    @Comment("대화 시작일")
    private LocalDateTime startedAt;

    @Column(name = "ENDED_AT")
    @Comment("대화 종료일")
    private LocalDateTime endedAt;

    @Column(name = "LAST_MESSAGE_AT")
    @Comment("마지막 메시지 시각")
    private LocalDateTime lastMessageAt;

    @Column(name = "META_JSON", columnDefinition = "JSON")
    @Comment("세션 메타정보(JSON)")
    private String metaJson;

    @PrePersist
    protected void onCreate() {
        startedAt = LocalDateTime.now();
    }
}
