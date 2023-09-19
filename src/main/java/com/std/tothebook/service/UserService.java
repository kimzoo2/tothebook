package com.std.tothebook.service;

import com.std.tothebook.dto.AddUserRequest;
import com.std.tothebook.dto.EditUserRequest;
import com.std.tothebook.dto.FindUserResponse;
import com.std.tothebook.entity.User;
import com.std.tothebook.repository.UserRepository;
import com.std.tothebook.exception.ExpectedException;
import com.std.tothebook.exception.UserException;
import com.std.tothebook.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder;

    /**
     * 회원 단건 조회
     */
    public User getUser(long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            System.out.println("Pull Request 수정용 주석, 0618 제거 예정");
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
}
