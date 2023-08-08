package com.std.tothebook.api.entity;

import com.std.tothebook.api.enums.CertificationType;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "certification")
public class Certification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // 인증 타입 (회원가입, 비밀번호 찾기)
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CertificationType type;

    // 인증 이메일
    @Column(nullable = false)
    private String email;

    // 인증 번호
    @Column(nullable = false, length = 10)
    private String number;

    // 유효 시간
    @Column(name = "limited_at", nullable = false)
    private LocalDateTime limitedAt;

    // 등록 일시
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    protected Certification() {}

    @Builder(builderMethodName = "create")
    public Certification(CertificationType type, String email, String number, long limitedMinutes) {
        this.type = type;
        this.email = email;
        this.number = number;

        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.limitedAt = now.plusMinutes(limitedMinutes);
    }
}
