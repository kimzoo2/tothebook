package com.std.tothebook.api.repository;

import com.std.tothebook.api.domain.dto.FindUserResponse;

import java.util.Optional;

public interface UserCustomRepository {

    Optional<FindUserResponse> findSimpleUser(long id);
}
