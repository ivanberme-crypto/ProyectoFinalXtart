package com.javierberme.Controllers;

import com.javierberme.Entities.Cliente;
import com.javierberme.Services.ClienteService;

import java.util.List;

public class ClienteController {

    private ClienteService clienteService;

    public ClienteController() {
        this.clienteService = new ClienteService();
    }

    public void crear(String nombre, String email, String telefono, String direccion) {
        clienteService.crearCliente(nombre, email, telefono, direccion);
    }

    public List<Cliente> listar() {
        return clienteService.listarClientes();
    }

    public Cliente buscar(int id) {
        return clienteService.buscarCliente(id);
    }

    public void actualizar(int id, String nombre, String email, String telefono, String direccion) {
        clienteService.actualizarCliente(id, nombre, email, telefono, direccion);
    }

    public void eliminar(int id) {
        clienteService.eliminarCliente(id);
    }

    public void exportarCSV() {
        clienteService.exportarClientesCSV();
    }
}
