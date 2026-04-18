package com.cursoonline.exception.auth;

public class ContrasenaIgualException extends RuntimeException {
    public ContrasenaIgualException() { super("La contraseña no puede ser igual a la actual."); }
}