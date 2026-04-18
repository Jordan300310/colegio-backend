package com.cursoonline.entity.auth;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "seg_sesion")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SegSesion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sesion")
    private Integer idSesion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private SegUsuario usuario;

    @Column(name = "tok_jwt", columnDefinition = "TEXT", nullable = false)
    private String tokJwt;

    @Column(name = "des_ip", length = 45)
    private String desIp;

    @Column(name = "est_activa", nullable = false)
    private Boolean estActiva = true;

    @Column(name = "fec_inicio", nullable = false)
    private LocalDateTime fecInicio;

    @Column(name = "fec_expiracion", nullable = false)
    private LocalDateTime fecExpiracion;

    @Column(name = "fec_cierre")
    private LocalDateTime fecCierre;

    @PrePersist
    protected void onCreate() { fecInicio = LocalDateTime.now(); }
}