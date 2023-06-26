package com.std.tothebook.api.service;

import com.std.tothebook.api.domain.dto.SignInRequest;
import com.std.tothebook.api.entity.User;
import com.std.tothebook.api.repository.UserRepository;
import com.std.tothebook.exception.ExpectedException;
import com.std.tothebook.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SignInService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder;

    public String signIn(SignInRequest request) {
        Optional<User> optionalUser = userRepository.findUserByEmail(request.getEmail());

        if (optionalUser.isEmpty()) {
            throw new ExpectedException(ErrorCode.SIGN_IN_USER_NOT_FOUND);
        }

        User user = optionalUser.get();

        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new ExpectedException(ErrorCode.SIGN_IN_USER_NOT_FOUND);
        }

        // jwt generateToken

        return "";
    }
}
