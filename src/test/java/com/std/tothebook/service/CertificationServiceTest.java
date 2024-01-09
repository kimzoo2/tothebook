package com.std.tothebook.service;

import com.std.tothebook.entity.Certification;
import com.std.tothebook.exception.CertificationException;
import com.std.tothebook.exception.enums.ErrorCode;
import com.std.tothebook.repository.CertificationRepository;
import com.std.tothebook.util.UserInputValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CertificationServiceTest {

    @InjectMocks
    CertificationService certificationService;

    @Mock
    CertificationRepository certificationRepository;

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
        try (MockedStatic<UserInputValidator> mockedValidator = mockStatic(UserInputValidator.class)) {
            mockedValidator
                    .when(() -> UserInputValidator.validateEmail(any()))
                    .thenAnswer(invocation -> null);

            int i = Integer.parseInt(certificationService.createCertificationNumber());
        }
    }

    @DisplayName("인증 완료한 건이 없으면 에러")
    @Test
    void noCertification() {
        when(certificationRepository.findForTemporaryPassword(any()))
                .thenReturn(Optional.empty());

        assertThrows(CertificationException.class,
                () -> certificationService.checkCertificationForTemporaryPassword(""),
                ErrorCode.CERTIFICATION_NOT_FOUND.getMessage());
    }

    @DisplayName("1시간 이내 인증 완료한 건이 없으면 에러")
    @Test
    void checkCreatedAt() {
        Certification mockedCertification = mock(Certification.class);

        when(mockedCertification.getCreatedAt())
                .thenReturn(LocalDateTime.now().minusHours(2));

        when(certificationRepository.findForTemporaryPassword(any()))
                .thenReturn(Optional.of(mockedCertification));

        assertThrows(CertificationException.class,
                () -> certificationService.checkCertificationForTemporaryPassword(""),
                ErrorCode.CERTIFICATION_PASSED_LIMITED_TIME.getMessage());
    }
}