package com.std.tothebook.exception;

import com.std.tothebook.exception.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(JwtAuthenticationException e) {
        log.error("{} : {}", e.toString(), e.getMessage());

        // ExpectedException을 상속 받지 않기 때문에 아래와 같이 구현
        final var response = new ErrorResponse(HttpStatus.UNAUTHORIZED, "", e.getMessage());

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(response);
    }

    private void log(ExpectedException e) {
        log.error("{} : {}", e.toString(), e.getErrorCode() == null ? e.getMessage() : e.getErrorCode().getMessage());
    }
}
