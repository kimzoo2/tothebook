package com.std.tothebook.repository;

import static com.std.tothebook.enums.MyBookStatus.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.std.tothebook.entity.Book;
import com.std.tothebook.entity.Category;
import com.std.tothebook.entity.MyBook;
import com.std.tothebook.entity.User;
import com.std.tothebook.helper.TestQueryDslConfig;

@Import(TestQueryDslConfig.class)
@DataJpaTest
class MyBookRepositoryTest {

	@Autowired
	private MyBookRepository myBookRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Test
	@DisplayName("내서재를 조회한다_성공")
	void findByIdAndIsDeletedFalse_success() {
		// given
		User user = User.create()
			.email("test@test.com")
			.password("12345678")
			.nickname("testUser")
			.build();
		userRepository.save(user);
		Book book = Book.create()
			.category(createCategoryFixture())
			.title("모순")
			.authors("양귀자")
			.isbn("123456789")
			.publisher("쓰다")
			.publicationDate(LocalDate.now())
			.page(300)
			.contents("""
				『모순』은 작가 양귀자가 1998년 펴낸 세 번째 장편소설로,
				책이 나온 지 한 달 만에 무서운 속도로 베스트셀러 1위에 진입,
				출판계를 놀라게 하고 그해 최고의 베스트셀러로 자리 잡으면서
				‘양귀자 소설의 힘’을 다시 한 번 유감없이 보여준 소설이다.
				""")
			.createdBy(1L)
			.build();
		bookRepository.save(book);
		MyBook myBook = MyBook.create()
			.user(user)
			.book(book)
			.startDate(LocalDate.now())
			.page(100)
			.myBookStatus(ONGOING)
			.build();
		myBookRepository.save(myBook);

		// when
		MyBook foundMyBook
			= myBookRepository.findByIdAndIsDeletedFalse(myBook.getId()).get();

		// then
		assertThat(foundMyBook).isEqualTo(myBook);
	}

	@Test
	@DisplayName("내서재를 조회한다_실패")
	void findByIdAndIsDeletedFalse_fail() {
		// given
		User user = User.create()
			.email("test@test.com")
			.password("12345678")
			.nickname("testUser")
			.build();
		userRepository.save(user);
		Book book = Book.create()
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
				""")
			.createdBy(1L)
			.build();
		bookRepository.save(book);
		MyBook myBook = MyBook.create()
			.user(user)
			.book(book)
			.startDate(LocalDate.now())
			.page(100)
			.myBookStatus(ONGOING)
			.build();
		myBook.delete();
		myBookRepository.save(myBook);

		// when, then
		assertThat(myBookRepository.findByIdAndIsDeletedFalse(myBook.getId())).isEmpty();
	}

	@Test
	@DisplayName("내서재를 조회한다_0개_성공")
	void findMyBookByUserId_success_0(){
		// given
		User user = User.create()
			.email("test@test.com")
			.password("12345678")
			.nickname("testUser")
			.build();
		userRepository.save(user);
		Book book = Book.create()
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
				""")
			.createdBy(1L)
			.build();
		bookRepository.save(book);
		// when
		List<MyBook> foundMyBooks = myBookRepository.findMyBookByUserId(user.getId());

		// then
		assertThat(foundMyBooks).isEmpty();
	}

	@Test
	@DisplayName("내서재를 조회한다_n개_성공")
	void findMyBookByUserId_success_N(){
		// given
		User user = User.create()
			.email("test@test.com")
			.password("12345678")
			.nickname("testUser")
			.build();
		userRepository.save(user);
		Book book = Book.create()
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
				""")
			.createdBy(1L)
			.build();
		bookRepository.save(book);
		MyBook myBook1 = MyBook.create()
			.user(user)
			.book(book)
			.startDate(LocalDate.now().minusDays(10))
			.endDate(LocalDate.now().minusDays(5))
			.myBookStatus(COMPLETE)
			.build();
		MyBook myBook2 = MyBook.create()
			.user(user)
			.book(book)
			.startDate(LocalDate.now())
			.page(100)
			.myBookStatus(ONGOING)
			.build();
		myBookRepository.save(myBook1);
		myBookRepository.save(myBook2);
		
		// when
		List<MyBook> foundMyBooks = myBookRepository.findMyBookByUserId(user.getId());

		// then
		assertThat(foundMyBooks).hasSize(2);
	}

	@Test
	@DisplayName("내서재를 저장한다_성공")
	void save_success(){
		// given
		User user = User.create()
			.email("test@test.com")
			.password("12345678")
			.nickname("testUser")
			.build();
		userRepository.save(user);
		Book book = Book.create()
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
				""")
			.createdBy(1L)
			.build();
		bookRepository.save(book);
		MyBook myBook = MyBook.create()
			.user(user)
			.book(book)
			.startDate(LocalDate.now())
			.page(100)
			.myBookStatus(ONGOING)
			.build();

		// when
		myBookRepository.save(myBook);

		// then
		assertThat(myBookRepository.findByIdAndIsDeletedFalse(myBook.getId())).isNotEmpty();
	}

	@Test
	@DisplayName("내서재를 삭제한다_성공")
	void delete_success(){
		// given
		User user = User.create()
			.email("test@test.com")
			.password("12345678")
			.nickname("testUser")
			.build();
		userRepository.save(user);
		Book book = Book.create()
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
				""")
			.createdBy(1L)
			.build();
		bookRepository.save(book);
		MyBook myBook = MyBook.create()
			.user(user)
			.book(book)
			.startDate(LocalDate.now())
			.page(100)
			.myBookStatus(ONGOING)
			.build();
		myBookRepository.save(myBook);

		// when
		myBook.delete();

		// then
		MyBook foundBook = myBookRepository.findById(myBook.getId()).get();
		assertThat(foundBook.isDeleted()).isTrue();
	}

	private Category createCategoryFixture()  {
		Category 한국소설 = Category.create().categoryName("한국소설").build();
		categoryRepository.save(한국소설);
		return 한국소설;
	}

}
