package com.std.tothebook.api.service;

import com.std.tothebook.api.entity.User;
import com.std.tothebook.api.repository.UserRepository;
import com.std.tothebook.config.JwtTokenProvider;
import com.std.tothebook.exception.ExpectedException;
import com.std.tothebook.exception.enums.ErrorCode;
import com.std.tothebook.security.JsonWebToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final UserRepository userRepository;

    private final JwtTokenProvider jwtTokenProvider;

    // 토큰 저장

    // 토큰 존재 여부 체크

    // 이메일로 토큰 발급
    public JsonWebToken getTokenByEmail(String email) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ExpectedException(ErrorCode.USER_NOT_FOUND));

        return jwtTokenProvider.createToken(user.getId());
    }

    // id로 토큰 발급
    public JsonWebToken getTokenById(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ExpectedException(ErrorCode.USER_NOT_FOUND));

        return jwtTokenProvider.createToken(user.getId());
    }
}
