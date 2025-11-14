package com.dw.scm.aichat.repository;

import com.dw.scm.aichat.entity.AiChatAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AiChatAttachmentRepository extends JpaRepository<AiChatAttachment, Long> {
}
