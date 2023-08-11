package com.std.tothebook.api.domain.dto;

import com.std.tothebook.api.enums.CertificationType;
import com.std.tothebook.exception.ValidateDTOException;
import com.std.tothebook.exception.enums.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

@Schema(description = "인증번호 검증 Request")
public class ValidateCertificationNumberRequest {

    @Schema(description = "이메일")
    private final String email;

    @Schema(description = "인증 요청 타입")
    private final String certificationType;

    @Schema(description = "인증 번호")
    private final String certificationNumber;

    public ValidateCertificationNumberRequest(String email, String certificationType, String certificationNumber) {
        if (!StringUtils.hasText(email)) {
            throw new ValidateDTOException(ErrorCode.EMAIL_VALIDATE);
        }
        CertificationType type = CertificationType.findByValue(certificationType);
        if (type == null) {
            throw new ValidateDTOException(HttpStatus.BAD_REQUEST, "인증 타입이 잘못되었습니다.");
        }
        if (!StringUtils.hasText(certificationNumber)) {
            throw new ValidateDTOException(ErrorCode.CERTIFICATION_NUMBER_VALIDATE);
        }
        this.email = email;
        this.certificationType = certificationType;
        this.certificationNumber = certificationNumber;
    }

    public String getEmail() {
        return email;
    }

    public CertificationType getCertificationType() {
        return CertificationType.findByValue(this.certificationType);
    }

    public String getCertificationNumber() {
        return certificationNumber;
    }
}
