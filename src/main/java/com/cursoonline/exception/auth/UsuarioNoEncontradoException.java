package com.cursoonline.exception.auth;

public class UsuarioNoEncontradoException extends RuntimeException {
    public UsuarioNoEncontradoException(Integer id) {
        super("Usuario con ID " + id + " no encontrado.");
    }
}