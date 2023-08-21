package com.std.tothebook.api.controller.api;

import com.std.tothebook.api.domain.dto.SignUpRequest;
import com.std.tothebook.api.service.SignUpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "회원 가입")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SignUpController {

    private final SignUpService signUpService;

    @Operation(summary = "회원 가입", description = "비밀번호 규칙: 영어 대소문자 + 숫자 + 특수문자 (@$!%*#?&) 포함 10자리")
    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody SignUpRequest payload) {
        signUpService.signUp(payload);

        return ResponseEntity.ok().build();
    }
}
