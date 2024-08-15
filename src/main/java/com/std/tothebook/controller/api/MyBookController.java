package com.std.tothebook.controller.api;

import com.std.tothebook.dto.AddMyBookRequest;
import com.std.tothebook.dto.EditMyBookRequest;
import com.std.tothebook.dto.FindMyBookResponse;
import com.std.tothebook.dto.FindMyBooksResponse;
import com.std.tothebook.service.MyBookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "내 서재")
@RequestMapping("/api/myBook")
@RestController
@RequiredArgsConstructor
//TODO : ArgumentResolver로 로그인한 대상의 객체 가져오도록 변경
public class MyBookController {

    private final MyBookService myBookService;

    @Operation(summary = "독서기록 리스트 조회")
    @GetMapping("")
    public ResponseEntity<List<FindMyBooksResponse>> getMyBooks(long userId){
        final var myBooks = myBookService.getMyBooks(userId);
        return ResponseEntity.ok(myBooks);
    }

    @Operation(summary = "독서기록 상세 조회")
    @GetMapping("/{myBookId}")
    public ResponseEntity<FindMyBookResponse> getMyBook(@PathVariable long myBookId, long userId){
        return ResponseEntity.ok(myBookService.getMyBook(myBookId, userId));
    }

    @Operation(summary = "독서기록 등록")
    @PostMapping("")
    public ResponseEntity<Void> addMyBook(@Valid @RequestBody AddMyBookRequest request, long userId){
        myBookService.addMyBook(request, userId);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "독서기록 수정")
    @PutMapping("")
    public ResponseEntity<Void> updateMyBook(@Valid @RequestBody EditMyBookRequest request){
        myBookService.updateMyBook(request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "독서기록 삭제")
    @DeleteMapping("/{myBookId}")
    public ResponseEntity<Void> deleteMyBook(@PathVariable long myBookId){
        myBookService.deleteMyBook(myBookId);
        return ResponseEntity.ok().build();
    }

}
