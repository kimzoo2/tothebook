package com.std.tothebook.util;

import com.std.tothebook.exception.UserException;
import com.std.tothebook.exception.ValidateDTOException;
import com.std.tothebook.exception.enums.ErrorCode;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserInputValidatorTest {
//    static {
//        System.setProperty("jasypt.encryptor.password", "seventodevelop");
//    }

    @Test
    void errorNoEmail() {
        assertThrows(ValidateDTOException.class,
                () -> UserInputValidator.validateEmail(null),
                ErrorCode.EMAIL_VALIDATE.getMessage());
    }

    @Test
    void errorNotMatch() {
        assertThrows(UserException.class,
                () -> UserInputValidator.validateEmail("abc"),
                ErrorCode.REGULAR_EXPRESSION_EMAIL.getMessage());
    }

}