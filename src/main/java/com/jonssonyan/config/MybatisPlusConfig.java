package com.jonssonyan.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Mybatis Plus配置
 */
@Configuration
public class MybatisPlusConfig {
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        // 配置mybatis-plus分页插件
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 方言
        paginationInterceptor.setDialectType(DbType.MYSQL.getDb());
        return paginationInterceptor;
    }
}
