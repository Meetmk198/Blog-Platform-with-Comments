package com.meet.blog_post.swagger;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(new Info().title("Blog-Post Authentication Service"))
                .addSecurityItem(new SecurityRequirement().addList("BlogPostSecurityScheme"))
                .components(new Components().addSecuritySchemes("BlogPostSecurityScheme", new SecurityScheme()
                        .name("BlogPostSecurityScheme").type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));

    }
}