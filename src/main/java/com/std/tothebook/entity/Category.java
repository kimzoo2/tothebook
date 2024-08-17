package com.std.tothebook.entity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@Table(name="category")
public class Category {

    // id
    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // 카테고리명
    @Column(name = "category_name", nullable = false)
    private String categoryName;

    // 등록일
    @CreationTimestamp
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    // 삭제여부
    @ColumnDefault("false")
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    protected Category() { }

    @Builder(builderMethodName = "create")
    public Category(String categoryName) {
        this.categoryName = categoryName;
        this.createdDate = LocalDateTime.now();
        this.isDeleted = false;
    }

}
