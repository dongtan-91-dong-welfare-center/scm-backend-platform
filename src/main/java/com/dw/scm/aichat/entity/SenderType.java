package com.dw.scm.aichat.entity;

import lombok.Getter;

@Getter
public enum SenderType {
    USER("사용자"),
    ASSISTANT("어시스턴트"),
    SYSTEM("시스템");

    private final String description;

    SenderType(String description) {
        this.description = description;
    }
}
