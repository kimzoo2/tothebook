package com.std.tothebook.util;

import com.std.tothebook.config.RegexProperty;
import com.std.tothebook.exception.UserException;
import com.std.tothebook.exception.ValidateDTOException;
import com.std.tothebook.exception.enums.ErrorCode;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
public class UserInputValidator {

    /**
     * 이메일 검증
     */
    public static void validateEmail(String email) {
        if (!StringUtils.hasText(email)) {
            throw new ValidateDTOException(ErrorCode.EMAIL_VALIDATE);
        }

        Pattern pattern = Pattern.compile(RegexProperty.getEmail());
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new UserException(ErrorCode.REGULAR_EXPRESSION_EMAIL);
        }
    }

    /**
     * 비밀번호 검증
     */
    public static void validatePassword(String password) {
        if (!StringUtils.hasText(password)) {
            throw new ValidateDTOException(ErrorCode.PASSWORD_VALIDATE);
        }

        Pattern pattern = Pattern.compile(RegexProperty.getPassword());
        Matcher matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            throw new UserException(ErrorCode.REGULAR_EXPRESSION_PASSWORD);
        }
    }
}
