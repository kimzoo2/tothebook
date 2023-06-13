package com.std.tothebook.exception;

import com.std.tothebook.exception.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExpectedException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(ExpectedException e) {
        // TODO log
        System.out.println(e.toString());
        return StringUtils.hasText(e.getMessage()) ? ErrorResponse.errorWithMessage(e) : ErrorResponse.error(e);
    }
}
