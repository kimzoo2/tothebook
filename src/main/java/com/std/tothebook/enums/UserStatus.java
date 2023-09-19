package com.std.tothebook.enums;

import lombok.AllArgsConstructor;

import java.util.Arrays;

/**
 * 회원 상태
 */
@AllArgsConstructor
public enum UserStatus {
    JOIN("가입"),
    WITHDRAWAL("탈퇴"),
    NONE("");

    private final String description;

    public static UserStatus findByCode(String code) {
        return Arrays.stream(UserStatus.values())
                .filter(userStatus -> userStatus.name().equals(code))
                .findFirst()
                .orElse(NONE);
    }
}
