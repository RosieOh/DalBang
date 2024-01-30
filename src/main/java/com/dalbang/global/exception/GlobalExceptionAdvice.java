package com.dalbang.global.exception;

import com.dalbang.global.dto.ErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 장다슬
 */
@RestControllerAdvice
public class GlobalExceptionAdvice {
    /**
     * '@Valid' 유효성 검증 예외 처리
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> validErrors = new HashMap<>();

        ex.getBindingResult().getAllErrors()
                .forEach(c -> validErrors.put(((FieldError) c).getField(), c.getDefaultMessage()));

        ErrorResponseDto error = ErrorResponseDto.builder()
                .status(ex.getStatusCode().value())
                .message(ex.getMessage())
                .valid(validErrors)
                .build();

        return ResponseEntity.badRequest().body(error);
    }
}
