package com.std.tothebook.repository;

import com.std.tothebook.dto.FindUserResponse;
import com.std.tothebook.entity.User;

import java.time.LocalDate;
import java.util.Optional;

public interface UserCustomRepository {

    Optional<FindUserResponse> findSimpleUser(long id);

    Optional<User> findUserByEmail(String email);

    boolean existsUserByEmailOrNickname(String email, String nickname);

    Optional<User> findById(long id);

    boolean existsNicknameByCheckDate(String nickname, LocalDate checkDateTime);
}
