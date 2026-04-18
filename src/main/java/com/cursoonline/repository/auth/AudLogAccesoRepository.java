package com.cursoonline.repository.auth;

import com.cursoonline.entity.auth.AudLogAcceso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AudLogAccesoRepository extends JpaRepository<AudLogAcceso, Integer> {}