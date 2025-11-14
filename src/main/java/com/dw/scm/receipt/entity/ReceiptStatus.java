package com.dw.scm.receipt.entity;

import lombok.Getter;

@Getter
public enum ReceiptStatus {
    CONFIRMED("입고 확정"),
    CANCELED("입고 취소");

    private final String description;

    ReceiptStatus(String description) {
        this.description = description;
    }
}
