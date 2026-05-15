package com.javierberme.Controllers;

import com.javierberme.Entities.Producto;
import com.javierberme.Services.ProductoService;

import java.util.List;

public class ProductoController {

    private ProductoService productoService;

    public ProductoController() {
        this.productoService = new ProductoService();
    }

    public void crear(String nombre, String marca, String modelo, double talla,
                      String color, double precio, int stock, String categoria) {
        productoService.crearProducto(nombre, marca, modelo, talla, color, precio, stock, categoria);
    }

    public List<Producto> listar() {
        return productoService.listarProductos();
    }

    public Producto buscar(int id) {
        return productoService.buscarProducto(id);
    }

    public void eliminar(int id) {
        productoService.eliminarProducto(id);
    }
}
