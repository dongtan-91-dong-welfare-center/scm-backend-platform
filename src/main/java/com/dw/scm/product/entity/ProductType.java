package com.dw.scm.product.entity;

import lombok.Getter;

@Getter
public enum ProductType {
    RAW("원료"),
    INTERM("반제품"),
    MATERIAL("자재"),
    FINISHED("완제품");

    private final String description;

    ProductType(String description) {
        this.description = description;
    }
}
