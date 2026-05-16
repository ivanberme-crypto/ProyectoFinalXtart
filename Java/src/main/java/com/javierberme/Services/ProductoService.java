package com.javierberme.Services;

import com.javierberme.Entities.Producto;
import com.javierberme.Repositories.ProductoRepository;
import com.javierberme.Repositories.ProductoRepositoryImpl;

import java.util.List;

public class ProductoService {

    private ProductoRepository productoRepository;

    public ProductoService() {
        this.productoRepository = new ProductoRepositoryImpl();
    }

    public void crearProducto(String nombre, String marca, String modelo, double talla,
                              String color, double precio, int stock, String categoria) {

        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }

        if (stock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }

        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio");
        }

        Producto p = new Producto(0, nombre, marca, modelo, talla, color, precio, stock, categoria);
        productoRepository.guardar(p);
    }

    public List<Producto> listarProductos() {
        return productoRepository.listar();
    }

    public Producto buscarProducto(int id) {

        if (id <= 0) {
            throw new IllegalArgumentException("El ID tiene que ser mayor que 0");
        }

        Producto p = productoRepository.buscarPorId(id);

        if (p == null) {
            throw new IllegalArgumentException("No existe ningun producto con el ID: " + id);
        }

        return p;
    }

    public void eliminarProducto(int id) {
        productoRepository.eliminar(id);
    }
}
