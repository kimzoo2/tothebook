package com.std.tothebook.api.controller.api;

import com.std.tothebook.api.domain.dto.AddMyBookRequest;
import com.std.tothebook.api.domain.dto.EditMyBookRequest;
import com.std.tothebook.api.domain.dto.FindMyBookResponse;
import com.std.tothebook.api.service.MyBookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "독서기록")
@RequestMapping("/api/myBook")
@Controller
@RequiredArgsConstructor
public class MyBookController {

    private final MyBookService myBookService;

    @Operation(summary = "독서기록 리스트 조회")
    @GetMapping("")
    public String getMyBooks(Model model){
        final var myBooks = myBookService.getMyBooks();

        model.addAttribute("myBooks", myBooks);

        return "app/myBook/myBooks";
    }

    @Operation(summary = "독서기록 상세 조회")
    @GetMapping("/{id}")
    public ResponseEntity<FindMyBookResponse> getMyBook(@PathVariable long id){
        final var myBook = myBookService.getMyBook(id);

        return ResponseEntity.ok(myBook);
    }

    @Operation(summary = "독서기록 등록")
    @PostMapping("")
    public ResponseEntity<Void> addMyBook(@RequestBody AddMyBookRequest request){
        final var myBook = myBookService.addMyBook(request);

        return ResponseEntity.ok().build();
    }


    @Operation(summary = "독서기록 수정")
    @PutMapping("")
    public ResponseEntity<Void> updateMyBook(@Valid @RequestBody EditMyBookRequest request){
        myBookService.updateMyBook(request);
        return ResponseEntity.ok().build();
    }
}
