package com.javierberme.Services;

import com.javierberme.Entities.DetalleVenta;
import com.javierberme.Repositories.DetalleVentaRepository;
import com.javierberme.Repositories.DetalleVentaRepositoryImpl;

import java.util.List;

public class DetalleVentaService {

    private DetalleVentaRepository detalleVentaRepository;

    public DetalleVentaService() {
        this.detalleVentaRepository = new DetalleVentaRepositoryImpl();
    }

    public void crearDetalle(int idVenta, int idProducto, int cantidad, double precioUnitario) {

        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad tiene que ser mayor que 0");
        }

        if (precioUnitario < 0) {
            throw new IllegalArgumentException("El precio unitario no puede ser negativo");
        }

        DetalleVenta d = new DetalleVenta(0, idVenta, idProducto, cantidad, precioUnitario);
        detalleVentaRepository.guardar(d);
    }

    public List<DetalleVenta> listarDetalles() {
        return detalleVentaRepository.listar();
    }

    public DetalleVenta buscarDetalle(int id) {

        if (id <= 0) {
            throw new IllegalArgumentException("El ID tiene que ser mayor que 0");
        }

        DetalleVenta d = detalleVentaRepository.buscarPorId(id);

        if (d == null) {
            throw new IllegalArgumentException("No existe ningun detalle con el ID: " + id);
        }

        return d;
    }

    public void eliminarDetalle(int id) {
        detalleVentaRepository.eliminar(id);
    }
}
