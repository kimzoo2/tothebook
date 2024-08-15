package com.std.tothebook.dto;

import com.std.tothebook.exception.ValidateDTOException;
import com.std.tothebook.exception.enums.ErrorCode;
import lombok.Getter;

@Getter
public class EditUserPasswordRequest {

    private final Long userId;

    private final String password;

    private final String newPassword;

    public EditUserPasswordRequest(Long userId, String password, String newPassword) {
        if (userId == null) {
            throw new ValidateDTOException(ErrorCode.USER_NOT_FOUND);
        }
        if (password == null || password.isEmpty()) {
            throw new ValidateDTOException(ErrorCode.PASSWORD_VALIDATE);
        }
        if (newPassword == null || newPassword.isEmpty()) {
            throw new ValidateDTOException(ErrorCode.NEW_PASSWORD_VALIDATE);
        }

        this.userId = userId;
        this.password = password;
        this.newPassword = newPassword;
    }
}
