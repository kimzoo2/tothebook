package com.std.tothebook.entity;

import com.std.tothebook.enums.MyBookStatus;
import lombok.Builder;
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
    private Integer page;

    // 별점
    @Column(name = "rating")
    private Integer rating;

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

    @Builder
    public MyBook(User user, Book book, LocalDate startDate, LocalDate endDate, Integer page, Integer rating, MyBookStatus myBookStatus, LocalDateTime createdDate, LocalDateTime updateDate) {
        this.user = user;
        this.book = book;
        this.startDate = startDate;
        this.endDate = endDate;
        this.page = page;
        this.rating = rating;
        this.myBookStatus = myBookStatus;
        this.createdDate = LocalDateTime.now();
        this.isDeleted = false;
    }

    public void updateMyBook(LocalDate startDate, LocalDate endDate, Integer page, Integer rating, MyBookStatus myBookStatus) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.page = page;
        this.rating = rating;
        this.myBookStatus = myBookStatus;
        this.updateDate = LocalDateTime.now();
    }

    public void deleteMyBookById(){
        this.updateDate = LocalDateTime.now();
        this.isDeleted = true;
    }
}