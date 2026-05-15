package com.javierberme.Controllers;

import com.javierberme.Entities.Usuario;
import com.javierberme.Services.UsuarioService;

import java.util.List;

public class UsuarioController {

    private UsuarioService usuarioService;

    public UsuarioController() {
        this.usuarioService = new UsuarioService();
    }

    public void crear(String nombre, String email, String rol, String password) {
        usuarioService.crearUsuario(nombre, email, rol, password);
    }

    public List<Usuario> listar() {
        return usuarioService.listarUsuarios();
    }

    public Usuario buscar(int id) {
        return usuarioService.buscarUsuario(id);
    }

    public void actualizar(int id, String nombre, String email, String rol, String password) {
        usuarioService.actualizarUsuario(id, nombre, email, rol, password);
    }

    public void eliminar(int id) {
        usuarioService.eliminarUsuario(id);
    }
}
