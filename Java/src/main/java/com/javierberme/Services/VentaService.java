package com.javierberme.Services;

import com.javierberme.Entities.Venta;
import com.javierberme.Repositories.VentaRepository;
import com.javierberme.Repositories.VentaRepositoryImpl;

import java.util.List;

public class VentaService {

    private VentaRepository ventaRepository;

    public VentaService() {
        this.ventaRepository = new VentaRepositoryImpl();
    }

    public void crearVenta(int idCliente, int idUsuario, String estado, double total) {

        if (total < 0) {
            throw new IllegalArgumentException("El total no puede ser negativo");
        }

        if (estado == null || estado.isEmpty()) {
            throw new IllegalArgumentException("El estado no puede estar vacio");
        }

        Venta v = new Venta(0, idCliente, idUsuario, null, estado, total);
        ventaRepository.guardar(v);
    }

    public List<Venta> listarVentas() {
        return ventaRepository.listar();
    }

    public Venta buscarVenta(int id) {

        if (id <= 0) {
            throw new IllegalArgumentException("El ID tiene que ser mayor que 0");
        }

        Venta v = ventaRepository.buscarPorId(id);

        if (v == null) {
            throw new IllegalArgumentException("No existe ninguna venta con el ID: " + id);
        }

        return v;
    }

    public void eliminarVenta(int id) {
        ventaRepository.eliminar(id);
    }
}

// Fix: added null check for venta object
