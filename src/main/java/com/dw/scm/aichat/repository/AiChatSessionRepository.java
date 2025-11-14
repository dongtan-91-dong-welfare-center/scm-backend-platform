package com.dw.scm.aichat.repository;

import com.dw.scm.aichat.entity.AiChatSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AiChatSessionRepository extends JpaRepository<AiChatSession, Long> {
}
