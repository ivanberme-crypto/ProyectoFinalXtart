package com.javierberme.Entities;

public class Producto {

    private int idProducto;
    private String nombre;
    private String marca;
    private String modelo;
    private double talla;
    private String color;
    private double precio;
    private int stock;
    private String categoria;

    public Producto() {
    }

    public Producto(int idProducto, String nombre, String marca, String modelo, double talla,
                    String color, double precio, int stock, String categoria) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.marca = marca;
        this.modelo = modelo;
        this.talla = talla;
        this.color = color;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public double getTalla() {
        return talla;
    }

    public String getColor() {
        return color;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    public String getCategoria() {
        return categoria;
    }

    @Override
    public String toString() {
        return idProducto + " | " + nombre + " | " + marca + " | " + modelo +
                " | Talla: " + talla + " | " + precio + "€ | Stock: " + stock;
    }
}
