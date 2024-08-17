package com.std.tothebook.repository;

import java.util.List;
import java.util.Optional;

import com.std.tothebook.entity.MyBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyBookRepository extends JpaRepository<MyBook, Long> {

	Optional<MyBook> findByIdAndIsDeletedFalse(Long id);

	List<MyBook> findMyBookByUserId(Long userId);
}
