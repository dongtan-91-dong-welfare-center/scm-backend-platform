package com.dw.scm.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi v1Group() {
        return GroupedOpenApi.builder()
                .group("v1 API")
                .pathsToMatch("/api/v1/**")
                .addOpenApiCustomizer(openApi ->
                        openApi.info(new Info()
                                .title("SCM API v1")
                                .version("v1")
                                .description("v1 버전 API 문서")
                        )
                )
                .build();
    }
}