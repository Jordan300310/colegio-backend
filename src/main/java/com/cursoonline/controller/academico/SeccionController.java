package com.cursoonline.controller.academico;

import com.cursoonline.dto.academico.request.AsignarProfesorRequest;
import com.cursoonline.dto.academico.request.SeccionRequest;
import com.cursoonline.dto.academico.response.SeccionResponse;
import com.cursoonline.dto.common.ApiResponse;
import com.cursoonline.service.academico.SeccionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/secciones")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Secciones", description = "Gestión de secciones y asignación de profesores (CUS-08, CUS-09)")
public class SeccionController {

    private final SeccionService seccionService;

    @Operation(summary = "Listar todas las secciones activas")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<SeccionResponse>>> listar(
            @PageableDefault(size = 10, sort = "desNombre") Pageable pageable) {
        return ResponseEntity.ok(
                ApiResponse.ok("Secciones obtenidas correctamente.", seccionService.listar(pageable))
        );
    }

    @Operation(summary = "Listar secciones por curso")
    @GetMapping("/curso/{idCurso}")
    public ResponseEntity<ApiResponse<Page<SeccionResponse>>> listarPorCurso(
            @PathVariable Integer idCurso,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(
                ApiResponse.ok("Secciones obtenidas correctamente.",
                        seccionService.listarPorCurso(idCurso, pageable))
        );
    }

    @Operation(summary = "Listar secciones por año escolar")
    @GetMapping("/anio/{idAnio}")
    public ResponseEntity<ApiResponse<Page<SeccionResponse>>> listarPorAnio(
            @PathVariable Integer idAnio,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(
                ApiResponse.ok("Secciones obtenidas correctamente.",
                        seccionService.listarPorAnio(idAnio, pageable))
        );
    }

    @Operation(summary = "Obtener sección por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SeccionResponse>> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(
                ApiResponse.ok("Sección obtenida correctamente.", seccionService.obtener(id))
        );
    }

    @Operation(summary = "Crear sección — Solo ADMIN")
    @PostMapping
    @PreAuthorize("hasAuthority('ROL_ADMIN')")
    public ResponseEntity<ApiResponse<SeccionResponse>> crear(
            @Valid @RequestBody SeccionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.ok("Sección creada correctamente.", seccionService.crear(request))
        );
    }

    @Operation(summary = "Actualizar sección — Solo ADMIN")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROL_ADMIN')")
    public ResponseEntity<ApiResponse<SeccionResponse>> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody SeccionRequest request) {
        return ResponseEntity.ok(
                ApiResponse.ok("Sección actualizada correctamente.",
                        seccionService.actualizar(id, request))
        );
    }

    @Operation(summary = "CUS-09 · Asignar profesor a sección — Solo ADMIN")
    @PostMapping("/{id}/profesor")
    @PreAuthorize("hasAuthority('ROL_ADMIN')")
    public ResponseEntity<ApiResponse<SeccionResponse>> asignarProfesor(
            @Parameter(description = "ID de la sección", example = "1")
            @PathVariable Integer id,
            @Valid @RequestBody AsignarProfesorRequest request) {
        return ResponseEntity.ok(
                ApiResponse.ok("Profesor asignado correctamente.",
                        seccionService.asignarProfesor(id, request))
        );
    }

    @Operation(summary = "CUS-09 · Remover profesor de sección — Solo ADMIN")
    @DeleteMapping("/{id}/profesor")
    @PreAuthorize("hasAuthority('ROL_ADMIN')")
    public ResponseEntity<ApiResponse<SeccionResponse>> removerProfesor(
            @PathVariable Integer id) {
        return ResponseEntity.ok(
                ApiResponse.ok("Profesor removido correctamente.",
                        seccionService.removerProfesor(id))
        );
    }
}