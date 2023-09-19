package com.std.tothebook.service;

import com.std.tothebook.dto.SendCertificationNumberRequest;
import com.std.tothebook.dto.ValidateCertificationNumberRequest;
import com.std.tothebook.entity.Certification;
import com.std.tothebook.repository.CertificationRepository;
import com.std.tothebook.exception.CertificationException;
import com.std.tothebook.exception.ExpectedException;
import com.std.tothebook.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class CertificationService {

    private final int CERTIFICATION_NUMBER_LENGTH = 6;
    private final String REGEX_EMAIL = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";

    private final Pattern emailPattern = Pattern.compile(REGEX_EMAIL);

    private final EmailSendService emailSendService;
    private final CertificationRepository certificationRepository;

    @Value("${certification.limited-minutes}")
    private int limitedMinutes;

    /**
     * 인증번호 생성 후 이메일 전송
     */
    @Transactional
    public void sendNumber(SendCertificationNumberRequest payload) throws MessagingException {
        validateForSendNumber(payload);

        String certificationNumber = createCertificationNumber();

        addCertificationNumber(payload, certificationNumber);

        emailSendService.sendMail(payload.getEmail()
                , "[북쪽으로] 인증 번호 안내"
                , getEmailText(certificationNumber));
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

    // 인증번호 생성 전 검증
    private void validateForSendNumber(SendCertificationNumberRequest payload) {
        Matcher matcher = emailPattern.matcher(payload.getEmail());
        if (!matcher.matches()) {
            throw new ExpectedException(ErrorCode.REGULAR_EXPRESSION_EMAIL);
        }
    }

    // 인증번호 생성
    private String createCertificationNumber() {
        Random random = new Random();

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < CERTIFICATION_NUMBER_LENGTH; i++) {
            stringBuilder.append(random.nextInt(10));
        }

        return stringBuilder.toString();
    }

    // 인증번호 전송 메일 본문 생성
    private String getEmailText(String certificationNumber) {
        return "<div style='margin:2em;'>\n" +
                "<h1>북쪽으로</h1>\n" +
                "<br>\n" +
                "<span style=\"font-family: 맑은 고딕;\">\n" +
                "북쪽으로,\n" +
                "<strong>\n" +
                "<span style=\"color:darkgreen\"> 회원 가입을 위한 이메일 인증번호</span>\n" +
                "</strong>\n" +
                "입니다.\n" +
                "<br>\n" +
                "<br>\n" +
                "안녕하세요.<br>북쪽으로 입니다.<br><br>\n" +
                "아래의 인증 번호를 입력해주세요.<br>\n" +
                "인증 유효 시간은 5분입니다.<br>\n" +
                "<div style=\"border:1px solid darkgreen; margin:1em 0 0 0; padding: 1em;\">\n" +
                "<p>인증번호: <strong>" + certificationNumber + "</strong></p>\n" +
                "</div>\n" +
                "<br>\n" +
                "감사합니다.\n" +
                "</span>\n" +
                "</div>";
    }
}
