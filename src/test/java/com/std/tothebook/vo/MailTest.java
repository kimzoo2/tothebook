package com.std.tothebook.vo;

import com.std.tothebook.enums.CertificationType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MailTest {

    @Test
    void 비밀번호_타입에는_비밀번호_인증_메일이_생성된다() {
        Mail mail = new Mail("email@email.com", CertificationType.FIND_PASSWORD);

        assertTrue(mail.getSubject().contains("비밀번호"));
        assertTrue(mail.getText().contains("비밀번호"));
    }
}