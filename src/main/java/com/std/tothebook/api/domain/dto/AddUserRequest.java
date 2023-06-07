package com.std.tothebook.api.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@Schema(description = "회원 생성 Request")
public class AddUserRequest {

    @Schema(description = "이메일")
    private String email;

    @Schema(description = "비밀번호")
    private String password;

    @Schema(description = "닉네임")
    private String nickname;

    // TODO validate

    protected AddUserRequest () {}
}
