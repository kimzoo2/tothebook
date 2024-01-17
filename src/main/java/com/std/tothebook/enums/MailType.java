package com.std.tothebook.enums;

import lombok.AllArgsConstructor;

/**
 * 메일 종류
 */
@AllArgsConstructor
public enum MailType {
    // 인증
    CERTIFICATE,

    // 임시 비밀번호 발급
    TEMPORARY_PASSWORD
}
