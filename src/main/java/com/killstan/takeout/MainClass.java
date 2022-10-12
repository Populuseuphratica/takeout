package com.killstan.takeout;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Kill_Stan
 * @version 1.0
 * @description:
 * @date 2022/10/10 23:10
 */
@SpringBootApplication
@MapperScan("com.killstan.takeout.mapper")
public class MainClass {
    public static void main(String[] args) {
        SpringApplication.run(MainClass.class,args);
    }
}
