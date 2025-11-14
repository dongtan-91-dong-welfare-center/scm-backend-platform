package com.dw.scm.aichat.repository;

import com.dw.scm.aichat.entity.AiChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AiChatMessageRepository extends JpaRepository<AiChatMessage, Long> {
}
