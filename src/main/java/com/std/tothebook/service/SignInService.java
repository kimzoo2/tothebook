package com.std.tothebook.service;

import com.std.tothebook.dto.SignInRequest;
import com.std.tothebook.dto.SignInResponse;
import com.std.tothebook.entity.User;
import com.std.tothebook.repository.UserRepository;
import com.std.tothebook.config.JwtTokenProvider;
import com.std.tothebook.exception.ExpectedException;
import com.std.tothebook.exception.enums.ErrorCode;
import com.std.tothebook.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SignInService {

    private final JwtTokenService jwtTokenService;

    private final UserRepository userRepository;

    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder encoder;

    public SignInResponse signIn(SignInRequest request) {
        Optional<User> optionalUser = userRepository.findUserByEmail(request.getEmail());

        if (optionalUser.isEmpty()) {
            throw new ExpectedException(ErrorCode.SIGN_IN_USER_NOT_FOUND);
        }

        User user = optionalUser.get();

        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new ExpectedException(ErrorCode.SIGN_IN_USER_NOT_FOUND);
        }

        return new SignInResponse(
                jwtTokenProvider.createToken(user.getId()), user.isTemporaryPassword());
    }

    public void signOut() {
        SecurityUser user = jwtTokenProvider.getUser();

        jwtTokenService.expireRefreshToken(user.getId());
    }
}
