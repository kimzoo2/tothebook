package com.std.tothebook.controller;

import com.std.tothebook.api.entity.User;
import com.std.tothebook.api.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
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

        log.info("log test");

        return "viewtest/grammar/text";
    }

    @GetMapping("/ifelse")
    public String ifElse(Model model) {

        User user = userService.getUser(1);
        model.addAttribute("user", user);

        return "viewtest/grammar/ifelse";
    }

    @GetMapping("/each")
    public String each(Model model) {

        List<User> users = userService.getUsers();
        model.addAttribute("users", users);

        return "viewtest/grammar/each";
    }

}
