package com.killstan.takeout.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.killstan.takeout.util.ThreadLocalForEmp;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * @author Kill_Stan
 * @version 1.0
 * @description:
 * @date 2022/10/14 19:54
 */
@Slf4j
@Configuration
public class MybatisPlusConfig{

    /**
     * 添加分页插件
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        paginationInnerInterceptor.setDbType(DbType.MYSQL);
        paginationInnerInterceptor.setOverflow(true);
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor = new OptimisticLockerInnerInterceptor();
        interceptor.addInnerInterceptor(optimisticLockerInnerInterceptor);
        return interceptor;
    }

    /**
     * mybatis-plus 自动填充
     * @return
     */
    @Bean
    public MetaObjectHandler myMetaObjectHandler(){
        return new MetaObjectHandler(){
            @Override
            public void insertFill(MetaObject metaObject) {
                log.info("start insert fill ....");

                LocalDateTime now = LocalDateTime.now();
                // 如果字段存在，且有注解，则自动填充
                this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, now);
                this.strictInsertFill(metaObject, "createId", Long.class, ThreadLocalForEmp.get());

                // 一定存在的字段
                metaObject.setValue("updateTime",now);
                metaObject.setValue("updateId",ThreadLocalForEmp.get());

            }

            @Override
            public void updateFill(MetaObject metaObject) {
                log.info("start update fill ....");
                metaObject.setValue("updateTime",LocalDateTime.now());
                metaObject.setValue("updateId",ThreadLocalForEmp.get());
            }
        };
    }

}

