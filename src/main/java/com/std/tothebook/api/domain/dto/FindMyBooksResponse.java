package com.std.tothebook.api.domain.dto;

import com.std.tothebook.api.enums.MyBookStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Schema(description = "내서재 커스텀 리스트 조회")
@AllArgsConstructor
public class FindMyBooksResponse {

    @Schema(description = "ID")
    private long id;

    @Schema(description = "책제목")
    private String title;

    @Schema(description = "저자")
    private String authors;

    @Schema(description = "출판사")
    private String publisher;

    @Schema(description = "썸네일이미지")
    private String thumbnail;

    @Schema(description = "시작일")
    private LocalDate startDate;

    @Schema(description = "종료일")
    private LocalDate endDate;

    @Schema(description = "독서 상태")
    private MyBookStatus myBookStatus;

}
