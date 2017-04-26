package com.tianmaying.controller;

import com.tianmaying.form.UserRegisterForm;
import com.tianmaying.model.User;
import com.tianmaying.service.BlogService;
import com.tianmaying.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private String SESSION_LOGGED_IN = "logged";

    @Autowired
    private UserService userService;
    @Autowired
    private BlogService blogService;

    @GetMapping
    public String get(@ModelAttribute("user") User user) {
        return "register";
    }

    @PostMapping
    public String post(@ModelAttribute("user") @Valid UserRegisterForm form, BindingResult result, HttpSession session,
                       final RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            return "register";
        }
        //User register = userService.register(form.toUser());
        session.setAttribute(SESSION_LOGGED_IN, true);
        redirectAttributes.addFlashAttribute("register", "success");
        return "redirect:/admin";
    }

}
