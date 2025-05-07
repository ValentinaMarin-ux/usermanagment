package com.example.userManagment.config;

// Importaciones necesarias para la configuración de Swagger
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Marca esta clase como una clase de configuración de Spring
@Configuration
public class SwaggerConfig {

    // Define un bean que configura la documentación OpenAPI
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                // Configura la información básica de la API
                .info(new Info()
                        .title("API de Gestión de Usuarios") // Título de la API
                        .version("1.0") // Versión de la API
                        .description("API para la gestión de usuarios y autenticación")) // Descripción de la API
                // Configura el esquema de seguridad JWT
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("bearer-jwt", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP) // Tipo de esquema de seguridad
                                .scheme("bearer") // Esquema Bearer
                                .bearerFormat("JWT"))); // Formato JWT
    }
}