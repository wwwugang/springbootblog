package com.tianmaying.controller;

import com.tianmaying.model.Blog;
import com.tianmaying.model.Comment;
import com.tianmaying.model.User;
import com.tianmaying.service.BlogService;
import com.tianmaying.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogService blogService;

    @PostMapping("/blogs/{id}/comments")
    @ResponseBody
    public Comment comments(@PathVariable("id") long id, @RequestBody String content, HttpSession session) {
        Comment comment = new Comment();
        Blog blog = blogService.findBlog(id);
        comment.setBlog(blog);
        comment.setCommentor((User)session.getAttribute("CURRENT_USER"));
        comment.setCreatedTime(new Date());
        comment.setContent(content.split("=")[1]);
        commentService.createComment(comment);
        System.out.println(comment.getCreatedTime());
        System.out.println(comment.getContent());
        System.out.println(comment.getCommentor());
        return comment;

    }

}
