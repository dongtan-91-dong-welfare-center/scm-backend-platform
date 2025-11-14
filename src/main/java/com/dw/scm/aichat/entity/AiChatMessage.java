package com.dw.scm.aichat.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Comment("AI 대화 메시지 로그 테이블")
public class AiChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    @Comment("메시지 ID (PK)")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SESSION_ID", nullable = false)
    @Comment("세션 ID (FK)")
    private AiChatSession session;

    @Enumerated(EnumType.STRING)
    @Column(name = "SENDER_TYPE", length = 20, nullable = false)
    @Comment("발신자(USER, ASSISTANT, SYSTEM)")
    private SenderType senderType;

    @Column(name = "MESSAGE_ROLE", length = 20)
    @Comment("LLM 역할(user/assistant/system)")
    private String messageRole;

    @Lob
    @Column(name = "CONTENT", nullable = false)
    @Comment("메시지 원문")
    private String content;

    @Lob
    @Column(name = "MASKED_CONTENT")
    @Comment("마스킹된 메시지")
    private String maskedContent;

    @Column(name = "TOKENS_INPUT")
    @Comment("입력 토큰 수")
    private Integer tokensInput;

    @Column(name = "TOKENS_OUTPUT")
    @Comment("출력 토큰 수")
    private Integer tokensOutput;

    @Column(name = "IS_ERROR", nullable = false, columnDefinition = "TINYINT(1) default 0")
    @Comment("에러 여부")
    private boolean isError = false;

    @Column(name = "ERROR_CODE", length = 100)
    @Comment("에러 코드")
    private String errorCode;

    @Column(name = "META_JSON", columnDefinition = "JSON")
    @Comment("메타정보(JSON)")
    private String metaJson;

    @Column(name = "CREATED_AT", nullable = false, updatable = false, columnDefinition = "DATETIME default CURRENT_TIMESTAMP")
    @Comment("생성 일시")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
