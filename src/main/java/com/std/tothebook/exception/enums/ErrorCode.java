package com.std.tothebook.exception.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    /**
     * 공통
     */
    FORBIDDEN_ERROR(HttpStatus.FORBIDDEN, "오류가 발생했습니다."),

    /**
     * 회원
     */
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "회원을 찾을 수 없습니다."),
    SIGN_IN_USER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원이 없거나 아이디나 비밀번호가 일치하지 않습니다."),
    DUPLICATE_USER(HttpStatus.BAD_REQUEST, "중복된 아이디 혹은 닉네임을 가진 회원이 존재합니다."),
    REGULAR_EXPRESSION_EMAIL(HttpStatus.BAD_REQUEST, "이메일을 다시 입력해주세요."),
    REGULAR_EXPRESSION_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호를 다시 입력해주세요."),

    /**
     * 유효성
     */
    EMAIL_VALIDATE(HttpStatus.BAD_REQUEST, "이메일을 입력해주세요."),
    PASSWORD_VALIDATE(HttpStatus.BAD_REQUEST, "비밀번호를 입력해주세요"),
    NICKNAME_VALIDATE(HttpStatus.BAD_REQUEST, "닉네임을 입력해주세요."),

    /**
     * 인증
     */
    CERTIFICATION_NUMBER_VALIDATE(HttpStatus.BAD_REQUEST, "인증번호를 입력해주세요"),
    CERTIFICATION_NOT_FOUND(HttpStatus.BAD_REQUEST, "인증번호를 다시 발급해주세요."),
    CERTIFICATION_PASSED_LIMITED_TIME(HttpStatus.BAD_REQUEST, "인증 시간이 만료되었습니다."),
    CERTIFICATION_ALREADY(HttpStatus.BAD_REQUEST, "인증번호를 다시 발급해주세요."),

    /**
     * 카테고리
     */
    CATEGORY_NOT_FOUND(HttpStatus.BAD_REQUEST, "등록되지 않거나 삭제된 카테고리입니다.");


    private final HttpStatus httpStatus;
    private final String message;
}
