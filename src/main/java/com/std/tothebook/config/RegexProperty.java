package com.std.tothebook.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "regex")
public class RegexProperty {

    private static String email;

    public static String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        RegexProperty.email = email;
    }
}
