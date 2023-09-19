package com.std.tothebook.service;

import com.std.tothebook.dto.AddMyBookRequest;
import com.std.tothebook.dto.EditMyBookRequest;
import com.std.tothebook.dto.FindMyBookResponse;
import com.std.tothebook.dto.FindMyBooksResponse;
import com.std.tothebook.entity.Book;
import com.std.tothebook.entity.MyBook;
import com.std.tothebook.entity.User;
import com.std.tothebook.repository.BookRepository;
import com.std.tothebook.repository.MyBookRepository;
import com.std.tothebook.repository.UserRepository;
import com.std.tothebook.config.JwtTokenProvider;
import com.std.tothebook.exception.ExpectedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyBookService {

    private final MyBookRepository myBookRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final JwtTokenProvider jwtTokenProvider;


    public List<FindMyBooksResponse> getMyBooks(){
        long userId = getUserId();
        final var myBooks = myBookRepository.findMyBookByUserId(userId);

        return myBooks;
    }

    public FindMyBookResponse getMyBook(long id){

        FindMyBookResponse findMyBookResponse = myBookRepository.findMyBookById(id)
                .orElseThrow(() -> new ExpectedException(HttpStatus.BAD_REQUEST, id + ": 독서기록이 존재하지 않습니다."));

        validateAccess(findMyBookResponse.getUserId());

        return findMyBookResponse;
    }

    @Transactional
    public FindMyBookResponse addMyBook(AddMyBookRequest request){

        User user = userRepository.getReferenceById(getUserId());

        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new ExpectedException(HttpStatus.BAD_REQUEST, request.getBookId() + "책이 존재하지 않습니다."));

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
                .orElseThrow(() -> new ExpectedException(HttpStatus.NOT_FOUND, "저장된 독서 기록을 찾을 수 없습니다."));
    }

    @Transactional
    public void updateMyBook(EditMyBookRequest request){
        MyBook myBook = myBookRepository.findById(request.getId())
                .orElseThrow(() -> new ExpectedException(HttpStatus.NOT_FOUND, "저장된 독서 기록을 찾을 수 없습니다."));

        validateAccess(myBook.getUser().getId());

        myBook.updateMyBook(
                request.getStartDate()
                , request.getEndDate()
                , request.getPage()
                , request.getRating()
                , request.getMyBookStatus());
    }

    @Transactional
    public void deleteMyBook(long myBookId){
       MyBook myBook = myBookRepository.findById(myBookId)
               .orElseThrow(() -> new ExpectedException(HttpStatus.NOT_FOUND, "저장된 독서 기록을 찾을 수 없습니다."));

       validateAccess(myBook.getUser().getId());
       myBook.deleteMyBookById();

    }

    private long getUserId(){
        return jwtTokenProvider.getUserId();
    }

    private void validateAccess(long userId){
        if(userId != getUserId()){
            throw new ExpectedException(HttpStatus.FORBIDDEN, "잘못된 접근입니다.");
        }
    }
}