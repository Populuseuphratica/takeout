package com.killstan.takeout.config;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Kill_Stan
 * @version 1.0
 * @description: Jackson 相关设定
 * @date 2022/10/14 17:47
 */
@Configuration
public class JacksonConfig {
    /**
     * Jackson全局转化
     * long类型为String，解决jackson序列化时long类型缺失精度问题
     * 指定时间格式，防止 LocalDateTime 转 json 时带 T的问题
     * @return Jackson2ObjectMapperBuilderCustomizer 注入的对象
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder
                .serializerByType(Long.class, ToStringSerializer.instance)
                .serializerByType(Long.TYPE, ToStringSerializer.instance)
                .serializerByType(LocalDateTime.class,new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
}
