package com.dw.scm.common;

import com.dw.scm.common.exception.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private final boolean success;
    private final T data;
    private final ErrorResponse error;

    // 성공 시 생성자
    private ApiResponse(T data) {
        this.success = true;
        this.data = data;
        this.error = null;
    }

    // 실패 시 생성자 (BusinessException 용)
    private ApiResponse(ErrorCode errorCode) {
        this.success = false;
        this.data = null;
        this.error = new ErrorResponse(errorCode);
    }

    // 실패 시 생성자 (Validation Error 용)
    private ApiResponse(ErrorCode errorCode, List<FieldError> fieldErrors) {
        this.success = false;
        this.data = null;
        this.error = new ErrorResponse(errorCode, fieldErrors);
    }

    // 정적 팩토리 메서드
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(data);
    }

    public static ApiResponse<?> error(ErrorCode errorCode) {
        return new ApiResponse<>(errorCode);
    }

    public static ApiResponse<?> error(ErrorCode errorCode, BindingResult bindingResult) {
        return new ApiResponse<>(errorCode, FieldError.of(bindingResult));
    }

    @Getter
    private static class ErrorResponse {
        private final int status;
        private final String code;
        private final String message;
        private final List<FieldError> errors;

        ErrorResponse(ErrorCode errorCode) {
            this.status = errorCode.getStatus().value();
            this.code = errorCode.getCode();
            this.message = errorCode.getMessage();
            this.errors = List.of();
        }

        ErrorResponse(ErrorCode errorCode, List<FieldError> errors) {
            this.status = errorCode.getStatus().value();
            this.code = errorCode.getCode();
            this.message = errorCode.getMessage();
            this.errors = errors;
        }
    }

    @Getter
    public static class FieldError {
        private final String field;
        private final String value;
        private final String reason;

        private FieldError(String field, String value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        public static List<FieldError> of(BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()))
                    .collect(Collectors.toList());
        }
    }
}
