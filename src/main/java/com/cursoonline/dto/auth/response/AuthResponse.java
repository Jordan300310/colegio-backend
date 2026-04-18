package com.cursoonline.dto.auth.response;
import io.swagger.v3.oas.annotations.media.Schema;
@Schema(description = "Respuesta de login exitoso con token JWT y datos del usuario")
public record AuthResponse(

    @Schema(description = "Token JWT para usar en las peticiones autenticadas",
            example     = "eyJhbGciOiJIUzI1NiJ9...")
    String token,

    @Schema(description = "Tipo de token", example = "Bearer")
    String tipo,

    @Schema(description = "ID del usuario autenticado", example = "1")
    Integer idUsuario,

    @Schema(description = "Nombres del usuario", example = "Carlos Alberto")
    String nombres,

    @Schema(description = "Apellidos del usuario", example = "Quispe Mamani")
    String apellidos,

    @Schema(description = "Correo del usuario", example = "carlos@colegio.edu")
    String correo,

    @Schema(
        description = "Rol asignado al usuario",
        example     = "ROL_ALUMNO",
        allowableValues = {"ROL_ADMIN", "ROL_PROFESOR", "ROL_ALUMNO"}
    )
    String rol,

    @Schema(
        description = "true si la contraseña es temporal y debe cambiarse",
        example     = "false"
    )
    Boolean pwdTemporal
) {}