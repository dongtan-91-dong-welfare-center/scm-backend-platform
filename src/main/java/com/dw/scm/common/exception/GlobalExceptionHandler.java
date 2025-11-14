package com.dw.scm.common.exception;

import com.dw.scm.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * @Valid 어노테이션을 사용한 유효성 검증 실패 시 발생하는 예외를 처리합니다.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn("handleMethodArgumentNotValidException: {}", e.getMessage());
        final ErrorCode errorCode = ErrorCode.INVALID_INPUT_VALUE;
        final ApiResponse<?> errorResponse = ApiResponse.error(errorCode, e.getBindingResult());
        return new ResponseEntity<>(errorResponse, errorCode.getStatus());
    }

    /**
     * 부적절한 인자 값으로 인해 발생하는 예외를 처리합니다.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ApiResponse<?>> handleIllegalArgumentException(IllegalArgumentException e) {
        log.warn("handleIllegalArgumentException: {}", e.getMessage());
        final ErrorCode errorCode = ErrorCode.INVALID_INPUT_VALUE;
        final ApiResponse<?> errorResponse = ApiResponse.error(errorCode);
        return new ResponseEntity<>(errorResponse, errorCode.getStatus());
    }

    /**
     * 지원하지 않는 HTTP 메서드로 요청 시 발생하는 예외를 처리합니다.
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ApiResponse<?>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.warn("handleHttpRequestMethodNotSupportedException: {}", e.getMessage());
        final ErrorCode errorCode = ErrorCode.METHOD_NOT_ALLOWED;
        final ApiResponse<?> errorResponse = ApiResponse.error(errorCode);
        return new ResponseEntity<>(errorResponse, errorCode.getStatus());
    }

    /**
     * 직접 정의한 비즈니스 예외를 처리합니다.
     */
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ApiResponse<?>> handleBusinessException(BusinessException e) {
        log.error("handleBusinessException: {}", e.getMessage(), e);
        final ErrorCode errorCode = e.getErrorCode();
        final ApiResponse<?> errorResponse = ApiResponse.error(errorCode);
        return new ResponseEntity<>(errorResponse, errorCode.getStatus());
    }

    /**
     * 처리되지 않은 모든 예외(NPE 포함)를 처리하여 서버 오류로 응답합니다.
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApiResponse<?>> handleException(Exception e) {
        log.error("Unhandled Exception: {}", e.getMessage(), e);
        final ApiResponse<?> errorResponse = ApiResponse.error(ErrorCode.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
