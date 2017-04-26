package com.tianmaying.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by wugang on 2017/4/13.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No Such Blog")//404 Not Found
public class BlogNotFoundException extends RuntimeException {
    public BlogNotFoundException(String message) {
        super(message);
    }
}
