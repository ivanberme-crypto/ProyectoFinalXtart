package com.javierberme.Repositories;

import com.javierberme.Entities.Venta;

import java.util.List;

public interface VentaRepository {

    void guardar(Venta venta);

    List<Venta> listar();

    Venta buscarPorId(int id);

    void eliminar(int id);

}
