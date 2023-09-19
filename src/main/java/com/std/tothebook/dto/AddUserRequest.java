package com.std.tothebook.dto;

import com.std.tothebook.exception.ValidateDTOException;
import com.std.tothebook.exception.enums.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "회원 생성 Request")
public class AddUserRequest {

    @Schema(description = "이메일")
    private final String email;

    @Schema(description = "비밀번호")
    private final String password;

    @Schema(description = "닉네임")
    private final String nickname;

    public AddUserRequest (String email, String password, String nickname) {
        if (email == null || email.isEmpty()) {
            throw new ValidateDTOException(ErrorCode.EMAIL_VALIDATE);
        }
        if (password == null || password.isEmpty()) {
            throw new ValidateDTOException(ErrorCode.PASSWORD_VALIDATE);
        }
        if (nickname == null || nickname.isEmpty()) {
            throw new ValidateDTOException(ErrorCode.NICKNAME_VALIDATE);
        }

        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }
}
