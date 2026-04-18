package com.cursoonline.entity.auth;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cat_rol")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatRol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Integer idRol;

    @Column(name = "cod_rol", length = 20, nullable = false, unique = true)
    private String codRol;

    @Column(name = "des_nombre", length = 50, nullable = false)
    private String desNombre;

    @Column(name = "des_descripcion", columnDefinition = "TEXT")
    private String desDescripcion;

    @Column(name = "est_activo", nullable = false)
    private Boolean estActivo = true;

    @Column(name = "fec_creacion", nullable = false, updatable = false)
    private LocalDateTime fecCreacion;

    @PrePersist
    protected void onCreate() {
        fecCreacion = LocalDateTime.now();
    }
}