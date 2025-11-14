package com.dw.scm.aichat.repository;

import com.dw.scm.aichat.entity.AiReportHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AiReportHistoryRepository extends JpaRepository<AiReportHistory, Long> {
}
