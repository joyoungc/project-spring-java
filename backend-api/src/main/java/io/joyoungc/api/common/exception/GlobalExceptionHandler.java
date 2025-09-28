package io.joyoungc.api.common.exception;

import io.joyoungc.application.exception.ApplicationException;
import io.joyoungc.domain.enums.CommonError;
import io.joyoungc.domain.enums.ResponseCode;
import io.joyoungc.domain.model.common.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse> handleValidationException(MethodArgumentNotValidException e) {
        log.warn("Validation error occurred: {}", e.getMessage());
        
        String errorMessage = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("유효성 검증 실패");
        
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(CommonResponse.of(ResponseCode.FAILED, errorMessage));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CommonResponse> handleConstraintViolationException(ConstraintViolationException e) {
        log.warn("Constraint violation occurred: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(CommonResponse.of(ResponseCode.FAILED, "유효성 검증 실패"));
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<CommonResponse> handleApplicationException(ApplicationException e) {
        log.error("ApplicationException occurred: {}", e.getMessage(), e);
        
        // COMMON_NOT_FOUND 에러는 404로 처리
        if (e.getMessage() != null && e.getMessage().contains(CommonError.COMMON_NOT_FOUND.getCode())) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(CommonResponse.of(ResponseCode.FAILED, e.getMessage()));
        }
        
        // 기타 ApplicationException은 500으로 처리
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CommonResponse.of(ResponseCode.FAILED, e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse> handleException(Exception e) {
        log.error("Unexpected exception occurred: {}", e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CommonResponse.of(ResponseCode.FAILED, "서버 오류가 발생했습니다."));
    }
}