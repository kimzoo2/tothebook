package com.std.tothebook.exception;

import com.std.tothebook.exception.enums.ErrorCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
