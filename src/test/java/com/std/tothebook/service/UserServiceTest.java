package com.std.tothebook.service;

import com.std.tothebook.dto.EditUserPasswordRequest;
import com.std.tothebook.exception.UserException;
import com.std.tothebook.exception.enums.ErrorCode;
import com.std.tothebook.repository.UserRepository;
import com.std.tothebook.util.UserInputValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    void errorNoUser() {
        when(userRepository.findUserByEmail("email@email.com"))
                .thenReturn(Optional.empty());

        EditUserPasswordRequest payload = new EditUserPasswordRequest("email@email.com");

        // try-with-resources
        try (MockedStatic<UserInputValidator> mockedValidator = mockStatic(UserInputValidator.class)) {
            mockedValidator
                    .when(() -> UserInputValidator.validateEmail(any()))
                    .thenAnswer(invocation -> null);

            assertThrows(UserException.class,
                    () -> userService.updatePasswordAndSendMail(payload),
                    ErrorCode.USER_NOT_FOUND.getMessage());
        }
    }

    @DisplayName("비밀번호 파트 일부는 정규식을 통과해야 한다")
    @Test
    void passwordInitialPart_must_validated() {
        String initialPart = userService.generatePasswordInitialPart();

        assertTrue(
                Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*#?&]).{4,}$")
                        .matcher(initialPart)
                        .matches()
        );
    }

    @DisplayName("임시 비밀번호는 정규식을 통과해야 한다")
    @Test
    void temporaryPassword_must_validated() {
        String randomPassword = userService.generateRandomPassword(10);

        assertTrue(
                Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*#?&]).{10,}$")
                        .matcher(randomPassword)
                        .matches()
        );
    }

}