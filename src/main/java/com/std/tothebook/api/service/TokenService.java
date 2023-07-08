package com.std.tothebook.api.service;

import com.std.tothebook.api.entity.RefreshToken;
import com.std.tothebook.api.entity.User;
import com.std.tothebook.api.repository.RefreshTokenRepository;
import com.std.tothebook.api.repository.UserRepository;
import com.std.tothebook.config.JwtTokenProvider;
import com.std.tothebook.exception.ExpectedException;
import com.std.tothebook.exception.JwtAuthenticationException;
import com.std.tothebook.exception.enums.ErrorCode;
import com.std.tothebook.security.JsonWebToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtTokenProvider jwtTokenProvider;

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


    /**
     * access token 재발급
     */
    public String refresh(HttpHeaders httpHeaders) {
        List<String> refreshTokenList = httpHeaders.get("Refresh-token");
        if (refreshTokenList == null || refreshTokenList.isEmpty()) {
            throw new JwtAuthenticationException("토큰이 존재하지 않습니다.");
        }
        String token = refreshTokenList.get(0);

        RefreshToken refreshToken = refreshTokenRepository.findFirstByToken(token)
                .orElseThrow(() -> new JwtAuthenticationException("토큰이 알맞지 않습니다."));

        return jwtTokenProvider.createAccessToken(refreshToken.getUserId());
    }
}
