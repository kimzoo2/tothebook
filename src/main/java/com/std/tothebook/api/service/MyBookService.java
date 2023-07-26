package com.std.tothebook.api.service;

import com.std.tothebook.api.domain.dto.AddMyBookRequest;
import com.std.tothebook.api.domain.dto.FindMyBookResponse;
import com.std.tothebook.api.domain.dto.FindMyBooksResponse;
import com.std.tothebook.api.entity.Book;
import com.std.tothebook.api.entity.MyBook;
import com.std.tothebook.api.entity.User;
import com.std.tothebook.api.repository.BookRepository;
import com.std.tothebook.api.repository.MyBookRepository;
import com.std.tothebook.api.repository.UserRepository;
import com.std.tothebook.exception.ExpectedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyBookService {

    private final MyBookRepository myBookRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public List<FindMyBooksResponse> getMybooks(long userId){
        final var myBooks = myBookRepository.findMyBookByUserId(userId);

        return myBooks;
    }

    public FindMyBookResponse getMyBook(long id){

        Optional<FindMyBookResponse> optionalMyBook = myBookRepository.findMyBookById(id);

        // 요청한 user와 게시글 등록한 user 검증 필요

        if(optionalMyBook.isEmpty()){
            throw new ExpectedException(HttpStatus.BAD_REQUEST, id + ": 독서기록이 존재하지 않습니다.");
        }

        return optionalMyBook.get();
    }

    @Transactional
    public FindMyBookResponse addMyBook(AddMyBookRequest request){

        User user = userRepository.getReferenceById(request.getUserId());

        Book book = bookRepository.getReferenceById(request.getBookId());

        MyBook myBook = MyBook.builder()
                .user(user)
                .book(book)
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .page(request.getPage())
                .rating(request.getRating())
                .myBookStatus(request.getMyBookStatus())
                .build();

        MyBook savedMyBook = myBookRepository.save(myBook);

        return myBookRepository.findMyBookById(savedMyBook.getId())
                .orElseThrow(() -> new ExpectedException(HttpStatus.NOT_FOUND, "저장된 MyBook을 찾을 수 없습니다."));
    }
}
