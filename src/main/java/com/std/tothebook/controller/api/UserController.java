package com.std.tothebook.controller.api;

import com.std.tothebook.dto.*;
import com.std.tothebook.entity.User;
import com.std.tothebook.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "회원")
@RequestMapping("/api/user")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원 단건 조회")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id) {
        final var response = userService.getUser(id);

        return ResponseEntity.ok(response);
    }

    @Deprecated
    @GetMapping("/simple/{id}")
    public ResponseEntity<FindUserResponse> getSimpleUser(@PathVariable long id) {
        final var response = userService.getSimpleUser(id);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "로그인 한 회원 정보 조회")
    @GetMapping("")
    public ResponseEntity<LoginUserResponse> getLoginUser() {
        return ResponseEntity.ok(userService.getLoginUser());
    }

    @Operation(summary = "회원 생성")
    @PostMapping("")
    public ResponseEntity<Void> addUser(@RequestBody AddUserRequest payload) {
        userService.addUser(payload);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "회원 수정")
    @PutMapping("")
    public ResponseEntity<Void> editUser(@RequestBody EditUserRequest payload) {
        userService.editUser(payload);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "이메일 중복 체크")
    @GetMapping("/is-duplicated/email/{email}")
    public ResponseEntity<Boolean> isEmailDuplicated(@PathVariable String email) {
        final var response = userService.isEmailDuplicated(email);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "닉네임 중복 체크")
    @GetMapping("/is-duplicated/nickname/{nickname}")
    public ResponseEntity<Boolean> isNicknameDuplicated(@PathVariable String nickname) {
        final var response = userService.isNicknameDuplicated(nickname);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "임시 비밀번호 발급 및 메일 전송")
    @PostMapping("/temporary-password")
    public ResponseEntity<Void> createTemporaryPasswordAndSendMail(@RequestBody CreateUserTemporaryPasswordRequest payload) {
        userService.createTemporaryPasswordAndSendMail(payload);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "임시 비밀번호 상태일 때 비밀번호 변경")
    @PutMapping("/temporary-password")
    public ResponseEntity<Void> updatePassword(@RequestBody EditUserPasswordRequest payload) {
        userService.updatePassword(payload);

        return ResponseEntity.ok().build();
    }
}
