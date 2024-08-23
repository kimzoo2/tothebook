package com.std.tothebook.dto;

import com.std.tothebook.security.LoginUser;
import lombok.Getter;

/**
 * 로그인 회원 정보
 */
@Getter
public class LoginUserResponse {

    private final long id;
    private final String email;
    private final String nickname;

    public LoginUserResponse(LoginUser user) {
        this.id = user.getId();
        this.email = user.getUsername();
        this.nickname = user.getNickname();
    }
}
