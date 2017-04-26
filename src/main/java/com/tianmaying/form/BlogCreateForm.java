package com.tianmaying.form;


import com.tianmaying.model.Blog;
import com.tianmaying.model.User;

import javax.validation.constraints.Size;
import java.util.Date;

public class BlogCreateForm {

    @Size(min = 2, max = 30)
    private String title;

    @Size(min = 1)
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Blog toBlog(User author) {
        Blog blog = new Blog();
        blog.setTitle(this.title);
        blog.setContent(this.content);
        blog.setCreatedTime(new Date());
        blog.setAuthor(author);

        return blog;
    }
}
