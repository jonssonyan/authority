package com.jonsson.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfig {
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        // 配置mybatis-plus分页插件
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 方言
        paginationInterceptor.setDialectType("mysql");
        return paginationInterceptor;
    }
}
