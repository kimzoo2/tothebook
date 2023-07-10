package com.std.tothebook.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * security header 인증 타입
 */
@Getter
@AllArgsConstructor
public enum AuthorizationType {
    BEARER("Bearer"),
    REFRESH_TOKEN("Refresh-token");

    private final String code;
}
