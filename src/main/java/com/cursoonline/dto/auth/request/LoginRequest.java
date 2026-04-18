package com.cursoonline.dto.auth.request;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
@Schema(description = "Credenciales para iniciar sesión (CUS-01)")
public record LoginRequest(
    @Schema(description = "Correo electrónico del usuario", example = "admin@colegio.edu")
    @NotBlank(message = "El correo es obligatorio.")
    @Email(message = "Debe ingresar un correo válido.")
    String correo,

    @Schema(description = "Contraseña del usuario", example = "Mi$Clave2026")
    @NotBlank(message = "La contraseña es obligatoria.")
    String contrasena
) {}