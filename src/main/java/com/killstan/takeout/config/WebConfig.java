package com.killstan.takeout.config;

import com.killstan.takeout.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Kill_Stan
 * @version 1.0
 * @description: WebMvc 配置类
 * @date 2022/10/14 12:04
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").excludePathPatterns("/employee/login",
                "/employee/logout");
    }
}
