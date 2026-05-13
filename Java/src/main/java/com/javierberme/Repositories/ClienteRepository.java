package com.javierberme.Repositories;

import com.javierberme.Entities.Cliente;
import java.util.List;

public interface ClienteRepository {

    void guardar(Cliente cliente);

    List<Cliente> listar();

    Cliente buscarPorId(int id);

    void actualizar(Cliente cliente);

    void eliminar(int id);

}
