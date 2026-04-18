package com.cursoonline.entity.auth;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "seg_usuario")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SegUsuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_rol", nullable = false)
    private CatRol rol;

    @Column(name = "des_nombres", length = 80, nullable = false)
    private String desNombres;

    @Column(name = "des_apellidos", length = 80, nullable = false)
    private String desApellidos;

    @Column(name = "des_correo", length = 120, nullable = false, unique = true)
    private String desCorreo;

    @Column(name = "pwd_contrasena", length = 255, nullable = false)
    private String pwdContrasena;

    @Column(name = "est_activo", nullable = false)
    private Boolean estActivo = true;

    @Column(name = "est_pwd_temporal", nullable = false)
    private Boolean estPwdTemporal = false;

    @Column(name = "fec_creacion", nullable = false, updatable = false)
    private LocalDateTime fecCreacion;

    @Column(name = "fec_actualizacion")
    private LocalDateTime fecActualizacion;

    @Column(name = "fec_ultimo_acceso")
    private LocalDateTime fecUltimoAcceso;

    @PrePersist
    protected void onCreate() { fecCreacion = LocalDateTime.now(); }

    @PreUpdate
    protected void onUpdate() { fecActualizacion = LocalDateTime.now(); }

    // ── UserDetails ───────────────────────────────────────────────────────────

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(rol.getCodRol()));
    }

    @Override public String  getUsername()               { return desCorreo; }
    @Override public String  getPassword()               { return pwdContrasena; }
    @Override public boolean isEnabled()                 { return Boolean.TRUE.equals(estActivo); }
    @Override public boolean isAccountNonExpired()       { return true; }
    @Override public boolean isAccountNonLocked()        { return true; }
    @Override public boolean isCredentialsNonExpired()   { return true; }
}