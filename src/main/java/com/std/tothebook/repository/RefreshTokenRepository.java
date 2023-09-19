package com.std.tothebook.repository;

import com.std.tothebook.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    void deleteByUserId(long userId);

    Optional<RefreshToken> findFirstByToken(String token);
}
