package com.std.tothebook.service;

import static com.std.tothebook.enums.MyBookStatus.*;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.BDDAssertions.*;
import static org.assertj.core.api.SoftAssertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.std.tothebook.dto.AddMyBookRequest;
import com.std.tothebook.dto.EditMyBookRequest;
import com.std.tothebook.dto.FindMyBookDetailResponse;
import com.std.tothebook.dto.FindMyBooksResponse;
import com.std.tothebook.entity.Book;
import com.std.tothebook.entity.Category;
import com.std.tothebook.entity.MyBook;
import com.std.tothebook.entity.User;
import com.std.tothebook.exception.ExpectedException;
import com.std.tothebook.repository.BookRepository;
import com.std.tothebook.repository.MyBookRepository;
import com.std.tothebook.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class MyBookServiceTest {

	@InjectMocks
	private MyBookService myBookService;

	@Mock
	private MyBookRepository myBookRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private BookRepository bookRepository;

	@Test
	@DisplayName("내서재 리스트를 조회한다_성공_0개")
	void getMyBooks_success_0() {
		// given
		User 테스트유저 = User.create()
			.email("test@test.com")
			.password("12345678")
			.nickname("테스트유저")
			.build();
		List<MyBook> mockMyBooks = new ArrayList<>();
		given(myBookRepository.findMyBookByUserId(anyLong()))
			.willReturn(mockMyBooks);

		// when
		FindMyBooksResponse response = myBookService.getMyBooks(테스트유저.getId());

		// then
		assertThat(response.getFindMyBookResponseList()).isEmpty();
		verify(myBookRepository, times(1)).findMyBookByUserId(anyLong());
	}

	@Test
	@DisplayName("내서재 리스트를 조회한다_성공_N개")
	void getMyBooks_success_N() {
		// given
		User 테스트유저 = User.create()
			.email("test@test.com")
			.password("12345678")
			.nickname("테스트유저")
			.build();
		Book 모순 = Book.create()
			.category(createCategoryFixture())
			.title("모순")
			.authors("양귀자")
			.isbn("123456789")
			.publisher("쓰다")
			.publicationDate(LocalDate.now())
			.page(300)
			.contents(
				"""
					『모순』은 작가 양귀자가 1998년 펴낸 세 번째 장편소설로,
					책이 나온 지 한 달 만에 무서운 속도로 베스트셀러 1위에 진입,
					출판계를 놀라게 하고 그해 최고의 베스트셀러로 자리 잡으면서
					‘양귀자 소설의 힘’을 다시 한 번 유감없이 보여준 소설이다.
				"""
			)
			.createdBy(1L)
			.build();
		List<MyBook> mockMyBooks = new ArrayList<>();
		mockMyBooks.add(
			MyBook.create()
				.user(테스트유저)
				.book(모순)
				.startDate(LocalDate.now().minusDays(10))
				.endDate(LocalDate.now().minusDays(5))
				.myBookStatus(COMPLETE)
				.build()
		);
		mockMyBooks.add(
			MyBook.create().user(테스트유저).book(모순).startDate(LocalDate.now()).myBookStatus(ONGOING).build()
		);
		given(myBookRepository.findMyBookByUserId(anyLong()))
			.willReturn(mockMyBooks);

		// when
		FindMyBooksResponse response = myBookService.getMyBooks(테스트유저.getId());

		// then
		assertThat(response.getFindMyBookResponseList()).hasSize(2);
		verify(myBookRepository, times(1)).findMyBookByUserId(anyLong());
	}

	@Test
	@DisplayName("내서재 상세를 조회한다_성공")
	void getMyBook_success() {
		// given
		User 테스트유저 = User.create()
			.email("test@test.com")
			.password("12345678")
			.nickname("테스트유저")
			.build();
		Book 모순 = Book.create()
			.category(createCategoryFixture())
			.title("모순")
			.authors("양귀자")
			.isbn("123456789")
			.publisher("쓰다")
			.publicationDate(LocalDate.now())
			.page(300)
			.contents(
				"""
					『모순』은 작가 양귀자가 1998년 펴낸 세 번째 장편소설로,
					책이 나온 지 한 달 만에 무서운 속도로 베스트셀러 1위에 진입,
					출판계를 놀라게 하고 그해 최고의 베스트셀러로 자리 잡으면서
					‘양귀자 소설의 힘’을 다시 한 번 유감없이 보여준 소설이다.
				"""
			)
			.createdBy(1L)
			.build();
		MyBook mockMyBook = MyBook.create()
			.user(테스트유저)
			.book(모순)
			.startDate(LocalDate.now().minusDays(10))
			.endDate(LocalDate.now().minusDays(5))
			.myBookStatus(COMPLETE)
			.build();

		given(myBookRepository.findByIdAndIsDeletedFalse(anyLong()))
			.willReturn(Optional.ofNullable(mockMyBook));

		// when
		FindMyBookDetailResponse response = myBookService.getMyBook(1L, 1L);

		// then
		assertThat(response).isEqualTo(FindMyBookDetailResponse.from(mockMyBook));
		verify(myBookRepository, times(1)).findByIdAndIsDeletedFalse(anyLong());
	}

	@Test
	@DisplayName("내서재 상세를 조회한다_실패")
	void getMyBook_fail() {
		// given
		given(myBookRepository.findByIdAndIsDeletedFalse(anyLong()))
			.willReturn(Optional.empty());

		// when, then
		assertThatThrownBy(() -> myBookService.getMyBook(1L, 1L))
			.isInstanceOf(ExpectedException.class)
			.hasMessageContaining("독서기록이 존재하지 않습니다.");
		verify(myBookRepository, times(1)).findByIdAndIsDeletedFalse(anyLong());
	}

	@Test
	@DisplayName("내서재를 등록한다_성공")
	void addMyBook_success() {
		// given
		User 테스트유저 = User.create()
			.email("test@test.com")
			.password("12345678")
			.nickname("테스트유저")
			.build();
		Book 모순 = Book.create()
			.category(createCategoryFixture())
			.title("모순")
			.authors("양귀자")
			.isbn("123456789")
			.publisher("쓰다")
			.publicationDate(LocalDate.now())
			.page(300)
			.contents(
				"""
					『모순』은 작가 양귀자가 1998년 펴낸 세 번째 장편소설로,
					책이 나온 지 한 달 만에 무서운 속도로 베스트셀러 1위에 진입,
					출판계를 놀라게 하고 그해 최고의 베스트셀러로 자리 잡으면서
					‘양귀자 소설의 힘’을 다시 한 번 유감없이 보여준 소설이다.
				"""
			)
			.createdBy(1L)
			.build();
		MyBook mockMyBook = MyBook.create()
			.user(테스트유저)
			.book(모순)
			.startDate(LocalDate.now().minusDays(10))
			.endDate(LocalDate.now().minusDays(5))
			.myBookStatus(COMPLETE)
			.build();
		AddMyBookRequest request =
			new AddMyBookRequest(1L, LocalDate.now(), null, 30, null, ONGOING, null);

		given(myBookRepository.save(any())).willReturn(mockMyBook);
		given(userRepository.getReferenceById(anyLong())).willReturn(테스트유저);
		given(bookRepository.findById(anyLong())).willReturn(Optional.ofNullable(모순));

		// when
		myBookService.addMyBook(request, 1L);

		// then
		verify(myBookRepository, times(1)).save(any());
	}

	@Test
	@DisplayName("내서재를 등록한다_실패_책없음")
	void addMyBook_fail_noBook() {
		// given
		User 테스트유저 = User.create()
			.email("test@test.com")
			.password("12345678")
			.nickname("테스트유저")
			.build();
		AddMyBookRequest request =
			new AddMyBookRequest(1L, LocalDate.now(), null, 30, null, ONGOING, null);

		given(userRepository.getReferenceById(anyLong())).willReturn(테스트유저);
		given(bookRepository.findById(anyLong())).willReturn(Optional.empty());

		// when, then
		thenThrownBy(() -> myBookService.addMyBook(request, 1L))
			.isInstanceOf(ExpectedException.class)
			.hasMessageContaining("책이 존재하지 않습니다.");
		verify(myBookRepository, times(0)).save(any());
	}

	@Test
	@DisplayName("내서재를 수정한다_성공")
	void updateMyBook_success() {
		// given
		User 테스트유저 = User.create()
			.email("test@test.com")
			.password("12345678")
			.nickname("테스트유저")
			.build();
		Book 모순 = Book.create()
			.category(createCategoryFixture())
			.title("모순")
			.authors("양귀자")
			.isbn("123456789")
			.publisher("쓰다")
			.publicationDate(LocalDate.now())
			.page(300)
			.contents(
				"""
					『모순』은 작가 양귀자가 1998년 펴낸 세 번째 장편소설로,
					책이 나온 지 한 달 만에 무서운 속도로 베스트셀러 1위에 진입,
					출판계를 놀라게 하고 그해 최고의 베스트셀러로 자리 잡으면서
					‘양귀자 소설의 힘’을 다시 한 번 유감없이 보여준 소설이다.
				"""
			)
			.createdBy(1L)
			.build();
		MyBook mockMyBook = MyBook.create()
			.user(테스트유저)
			.book(모순)
			.startDate(LocalDate.now())
			.page(100)
			.myBookStatus(ONGOING)
			.build();
		EditMyBookRequest request =
			new EditMyBookRequest(1L, LocalDate.now(), LocalDate.now(), 300, 3, COMPLETE);

		given(myBookRepository.findByIdAndIsDeletedFalse(anyLong())).willReturn(Optional.ofNullable(mockMyBook));

		// when
		myBookService.updateMyBook(request);

		// then
		assertSoftly(softly -> {
			softly.assertThat(mockMyBook.getEndDate()).isEqualTo(request.getEndDate());
			softly.assertThat(mockMyBook.getPage()).isEqualTo(request.getPage());
			softly.assertThat(mockMyBook.getRating()).isEqualTo(request.getRating());
			softly.assertThat(mockMyBook.getMyBookStatus()).isEqualTo(request.getMyBookStatus());
		});
	}

	@Test
	@DisplayName("내서재를 수정한다_실패")
	void updateMyBook_fail() {
		// given
		EditMyBookRequest request =
			new EditMyBookRequest(1L, LocalDate.now(), LocalDate.now(), 300, 3, COMPLETE);

		given(myBookRepository.findByIdAndIsDeletedFalse(anyLong())).willReturn(Optional.empty());

		// when, then
		thenThrownBy(() -> myBookService.updateMyBook(request))
			.isInstanceOf(ExpectedException.class)
			.hasMessageContaining("저장된 독서 기록을 찾을 수 없습니다.");
	}

	@Test
	@DisplayName("내서재를 삭제한다_성공")
	void deleteMyBook_success() {
		// given
		User 테스트유저 = User.create()
			.email("test@test.com")
			.password("12345678")
			.nickname("테스트유저")
			.build();
		Book 모순 = Book.create()
			.category(createCategoryFixture())
			.title("모순")
			.authors("양귀자")
			.isbn("123456789")
			.publisher("쓰다")
			.publicationDate(LocalDate.now())
			.page(300)
			.contents(
				"""
					『모순』은 작가 양귀자가 1998년 펴낸 세 번째 장편소설로,
					책이 나온 지 한 달 만에 무서운 속도로 베스트셀러 1위에 진입,
					출판계를 놀라게 하고 그해 최고의 베스트셀러로 자리 잡으면서
					‘양귀자 소설의 힘’을 다시 한 번 유감없이 보여준 소설이다.
				"""
			)
			.createdBy(1L)
			.build();
		MyBook mockMyBook = MyBook.create()
			.user(테스트유저)
			.book(모순)
			.startDate(LocalDate.now())
			.page(100)
			.myBookStatus(ONGOING)
			.build();

		given(myBookRepository.findByIdAndIsDeletedFalse(anyLong())).willReturn(Optional.ofNullable(mockMyBook));

		// when
		myBookService.deleteMyBook(1L);

		// then
		assertThat(mockMyBook.isDeleted()).isTrue();
	}

	@Test
	@DisplayName("내서재를 삭제한다_실패")
	void deleteMyBook_fail() {
		// given
		given(myBookRepository.findByIdAndIsDeletedFalse(anyLong())).willReturn(Optional.empty());

		// when, then
		thenThrownBy(() -> myBookService.deleteMyBook(1L))
			.isInstanceOf(ExpectedException.class)
			.hasMessageContaining("저장된 독서 기록을 찾을 수 없습니다.");
	}

	private Category createCategoryFixture() {
		Category 한국소설 = Category.create().categoryName("한국소설").build();
		return 한국소설;
	}

}
