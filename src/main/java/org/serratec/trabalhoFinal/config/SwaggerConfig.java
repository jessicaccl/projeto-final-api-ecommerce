package org.serratec.trabalhoFinal.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("API E-Commerce")
                        .description("API para gerenciamento de clientes, produtos, categorias e pedidos")
                        .version("1.0"))
                .externalDocs(new ExternalDocumentation()
                        .description("Documentação externa")
                        .url("https://example.com"));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("ecommerce-api")
                .packagesToScan("org.serratec.trabalhoFinal.controller")
                .pathsToMatch("/**")
                .build();
    }
}
