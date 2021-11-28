package com.jonssonyan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger配置
 * 项目运行后访问：http://localhost:port/swagger-ui.html
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    // 配置swagger2核心配置 docket
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2) // 指定api类型为swagger2
                .groupName("authority") // 分组
                .apiInfo(apiInfo()) // 用于定义api文档汇总信息
                .select() // 通过.select()方法，去配置扫描接口。RequestHandlerSelectors配置如何扫描接口
                /*
                    // RequestHandlerSelectors配置方法
                    any() // 扫描所有，项目中的所有接口都会被扫描到
                    none() // 不扫描接口
                    // 通过方法上的注解扫描，如withMethodAnnotation(GetMapping.class)只扫描get请求
                    withMethodAnnotation( final Class<? extends Annotation> annotation)
                    // 通过类上的注解扫描，如.withClassAnnotation(Controller.class)只扫描有controller注解的类中的接口
                    withClassAnnotation( final Class<? extends Annotation> annotation)
                    basePackage( final String basePackage) // 根据包路径扫描接口
                */
                .apis(RequestHandlerSelectors.basePackage("com.jonssonyan")) // 指定扫描包
                /*
                    // 配置如何通过path过滤,即这里只扫描请求以/开头的接口
                    any() // 任何请求都扫描
                    none() // 任何请求都不扫描
                    regex(final String pathRegex) // 通过正则表达式控制
                    ant(final String antPattern) // 通过ant()控制
                */
                .paths(PathSelectors.any()) // 所有controller
                .build();
    }

    //构建 api文档的详细信息函数,注意这里的注解引用的是哪个
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("权限管理系统 API") // 文档页标题
                .contact(new Contact("jonssonyan", "https://jonssonyan.com", "yz808@outlook.com")) // 联系人信息
                .version("1.0") // 文档版本号
                .description("API 描述") // 描述
                .build();
    }
}
