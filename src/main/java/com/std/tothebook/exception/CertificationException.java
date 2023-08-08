package com.std.tothebook.exception;

import com.std.tothebook.exception.enums.ErrorCode;
import org.springframework.http.HttpStatus;

public class CertificationException extends ExpectedException{
    public CertificationException(ErrorCode errorCode) {
        super(errorCode);
    }

    public CertificationException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
