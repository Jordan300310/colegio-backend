package com.cursoonline.dto.academico.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

@Schema(description = "Datos para asignar un profesor a varias secciones")
public record AsignarProfesorSeccionesRequest(
        @NotEmpty(message = "La lista de IDs de seccion es obligatoria.")
        List<Integer> idsSeccion
) {}
