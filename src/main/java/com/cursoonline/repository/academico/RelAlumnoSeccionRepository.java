package com.cursoonline.repository.academico;

import com.cursoonline.entity.academico.CatCurso;
import com.cursoonline.entity.academico.RelAlumnoSeccion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RelAlumnoSeccionRepository extends JpaRepository<RelAlumnoSeccion, Integer> {

    @Query("""
           SELECT DISTINCT c
           FROM   RelAlumnoSeccion ras
                  JOIN ras.seccion s
                  JOIN s.curso c
           WHERE  ras.alumno.idUsuario = :idUsuario
             AND  ras.estActivo  = true
             AND  s.estActiva    = true
             AND  c.estActivo    = true
             AND  c.estPublicado = true
           """)
    Page<CatCurso> findCursosPublicadosByAlumno(
            @Param("idUsuario") Integer idUsuario,
            Pageable pageable);
}