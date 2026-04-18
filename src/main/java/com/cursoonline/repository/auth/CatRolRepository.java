package com.cursoonline.repository.auth;

import com.cursoonline.entity.auth.CatRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CatRolRepository extends JpaRepository<CatRol, Integer> {
    Optional<CatRol> findByCodRol(String codRol);
}