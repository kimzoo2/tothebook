package com.std.tothebook.api.domain.dto;

import com.std.tothebook.api.enums.MyBookStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "내 서재 변경 Request")
public class EditMyBookRequest {

    @Schema(description = "내 서재 id")
    private long id;

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

}