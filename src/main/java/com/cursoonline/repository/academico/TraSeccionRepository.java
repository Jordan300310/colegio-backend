package com.cursoonline.repository.academico;

import java.util.List;
import com.cursoonline.entity.academico.TraSeccion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TraSeccionRepository extends JpaRepository<TraSeccion, Integer> {

    Page<TraSeccion> findByEstActivaTrue(Pageable pageable);

    Page<TraSeccion> findByCurso_IdCursoAndEstActivaTrue(Integer idCurso, Pageable pageable);

    Page<TraSeccion> findByAnioEscolar_IdAnioEscolarAndEstActivaTrue(
            Integer idAnioEscolar, Pageable pageable);

        @Query("""
                SELECT DISTINCT s FROM TraSeccion s
                LEFT JOIN RelProfesorSeccion rps
                             ON rps.seccion = s AND rps.estActivo = true
                WHERE (:idCurso IS NULL OR s.curso.idCurso = :idCurso)
                    AND (:idAnio IS NULL OR s.anioEscolar.idAnioEscolar = :idAnio)
                    AND (:estActiva IS NULL OR s.estActiva = :estActiva)
                    AND (:idProfesor IS NULL OR rps.profesor.idUsuario = :idProfesor)
                """)
        Page<TraSeccion> findConFiltros(
                        @Param("idCurso") Integer idCurso,
                        @Param("idAnio") Integer idAnio,
                        @Param("idProfesor") Integer idProfesor,
                        @Param("estActiva") Boolean estActiva,
                        Pageable pageable);

    boolean existsByDesNombreAndCurso_IdCursoAndAnioEscolar_IdAnioEscolar(
            String desNombre, Integer idCurso, Integer idAnioEscolar);

    @Query("""
        SELECT s FROM TraSeccion s
        JOIN FETCH s.curso
        JOIN FETCH s.anioEscolar
        WHERE s.estActiva = true
        AND NOT EXISTS (
                SELECT 1 FROM RelProfesorSeccion rps
                WHERE rps.seccion = s AND rps.estActivo = true
        )
        ORDER BY s.desNombre
        """)
    List<TraSeccion> findSeccionesSinProfesor();

    @Query("SELECT COUNT(s) FROM TraSeccion s WHERE s.anioEscolar.idAnioEscolar = :idAnio AND s.estActiva = true")
    long countSeccionesPorAnio(@Param("idAnio") Integer idAnio);

    @Query("SELECT COUNT(DISTINCT s.curso.idCurso) FROM TraSeccion s WHERE s.anioEscolar.idAnioEscolar = :idAnio AND s.estActiva = true")
    long countCursosPorAnio(@Param("idAnio") Integer idAnio);

    // CORREGIDO: Cambiado ras.usuario por ras.alumno
    @Query("""
        SELECT COUNT(DISTINCT ras.alumno.idUsuario)
        FROM RelAlumnoSeccion ras
        WHERE ras.seccion.anioEscolar.idAnioEscolar = :idAnio
        AND ras.estActivo = true
        """)
    long countAlumnosPorAnio(@Param("idAnio") Integer idAnio);

    // CORREGIDO: Cambiado rps.usuario por rps.profesor
    @Query("""
        SELECT COUNT(DISTINCT rps.profesor.idUsuario)
        FROM RelProfesorSeccion rps
        WHERE rps.seccion.anioEscolar.idAnioEscolar = :idAnio
        AND rps.estActivo = true
        """)
    long countProfesoresPorAnio(@Param("idAnio") Integer idAnio);
}