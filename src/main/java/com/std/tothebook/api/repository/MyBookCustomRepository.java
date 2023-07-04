package com.std.tothebook.api.repository;

import com.std.tothebook.api.domain.dto.FindMyBookResponse;
import com.std.tothebook.api.domain.dto.FindMyBooksResponse;

import java.util.List;
import java.util.Optional;

public interface MyBookCustomRepository {

    List<FindMyBooksResponse> findMyBookByUserId(long userId);

    Optional<FindMyBookResponse> findSimpleMyBook(long id);
}
