package com.cursoonline.exception.auth;

public class UsuarioInactivoException extends RuntimeException {
    public UsuarioInactivoException() { super("Cuenta inactiva. Contacte al administrador."); }
}