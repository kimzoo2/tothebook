package com.std.tothebook.entity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "refresh_token")
public class RefreshToken {

    @Id
    @Column(name = "refresh_token_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // 회원 번호
    @Column(name = "user_id", nullable = false)
    private long userId;

    // 토큰
    @Column(nullable = false)
    private String token;

    // 만료 일시
    @Column(name = "expired_at", nullable = false)
    private LocalDateTime expiredAt;

    protected RefreshToken() {}

    @Builder
    public RefreshToken(long userId, String token, LocalDateTime expiredAt) {
        this.userId = userId;
        this.token = token;
        this.expiredAt = expiredAt;
    }

    // 만료
    public void expireToken() {
        this.expiredAt = LocalDateTime.now();
    }
}
