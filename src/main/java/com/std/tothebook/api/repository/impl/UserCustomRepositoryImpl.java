package com.std.tothebook.api.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.std.tothebook.api.domain.dto.FindUserResponse;
import com.std.tothebook.api.repository.UserCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.std.tothebook.api.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class UserCustomRepositoryImpl implements UserCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<FindUserResponse> findSimpleUser(long id) {
        final var query = queryFactory
                .select(Projections.constructor(
                        FindUserResponse.class,
                        user.email,
                        user.nickname,
                        user.userStatus
                ))
                .from(user)
                .where(
                        user.id.eq(id)
                );

        final var results = query.fetch();
        if (results == null || results.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(results.get(0));
    }
}
