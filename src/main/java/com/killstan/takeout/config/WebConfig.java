package com.killstan.takeout.config;

import com.killstan.takeout.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
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

    // 登陆拦截器
    private final LoginInterceptor loginInterceptor;

    @Autowired
    public WebConfig(LoginInterceptor loginInterceptor) {
        this.loginInterceptor = loginInterceptor;
    }

    /**
     * @Description: 加入自定义拦截器
     * @Param: [registry]
     * @Return: void
     * @Author Kill_Stan
     * @Date 2022/11/27 12:44
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns("/employee/login",
                "/employee/logout", "/user/sendMsg", "/user/login", "/user/logout", "/user/sendMsg");
    }
}
