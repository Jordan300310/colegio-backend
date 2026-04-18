package com.cursoonline.dto.auth.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta del registro de un nuevo usuario")
public record RegisterResponse(

    @Schema(description = "ID del usuario creado", example = "12")
    Integer idUsuario,

    @Schema(description = "Nombres del usuario", example = "Carlos Alberto")
    String nombres,

    @Schema(description = "Apellidos del usuario", example = "Quispe Mamani")
    String apellidos,

    @Schema(description = "Correo del usuario", example = "carlos@colegio.edu")
    String correo,

    @Schema(description = "Rol asignado", example = "ROL_ALUMNO")
    String rol,

    @Schema(
        description = "Contraseña temporal generada. Comunicar al usuario para su primer acceso.",
        example     = "aB3xZ9qR2"
    )
    String contrasenaTemporal
) {}