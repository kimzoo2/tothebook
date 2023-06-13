package com.std.tothebook.exception;

import com.std.tothebook.exception.enums.ErrorCode;
import org.springframework.http.HttpStatus;

public class UserException extends ExpectedException{
    public UserException(ErrorCode errorCode) {
        super(errorCode);
    }

    public UserException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
