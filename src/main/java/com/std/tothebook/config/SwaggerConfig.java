package com.std.tothebook.config;

import com.std.tothebook.api.enums.AuthorizationType;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {

        Info info = new Info()
                .title("북쪽으로 API 명세서")
                .description("독서 기록 웹 '북쪽으로' API 명세서")
                .version("v0.0.1")
                .license(new License().name("SevenDays To Develop, Git Url")
                        .url("https://github.com/kimzoo2/tothebook")
                );

        String key = "Bearer 인증";

        SecurityRequirement securityRequirement = new SecurityRequirement().addList(key);

        Components components = new Components()
                .addSecuritySchemes(
                        key,
                        new SecurityScheme()
                                .name(key)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme(AuthorizationType.BEARER.getCode())
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")
                );

        return new OpenAPI()
                .info(info)
                .addSecurityItem(securityRequirement)
                .components(components);
    }
}
