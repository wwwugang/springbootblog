package com.tianmaying.config;

import com.tianmaying.Interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by wugang on 2017/4/5.
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/blogs/create");
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**");
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/blogs/{id}");
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/profile");
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/blogs/{id}/edit");
    }
}
