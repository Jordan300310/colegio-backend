package com.cursoonline.entity.auth;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "aud_log_contrasena")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AudLogContrasena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_log_contrasena")
    private Integer idLogContrasena;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private SegUsuario usuario;

    @Column(name = "des_ip", length = 45)
    private String desIp;

    @Column(name = "est_exitoso", nullable = false)
    private Boolean estExitoso = true;

    @Column(name = "des_detalle", length = 255)
    private String desDetalle;

    @Column(name = "fec_cambio", nullable = false)
    private LocalDateTime fecCambio;

    @PrePersist
    protected void onCreate() { fecCambio = LocalDateTime.now(); }
}