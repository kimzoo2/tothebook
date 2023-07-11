package com.std.tothebook.api.controller.view;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "로그인 화면 컨트롤러")
@Controller
@RequestMapping("/view/sign-in")
public class SignInViewController {

    @GetMapping("")
    public String loginForm() {
        return "app/login/form";
    }

}
