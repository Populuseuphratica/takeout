package com.killstan.takeout.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.killstan.takeout.util.ThreadLocalForId;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.LocalDateTypeHandler;
import org.apache.ibatis.type.LocalTimeTypeHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Kill_Stan
 * @version 1.0
 * @description:
 * @date 2022/10/14 19:54
 */
@Slf4j
@Configuration
public class MybatisPlusConfig {

    public MybatisPlusConfig(MybatisPlusProperties mybatisPlusProperties) {
        MybatisConfiguration configuration = mybatisPlusProperties.getConfiguration();
        GlobalConfig globalConfig = mybatisPlusProperties.getGlobalConfig();
        GlobalConfigUtils.setGlobalConfig(configuration, globalConfig);
        configuration.getTypeHandlerRegistry().register(LocalDateTime.class, new LocalDateTimeTypeHandler());
        configuration.getTypeHandlerRegistry().register(LocalDate.class, new LocalDateTypeHandler());
        configuration.getTypeHandlerRegistry().register(LocalTime.class, new LocalTimeTypeHandler());
        GlobalConfigUtils.setGlobalConfig(configuration, globalConfig);
    }


    /**
     * 添加分页插件
     *
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
     *
     * @return
     */
    @Bean
    public MetaObjectHandler myMetaObjectHandler() {
        return new MetaObjectHandler() {
            @Override
            public void insertFill(MetaObject metaObject) {
                log.info("start insert fill ....");

                LocalDateTime now = LocalDateTime.now();
                // 如果字段存在，且有注解，则自动填充
                this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, now);
                this.strictInsertFill(metaObject, "createId", Long.class, ThreadLocalForId.get());
                this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, now);
                this.strictInsertFill(metaObject, "updateId", Long.class, ThreadLocalForId.get());

            }

            @Override
            public void updateFill(MetaObject metaObject) {
                log.info("start update fill ....");
                this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
                this.strictInsertFill(metaObject, "updateId", Long.class, ThreadLocalForId.get());
            }
        };
    }

}

class LocalDateTimeTypeHandler extends BaseTypeHandler<LocalDateTime> {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, LocalDateTime parameter, JdbcType jdbcType)
            throws SQLException {
        if (parameter != null) {
            ps.setString(i, dateTimeFormatter.format(parameter));
        }
    }

    @Override
    public LocalDateTime getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String target = rs.getString(columnName);
        if (StringUtils.isEmpty(target)) {
            return null;
        }
        return LocalDateTime.parse(target, dateTimeFormatter);
    }

    @Override
    public LocalDateTime getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String target = rs.getString(columnIndex);
        if (StringUtils.isEmpty(target)) {
            return null;
        }
        return LocalDateTime.parse(target, dateTimeFormatter);
    }

    @Override
    public LocalDateTime getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String target = cs.getString(columnIndex);
        if (StringUtils.isEmpty(target)) {
            return null;
        }
        return LocalDateTime.parse(target, dateTimeFormatter);
    }
}