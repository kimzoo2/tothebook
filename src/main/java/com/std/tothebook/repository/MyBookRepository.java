package com.std.tothebook.repository;

import com.std.tothebook.entity.MyBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyBookRepository extends JpaRepository<MyBook, Long>, MyBookCustomRepository {
}
