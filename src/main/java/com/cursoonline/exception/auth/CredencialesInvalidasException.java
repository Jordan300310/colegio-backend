package com.cursoonline.exception.auth;
public class CredencialesInvalidasException extends RuntimeException {
    public CredencialesInvalidasException() { super("Correo o contraseña incorrectos."); }
}