package com.wish.dms_api.security;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
 
public class OpenApiConfig {


    @Bean

    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Wish Spine API")
                        .description("Wish Spine API Documentation")
                        .license(new License().name("Apache 2.0")
                                .url("http://springdoc.org"))
                        .termsOfService("Terms & Conditions applied..")

                        .contact(new Contact()
                                .name("Wish Spine API Team")
                                .email("samirram007@gmail.com"))
                        .version("1.0.0")

                )
                .addSecurityItem(new SecurityRequirement().addList("JavaInUseSecurityScheme"))
                .components(new Components().addSecuritySchemes("JavaInUseSecurityScheme", new SecurityScheme()
                 .name("JavaInUseSecurityScheme").type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));

    }
}