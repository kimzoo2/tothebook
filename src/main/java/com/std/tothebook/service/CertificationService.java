package com.std.tothebook.service;

import com.std.tothebook.dto.SendCertificationNumberRequest;
import com.std.tothebook.dto.ValidateCertificationNumberRequest;
import com.std.tothebook.entity.Certification;
import com.std.tothebook.enums.CertificationType;
import com.std.tothebook.repository.CertificationRepository;
import com.std.tothebook.exception.CertificationException;
import com.std.tothebook.exception.enums.ErrorCode;
import com.std.tothebook.util.UserInputValidator;
import com.std.tothebook.vo.Mail;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CertificationService {

    private final int CERTIFICATION_NUMBER_LENGTH = 6;

    private final EmailSendService emailSendService;
    private final CertificationRepository certificationRepository;

    @Value("${certification.limited-minutes}")
    private int limitedMinutes;

    /**
     * 인증번호 생성 후 이메일 전송
     */
    @Transactional
    public void sendNumber(SendCertificationNumberRequest payload) throws MessagingException {
        UserInputValidator.validateEmail(payload.getEmail());

        // 인증 번호 생성
        String certificationNumber = createCertificationNumber();

        // 인증 번호 저장
        addCertificationNumber(payload, certificationNumber);

        // 메일 생성
        Mail mail = new Mail(payload.getEmail(), payload.getCertificationType());

        if (mail.getCertificationType().equals(CertificationType.SIGN_UP)
                || mail.getCertificationType().equals(CertificationType.FIND_PASSWORD)) {
            mail.replaceCertificationNumber(certificationNumber);
        }

        emailSendService.sendMail(mail);
    }

    /**
     * 인증번호 검증
     */
    public boolean validateCertificationNumber(ValidateCertificationNumberRequest payload) {
        Certification certification = certificationRepository.findFirstByEmailAndTypeOrderByCreatedAtDesc(payload.getEmail(), payload.getCertificationType())
                .orElseThrow(() -> new CertificationException(ErrorCode.CERTIFICATION_NOT_FOUND));

        if (certification.getLimitedAt().isBefore(LocalDateTime.now())) {
            throw new CertificationException(ErrorCode.CERTIFICATION_PASSED_LIMITED_TIME);
        }

        if (certification.isCompleted()) {
            throw new CertificationException(ErrorCode.CERTIFICATION_ALREADY);
        }

        if (payload.getCertificationNumber().equals(certification.getNumber())) {
            certification.completeCertification();
            certificationRepository.save(certification);

            return true;
        } else {
            return false;
        }
    }

    // 인증번호 저장
    private void addCertificationNumber(SendCertificationNumberRequest payload, String number) {
        certificationRepository.deleteAllByEmailAndType(payload.getEmail(), payload.getCertificationType());

        certificationRepository.save(Certification.create()
                .type(payload.getCertificationType())
                .email(payload.getEmail())
                .number(number)
                .limitedMinutes(limitedMinutes)
                .build());
    }

    // 인증번호 생성
    public String createCertificationNumber() {
        Random random = new Random();

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < CERTIFICATION_NUMBER_LENGTH; i++) {
            stringBuilder.append(random.nextInt(10));
        }

        return stringBuilder.toString();
    }

    // 임시 비밀번호 발급용 인증 진행 여부 체크 TODO 테스트 코드 추가
    public void checkCertificationForTemporaryPassword(String email) {
        Certification certification = certificationRepository.findForTemporaryPassword(email)
                .orElseThrow(() -> new CertificationException(ErrorCode.CERTIFICATION_NOT_FOUND));

        LocalDateTime checkDateTime = LocalDateTime.now().minusHours(1);
        if (certification.getCreatedAt().isBefore(checkDateTime)) {
            throw new CertificationException(ErrorCode.CERTIFICATION_PASSED_LIMITED_TIME);
        }
    }
}
