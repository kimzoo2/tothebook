package com.std.tothebook.dto;

import com.std.tothebook.exception.ValidateDTOException;
import com.std.tothebook.exception.enums.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "로그인 Request")
public class SignInRequest {

    @Schema(description = "이메일")
    private final String email;

    @Schema(description = "비밀번호")
    private final String password;

    public SignInRequest(String email, String password) {
        if (email == null || email.isEmpty()) {
            throw new ValidateDTOException(ErrorCode.EMAIL_VALIDATE);
        }
        if (password == null || password.isEmpty()) {
            throw new ValidateDTOException(ErrorCode.PASSWORD_VALIDATE);
        }

        this.email = email;
        this.password = password;
    }
}
