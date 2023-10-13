package com.std.tothebook.controller.api;

import com.std.tothebook.dto.AddUserRequest;
import com.std.tothebook.dto.EditUserPasswordRequest;
import com.std.tothebook.dto.EditUserRequest;
import com.std.tothebook.dto.FindUserResponse;
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

    @GetMapping("/simple/{id}")
    public ResponseEntity<FindUserResponse> getSimpleUser(@PathVariable long id) {
        final var response = userService.getSimpleUser(id);

        return ResponseEntity.ok(response);
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

    @Operation(summary = "신규 비밀번호 발급 및 메일 전송")
    @PutMapping("/password")
    public ResponseEntity<Void> updateUserPasswordAndSendMail(@RequestBody EditUserPasswordRequest payload) {
        userService.updatePasswordAndSendMail(payload);

        return ResponseEntity.ok().build();
    }
}
