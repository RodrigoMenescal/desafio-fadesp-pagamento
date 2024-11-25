package com.fadesp.pagamento.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Desafio API REST")
                        .description("API REST do Desafio de pagamento da FADESP.")
                        .version("1.0")
                        .termsOfService("http://swagger.io/terms/")
                        .contact(new Contact()
                                .name("Rodrigo Menescal")
                                .url("https://github.com/RodrigoMenescal")
                                .email("rodrigomenescal.rm@gmail.com"))
                        .license(new License()
                                .name("Apache License Version 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")));
    }
}