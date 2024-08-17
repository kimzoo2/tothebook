package com.std.tothebook.entity;

import com.std.tothebook.enums.CertificationType;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Table(name = "certification")
public class Certification {

    @Id
    @Column(name ="certification_id")
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

    // 인증 완료 여부
    @ColumnDefault("false")
    @Column(name = "is_completed", nullable = false)
    private boolean isCompleted;

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
        this.isCompleted = false;
    }

    /**
     * 인증번호 인증 완료 처리
     */
    public void completeCertification() {
        this.isCompleted = true;
    }
}
