package com.std.tothebook.service;

import com.std.tothebook.config.JwtTokenProvider;
import com.std.tothebook.dto.AddUserRequest;
import com.std.tothebook.dto.EditUserPasswordRequest;
import com.std.tothebook.dto.EditUserRequest;
import com.std.tothebook.dto.FindUserResponse;
import com.std.tothebook.entity.User;
import com.std.tothebook.enums.MailType;
import com.std.tothebook.repository.UserRepository;
import com.std.tothebook.exception.ExpectedException;
import com.std.tothebook.exception.UserException;
import com.std.tothebook.exception.enums.ErrorCode;
import com.std.tothebook.util.UserInputValidator;
import com.std.tothebook.vo.Mail;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final CertificationService certificationService;
    private final EmailSendService emailSendService;

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder;
    private final JwtTokenProvider jwtTokenProvider;

    private static final Random RANDOM = new SecureRandom();
    private static final String NUMBERS = "0123456789";
    private static final String ALPHABET_UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String ALPHABET_LOWER_CASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String SPECIAL_CHARACTER = "!@#$%^&*?";

    @Value("${policy.nickname-duplicate-check-days}")
    private int nicknameCheckDays;
    @Value("${policy.password-length}")
    private int passwordLength;

    /**
     * 회원 단건 조회
     */
    public User getUser(long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            throw new UserException(ErrorCode.USER_NOT_FOUND);
        }

        return optionalUser.get();
    }

    /**
     * 회원 커스텀 단건 조회
     */
    public FindUserResponse getSimpleUser(long id) {
        Optional<FindUserResponse> optionalUser = userRepository.findSimpleUser(id);

        if (optionalUser.isEmpty()) {
            throw new ExpectedException(HttpStatus.BAD_REQUEST, id + ": 회원이 존재하지 않습니다.");
        }

        return optionalUser.get();
    }

    /**
     * 회원 생성
     */
    @Transactional
    public void addUser(AddUserRequest payload) {
        if (userRepository.existsUserByEmailOrNickname(payload.getEmail(), payload.getNickname())) {
            throw new ExpectedException(ErrorCode.DUPLICATE_USER);
        }

        User user = User.create()
                .email(payload.getEmail())
                .password(encoder.encode(payload.getPassword()))
                .nickname(payload.getNickname())
                .build();

        userRepository.save(user);
    }

    /**
     * 회원 수정
     */
    @Transactional
    public void editUser(EditUserRequest payload) {
        User user = userRepository.findById(payload.getId())
                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));

        if (jwtTokenProvider.getUserId() == payload.getId()) {
            throw new UserException(ErrorCode.FORBIDDEN_ERROR);
        }

        if (isNicknameDuplicated(payload.getNickname())) {
            throw new UserException(ErrorCode.DUPLICATE_USER);
        }

        user.modifyUser(payload.getNickname());
    }

    /**
     * 회원 리스트 조회
     */
    public List<User> getUsers() {
        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            throw new UserException(ErrorCode.USER_NOT_FOUND);
        }

        return users;
    }

    /**
     * 이메일 중복 체크
     */
    public Boolean isEmailDuplicated(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * 닉네임 중복 체크
     */
    public Boolean isNicknameDuplicated(String nickname) {
        LocalDate checkDate = LocalDate.now().minusDays(nicknameCheckDays);
        return userRepository.existsNicknameByCheckDate(nickname, checkDate);
    }

    /**
     * 임시 비밀번호 설정 및 이메일 전송
     */
    @Transactional
    public void updatePasswordAndSendMail(EditUserPasswordRequest payload) {
        String email = payload.getEmail();
        UserInputValidator.validateEmail(email);

        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));

        // 인증 여부 체크
        certificationService.checkCertificationForTemporaryPassword(email);

        // 비밀번호 생성
        String randomPassword = generateRandomPassword(passwordLength);

        // 회원 업데이트
        user.updateWithTemporaryPassword(encoder.encode(randomPassword));

        // 이메일 전송
        sendPasswordMail(user.getEmail(), randomPassword);
    }

    /**
     * 비밀번호 생성
     */
    public String generateRandomPassword(int passwordLength) {
        String all = NUMBERS + ALPHABET_UPPER_CASE + ALPHABET_LOWER_CASE + SPECIAL_CHARACTER;

        StringBuilder sb = new StringBuilder();
        // 정규식을 충족하게 4개 짜리 필수 패스워드를 생성하기 때문에 4자리 제거한다
        for (int i = 0; i < passwordLength - 4; i++) {
            sb.append(
                    all.charAt(RANDOM.nextInt(all.length()))
            );
        }

        String password = sb + generatePasswordInitialPart();

        // 순서 섞기
        List<String> list = List.of(password);
        Collections.shuffle(list);

        return String.join("", list);
    }

    /**
     * 정규식을 만족하는 최소 패스워드 일부 생성
     */
    public String generatePasswordInitialPart() {
        return String.valueOf(NUMBERS.charAt(RANDOM.nextInt(NUMBERS.length()))) +
                ALPHABET_UPPER_CASE.charAt(RANDOM.nextInt(ALPHABET_UPPER_CASE.length())) +
                ALPHABET_LOWER_CASE.charAt(RANDOM.nextInt(ALPHABET_LOWER_CASE.length())) +
                SPECIAL_CHARACTER.charAt(RANDOM.nextInt(SPECIAL_CHARACTER.length()));
    }

    /**
     * 임시 비밀번호 전송
     */
    public void sendPasswordMail(String email, String password) {
        Mail mail = new Mail(email, MailType.TEMPORARY_PASSWORD);
        mail.replaceTemporaryPassword(password);

        try {
            emailSendService.sendMail(mail);
        } catch (Exception e) {
            throw new UserException(ErrorCode.ERROR);
        }
    }
}
