package com.std.tothebook.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "회원 수정 Request")
public class EditUserRequest {

    @Schema(description = "회원 번호")
    private final long id;

    @Schema(description = "닉네임")
    private final String nickname;
}
