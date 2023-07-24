package com.std.tothebook.api.repository;

import com.std.tothebook.api.entity.MyBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyBookRepository extends JpaRepository<MyBook, Long>, MyBookCustomRepository {
}
