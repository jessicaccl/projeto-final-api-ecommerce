package org.serratec.trabalhoFinal.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {
	
	@Value("${prop.swagger.dev-url}")
	private String devUrl;

    @Bean
    public OpenAPI apiInfo() {
    	Server server = new Server();
    	server.setUrl(devUrl);
    	server.setDescription("Servidor de Desenvolvimento");
        return new OpenAPI()
                .info(new Info()
                        .title("API E-Commerce")
                        .description("API para gerenciamento de clientes, produtos, categorias e pedidos")
                        .version("1.0"))
                .externalDocs(new ExternalDocumentation()
                        .description("Documentação externa")
                        .url("https://example.com"));
//                .servers(List.of(server));
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
