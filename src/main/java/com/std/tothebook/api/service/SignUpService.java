package com.std.tothebook.api.service;

import com.std.tothebook.api.domain.dto.SignUpRequest;
import com.std.tothebook.api.entity.User;
import com.std.tothebook.api.repository.UserRepository;
import com.std.tothebook.exception.ExpectedException;
import com.std.tothebook.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class SignUpService {

    private final String REGEX_PASSWORD = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{10,}$";
    private final Pattern passwordPattern = Pattern.compile(REGEX_PASSWORD);

    private final UserService userService;

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder;

    /**
     * 회원 가입
     */
    public void signUp(SignUpRequest payload) {
        validateForSignUp(payload);

        String password = encoder.encode(payload.getPassword());

        User user = User.create()
                .email(payload.getEmail())
                .password(password)
                .nickname(payload.getNickname())
                .build();

        userRepository.save(user);
    }

    // 회원 가입 검증
    private void validateForSignUp(SignUpRequest payload) {
        // 이메일, 닉네임 중복 체크
        if (userService.isEmailDuplicated(payload.getEmail())
                || userService.isNicknameDuplicated(payload.getNickname())) {
            throw new ExpectedException(ErrorCode.DUPLICATE_USER);
        }
        // 비밀번호 규칙 체크
        Matcher matcher = passwordPattern.matcher(payload.getPassword());
        if (!matcher.matches()) {
            throw new ExpectedException(ErrorCode.REGULAR_EXPRESSION_PASSWORD);
        }
    }
}
