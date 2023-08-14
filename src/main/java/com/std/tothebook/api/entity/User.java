package com.std.tothebook.api.entity;

import com.std.tothebook.api.enums.UserStatus;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // 이메일
    @Column(nullable = false)
    private String email;

    // 비밀번호
    @Column(nullable = false)
    private String password;

    // 닉네임
    @Column(nullable = false)
    private String nickname;

    // 회원 상태
    @Column(name = "user_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    // 가입일
    @Column(name = "join_date", nullable = false, columnDefinition = "default (current_date)")
    private LocalDate joinDate;

    // 탈퇴일
    @Column(name = "leave_date")
    private LocalDate leaveDate;

    // 등록일
    @Column(name = "created_at", columnDefinition = "default now()")
    private LocalDateTime createdAt;

    // 수정일
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    // 임시 비밀번호 발급 여부
    @Column(name = "is_temporary_password", columnDefinition = "default 0")
    private boolean isTemporaryPassword;

    protected User() {}

    @Builder(builderMethodName = "create")
    public User(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.userStatus = UserStatus.JOIN;
        this.joinDate = LocalDate.now();
        this.createdAt = LocalDateTime.now();
        this.isTemporaryPassword = false;
    }

    public void updateUser(String nickname) {
        this.nickname = nickname;
        this.updatedDate = LocalDateTime.now();
    }
}
