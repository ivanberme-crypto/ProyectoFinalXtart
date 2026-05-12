package com.javierberme.Entities;

public class Usuario extends Cliente {

    private String rol;
    private String passwordHash;

    public Usuario() {
        super();
    }

    public Usuario(int idUsuario, String nombre, String email, String rol, String passwordHash) {
        super(idUsuario, nombre, email, null, null);
        this.rol = rol;
        this.passwordHash = passwordHash;
    }

    public int getIdUsuario() {
        return idCliente;
    }

    public void setIdUsuario(int idUsuario) {
        this.idCliente = idUsuario;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Override
    public String toString() {
        return idCliente + " | " + nombre + " | " + email + " | " + rol;
    }
}
