package com.std.tothebook.api.repository;

import com.std.tothebook.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserCustomRepository {

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);
}
