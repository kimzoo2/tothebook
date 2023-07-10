package com.std.tothebook.api.domain.dto;

import com.std.tothebook.api.enums.MyBookStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Schema(description = "내서재 커스텀 조회")
@AllArgsConstructor
public class FindMyBookResponse {

    @Schema(description = "ID")
    private long id;

    @Schema(description = "회원 ID")
    private long userId;

    @Schema(description = "책제목")
    private String title;

    @Schema(description = "저자")
    private String authors;

    @Schema(description = "출판사")
    private String publisher;

    @Schema(description = "시작일")
    private LocalDate startDate;

    @Schema(description = "종료일")
    private LocalDate endDate;

    @Schema(description = "별점")
    private int rating;

    @Schema(description = "현재 페이지")
    private int currentPage;

    @Schema(description = "총 페이지")
    private int totalPage;

    @Schema(description = "썸네일이미지")
    private String thumbnail;

    @Schema(description = "독서 상태")
    private MyBookStatus myBookStatus;

    public String getMyBookStatusDescription() {
        return this.myBookStatus.getDescription();
    }
}
