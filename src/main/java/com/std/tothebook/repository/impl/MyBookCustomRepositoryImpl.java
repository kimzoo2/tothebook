package com.std.tothebook.repository.impl;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.std.tothebook.dto.FindMyBookResponse;
import com.std.tothebook.dto.FindMyBooksResponse;
import com.std.tothebook.repository.MyBookCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.std.tothebook.entity.QBook.book;
import static com.std.tothebook.entity.QMyBook.myBook;


@Repository
@RequiredArgsConstructor
public class MyBookCustomRepositoryImpl implements MyBookCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<FindMyBooksResponse> findMyBookByUserId(long userId) {
        final var query = queryFactory
                .select(Projections.constructor(
                        FindMyBooksResponse.class,
                        myBook.id,
                        myBook.book.title,
                        myBook.book.authors,
                        myBook.book.publisher,
                        myBook.book.thumbnail,
                        myBook.startDate,
                        myBook.endDate,
                        myBook.myBookStatus
                ))
                .from(myBook)
                .join(myBook.book, book)
                .where(
                        myBook.user.id.eq(userId),
                        myBook.isDeleted.eq(false)
                );
        final var results = query.fetch();

        return results;
    }

    @Override
    public Optional<FindMyBookResponse> findMyBookById(long id) {
        final var query = queryFactory
                .select(Projections.constructor(
                        FindMyBookResponse.class,
                        myBook.id,
                        myBook.user.id.as("userId"),
                        myBook.book.title,
                        myBook.book.authors,
                        myBook.book.publisher,
                        myBook.startDate,
                        myBook.endDate,
                        myBook.rating,
                        myBook.page.as("currentPage"),
                        myBook.book.page.as("totalPage"),
                        myBook.book.thumbnail,
                        myBook.myBookStatus
                ))
                .from(myBook)
                .join(myBook.book, book)
                .where(
                        myBook.id.eq(id),
                        myBook.isDeleted.eq(false)
                );

        final var results = query.fetch();

        if (results == null || results.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(results.get(0));
    }
}
