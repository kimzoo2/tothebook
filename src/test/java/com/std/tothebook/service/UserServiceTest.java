package com.std.tothebook.service;

import com.std.tothebook.dto.EditUserPasswordRequest;
import com.std.tothebook.exception.UserException;
import com.std.tothebook.exception.ValidateDTOException;
import com.std.tothebook.exception.enums.ErrorCode;
import com.std.tothebook.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    void errorNoEmailValue() {
        assertThrows(ValidateDTOException.class,
                () -> userService.validateEmail(null));
    }

    @Test
    void errorWrongEmailRegex() {
        assertThrows(UserException.class,
                () -> userService.validateEmail("1234"),
                ErrorCode.REGULAR_EXPRESSION_EMAIL.getMessage());

        userService.validateEmail("abc@abc.com");
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