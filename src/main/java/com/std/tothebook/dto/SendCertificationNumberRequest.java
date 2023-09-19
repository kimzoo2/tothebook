package com.std.tothebook.dto;

import com.std.tothebook.enums.CertificationType;
import com.std.tothebook.exception.ValidateDTOException;
import com.std.tothebook.exception.enums.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

@Schema(description = "인증번호 전송 Request")
public class SendCertificationNumberRequest {

    @Schema(description = "이메일")
    private final String email;

    @Schema(description = "인증 요청 타입")
    private final String certificationType;

    public SendCertificationNumberRequest(String email, String certificationType) {
        if (!StringUtils.hasText(email)) {
            throw new ValidateDTOException(ErrorCode.EMAIL_VALIDATE);
        }
        this.email = email;

        CertificationType type = CertificationType.findByValue(certificationType);
        if (type == null) {
            throw new ValidateDTOException(HttpStatus.BAD_REQUEST, "인증 타입이 잘못되었습니다.");
        }
        this.certificationType = certificationType;
    }

    public String getEmail() {
        return email;
    }

    public CertificationType getCertificationType() {
        return CertificationType.findByValue(this.certificationType);
    }
}
