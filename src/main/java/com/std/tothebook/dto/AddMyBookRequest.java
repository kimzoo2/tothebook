package com.std.tothebook.dto;

import com.std.tothebook.enums.MyBookStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "내 서재 등록 Request")
public class AddMyBookRequest {

    @Schema(description = "책 id")
    private long bookId;

    @Schema(description = "시작일")
    private LocalDate startDate;

    @Schema(description = "종료일")
    private LocalDate endDate;

    @Schema(description = "현재 페이지")
    private Integer page;

    @Schema(description = "별점")
    private Integer rating;

    @NotNull(message="독서상태는 필수 값 입니다.")
    @Schema(description = "독서상태")
    private MyBookStatus myBookStatus;

    @Schema(description = "수정일")
    private LocalDateTime updateDate;

}
