package com.std.tothebook.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.std.tothebook.entity.Certification;
import com.std.tothebook.enums.CertificationType;
import com.std.tothebook.repository.CertificationCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.std.tothebook.entity.QCertification.certification;

@Repository
@RequiredArgsConstructor
public class CertificationCustomRepositoryImpl implements CertificationCustomRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * 이메일로 비밀번호 찾기를 진행한 인증 정보 조회
     */
    @Override
    public Optional<Certification> findForTemporaryPassword(String email) {
        final var query = queryFactory
                .selectFrom(certification)
                .where(
                        certification.email.eq(email),
                        certification.type.eq(CertificationType.FIND_PASSWORD),
                        certification.isCompleted.isTrue()
                );

        final var results = query.fetch();
        if (results == null || results.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(results.get(0));
    }
}
