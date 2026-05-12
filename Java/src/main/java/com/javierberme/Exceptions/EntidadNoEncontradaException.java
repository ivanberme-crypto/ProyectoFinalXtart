package com.javierberme.Exceptions;

public class EntidadNoEncontradaException extends Exception {


    public EntidadNoEncontradaException(String mensaje) {
        super(mensaje);
    }


    public EntidadNoEncontradaException(String tipoEntidad, int id) {
        super("No existe ningún/a " + tipoEntidad + " con ID " + id);
    }
}
