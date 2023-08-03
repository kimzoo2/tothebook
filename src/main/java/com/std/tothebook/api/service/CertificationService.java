package com.std.tothebook.api.service;

import com.std.tothebook.api.domain.dto.SendCertificationNumberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CertificationService {

    private final int CERTIFICATION_NUMBER_LENGTH = 6;

    private final EmailSendService emailSendService;

    public void sendNumber(SendCertificationNumberRequest payload) throws MessagingException {
        String certificationNumber = createCertificationNumber();
        String text = getEmailText(certificationNumber);

        // TODO 인증번호 저장

        emailSendService.sendMail(payload.getEmail()
                , "[북쪽으로] 인증 번호 안내"
                , text);
    }

    private String createCertificationNumber() {
        Random random = new Random();

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < CERTIFICATION_NUMBER_LENGTH; i++) {
            stringBuilder.append(random.nextInt(10));
        }

        return stringBuilder.toString();
    }

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
                "<div style=\"border:1px solid darkgreen; margin:1em 0 0 0; padding: 1em;\">\n" +
                "<p>인증번호: <strong>" + certificationNumber + "</strong></p>\n" +
                "</div>\n" +
                "<br>\n" +
                "감사합니다.\n" +
                "</span>\n" +
                "</div>";
    }
}
