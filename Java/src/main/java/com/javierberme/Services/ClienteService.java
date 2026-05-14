package com.javierberme.Services;

import com.javierberme.Entities.Cliente;
import com.javierberme.Repositories.ClienteRepository;
import com.javierberme.Repositories.ClienteRepositoryImpl;
import com.javierberme.Utils.ExportadorCSV;

import java.util.List;

public class ClienteService {

    private ClienteRepository clienteRepository;

    public ClienteService() {
        this.clienteRepository = new ClienteRepositoryImpl();
    }

    public void crearCliente(String nombre, String email, String telefono, String direccion) {

        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio");
        }
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("El email no puede estar vacio");
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException("El email no es valido");
        }

        Cliente cliente = new Cliente(0, nombre, email, telefono, direccion);
        clienteRepository.guardar(cliente);
    }

    public List<Cliente> listarClientes() {
        return clienteRepository.listar();
    }

    public Cliente buscarCliente(int id) {

        if (id <= 0) {
            throw new IllegalArgumentException("El ID tiene que ser mayor que 0");
        }

        Cliente c = clienteRepository.buscarPorId(id);

        if (c == null) {
            throw new IllegalArgumentException("No existe ningun cliente con el ID: " + id);
        }

        return c;
    }

    public void actualizarCliente(int id, String nombre, String email, String telefono, String direccion) {

        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio");
        }

        Cliente c = new Cliente(id, nombre, email, telefono, direccion);
        clienteRepository.actualizar(c);
    }

    public void eliminarCliente(int id) {
        clienteRepository.eliminar(id);
    }

    public void exportarClientesCSV() {

        List<Cliente> lista = clienteRepository.listar();
        ExportadorCSV.exportarClientes(lista);
    }
}
