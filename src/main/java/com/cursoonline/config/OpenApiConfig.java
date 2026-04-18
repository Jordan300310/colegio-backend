package com.cursoonline.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title       = "Cursos Online API — Autenticación",
        version     = "1.0.0",
        description = "Módulo de autenticación: login, logout, cambio de contraseña, registro y recuperación de credenciales.",
        contact     = @Contact(name = "Equipo Backend", email = "backend@cursoonline.com")
    )
)
@SecurityScheme(
    name        = "bearerAuth",
    type        = SecuritySchemeType.HTTP,
    scheme      = "bearer",
    bearerFormat = "JWT",
    description = "Ingresa el token JWT obtenido en /auth/login"
)
public class OpenApiConfig {}