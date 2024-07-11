package dev.martin1500.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Time Line Dinosaur API")
                        .description("Rest API.")
                        .version("v1"));
    }
}
