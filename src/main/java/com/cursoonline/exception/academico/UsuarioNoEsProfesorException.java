package com.cursoonline.exception.academico;

public class UsuarioNoEsProfesorException extends RuntimeException {
    public UsuarioNoEsProfesorException() {
        super("El usuario seleccionado no tiene el rol de Profesor.");
    }

    public UsuarioNoEsProfesorException(Integer idUsuario) {
        super("El usuario con id " + idUsuario + " no tiene el rol de Profesor.");
    }
}