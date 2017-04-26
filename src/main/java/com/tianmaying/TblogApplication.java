package com.tianmaying;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@Controller
public class TblogApplication {

    @RequestMapping("/time")
    @ResponseBody
    String index() {
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date date = new Date();
    	String str = df.format(date);
        return str;
    }

    public static void main(String[] args) {
        SpringApplication.run(TblogApplication.class, args);
    }
}