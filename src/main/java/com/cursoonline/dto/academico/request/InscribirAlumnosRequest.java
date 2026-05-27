package com.cursoonline.dto.academico.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

@Schema(description = "Datos para inscribir varios alumnos a una seccion")
public record InscribirAlumnosRequest(
        @NotEmpty(message = "La lista de IDs de alumnos es obligatoria.")
        List<Integer> idsAlumno
) {}
