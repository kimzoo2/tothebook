package com.std.tothebook.controller.view;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "메인 화면 컨트롤러")
@Controller
@RequestMapping("/main")
public class MainViewController {

    @GetMapping("")
    public String main() {
        return "index";
    }
}
