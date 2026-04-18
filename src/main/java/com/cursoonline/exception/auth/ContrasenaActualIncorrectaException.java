package com.cursoonline.exception.auth;

public class ContrasenaActualIncorrectaException extends RuntimeException {
    public ContrasenaActualIncorrectaException() { super("La contraseña actual es incorrecta."); }
}
