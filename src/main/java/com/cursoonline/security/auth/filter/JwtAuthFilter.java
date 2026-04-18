package com.cursoonline.security.auth.filter;
import com.cursoonline.entity.auth.SegUsuario;
import com.cursoonline.repository.auth.SegSesionRepository;

import com.cursoonline.repository.auth.SegUsuarioRepository;
import com.cursoonline.security.auth.jwt.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService           jwtService;
    private final SegUsuarioRepository usuarioRepository;
    private final SegSesionRepository  sesionRepository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest  request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain         filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = authHeader.substring(7);

        try {
            final String correo = jwtService.extraerCorreo(token);

            if (correo != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                SegUsuario usuario = usuarioRepository.findByDesCorreo(correo).orElse(null);

                if (usuario == null) {
                    filterChain.doFilter(request, response);
                    return;
                }

                // Verifica sesión activa en BD → soporte de logout real (CUS-02)
                boolean sesionActiva = sesionRepository
                        .findByTokJwtAndEstActivaTrue(token).isPresent();

                if (jwtService.esTokenValido(token, usuario) && sesionActiva) {
                    var authToken = new UsernamePasswordAuthenticationToken(
                            usuario, null, usuario.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (JwtException e) {
            log.warn("Token rechazado: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}