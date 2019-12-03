package com.qianfeng.smsplatform.cache.config;

import org.springframework.beans.factory.annotation.Value;
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
 * @author damon
 * @Classname SwaggerConfig
 * @Date 2019/12/3 14:07
 * @Description TODO
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Value("${swagger.contact.name}")
    private String name;
    @Value("${swagger.contact.url}")
    private String url;
    @Value("${swagger.contact.email}")
    private String email;

    @Bean
    public Docket docket(ApiInfo apiInfo) {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo)///
                .select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();
    }

    @Bean
    public ApiInfo apiInfo() {
        return  new ApiInfoBuilder()
                .title("Cache模块")//
                .contact(new Contact(name,url,email))//
                .description("这个是一个介绍")//
                .version("1.0")//
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")//
                .license("apache2.0")
                .build();
    }
}
