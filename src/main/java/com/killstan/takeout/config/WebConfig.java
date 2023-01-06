package com.killstan.takeout.config;

import com.killstan.takeout.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

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
                "/employee/logout", "/user/sendMsg", "/user/login", "/user/logout", "/user/sendMsg","/doc.html","/webjars/**","/swagger-resources","/v2/api-docs");
    }

    /**
     * @Description: 接口文档配置
     * @Param: []
     * @Return: springfox.documentation.spring.web.plugins.Docket
     * @Author Kill_Stan
     * @Date 2023/1/6 16:35
     */
    @Bean
    public Docket createRestApi() {
        // 文档类型
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.killstan.takeout.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("外卖")
                .version("1.0")
                .description("外卖接口文档")
                .build();
    }
}
