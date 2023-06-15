package com.std.tothebook.api.service;

import com.std.tothebook.api.domain.dto.AddUserRequest;
import com.std.tothebook.api.domain.dto.EditUserRequest;
import com.std.tothebook.api.domain.dto.FindUserResponse;
import com.std.tothebook.api.entity.User;
import com.std.tothebook.api.repository.UserRepository;
import com.std.tothebook.exception.ExpectedException;
import com.std.tothebook.exception.UserException;
import com.std.tothebook.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

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
        // TODO 비밀번호 암호화, 중복 체크, 매핑

        User user = User.create()
                .email(payload.getEmail())
                .password(payload.getPassword())
                .nickname(payload.getNickname())
                .build();

        userRepository.save(user);

        System.out.println(user.getId());
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
}
