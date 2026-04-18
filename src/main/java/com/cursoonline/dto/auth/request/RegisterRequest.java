package com.cursoonline.dto.auth.request;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
@Schema(description = "Datos para registrar un nuevo usuario (CUS-06)")
public record RegisterRequest(

    @Schema(description = "Nombres del usuario", example = "Carlos Alberto")
    @NotBlank(message = "Los nombres son obligatorios.")
    String nombres,

    @Schema(description = "Apellidos del usuario", example = "Quispe Mamani")
    @NotBlank(message = "Los apellidos son obligatorios.")
    String apellidos,

    @Schema(description = "Correo electrónico único", example = "carlos@colegio.edu")
    @NotBlank(message = "El correo es obligatorio.")
    @Email(message = "Formato de correo inválido.")
    String correo,

    @Schema(
        description = "Código del rol a asignar",
        example     = "ROL_ALUMNO",
        allowableValues = {"ROL_ADMIN", "ROL_PROFESOR", "ROL_ALUMNO"}
    )
    @NotNull(message = "El rol es obligatorio.")
    String codRol,

    // Opcional: si viene vacío se genera automáticamente (CUS-06 A1)
    @Schema(
        description = "Contraseña opcional. Si se omite, el sistema genera una temporal.",
        example     = "Temporal$123",
        nullable    = true
    )
    String contrasenaTemporal
) {}