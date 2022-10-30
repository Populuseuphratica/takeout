package com.killstan.takeout;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Kill_Stan
 * @version 1.0
 * @description:
 * @date 2022/10/10 23:10
 */
@SpringBootApplication
@MapperScan("com.killstan.takeout.mapper")
@EnableTransactionManagement
public class MainClass {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(MainClass.class, args);
//        MetaObjectHandler bean = run.getBean(MetaObjectHandler.class);
//        String[] beanNamesForType = run.getBeanNamesForType(MetaObjectHandler.class);
//        System.out.println("----------------------------------------");
//        System.out.println(beanNamesForType[0]);
//        System.out.println(bean);
//        System.out.println("----------------------------------------");
    }
}
