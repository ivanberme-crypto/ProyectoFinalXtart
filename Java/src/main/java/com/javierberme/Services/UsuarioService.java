package com.javierberme.Services;

import com.javierberme.Entities.Usuario;
import com.javierberme.Repositories.UsuarioRepository;
import com.javierberme.Repositories.UsuarioRepositoryImpl;

import java.util.List;

public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    public UsuarioService() {
        this.usuarioRepository = new UsuarioRepositoryImpl();
    }

    public void crearUsuario(String nombre, String email, String rol, String passwordHash) {

        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio");
        }
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("El email no puede estar vacio");
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException("El email no es valido");
        }

        Usuario u = new Usuario(0, nombre, email, rol, passwordHash);
        usuarioRepository.guardar(u);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.listar();
    }

    public Usuario buscarUsuario(int id) {

        if (id <= 0) {
            throw new IllegalArgumentException("El ID tiene que ser mayor que 0");
        }

        Usuario u = usuarioRepository.buscarPorId(id);

        if (u == null) {
            throw new IllegalArgumentException("No existe ningun usuario con el ID: " + id);
        }

        return u;
    }

    public void actualizarUsuario(int id, String nombre, String email, String rol, String passwordHash) {

        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio");
        }

        Usuario u = new Usuario(id, nombre, email, rol, passwordHash);
        usuarioRepository.actualizar(u);
    }

    public void eliminarUsuario(int id) {
        usuarioRepository.eliminar(id);
    }
}
