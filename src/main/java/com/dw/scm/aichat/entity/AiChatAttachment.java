package com.dw.scm.aichat.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Comment("AI 대화 첨부파일 테이블")
public class AiChatAttachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    @Comment("AI 첨부파일 ID (PK)")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SESSION_ID", nullable = false)
    @Comment("세션 ID (FK)")
    private AiChatSession session;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MESSAGE_ID")
    @Comment("메시지 ID (FK)")
    private AiChatMessage message;

    @Column(name = "FILE_NAME", length = 255, nullable = false)
    @Comment("파일명")
    private String fileName;

    @Column(name = "FILE_PATH", length = 500, nullable = false)
    @Comment("파일 경로")
    private String filePath;

    @Column(name = "FILE_TYPE", length = 50, nullable = false)
    @Comment("파일 타입")
    private String fileType;

    @Column(name = "FILE_SIZE")
    @Comment("파일 크기")
    private Long fileSize;

    @Column(name = "DESCRIPTION", length = 500)
    @Comment("설명")
    private String description;

    @Column(name = "META_JSON", columnDefinition = "JSON")
    @Comment("메타정보(JSON)")
    private String metaJson;

    @Column(name = "CREATED_AT", nullable = false, updatable = false, columnDefinition = "DATETIME default CURRENT_TIMESTAMP")
    @Comment("등록일")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
