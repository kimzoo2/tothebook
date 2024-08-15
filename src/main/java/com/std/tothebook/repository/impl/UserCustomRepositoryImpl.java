package com.std.tothebook.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.std.tothebook.dto.FindUserResponse;
import com.std.tothebook.entity.User;
import com.std.tothebook.enums.UserStatus;
import com.std.tothebook.repository.UserCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.std.tothebook.entity.QUser.user;

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

    /**
     * id에 해당하는 활동 회원 단건 조회
     */
    @Override
    public Optional<User> findById(long id) {
        final var query = queryFactory
                .selectFrom(user)
                .where(
                        user.userStatus.eq(UserStatus.JOIN),
                        user.id.eq(id)
                );

        final var results = query.fetch();

        if (results == null || results.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(results.get(0));
    }

    /**
     * 닉네임 중복 체크, 존재 여부
     * 활동 회원 중 닉네임이 중복되거나 or 탈퇴한 지 30일이 지나지 않은 회원의 닉네임이 중복되거나
     * @param nickname      닉네임
     * @param checkDate     오늘 - 30일
     */
    @Override
    public boolean existsNicknameByCheckDate(String nickname, LocalDate checkDate) {
        final var query = queryFactory
                .select(user.id)
                .from(user)
                .where(user.nickname.eq(nickname))
                .where(
                        user.userStatus.eq(UserStatus.JOIN)
                                .or(user.userStatus.eq(UserStatus.WITHDRAWAL).and(user.leaveDate.after(checkDate)))
                );

        final var results = query.fetch();

        return results != null && !results.isEmpty();
    }
}
