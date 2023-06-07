package com.std.tothebook.api.domain.dto;

import com.std.tothebook.api.enums.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Schema(description = "회원 커스텀 단건 조회")
@AllArgsConstructor
public class FindUserResponse {

    @Schema(description = "이메일")
    private final String email;

    @Schema(description = "닉네임")
    private final String nickname;

    @Schema(description = "상태")
    private final UserStatus userStatus;

}
