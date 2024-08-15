package com.std.tothebook.dto;

import com.std.tothebook.security.JsonWebToken;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignInResponse {

    private JsonWebToken jsonWebToken;
    private Boolean isTemporaryPassword;
}
