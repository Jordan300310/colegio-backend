package com.cursoonline.repository.auth;

import com.cursoonline.entity.auth.SegSesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface SegSesionRepository extends JpaRepository<SegSesion, Integer> {

    Optional<SegSesion> findByTokJwtAndEstActivaTrue(String tokJwt);

    @Modifying
    @Query("""
        UPDATE SegSesion s
        SET s.estActiva = false, s.fecCierre = :ahora
        WHERE s.usuario.idUsuario = :idUsuario AND s.estActiva = true
    """)
    void cerrarSesionesPorUsuario(@Param("idUsuario") Integer idUsuario,
                                   @Param("ahora") LocalDateTime ahora);

    @Modifying
    @Query("""
        UPDATE SegSesion s
        SET s.estActiva = false, s.fecCierre = :ahora
        WHERE s.tokJwt = :token AND s.estActiva = true
    """)
    void cerrarSesionPorToken(@Param("token") String token,
                               @Param("ahora") LocalDateTime ahora);
}