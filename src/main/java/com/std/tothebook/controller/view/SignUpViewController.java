package com.std.tothebook.controller.view;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "회원가입 화면 컨트롤러")
@Controller
@RequestMapping("/view/sign-up")
public class SignUpViewController {

    @GetMapping("")
    public String singUpForm() {
        return "app/signUp/form";
    }
}
