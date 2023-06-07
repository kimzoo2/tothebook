package com.std.tothebook.api.service;

import com.std.tothebook.api.domain.dto.AddUserRequest;
import com.std.tothebook.api.domain.dto.FindUserResponse;
import com.std.tothebook.api.entity.User;
import com.std.tothebook.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
            // TODO 로그 작성 필요, 또는 error throw
            System.out.println("회원이 존재하지 않습니다. (Bad Request)");
            return null;
        }

        return optionalUser.get();
    }

    /**
     * 회원 커스텀 단건 조회
     */
    public FindUserResponse getSimpleUser(long id) {
        Optional<FindUserResponse> optionalUser = userRepository.getSimpleUser(id);

        if (optionalUser.isEmpty()) {
            System.out.println("회원이 존재하지 않습니다.");
            return null;
        }

        return optionalUser.get();
    }

    /**
     * 회원 생성
     */
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
    public void editUser() {

    }
}
