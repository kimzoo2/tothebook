package com.std.tothebook.api.entity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name="book")
public class Book {

    //id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // 카테고리 번호
    @OneToOne
    @JoinColumn(name = "id", nullable = false)
    private Category category;

    // 제목
    @Column(name = "title", nullable = false)
    private String title;

    // 저자
    @Column(name = "authors")
    private String authors;

    // ISBN
    @Column(name = "isbn", nullable = false)
    private String isbn;

    // 출판사
    @Column(name = "publisher")
    private String publisher;

    // 출판일
    @Column(name = "publication_date")
    private LocalDate publicationDate;

    // 페이지
    @Column(name = "page")
    private int page;

    // 설명
    @Column(name = "contents", columnDefinition = "TEXT")
    private String contents;

    // 이미지
    @Column(name = "thumbnail")
    private String thumbnail;

    // 등록일
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    // 등록자
    @Column(name = "created_by")
    private long createdBy;

    // 수정일
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    // 삭제여부
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @OneToMany(mappedBy = "book")
    private List<MyBook> myBooks = new ArrayList<>();

    protected Book() {}

    @Builder(builderMethodName = "create")
    public Book(Category category, String title, String isbn) {
        this.category = category;
        this.title = title;
        this.isbn = isbn;
    }

}
