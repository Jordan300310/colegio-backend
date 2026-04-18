package com.cursoonline.entity.auth;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "aud_log_acceso")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AudLogAcceso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_log_acceso")
    private Integer idLogAcceso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private SegUsuario usuario;

    @Column(name = "des_correo", length = 120)
    private String desCorreo;

    @Column(name = "des_ip", length = 45)
    private String desIp;

    @Column(name = "cod_rol", length = 20)
    private String codRol;

    @Column(name = "est_exitoso", nullable = false)
    private Boolean estExitoso = false;

    @Column(name = "des_detalle", length = 255)
    private String desDetalle;

    @Column(name = "fec_intento", nullable = false)
    private LocalDateTime fecIntento;

    @PrePersist
    protected void onCreate() { fecIntento = LocalDateTime.now(); }
}