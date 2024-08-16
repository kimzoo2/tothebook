package com.std.tothebook.dto;

import java.time.LocalDate;

import com.std.tothebook.entity.Book;
import com.std.tothebook.entity.MyBook;
import com.std.tothebook.enums.MyBookStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;



@Getter
@Schema(description = "내서재 리스트 조회")
@AllArgsConstructor
public class FindMyBookResponse {

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

	public static FindMyBookResponse from(MyBook myBook) {
		Book abook = myBook.getBook();
		return new FindMyBookResponse
			(
				myBook.getId(),
				abook.getTitle(),
				abook.getAuthors(),
				abook.getPublisher(),
				abook.getThumbnail(),
				myBook.getStartDate(),
				myBook.getEndDate(),
				myBook.getMyBookStatus()
			);
	}

}
