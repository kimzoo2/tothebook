package com.std.tothebook.api.domain.dto;

import com.std.tothebook.api.entity.MyBook;
import com.std.tothebook.api.enums.MyBookStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "내 서재 Request")
public class AddMyBookRequest {

    @Schema(description = "고객 id")
    private long userId;

    @Schema(description = "책 id")
    private long bookId;

    // 시작일
    @Schema(description = "시작일")
    private LocalDate startDate;

    // 종료일
    @Schema(description = "종료일")
    private LocalDate endDate;

    // 현재 페이지
    @Schema(description = "현재 페이지")
    private Integer page;

    // 별점
    @Schema(description = "별점")
    private Integer rating;

    // 독서 상태
    @Schema(description = "독서상태")
    private MyBookStatus myBookStatus;

    // 등록일
    @Schema(description = "등록일")
    private LocalDateTime createdDate;

    // 수정일
    @Schema(description = "수정일")
    private LocalDateTime updateDate;

}
