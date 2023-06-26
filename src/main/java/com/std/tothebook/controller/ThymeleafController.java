package com.std.tothebook.controller;

import com.std.tothebook.api.domain.dto.AddUserRequest;
import com.std.tothebook.api.entity.User;
import com.std.tothebook.api.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String th_text(Model model) {

        User user = userService.getUser(1);
        model.addAttribute("user", user);

        log.info("{}, log test", user);

        return "viewtest/grammar/text";
    }

    @GetMapping("/ifelse")
    public String th_ifElse(Model model) {

        User user = userService.getUser(1);
        model.addAttribute("user", user);

        log.debug("log test");

        return "viewtest/grammar/ifelse";
    }

    @GetMapping("/each")
    public String th_each(Model model) {

        List<User> users = userService.getUsers();
        model.addAttribute("users", users);

        return "viewtest/grammar/each";
    }

    @PostMapping("/add")
    public String addUser(AddUserRequest user) {

        //userService.addUser(user);
        log.info("email = {} nickname= {} password= {}", user.getEmail( ), user.getNickname(), user.getPassword());

        return "redirect:/users";
    }

    @GetMapping("/user/{id}")
    public String getUser(@PathVariable long id, Model model) {

        User user = userService.getUser(id);
        model.addAttribute(user);

        return "viewtest/grammar/user";
    }

    @GetMapping("/users")
    public String getUsers(Model model) {

        List<User> users = userService.getUsers();
        model.addAttribute("users", users);

        return "viewtest/grammar/users";
    }

    @GetMapping("/add")
    public String addForm() {
        return "viewtest/grammar/add";
    }


}
