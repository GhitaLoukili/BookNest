package com.ghita.booknestbackend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI bookDatabaseOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                                .title("Book REST API")
                                .description("My book stock")
                                .version("1.0"));
    }
}
