package com.std.tothebook.service;

import com.std.tothebook.entity.RefreshToken;
import com.std.tothebook.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * refresh 토큰 저장
     */
    @Transactional
    public void handleRefreshToken(long userId, String token, Date expiredDate) {
        refreshTokenRepository.deleteByUserId(userId);

        refreshTokenRepository.save(
                RefreshToken.builder()
                        .userId(userId)
                        .token(token)
                        .expiredAt(LocalDateTime.ofInstant(expiredDate.toInstant(), ZoneId.systemDefault()))
                        .build()
        );
    }

    /**
     * refresh 토큰 만료
     */
    @Transactional
    public void expireRefreshToken(long userId) {
        Optional<RefreshToken> optionalToken = refreshTokenRepository.findTopByUserIdOrderById(userId);

        if (optionalToken.isPresent()) {
            RefreshToken refreshToken = optionalToken.get();
            refreshToken.expireToken();
        }
    }
}
