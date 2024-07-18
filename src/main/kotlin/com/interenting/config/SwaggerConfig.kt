package com.interenting.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling

@Configuration
@SpringBootApplication
@OpenAPIDefinition
@EnableScheduling
class SwaggerConfig {

    private fun createAPIKeyScheme(): SecurityScheme {
        return SecurityScheme().type(SecurityScheme.Type.HTTP)
            .bearerFormat("JWT")
            .scheme("Bearer")
    }

    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI().addSecurityItem(
            SecurityRequirement()
                .addList("Bearer Authentication")
        )
            .components(
                Components()
                    .addSecuritySchemes(
                        "Bearer Authentication",
                        createAPIKeyScheme()
                    )
            )
            .info(
                Info()
                    .title("Interenting API")
                    .description("Interenting Management API.")
                    .version("1.0")
                    .contact(
                        Contact()
                            .name("Interenting")
                            .email("contact@interenting.io")
                            .url("www.interenting.io")
                    )
                    .license(
                        License()
                            .name("License")
                            .url("www.interenting.io/legal")
                    )
            )
    }
}
