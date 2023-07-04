package com.std.tothebook.exception;

import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public class JwtAuthenticationException extends AuthenticationException {
    public JwtAuthenticationException(String message) {
        super(message);
    }

    public static JwtAuthenticationException create(String message) {
        return new JwtAuthenticationException(message);
    }
}
