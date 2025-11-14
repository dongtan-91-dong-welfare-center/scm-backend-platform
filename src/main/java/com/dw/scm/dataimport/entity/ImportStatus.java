package com.dw.scm.dataimport.entity;

import lombok.Getter;

@Getter
public enum ImportStatus {
    // 파일 업로드 및 처리 과정 상태
    UPLOADED("파일 업로드 완료"),
    PARSING("파일 분석 중"),
    PARSED("파일 분석 완료"),
    VALIDATING("데이터 검증 중"),
    VALIDATED("데이터 검증 완료"),
    IMPORTING("데이터 반영 중"),
    COMPLETED("가져오기 완료"),
    FAILED("실패"),

    // 행(Row) 데이터의 상태
    RAW("원본"),
    VALID("검증 성공"),
    INVALID("검증 실패"),
    IMPORTED("반영 완료");

    private final String description;

    ImportStatus(String description) {
        this.description = description;
    }
}
