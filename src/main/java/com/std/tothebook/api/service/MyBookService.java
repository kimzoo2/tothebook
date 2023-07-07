package com.std.tothebook.api.service;

import com.std.tothebook.api.domain.dto.FindMyBookResponse;
import com.std.tothebook.api.domain.dto.FindMyBooksResponse;
import com.std.tothebook.api.repository.MyBookCustomRepository;
import com.std.tothebook.exception.ExpectedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyBookService {

    private final MyBookCustomRepository myBookCustomRepository;

    public List<FindMyBooksResponse> getMybooks(long userId){
        var myBooks = myBookCustomRepository.findMyBookByUserId(userId);

        return myBooks;
    }

    public FindMyBookResponse getMyBook(long id){

        Optional<FindMyBookResponse> optionalMyBook = myBookCustomRepository.findSimpleMyBook(id);

        // 요청한 user와 게시글 등록한 user 검증 필요

        if(optionalMyBook.isEmpty()){
            throw new ExpectedException(HttpStatus.BAD_REQUEST, id + ": 독서기록이 존재하지 않습니다.");
        }

        return optionalMyBook.get();
    }
}
