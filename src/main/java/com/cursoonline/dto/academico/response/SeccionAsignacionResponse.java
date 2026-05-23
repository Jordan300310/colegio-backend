package com.cursoonline.dto.academico.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Seccion con estado de asignacion para un profesor")
public record SeccionAsignacionResponse(
        Integer idSeccion,
        Integer idCurso,
        String desCurso,
        Integer idAnioEscolar,
        Short valAnio,
        String desNombre,
        Boolean estActiva,
        LocalDateTime fecCreacion,
        Integer idProfesorAsignado,
        String desProfesorAsignado,
        Boolean asignadaAlProfesor
) {}
