package com.javierberme.Repositories;

import com.javierberme.Entities.DetalleVenta;
import java.util.List;

public interface DetalleVentaRepository {

    void guardar(DetalleVenta detalle);

    List<DetalleVenta> listar();

    DetalleVenta buscarPorId(int id);

    void eliminar(int id);
}
