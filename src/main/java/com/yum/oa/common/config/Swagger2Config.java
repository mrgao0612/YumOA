package com.yum.oa.common.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * swagger2配置类
 * @description
 * @author: gaoyu
 * @create: 2020-08-05
 * @version: 0.0.1
 **/
@Configuration
@EnableSwagger2
@ConditionalOnProperty(name = "swagger.enable", havingValue = "true")
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yum.oa.controller"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Springboot 测试使用swagger2 构建RESTFUL API")
                .contact(new Contact("gaoyu", "https://github.com/mrgao0612", "mrgao0612@gmail.com"))
                .version("0.0.1")
                .description("")
                .build();
    }

    private List<ApiKey> securitySchemes() {
        ApiKey apiKey = new ApiKey("Authorization", "Authorization", "header");
        List<ApiKey> keys = new ArrayList<>();
        keys.add(apiKey);
        return keys;
    }
}
