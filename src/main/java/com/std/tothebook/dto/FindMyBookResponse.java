package com.std.tothebook.dto;

import com.std.tothebook.entity.Book;
import com.std.tothebook.entity.MyBook;
import com.std.tothebook.enums.MyBookStatus;

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

	public static FindMyBookResponse from(MyBook myBook) {
		Book aBook = myBook.getBook();
		return new FindMyBookResponse(
			myBook.getId()
			, aBook.getTitle()
			, aBook.getAuthors()
			, aBook.getPublisher()
			, myBook.getStartDate()
			, myBook.getEndDate()
			, myBook.getRating()
			, myBook.getPage()
			, aBook.getPage()
			, aBook.getThumbnail()
			, myBook.getMyBookStatus());
	}

	public String getMyBookStatusDescription() {
		return this.myBookStatus.getDescription();
	}
}
