package com.cursoonline.controller.reporte;

import com.cursoonline.entity.auth.SegUsuario;
import com.cursoonline.service.reporte.ReporteService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/reportes")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Reportes", description = "Reportes PDF/Excel (CUS-17)")
public class ReporteController {

    private final ReporteService service;

    private static final String MIME_XLSX =
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    @GetMapping("/seccion/{idSeccion}/grupal/excel")
    @PreAuthorize("hasAnyAuthority('ROL_ADMIN','ROL_PROFESOR')")
    public ResponseEntity<byte[]> grupalExcel(
            @PathVariable Integer idSeccion,
            @AuthenticationPrincipal SegUsuario usuario) {
        return descarga(service.grupalSeccionExcel(idSeccion, usuario),
                "reporte-grupal-seccion-" + idSeccion + ".xlsx", MIME_XLSX);
    }

    @GetMapping("/seccion/{idSeccion}/grupal/pdf")
    @PreAuthorize("hasAnyAuthority('ROL_ADMIN','ROL_PROFESOR')")
    public ResponseEntity<byte[]> grupalPdf(
            @PathVariable Integer idSeccion,
            @AuthenticationPrincipal SegUsuario usuario) {
        return descarga(service.grupalSeccionPdf(idSeccion, usuario),
                "reporte-grupal-seccion-" + idSeccion + ".pdf", "application/pdf");
    }

    @GetMapping("/alumno/{idAlumno}/curso/{idCurso}/individual/excel")
    @PreAuthorize("hasAnyAuthority('ROL_ADMIN','ROL_PROFESOR')")
    public ResponseEntity<byte[]> individualExcel(
            @PathVariable Integer idAlumno,
            @PathVariable Integer idCurso,
            @AuthenticationPrincipal SegUsuario usuario) {
        return descarga(service.individualAlumnoExcel(idAlumno, idCurso, usuario),
                "reporte-alumno-" + idAlumno + "-curso-" + idCurso + ".xlsx", MIME_XLSX);
    }

    @GetMapping("/alumno/{idAlumno}/curso/{idCurso}/individual/pdf")
    @PreAuthorize("hasAnyAuthority('ROL_ADMIN','ROL_PROFESOR')")
    public ResponseEntity<byte[]> individualPdf(
            @PathVariable Integer idAlumno,
            @PathVariable Integer idCurso,
            @AuthenticationPrincipal SegUsuario usuario) {
        return descarga(service.individualAlumnoPdf(idAlumno, idCurso, usuario),
                "reporte-alumno-" + idAlumno + "-curso-" + idCurso + ".pdf", "application/pdf");
    }

    @GetMapping("/accesos/excel")
    @PreAuthorize("hasAuthority('ROL_ADMIN')")
    public ResponseEntity<byte[]> accesosExcel(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaDesde,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaHasta) {
        return descarga(service.historialAccesosExcel(fechaDesde, fechaHasta),
                "historial-accesos.xlsx", MIME_XLSX);
    }

    @GetMapping("/progreso")
    @PreAuthorize("hasAnyAuthority('ROL_ADMIN','ROL_PROFESOR')")
    public ResponseEntity<byte[]> progreso(
            @RequestParam Integer idSeccion,
            @RequestParam(required = false) Integer idAlumno,
            @RequestParam String formato,
            @AuthenticationPrincipal SegUsuario usuario) {

        String fmt = formato.toLowerCase();
        boolean excel = fmt.equals("excel") || fmt.equals("xlsx");
        boolean pdf = fmt.equals("pdf");

        if (!excel && !pdf) {
            throw new IllegalArgumentException("Formato invalido. Use pdf o excel.");
        }

        if (idAlumno == null) {
            return excel
                    ? descarga(service.grupalSeccionExcel(idSeccion, usuario),
                            "reporte-grupal-seccion-" + idSeccion + ".xlsx", MIME_XLSX)
                    : descarga(service.grupalSeccionPdf(idSeccion, usuario),
                            "reporte-grupal-seccion-" + idSeccion + ".pdf", "application/pdf");
        }

        return excel
                ? descarga(service.individualAlumnoSeccionExcel(idSeccion, idAlumno, usuario),
                        "reporte-alumno-" + idAlumno + "-seccion-" + idSeccion + ".xlsx", MIME_XLSX)
                : descarga(service.individualAlumnoSeccionPdf(idSeccion, idAlumno, usuario),
                        "reporte-alumno-" + idAlumno + "-seccion-" + idSeccion + ".pdf", "application/pdf");
    }

    private ResponseEntity<byte[]> descarga(byte[] data, String filename, String mime) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(mime));
        headers.setContentDisposition(ContentDisposition.attachment().filename(filename).build());
        return new ResponseEntity<>(data, headers, HttpStatus.OK);
    }
}