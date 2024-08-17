package com.std.tothebook.controller.api;

import com.std.tothebook.dto.AddMyBookRequest;
import com.std.tothebook.dto.EditMyBookRequest;
import com.std.tothebook.dto.FindMyBookDetailResponse;
import com.std.tothebook.dto.FindMyBooksResponse;
import com.std.tothebook.service.MyBookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "내 서재")
@RequestMapping("/api/my-book")
@RestController
@RequiredArgsConstructor
//TODO : ArgumentResolver로 로그인한 대상의 객체 가져오도록 변경
public class MyBookController {

    private final MyBookService myBookService;

    @Operation(summary = "내서재 리스트 조회")
    @GetMapping
    public ResponseEntity<FindMyBooksResponse> getMyBooks(long userId){
        return ResponseEntity.ok(myBookService.getMyBooks(userId));
    }

    @Operation(summary = "내서재 상세 조회")
    @GetMapping("/{myBookId}")
    public ResponseEntity<FindMyBookDetailResponse> getMyBook(@PathVariable long myBookId, long userId){
        return ResponseEntity.ok(myBookService.getMyBook(myBookId, userId));
    }

    @Operation(summary = "내서재 등록")
    @PostMapping
    public ResponseEntity<Void> addMyBook(@Valid @RequestBody AddMyBookRequest request, long userId){
        myBookService.addMyBook(request, userId);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "내서재 수정")
    @PutMapping
    public ResponseEntity<Void> updateMyBook(@Valid @RequestBody EditMyBookRequest request){
        myBookService.updateMyBook(request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "내서재 삭제")
    @DeleteMapping("/{myBookId}")
    public ResponseEntity<Void> deleteMyBook(@PathVariable long myBookId){
        myBookService.deleteMyBook(myBookId);
        return ResponseEntity.ok().build();
    }

}
