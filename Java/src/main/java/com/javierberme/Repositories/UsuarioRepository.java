package com.javierberme.Repositories;

import com.javierberme.Entities.Usuario;
import java.util.List;

public interface UsuarioRepository {

    void guardar(Usuario usuario);

    List<Usuario> listar();

    Usuario buscarPorId(int id);

    void actualizar(Usuario usuario);

    void eliminar(int id);
}
