package com.std.tothebook.vo;

import com.std.tothebook.enums.CertificationType;
import lombok.Getter;

@Getter
public class Mail {

    // 인증 타입
    private CertificationType certificationType;

    // 수신 이메일
    private String to;

    // 메일 제목
    private String subject;

    // 메일 내용
    private String text;

    public Mail(String to, CertificationType certificationType) {
        this.to = to;
        this.certificationType = certificationType;

        setSubject();
        setContent();
    }

    // 제목 세팅
    private void setSubject() {
        if (certificationType.equals(CertificationType.SIGN_UP)) {
            this.subject = "[북쪽으로] 회원 가입 인증 번호 안내";
        } else if (certificationType.equals(CertificationType.FIND_PASSWORD)) {
            this.subject = "[북쪽으로] 비밀번호 찾기 인증 번호 안내";
        }
    }

    // 인증번호 replace
    public void replaceCertificationNumber(String certificationNumber) {
        this.text = this.text.replace("{certificationNumber}", certificationNumber);
    }

    // 본문 세팅
    private void setContent() {
        if (this.certificationType.equals(CertificationType.SIGN_UP)) {
            this.text = """
                <div style='margin:2em;'>
                <h1>북쪽으로</h1>
                <br>
                <span style="font-family: 맑은 고딕;">
                북쪽으로,
                <strong>
                <span style="color:darkgreen"> 회원 가입을 위한 이메일 인증번호</span>
                </strong>
                입니다.
                <br>
                <br>
                안녕하세요.<br>북쪽으로 입니다.<br><br>
                아래의 인증 번호를 입력해주세요.<br>
                인증 유효 시간은 5분입니다.<br>
                <div style="border:1px solid darkgreen; margin:1em 0 0 0; padding: 1em;">
                <p>인증번호: <strong>{certificationNumber}</strong></p>
                </div>
                <br>
                감사합니다.
                </span>
                </div>""";
        } else if (this.certificationType.equals(CertificationType.FIND_PASSWORD)) {
            this.text = """
                <div style='margin:2em;'>
                <h1>북쪽으로</h1>
                <br>
                <span style="font-family: 맑은 고딕;">
                북쪽으로,
                <strong>
                <span style="color:darkgreen"> 비밀번호 수정을 위한 이메일 인증번호</span>
                </strong>
                입니다.
                <br>
                <br>
                안녕하세요.<br>북쪽으로 입니다.<br><br>
                아래의 인증 번호를 입력해주세요.<br>
                인증 유효 시간은 5분입니다.<br>
                <div style="border:1px solid darkgreen; margin:1em 0 0 0; padding: 1em;">
                <p>인증번호: <strong>{certificationNumber}</strong></p>
                </div>
                <br>
                감사합니다.
                </span>
                </div>""";
        } else {
            this.text = "";
        }
    }
}