package com.std.tothebook.exception;

import com.std.tothebook.exception.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExpectedException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(ExpectedException e) {
        this.log(e);
        return StringUtils.hasText(e.getMessage()) ? ErrorResponse.errorWithMessage(e) : ErrorResponse.error(e);
    }

    private void log(ExpectedException e) {
        log.error("{} : {}", e.toString(), e.getErrorCode() == null ? e.getMessage() : e.getErrorCode().getMessage());
    }
}
