package com.std.tothebook.config.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@Schema(description = "테스트 사용자 DTO")
public class TestUser {

    @Schema(description = "사용자 ID", nullable = false, example = "userid_test")
    private String userId;

    @Schema(description = "사용자명", example = "이름")
    private String userName;

    @Schema(description = "사용자 나이", example = "31")
    private int age;

    @Schema(description = "탈퇴 여부", defaultValue = "N", allowableValues = {"Y", "N"})
    private String isWithdrawal;

    protected TestUser() {}
}
