package com.example.hdfs.kerberos.provider.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
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

@Configuration
@EnableSwagger2
public class Swagger2 {

    @Autowired
    private ServerProperties serverProperties;

    /**
     * 配置swagger2核心配置 docket
     * 路径1：http://localhost:8080/swagger-ui.html
     * 路径2：http://localhost:8080/doc.html
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 指定controller包
                .apis(RequestHandlerSelectors.basePackage("com.example.hdfs.kerberos.provider.controller"))
                // 所有controller
                .paths(PathSelectors.regex("/.*"))
                .build();
    }

    private ApiInfo apiInfo() {
        String url = "http://127.0.0.1:" + serverProperties.getPort();
        return new ApiInfoBuilder()
                .title("测试开启kerberos认证的hadoop")
                .contact(new Contact("spring-boot-hdfs-kerberos", url, ""))
                .description("测试开启kerberos认证的hadoop")
                .version("1.0.0")
                .termsOfServiceUrl(url)
                .build();
    }

}
