package com.hasakiii.rfid.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @Author: Z.Richard
 * @CreateTime: 2020/11/23 17:46
 * @Description:
 **/


@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    @Resource
    private JwtInterceptor jwtInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截配置
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/user/**", "/admin/**")
                .excludePathPatterns("/user/login", "/admin/login")
                .excludePathPatterns("/user/test",
                        "/user/isExist",
                        "/user/getInvalid",
                        "/user/ioLog");
    }
}
