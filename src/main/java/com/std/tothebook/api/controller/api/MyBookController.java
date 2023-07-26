package com.std.tothebook.api.controller.api;

import com.std.tothebook.api.domain.dto.AddMyBookRequest;
import com.std.tothebook.api.domain.dto.FindMyBookResponse;
import com.std.tothebook.api.entity.MyBook;
import com.std.tothebook.api.service.MyBookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Tag(name = "독서기록")
@RequestMapping("/api/myBook")
@Controller
@RequiredArgsConstructor
public class MyBookController {

    private final MyBookService myBookService;

    @Operation(summary = "독서기록 리스트 조회")
    @GetMapping("")
    public String getMyBooks(Model model){
        long userId = 1L;
        final var myBooks = myBookService.getMybooks(userId);

        model.addAttribute("myBooks", myBooks);

        return "app/myBook/myBooks";
    }

    @Operation(summary = "독서기록 상세 조회")
    @GetMapping("/{id}")
    public ResponseEntity<FindMyBookResponse> getMyBook(@PathVariable long id, Model model){

        final var myBook = myBookService.getMyBook(id);

        model.addAttribute("myBook", myBook);

        // 화면 수정 중이라 ResponseEntity 형태로 return
        // 화면 수정 완료 후, viewPath로 변경할 예정
        return ResponseEntity.ok(myBook);
    }

    @Operation(summary = "독서기록 등록")
    @PostMapping("")
    public ResponseEntity<Void> addMyBook(@RequestBody AddMyBookRequest request){
        final var myBook = myBookService.addMyBook(request);

        // 상세 화면으로 redirect하도록 변경 예정
//        redirectAttributes.addAttribute("id", myBook.getId());
//        return "redirect:/basic/items/{itemId}";

        return ResponseEntity.ok().build();
    }
}
