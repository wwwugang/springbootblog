package com.tianmaying.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by wugang on 2017/4/13.
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Not The Author")
public class NotAuthorException extends RuntimeException {
    public NotAuthorException(String message) {
        super(message);
    }
}
