package com.std.tothebook.service;

import com.std.tothebook.dto.AddUserRequest;
import com.std.tothebook.dto.EditUserPasswordRequest;
import com.std.tothebook.dto.EditUserRequest;
import com.std.tothebook.dto.FindUserResponse;
import com.std.tothebook.entity.User;
import com.std.tothebook.exception.ValidateDTOException;
import com.std.tothebook.repository.UserRepository;
import com.std.tothebook.exception.ExpectedException;
import com.std.tothebook.exception.UserException;
import com.std.tothebook.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder;

    private final String REGEX_EMAIL = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";
    private final Pattern emailPattern = Pattern.compile(REGEX_EMAIL);

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
        Optional<User> optionalUser = userRepository.findById(payload.getId());

        if (optionalUser.isEmpty()) {
            System.out.println("회원이 존재하지 않습니다.");
            return;
        }

        User user = optionalUser.get();
        user.updateUser(payload.getNickname());
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
        return userRepository.existsByNickname(nickname);
    }

    /**
     * 임시 비밀번호 설정 및 이메일 전송
     */
    public void updatePasswordAndSendMail(EditUserPasswordRequest payload) {
        /*
        - 최근에 인증한 건이 있는지 (1시간) -> 존재하지 않으면 에러
        - 비밀번호 신규 생성
        - 회원 업데이트
        - 이메일 전송
         */
        String email = payload.getEmail();
        validateEmail(email);

        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new UserException(ErrorCode.USER_NOT_FOUND);
        }
    }

    /**
     * 이메일 존재 여부
     * 이메일 규칙 체크
     */
    public void validateEmail(String email) {
        if (!StringUtils.hasText(email)) {
            throw new ValidateDTOException(ErrorCode.EMAIL_VALIDATE);
        }
        Matcher matcher = emailPattern.matcher(email);
        if (!matcher.matches()) {
            throw new UserException(ErrorCode.REGULAR_EXPRESSION_EMAIL);
        }
    }
}
