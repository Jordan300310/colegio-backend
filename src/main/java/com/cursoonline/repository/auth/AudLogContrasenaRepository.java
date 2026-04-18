package com.cursoonline.repository.auth;

import com.cursoonline.entity.auth.AudLogContrasena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AudLogContrasenaRepository extends JpaRepository<AudLogContrasena, Integer> {}