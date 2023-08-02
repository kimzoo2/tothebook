package com.std.tothebook.api.controller.api;

import com.std.tothebook.api.domain.dto.SendCertificationNumberRequest;
import com.std.tothebook.api.service.CertificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@Tag(name = "인증")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/certification")
public class CertificationController {

    private final CertificationService certificationService;

    @Operation(summary = "인증 번호 생성 후 메일 전송", description = "패스워드 암호화가 번거롭다 느껴서 실행 시에 application 내 정보 비워뒀음")
    @PostMapping("/send")
    public ResponseEntity<Void> sendNumber(@RequestBody SendCertificationNumberRequest payload) throws MessagingException {
        certificationService.sendNumber(payload);

        return ResponseEntity.ok().build();
    }
}
