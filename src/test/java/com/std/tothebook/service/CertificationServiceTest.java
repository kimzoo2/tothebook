package com.std.tothebook.service;

import com.std.tothebook.util.UserInputValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class CertificationServiceTest {

    @InjectMocks
    CertificationService certificationService;

    @DisplayName("인증번호는 6자리여야 한다")
    @Test
    void 인증번호_생성_확인() {
        try (MockedStatic<UserInputValidator> mockedValidator = mockStatic(UserInputValidator.class)) {
            mockedValidator
                    .when(() -> UserInputValidator.validateEmail(any()))
                    .thenAnswer(invocation -> null);

            assertEquals(6, certificationService.createCertificationNumber().length());
        }
    }

    @DisplayName("인증번호는 모두 숫자여야 한다")
    @Test
    void 인증번호_숫자_확인() {

    }

}