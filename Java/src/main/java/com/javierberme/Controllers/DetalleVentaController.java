package com.javierberme.Controllers;

import com.javierberme.Entities.DetalleVenta;
import com.javierberme.Services.DetalleVentaService;

import java.util.List;

public class DetalleVentaController {

    private DetalleVentaService detalleVentaService;

    public DetalleVentaController() {
        this.detalleVentaService = new DetalleVentaService();
    }

    public void crear(int idVenta, int idProducto, int cantidad, double precioUnitario) {
        detalleVentaService.crearDetalle(idVenta, idProducto, cantidad, precioUnitario);
    }

    public List<DetalleVenta> listar() {
        return detalleVentaService.listarDetalles();
    }

    public DetalleVenta buscar(int id) {
        return detalleVentaService.buscarDetalle(id);
    }

    public void eliminar(int id) {
        detalleVentaService.eliminarDetalle(id);
    }
}
