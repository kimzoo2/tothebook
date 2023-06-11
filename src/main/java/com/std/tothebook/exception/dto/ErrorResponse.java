package com.std.tothebook.exception.dto;

import com.std.tothebook.exception.CustomException;
import com.std.tothebook.exception.enums.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
@RequiredArgsConstructor
public class ErrorResponse {

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    public ErrorResponse(ErrorCode errorCode) {
        this.httpStatus = errorCode.getHttpStatus();
        this.code = errorCode.name();
        this.message = errorCode.getMessage();
    }

    public static ResponseEntity<ErrorResponse> error(CustomException e) {
        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(ErrorResponse.builder()
                        .httpStatus(e.getErrorCode().getHttpStatus())
                        .code(e.getErrorCode().name())
                        .message(e.getErrorCode().getMessage())
                        .build());
    }
}
