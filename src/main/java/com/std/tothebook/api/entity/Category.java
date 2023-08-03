package com.std.tothebook.api.entity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="category")
public class Category {

    // id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // 카테고리명
    @Column(name = "category_name", nullable = false)
    private String categoryName;

    // 등록일
    @Column(name = "created_date", nullable = false, columnDefinition = "default now()")
    private LocalDateTime createdDate;

    // 삭제여부
    @Column(name = "is_deleted", nullable = false, columnDefinition = "default 0")
    private boolean isDeleted;

    protected Category() { }

    @Builder(builderMethodName = "create")
    public Category(String categoryName) {
        this.categoryName = categoryName;
        this.createdDate = LocalDateTime.now();
        this.isDeleted = false;
    }

}
