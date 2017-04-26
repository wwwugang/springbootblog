package com.tianmaying.controller;

import com.tianmaying.Exception.UserNotFoundException;
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
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class IndexController {
	
	@Autowired
	private BlogService blogService;
	@Autowired
	private UserService userService;

    @GetMapping("/{username:[a-z0-9_]+}")
    public String getUserBlogs(@PathVariable("username") String username,
    		@PageableDefault(value = 5, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable,
			Model model){

    	User user = userService.findByName(username);
		if( user == null ) throw new UserNotFoundException("user not found");
		model.addAttribute("user", user);
    	model.addAttribute("blogs", blogService.findBlogs(user, pageable));
    	return "list";
    	
    }
    
    @GetMapping("/about")
    public String about() {

    	return "about";
    }
}
