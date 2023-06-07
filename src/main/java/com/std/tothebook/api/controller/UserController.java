package com.std.tothebook.api.controller;

import com.std.tothebook.api.domain.dto.AddUserRequest;
import com.std.tothebook.api.domain.dto.FindUserResponse;
import com.std.tothebook.api.entity.User;
import com.std.tothebook.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "회원")
@RequestMapping("/user")
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

    @PutMapping("")
    public ResponseEntity<Void> editUser() {
        // TODO
        userService.editUser();

        return ResponseEntity.ok().build();
    }
}
