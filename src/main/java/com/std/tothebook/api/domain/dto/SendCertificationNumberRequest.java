package com.std.tothebook.api.domain.dto;

import com.std.tothebook.exception.ValidateDTOException;
import com.std.tothebook.exception.enums.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "인증번호 전송 Request")
public class SendCertificationNumberRequest {

    @Schema(description = "이메일")
    private String email;

    public SendCertificationNumberRequest(String email) {
        this.email = email;
    }

    // TODO 생성자 validation 체크 추가 예정
//    public SendCertificationNumberRequest(String email) {
//        if (email == null || email.isEmpty()) {
//            throw new ValidateDTOException(ErrorCode.EMAIL_VALIDATE);
//        }
//        this.email = email;
//    }
}
