package com.std.tothebook.service;

import com.std.tothebook.dto.EditUserTemporaryPasswordRequest;
import com.std.tothebook.dto.CreateUserTemporaryPasswordRequest;
import com.std.tothebook.entity.User;
import com.std.tothebook.exception.UserException;
import com.std.tothebook.exception.enums.ErrorCode;
import com.std.tothebook.repository.UserRepository;
import com.std.tothebook.util.UserInputValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
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

        CreateUserTemporaryPasswordRequest payload = new CreateUserTemporaryPasswordRequest("email@email.com");

        // try-with-resources
        try (MockedStatic<UserInputValidator> mockedValidator = mockStatic(UserInputValidator.class)) {
            mockedValidator
                    .when(() -> UserInputValidator.validateEmail(any()))
                    .thenAnswer(invocation -> null);

            assertThrows(UserException.class,
                    () -> userService.createTemporaryPasswordAndSendMail(payload),
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

    @DisplayName("임시 비밀번호 수정 시 회원은 임시 비밀번호 발급 상태여야 한다")
    @Test
    void updateTemporaryPassword_needs_temporaryStatus() {
        User user = User.create()
                .email("test@test.com")
                .password("1234")
                .nickname("test")
                .build();

        when(userRepository.findById(any()))
                .thenReturn(Optional.of(user));

        EditUserTemporaryPasswordRequest request = new EditUserTemporaryPasswordRequest(1L, "", "");

        try (MockedStatic<UserInputValidator> mockedValidator = mockStatic(UserInputValidator.class)) {
            mockedValidator
                    .when(() -> UserInputValidator.validateEmail(any()))
                    .thenAnswer(invocation -> null);

            assertThrows(UserException.class,
                    () -> userService.updatePassword(request),
                    ErrorCode.NOT_TEMPORARY_PASSWORD.getMessage());
        }
    }
}