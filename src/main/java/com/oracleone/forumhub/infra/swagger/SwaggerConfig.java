package com.oracleone.forumhub.infra.swagger;

import io.swagger.v3.oas.models.OpenAPI;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ForumHub API")
                        .version("1.0")
                        .description("API REST para o Fórum Hub")
                        .license(new License().name("MIT").url("https://opensource.org/licenses/MIT"))
                );
    }
}
