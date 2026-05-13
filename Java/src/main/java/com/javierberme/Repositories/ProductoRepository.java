package com.javierberme.Repositories;

import com.javierberme.Entities.Producto;

import java.util.List;

public interface ProductoRepository {

    void guardar(Producto producto);

    List<Producto> listar();

    Producto buscarPorId(int id);

    void eliminar(int id);

}
