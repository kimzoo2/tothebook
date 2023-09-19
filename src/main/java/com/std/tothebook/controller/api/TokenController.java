package com.std.tothebook.controller.api;

import com.std.tothebook.service.JwtTokenService;
import com.std.tothebook.service.TokenService;
import com.std.tothebook.config.JwtTokenProvider;
import com.std.tothebook.security.JsonWebToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 매번 로그인 하기 귀찮을 때 임시로 발급하기 위한 컨트롤러
 */
@Tag(name = "토큰 발급", description = "개발 시에만 사용")
@RestController
@RequiredArgsConstructor
@RequestMapping("/token")
public class TokenController {

    private final TokenService tokenService;
    private final JwtTokenService jwtTokenService;

    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "인증된 회원 아이디 조회", description = "회원이 인증된 상황에서 인증 여부를 체크하기 위해 실행")
    @GetMapping("/user-id")
    public long getUserId() {
        return jwtTokenProvider.getUserId();
    }

    @Operation(summary = "이메일로만 토큰 발급")
    @GetMapping("/by-email/{email}")
    public ResponseEntity<JsonWebToken> getTokenByEmail(@PathVariable String email) {
        return ResponseEntity.ok(tokenService.getTokenByEmail(email));
    }

    @Operation(summary = "id로만 토큰 발급")
    @GetMapping("/by-id/{id}")
    public ResponseEntity<JsonWebToken> getTokenById(@PathVariable long id) {
        return ResponseEntity.ok(tokenService.getTokenById(id));
    }

    @Operation(summary = "refresh 토큰으로 access 토큰 발급", description = "이때는 access token 발급만 진행된다.")
    @PostMapping("/refresh")
    public ResponseEntity<String> getRefreshToken(@RequestHeader HttpHeaders httpHeaders) {
        final var response = tokenService.refresh(httpHeaders);

        return ResponseEntity.ok(response);
    }

}
