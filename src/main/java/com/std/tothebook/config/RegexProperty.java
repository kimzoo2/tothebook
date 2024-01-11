package com.std.tothebook.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "regex")
public class RegexProperty {

    @Getter
    private static String email;

    @Getter
    private static String password;

    public void setEmail(String email) {
        RegexProperty.email = email;
    }

    public void setPassword(String password) {
        RegexProperty.password = password;
    }
}
