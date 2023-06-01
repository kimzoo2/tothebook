package com.std.tothebook.api.service;

import com.std.tothebook.api.entity.User;
import com.std.tothebook.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUser(long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            // TODO 로그 작성 필요, 또는 error throw
            System.out.println("회원이 존재하지 않습니다. (Bad Request)");
            return null;
        }

        return optionalUser.get();
    }
}
