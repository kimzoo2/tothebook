package com.std.tothebook.exception;

import com.std.tothebook.exception.enums.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ExpectedException extends RuntimeException {

    private ErrorCode errorCode;
    private HttpStatus httpStatus;
    private String message;

    public ExpectedException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ExpectedException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
