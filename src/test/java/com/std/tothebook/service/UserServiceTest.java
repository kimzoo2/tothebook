package com.std.tothebook.service;

import com.std.tothebook.dto.EditUserPasswordRequest;
import com.std.tothebook.exception.UserException;
import com.std.tothebook.exception.enums.ErrorCode;
import com.std.tothebook.repository.UserRepository;
import com.std.tothebook.util.UserInputValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @BeforeEach
    void setUp() {
        MockedStatic<UserInputValidator> mockedValidator = mockStatic(UserInputValidator.class);
        mockedValidator
                .when(() -> UserInputValidator.validateEmail(any()))
                .thenAnswer(invocation -> null);
    }

    @Test
    void errorNoUser() {
        when(userRepository.findUserByEmail("email@email.com"))
                .thenReturn(Optional.empty());

        EditUserPasswordRequest payload = new EditUserPasswordRequest("email@email.com");

        assertThrows(UserException.class,
                () -> userService.updatePasswordAndSendMail(payload),
                ErrorCode.USER_NOT_FOUND.getMessage());
    }

    @Test
    void 최근에_인증한_건이_없으면_에러() {

    }

    @Test
    void 비밀번호_정규식_체크() {

    }

}