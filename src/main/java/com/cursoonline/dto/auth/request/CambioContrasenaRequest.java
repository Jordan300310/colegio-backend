package com.cursoonline.dto.auth.request;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
@Schema(description = "Datos para cambiar la contraseña del usuario autenticado (CUS-03)")
public record CambioContrasenaRequest(

    @Schema(description = "Contraseña actual del usuario", example = "MiClaveVieja$1")
    @NotBlank(message = "La contraseña actual es obligatoria.")
    String contrasenaActual,

    @Schema(
        description = "Nueva contraseña. Mínimo 8 caracteres, mayúscula, número y símbolo.",
        example     = "NuevaClave$2026"
    )
    @NotBlank(message = "La nueva contraseña es obligatoria.")
    @Size(min = 8, message = "La contraseña debe tener mínimo 8 caracteres.")
    @Pattern(
        regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).+$",
        message = "La contraseña debe incluir mayúsculas, números y símbolos."
    )
    String nuevaContrasena,

    @Schema(description = "Confirmación de la nueva contraseña", example = "NuevaClave$2026")
    @NotBlank(message = "La confirmación de contraseña es obligatoria.")
    String confirmarContrasena
) {}