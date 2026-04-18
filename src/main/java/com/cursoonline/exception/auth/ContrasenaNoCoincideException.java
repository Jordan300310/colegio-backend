package com.cursoonline.exception.auth;

public class ContrasenaNoCoincideException extends RuntimeException {
    public ContrasenaNoCoincideException() { super("Las contraseñas no coinciden."); }
}