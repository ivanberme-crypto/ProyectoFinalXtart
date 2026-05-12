package com.javierberme.Entities;

public class Venta {

    private int idVenta;
    private int idCliente;
    private int idUsuario;
    private String fecha;
    private String estado;
    private double total;

    public Venta() {
    }

    public Venta(int idVenta, int idCliente, int idUsuario,
                 String fecha, String estado, double total) {

        this.idVenta = idVenta;
        this.idCliente = idCliente;
        this.idUsuario = idUsuario;
        this.fecha = fecha;
        this.estado = estado;
        this.total = total;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {

        return idVenta +
                " | Cliente: " + idCliente +
                " | Usuario: " + idUsuario +
                " | Estado: " + estado +
                " | Total: " + total + "€";
    }
}
