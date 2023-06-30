package com.std.tothebook.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class JsonWebToken {

    private String grantType;
    private String accessToken;
    private String refreshToken;

    protected JsonWebToken() {}
}
