package com.tianmaying.controller;

import com.tianmaying.model.User;
import com.tianmaying.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private UserService userService;

	@GetMapping
	public String get(){
		return "login";
	}

	@PostMapping
	public String post(@RequestParam("next") Optional<String> next, User user, HttpSession session){
		user = userService.login(user.getEmail(),user.getPassword());
		String username = user.getName();
		System.out.println(username);
		session.setAttribute("CURRENT_USER", user);
		return "redirect:".concat(next.orElse("/" + username));
	}
}
