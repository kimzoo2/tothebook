package com.std.tothebook.exception;

import com.std.tothebook.exception.enums.ErrorCode;
import org.springframework.http.HttpStatus;

public class ValidateDTOException extends ExpectedException{
    public ValidateDTOException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ValidateDTOException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
