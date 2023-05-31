package com.std.tothebook.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("북쪽으로 API 명세서")
                                .description("독서 기록 웹 '북쪽으로' API 명세서")
                                .version("v0.0.1")
                                .license(new License().name("SevenDays To Develop, Git Url")
                                        .url("https://github.com/kimzoo2/tothebook")
                                )
                );
    }
}
