package com.bitsmi.springbootshowcase.sampleapps.webmvc.web.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    public static final String BEARER_SECURITY_SCHEME_NAME = "bearerAuth";
    public static final String BASIC_SECURITY_SCHEME_NAME = "basicAuth";

    @Bean
    public OpenAPI customizeOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Inventory-Clerk API")
                        .description("Inventory-Clerk management API")
                        .version("v1")
                )
                .components(new Components()
                        .addSecuritySchemes(BEARER_SECURITY_SCHEME_NAME, new SecurityScheme()
                                .name(BEARER_SECURITY_SCHEME_NAME)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                        )
                        .addSecuritySchemes(BASIC_SECURITY_SCHEME_NAME, new SecurityScheme()
                                .name(BASIC_SECURITY_SCHEME_NAME)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("basic")
                        )
                )
                .addSecurityItem(new SecurityRequirement()
                        .addList(BEARER_SECURITY_SCHEME_NAME)
                );
    }
}
