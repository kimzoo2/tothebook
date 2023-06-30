package com.std.tothebook.security;

import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * UserDetails 구현 클래스
 * Jwt -> 회원 정보 조회하는 dto
 */
public class SecurityUser implements UserDetails {

    private final long id;
    private final String email;
    private final String password;
    private final String nickname;

    @Builder
    public SecurityUser(long id, String email, String password, String nickname) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public long getId() {
        return id;
    }

    // 사용자의 권한을 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 권한은 2차 개발이라 null 리턴
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    /*
    사용자 계정을 필요에 따라 활성화 또는 비활성화 하는 메서드
    전부 true: 사용자가 항상 활성화 되고 사용 가능하다
     */

    // 계정 만료
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 잠금
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 자격 증명 만료
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 비활성화
    @Override
    public boolean isEnabled() {
        return true;
    }
}
