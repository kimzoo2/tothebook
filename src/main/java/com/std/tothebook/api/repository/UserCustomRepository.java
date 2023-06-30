package com.std.tothebook.api.repository;

import com.std.tothebook.api.domain.dto.FindUserResponse;
import com.std.tothebook.api.entity.User;

import java.util.Optional;

public interface UserCustomRepository {

    Optional<FindUserResponse> findSimpleUser(long id);

    Optional<User> findUserByEmail(String email);

    boolean existsUserByEmailOrNickname(String email, String nickname);

    Optional<User> findById(long id);
}
