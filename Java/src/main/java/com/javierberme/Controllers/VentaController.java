package com.javierberme.Controllers;

import com.javierberme.Entities.Venta;
import com.javierberme.Services.VentaService;

import java.util.List;

public class VentaController {

    private VentaService ventaService;

    public VentaController() {
        this.ventaService = new VentaService();
    }

    public void crear(int idCliente, int idUsuario, String estado, double total) {
        ventaService.crearVenta(idCliente, idUsuario, estado, total);
    }

    public List<Venta> listar() {
        return ventaService.listarVentas();
    }

    public Venta buscar(int id) {
        return ventaService.buscarVenta(id);
    }

    public void eliminar(int id) {
        ventaService.eliminarVenta(id);
    }
}
