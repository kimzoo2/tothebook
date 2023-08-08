package com.std.tothebook.api.enums;

import lombok.AllArgsConstructor;

import java.util.Arrays;

/**
 * 인증 요청 타입
 */
@AllArgsConstructor
public enum CertificationType {
    SIGN_UP("가입"),
    FIND_PASSWORD("비밀번호 찾기");

    private final String description;

    public static CertificationType findByValue(String value) {
        return Arrays.stream(CertificationType.values())
                .filter(certificationType -> certificationType.name().equals(value))
                .findFirst()
                .orElse(null);
    }
}
