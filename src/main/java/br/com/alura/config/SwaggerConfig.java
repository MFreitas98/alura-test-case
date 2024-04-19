package br.com.alura.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
public class SwaggerConfig {

    @Bean
    public OpenAPI customizeOpenAPI() {
        return new OpenAPI()
                .schemaRequirement("Basic", new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic"))
                .info(new Info().title("Swagger Documentation for Alura API")
                        .description("Restful API")
                        .version("1.0"));
    }
}
