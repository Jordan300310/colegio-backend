package com.cursoonline.repository.auth;

import com.cursoonline.entity.auth.SegUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface SegUsuarioRepository extends JpaRepository<SegUsuario, Integer> {

    Optional<SegUsuario> findByDesCorreo(String desCorreo);

    boolean existsByDesCorreo(String desCorreo);

    @Modifying
    @Query("UPDATE SegUsuario u SET u.fecUltimoAcceso = :fecha WHERE u.idUsuario = :id")
    void actualizarUltimoAcceso(@Param("id") Integer id, @Param("fecha") LocalDateTime fecha);
}