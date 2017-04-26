package com.tianmaying.controller;

import com.tianmaying.Exception.BlogNotFoundException;
import com.tianmaying.Exception.NotAuthorException;
import com.tianmaying.form.BlogCreateForm;
import com.tianmaying.model.Blog;
import com.tianmaying.model.Comment;
import com.tianmaying.model.User;
import com.tianmaying.service.BlogService;
import com.tianmaying.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping
public class BlogController {

    @Autowired
    private  BlogService blogService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/blogs/{id}")
    public String get(@PathVariable("id") long id, Model model) {
    	Blog blog = blogService.findBlog(id);
        if (blog == null) throw new BlogNotFoundException("blog not found");
        List<Comment> comments = commentService.getCommentOfBlog(blog);
        model.addAttribute("blog",blog);
        model.addAttribute("comments",comments);
        model.addAttribute("id",blog.getId());
        return "item";
    }

    @GetMapping("/blogs/{id}/edit")
    public String editBlog(@PathVariable("id") long id, Model model, HttpSession session) {
        User user = (User)session.getAttribute("CURRENT_USER");
        if(user.getId() != blogService.findBlog(id).getAuthor().getId()) throw new NotAuthorException("not the author");
        Blog blog = blogService.findBlog(id);
        model.addAttribute("blog", blog);

        return "edit";
    }

    @PutMapping("/blogs/{id}")
    public String update(@PathVariable("id") long id, @ModelAttribute("blog") @Valid BlogCreateForm form, BindingResult result,
                         HttpSession session) {
        if(result.hasErrors()){
            return "edit";
        }
        Blog blog = blogService.findBlog(id);
        User user = (User)session.getAttribute("CURRENT_USER");
        if(user.getId() != blog.getAuthor().getId()) throw new NotAuthorException("not the author");
        blog.setContent(form.getContent());
        blog.setTitle(form.getTitle());

        blogService.updateBlog(blog);
        System.out.println(blog.getContent());
        System.out.println(blog.getCreatedTime());
        System.out.println(blog.getId());
        return "redirect:/blogs/" + blog.getId();
    }

    @DeleteMapping("/blogs/{id}")
    public String delete(@PathVariable("id") long id, final RedirectAttributes redirectAttributes, HttpSession session){
        Blog blog = blogService.findBlog(id);
        User user = (User)session.getAttribute("CURRENT_USER");
        if(user.getId() != blog.getAuthor().getId()) throw new NotAuthorException("not the author");
        blogService.deleteBlog(blog);
        redirectAttributes.addFlashAttribute("delete", "success");
        System.out.println("delete");
        return "redirect:/admin";
    }
}
