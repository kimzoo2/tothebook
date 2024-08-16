package com.std.tothebook.dto;

import com.std.tothebook.entity.MyBook;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
@Schema(description = "내서재 커스텀 리스트 조회")
@AllArgsConstructor
public class FindMyBooksResponse {

	@Schema(description = "내서재 리스트")
	private List<FindMyBookResponse> findMyBookResponseList;

	public static FindMyBooksResponse from(List<MyBook> books) {
		if(books.isEmpty()){
			return new FindMyBooksResponse(Collections.emptyList());
		}
		return new FindMyBooksResponse
			(
				books.stream()
					.map(FindMyBookResponse::from)
					.toList()
			);
	}
}
