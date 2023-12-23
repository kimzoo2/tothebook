package com.std.tothebook.controller.api;

import com.std.tothebook.dto.SignInRequest;
import com.std.tothebook.service.SignInService;
import com.std.tothebook.security.JsonWebToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "로그인/로그아웃")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SignInController {

    private final SignInService signInService;

    @Operation(summary = "로그인")
    @PostMapping("/sign-in")
    public ResponseEntity<JsonWebToken> signIn(@RequestBody SignInRequest request) {
        final var response = signInService.signIn(request);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "로그아웃")
    @PostMapping("/sign-out")
    public ResponseEntity<Void> signOut() {
        signInService.signOut();

        return ResponseEntity.ok().build();
    }
}
