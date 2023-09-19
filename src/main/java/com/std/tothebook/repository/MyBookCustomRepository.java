package com.std.tothebook.repository;

import com.std.tothebook.dto.FindMyBookResponse;
import com.std.tothebook.dto.FindMyBooksResponse;

import java.util.List;
import java.util.Optional;

public interface MyBookCustomRepository {

    List<FindMyBooksResponse> findMyBookByUserId(long userId);

    Optional<FindMyBookResponse> findMyBookById(long id);
}
