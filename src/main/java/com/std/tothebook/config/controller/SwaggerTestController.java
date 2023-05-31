package com.std.tothebook.config.controller;

import com.std.tothebook.config.domain.TestUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "스웨거 테스트", description = "swagger 테스트용 API Controller")
@RestController
@RequestMapping("")
public class SwaggerTestController {

    @Operation(summary = "test-get", description = "test get version 메서드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @GetMapping("")
    public ResponseEntity<Void> test(@Parameter(description = "테스트 파라미터") String testStr) {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "test-get-user", description = "test get version 유저 정보 조회 메서드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = TestUser.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @GetMapping("/user")
    public ResponseEntity<TestUser> getTestUser() {
        return ResponseEntity.ok(TestUser.builder()
                .userId("testId")
                .userName("홍길동")
                .age(30)
                .isWithdrawal("N")
                .build());
    }
}
