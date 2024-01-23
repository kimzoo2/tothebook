package com.std.tothebook.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "비밀번호 변경 Request")
public class EditUserTemporaryPasswordRequest {

    private String email;

    protected EditUserTemporaryPasswordRequest() {}

    public EditUserTemporaryPasswordRequest(String email) {
        this.email = email;
    }
}
