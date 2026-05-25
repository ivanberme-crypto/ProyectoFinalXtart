package com.javierberme.Interfaz;

import com.javierberme.Controllers.ClienteController;
import com.javierberme.Entities.Cliente;

import java.util.List;
import java.util.Scanner;

public class MenuClientes {

    private Scanner sc;
    private ClienteController clienteController;

    public MenuClientes(Scanner sc) {
        this.sc = sc;
        this.clienteController = new ClienteController();
    }

    public void mostrar() {

        int opcion = -1;

        while (opcion != 0) {

            System.out.println("╔═══════════════════════════════════╗");
            System.out.println("║           CLIENTES                ║");
            System.out.println("╠═══════════════════════════════════╣");
            System.out.println("║  1. Añadir cliente                ║");
            System.out.println("║  2. Ver todos los clientes        ║");
            System.out.println("║  3. Buscar cliente por ID         ║");
            System.out.println("║  4. Actualizar cliente            ║");
            System.out.println("║  5. Eliminar cliente              ║");
            System.out.println("║  6. Exportar clientes a CSV       ║");
            System.out.println("║  0. Volver al menú principal      ║");
            System.out.println("╚═══════════════════════════════════╝");
            System.out.print("Elige una opción: ");

            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Tienes que escribir un numero");
                continue;
            }

            switch (opcion) {
                case 1 -> añadirCliente();
                case 2 -> listarClientes();
                case 3 -> buscarCliente();
                case 4 -> actualizarCliente();
                case 5 -> eliminarCliente();
                case 6 -> clienteController.exportarCSV();
                case 0 -> System.out.println("Volviendo...");
                default -> System.out.println("Opcion no valida, prueba otra vez");
            }
        }
    }

    private void añadirCliente() {
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Telefono: ");
        String telefono = sc.nextLine();
        System.out.print("Direccion: ");
        String direccion = sc.nextLine();

        try {
            clienteController.crear(nombre, email, telefono, direccion);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error inesperado: " + e.getMessage());
        }
    }

    private void listarClientes() {
        List<Cliente> lista = clienteController.listar();
        if (lista.isEmpty()) {
            System.out.println("No hay clientes en la base de datos");
        } else {
            System.out.println("Lista de clientes:");
            for (Cliente c : lista) {
                System.out.println(c);
            }
        }
    }

    private void buscarCliente() {
        System.out.print("Escribe el ID del cliente: ");
        try {
            int id = Integer.parseInt(sc.nextLine());
            Cliente c = clienteController.buscar(id);
            System.out.println("Cliente encontrado: " + c);
        } catch (NumberFormatException e) {
            System.out.println("El ID tiene que ser un numero");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error: " + e.getMessage());
        }
    }

    private void actualizarCliente() {
        List<Cliente> clientes = clienteController.listar();
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes para actualizar.");
            return;
        }
        System.out.println("Clientes disponibles:");
        for (Cliente c : clientes) {
            System.out.println("  ID: " + c.getIdCliente() + " | " + c.getNombre() + " | " + c.getEmail());
        }

        System.out.print("Escribe el ID del cliente que quieres actualizar: ");
        try {
            int id = Integer.parseInt(sc.nextLine());
            System.out.print("Nuevo nombre: ");
            String nombre = sc.nextLine();
            System.out.print("Nuevo email: ");
            String email = sc.nextLine();
            System.out.print("Nuevo telefono: ");
            String telefono = sc.nextLine();
            System.out.print("Nueva direccion: ");
            String direccion = sc.nextLine();
            clienteController.actualizar(id, nombre, email, telefono, direccion);
        } catch (NumberFormatException e) {
            System.out.println("El ID tiene que ser un numero");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error: " + e.getMessage());
        }
    }

    private void eliminarCliente() {
        System.out.print("Escribe el ID del cliente a eliminar: ");
        try {
            int id = Integer.parseInt(sc.nextLine());
            clienteController.eliminar(id);
        } catch (NumberFormatException e) {
            System.out.println("El ID tiene que ser un numero");
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error: " + e.getMessage());
        }
    }
}
