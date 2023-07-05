package com.std.tothebook.api.enums;

import lombok.AllArgsConstructor;

import java.util.Arrays;

/**
 * 독서 상태
 */
@AllArgsConstructor
public enum MyBookStatus {

    ONGOING("독서중"),
    COMPLETE("완독"),
    NONE("");

    private final String description;

    public static MyBookStatus findByCode(String code) {
        return Arrays.stream(MyBookStatus.values())
                .filter(myBookStatus -> myBookStatus.name().equals(code))
                .findFirst()
                .orElse(NONE);
    }
}
