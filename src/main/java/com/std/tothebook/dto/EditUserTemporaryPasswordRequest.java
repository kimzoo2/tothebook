package com.std.tothebook.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EditUserTemporaryPasswordRequest {

    private Long userId;

    private String temporaryPassword;

    private String newPassword;
}
