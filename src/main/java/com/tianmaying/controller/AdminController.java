package com.tianmaying.controller;

import com.tianmaying.model.User;
import com.tianmaying.service.BlogService;
import com.tianmaying.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class AdminController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String admin(@PageableDefault(value = 5, sort = { "createdTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model, HttpSession session){
        User user = (User) session.getAttribute("CURRENT_USER");
        model.addAttribute("blogs", blogService.findBlogs(user, pageable));

        return "admin";
    }
}