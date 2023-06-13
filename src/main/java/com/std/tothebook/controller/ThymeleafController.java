package com.std.tothebook.controller;

import com.std.tothebook.api.entity.User;
import com.std.tothebook.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ThymeleafController {

    private final UserService userService;

    @GetMapping("/")
    public String index() {
        return "viewtest/index";
    }

    @GetMapping("/text")
    public String text(Model model) {

        User user = userService.getUser(1);
        model.addAttribute("user", user);

        return "viewtest/grammar/text";
    }

}
