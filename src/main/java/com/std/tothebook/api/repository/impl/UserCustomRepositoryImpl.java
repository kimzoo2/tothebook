package com.std.tothebook.api.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.std.tothebook.api.domain.dto.FindUserResponse;
import com.std.tothebook.api.entity.User;
import com.std.tothebook.api.enums.UserStatus;
import com.std.tothebook.api.repository.UserCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.std.tothebook.api.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class UserCustomRepositoryImpl implements UserCustomRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * id 에 해당하는 활동 회원 단건 조회
     */
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

    /**
     * 이메일에 해당하는 활동 회원 단건 조회
     */
    @Override
    public Optional<User> findUserByEmail(String email) {
        final JPAQuery<User> query = queryFactory
                .selectFrom(user)
                .where(
                        user.userStatus.eq(UserStatus.JOIN),
                        user.email.eq(email)
                );

        final List<User> results = query.fetch();

        if (results == null || results.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(results.get(0));
    }

    /**
     * 이메일 혹은 닉네임에 일치하는 회원 존재 여부 체크
     */
    @Override
    public boolean existsUserByEmailOrNickname(String email, String nickname) {
        final var query = queryFactory
                .selectFrom(user)
                .where(user.userStatus.eq(UserStatus.JOIN))
                .where(user.email.eq(email).or(user.nickname.eq(nickname)));

        final var results = query.fetch();

        return results != null && !results.isEmpty();
    }
}
