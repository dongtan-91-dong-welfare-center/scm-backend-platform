package com.dw.scm.receipt.repository;

import com.dw.scm.receipt.entity.ReceiptLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptLineRepository extends JpaRepository<ReceiptLine, Long> {
}
