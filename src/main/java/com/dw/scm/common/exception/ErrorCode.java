package com.dw.scm.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // Common
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C001", "서버 내부 오류가 발생했습니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C002", "유효하지 않은 입력 값입니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "C003", "허용되지 않은 HTTP 메서드입니다."),
    HANDLE_ACCESS_DENIED(HttpStatus.FORBIDDEN, "C004", "접근 권한이 없습니다."),

    // File Storage
    FILE_STORAGE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "F001", "파일 저장에 실패했습니다."),
    INVALID_FILE_PATH(HttpStatus.BAD_REQUEST, "F002", "파일 이름에 부적절한 경로가 포함되어 있습니다."),
    DIRECTORY_CREATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "F003", "파일을 업로드할 디렉토리를 생성할 수 없습니다."),

    // Excel Parsing
    EXCEL_PARSING_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "E001", "엑셀 파일 파싱 중 오류가 발생했습니다.");


    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
