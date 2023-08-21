package com.std.tothebook.api.domain.dto;

import com.std.tothebook.exception.ValidateDTOException;
import com.std.tothebook.exception.enums.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
@Schema(description = "회원 가입 Request")
public class SignUpRequest {

    @Schema(description = "이메일")
    private final String email;

    @Schema(description = "닉네임")
    private final String nickname;

    @Schema(description = "패스워드")
    private final String password;

    public SignUpRequest(String email, String nickname, String password) {
        if (!StringUtils.hasText(email)) {
            throw new ValidateDTOException(ErrorCode.EMAIL_VALIDATE);
        }
        if (!StringUtils.hasText(nickname)) {
            throw new ValidateDTOException(ErrorCode.NICKNAME_VALIDATE);
        }
        if (!StringUtils.hasText(password)) {
            throw new ValidateDTOException(ErrorCode.PASSWORD_VALIDATE);
        }
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }
}
