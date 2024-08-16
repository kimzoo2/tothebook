package com.std.tothebook.service;

import com.std.tothebook.dto.AddMyBookRequest;
import com.std.tothebook.dto.EditMyBookRequest;
import com.std.tothebook.dto.FindMyBookDetailResponse;
import com.std.tothebook.dto.FindMyBooksResponse;
import com.std.tothebook.entity.Book;
import com.std.tothebook.entity.MyBook;
import com.std.tothebook.entity.User;
import com.std.tothebook.repository.BookRepository;
import com.std.tothebook.repository.MyBookRepository;
import com.std.tothebook.repository.UserRepository;
import com.std.tothebook.exception.ExpectedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
// TODO : 작성자와 로그인한 유저간 일치 여부는 User 객체 비교하도록 변경
public class MyBookService {

    private final MyBookRepository myBookRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public FindMyBooksResponse getMyBooks(long userId){
        List<MyBook> foundMyBooks = myBookRepository.findMyBookByUserId(userId);
        return FindMyBooksResponse.from(foundMyBooks);
    }

    public FindMyBookDetailResponse getMyBook(long myBookId, long userId){
        MyBook foundMyBook = myBookRepository.findByIdAndIsDeletedFalse(myBookId)
                .orElseThrow(() -> new ExpectedException(HttpStatus.BAD_REQUEST, myBookId + ": 독서기록이 존재하지 않습니다."));
        // validateAccess(userId);
        return FindMyBookDetailResponse.from(foundMyBook);
    }

    @Transactional
    public FindMyBookDetailResponse addMyBook(AddMyBookRequest request, long userId){

        User user = userRepository.getReferenceById(userId);

        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new ExpectedException(HttpStatus.BAD_REQUEST, request.getBookId() + "책이 존재하지 않습니다."));

        MyBook myBook = MyBook.create()
                .user(user)
                .book(book)
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .page(request.getPage())
                .rating(request.getRating())
                .myBookStatus(request.getMyBookStatus())
                .build();

        MyBook savedMyBook = myBookRepository.save(myBook);

        return FindMyBookDetailResponse.from(savedMyBook);
    }

    @Transactional
    public void updateMyBook(EditMyBookRequest request){
        MyBook myBook = myBookRepository.findByIdAndIsDeletedFalse(request.getId())
                .orElseThrow(() -> new ExpectedException(HttpStatus.NOT_FOUND, "저장된 독서 기록을 찾을 수 없습니다."));

        // validateAccess(myBook.getUser().getId());

        myBook.update(
                request.getStartDate()
                , request.getEndDate()
                , request.getPage()
                , request.getRating()
                , request.getMyBookStatus());
    }

    @Transactional
    public void deleteMyBook(long myBookId){
       MyBook myBook = myBookRepository.findByIdAndIsDeletedFalse(myBookId)
               .orElseThrow(() -> new ExpectedException(HttpStatus.NOT_FOUND, "저장된 독서 기록을 찾을 수 없습니다."));

       // validateAccess(myBook.getUser().getId());
       myBook.delete();
    }
}
