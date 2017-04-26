package com.tianmaying.data;

import com.tianmaying.model.Blog;
import com.tianmaying.model.User;
import com.tianmaying.service.BlogService;
import com.tianmaying.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Initialization implements CommandLineRunner {

    private final UserService userService;

    private final BlogService blogService;

    @Autowired
    public Initialization(UserService userService, BlogService blogService) {
        this.userService = userService;
        this.blogService = blogService;
    }

    @Override
    public void run(String... strings) throws Exception {
        User user = createUser("tianmaying", "admin@tianmaying.com");
        User user1 = createUser("wugang", "376868393@qq.com");
        createBlog("Title 1", "Content 1", user);
        createBlog("Title 2", "Content 2", user);
        createBlog("Title 3", "Content 3", user);
        createBlog("Title 4", "Content 4", user);
        createBlog("Title 5", "Content 5", user);
        createBlog("Title 6", "Content 6", user);
        createBlog("Title 7", "Content 7", user);
        createBlog("Title 8", "Content 8", user);
        createBlog("Title 9", "Content 9", user);
        createBlog("Title 10", "Content 10", user);
        createBlog("Text","ContentText",user1);
    }

    private User createUser(String username, String email) {
        User user = new User(username, email);
        user.setPassword("123456");
        return userService.register(user);
    }

    private Blog createBlog(String title, String content, User author) {
        Blog blog = new Blog();
        blog.setTitle(title);
        blog.setContent(content);
        blog.setAuthor(author);
        blog.setCreatedTime(new Date());

        return blogService.createBlog(blog);
    }

}
