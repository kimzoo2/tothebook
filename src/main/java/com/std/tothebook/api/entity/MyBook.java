package com.std.tothebook.api.entity;

import com.std.tothebook.api.enums.MyBookStatus;
import lombok.Getter;
import javax.persistence.Entity;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "my_book")
public class MyBook {

    // id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // 고객 id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 책 id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    // 시작일
    @Column(name = "start_date")
    private LocalDate startDate;

    // 종료일
    @Column(name = "end_date")
    private LocalDate endDate;

    // 현재 페이지
    @Column(name = "page")
    private int page;

    // 별점
    @Column(name = "rating")
    private int rating;

    // 독서 상태
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private MyBookStatus myBookStatus;

    // 등록일
    @Column(name = "created_date", nullable = false, columnDefinition = "default now()")
    private LocalDateTime createdDate;

    // 수정일
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    // 삭제 여부
    @Column(name = "is_deleted", nullable = false, columnDefinition = "default 0")
    private boolean isDeleted;

    protected MyBook() {}

    public MyBook(User user, Book book, MyBookStatus myBookStatus) {
        this.user = user;
        this.book = book;
        this.myBookStatus = myBookStatus;
        this.isDeleted = false;
    }
}
