package com.dw.scm.purchaseorder.entity;

import lombok.Getter;

@Getter
public enum OrderStatus {
    OPEN("주문 생성"),
    PARTIAL("부분 입고"),
    CLOSED("입고 완료"),
    CANCELED("주문 취소");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }
}
