package com.tianmaying.controller;

import com.tianmaying.form.BlogCreateForm;
import com.tianmaying.model.Blog;
import com.tianmaying.model.User;
import com.tianmaying.service.BlogService;
import com.tianmaying.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;

@Controller
public class CreateBlogController {

    @Autowired
    private UserService userService;
    @Autowired
    private BlogService blogService;

    @GetMapping("/blogs/create")
    public String createPage(@ModelAttribute("blog") Blog blog) {
        return "create";
    }

    @PostMapping("/blogs")
    public String create(@ModelAttribute("blog") @Valid BlogCreateForm form, BindingResult result, HttpSession session) {
        if(result.hasErrors()){
            return "create";
        }
        User user = (User) session.getAttribute("CURRENT_USER");
        Blog blog = form.toBlog(user);
        blog.setCreatedTime(new Date());
        blogService.createBlog(blog);
        return "redirect:/blogs/" + blog.getId();
    }
}
