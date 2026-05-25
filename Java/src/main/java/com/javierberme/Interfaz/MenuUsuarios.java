package com.javierberme.Interfaz;

import com.javierberme.Controllers.UsuarioController;
import com.javierberme.Entities.Usuario;

import java.util.List;
import java.util.Scanner;

public class MenuUsuarios {

    private Scanner sc;
    private UsuarioController usuarioController;

    public MenuUsuarios(Scanner sc) {
        this.sc = sc;
        this.usuarioController = new UsuarioController();
    }

    public void mostrar() {

        int opcion = -1;

        while (opcion != 0) {

            System.out.println("╔═══════════════════════════════════╗");
            System.out.println("║             USUARIOS              ║");
            System.out.println("╠═══════════════════════════════════╣");
            System.out.println("║  1. Añadir usuario                ║");
            System.out.println("║  2. Ver todos los usuarios        ║");
            System.out.println("║  3. Buscar usuario por ID         ║");
            System.out.println("║  4. Actualizar usuario            ║");
            System.out.println("║  5. Eliminar usuario              ║");
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
                case 1 -> añadirUsuario();
                case 2 -> listarUsuarios();
                case 3 -> buscarUsuario();
                case 4 -> actualizarUsuario();
                case 5 -> eliminarUsuario();
                case 0 -> System.out.println("Volviendo...");
                default -> System.out.println("Opcion no valida");
            }
        }
    }

    private void añadirUsuario() {
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Rol (Administrador/Vendedor): ");
        String rol = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();

        try {
            usuarioController.crear(nombre, email, rol, password);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error: " + e.getMessage());
        }
    }

    private void listarUsuarios() {
        List<Usuario> lista = usuarioController.listar();
        if (lista.isEmpty()) {
            System.out.println("No hay usuarios en la base de datos");
        } else {
            System.out.println("Lista de usuarios:");
            for (Usuario u : lista) {
                System.out.println(u);
            }
        }
    }

    private void buscarUsuario() {
        System.out.print("Escribe el ID del usuario: ");
        try {
            int id = Integer.parseInt(sc.nextLine());
            Usuario u = usuarioController.buscar(id);
            System.out.println("Usuario encontrado: " + u);
        } catch (NumberFormatException e) {
            System.out.println("El ID tiene que ser un numero");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error: " + e.getMessage());
        }
    }

    private void actualizarUsuario() {
        System.out.print("Escribe el ID del usuario que quieres actualizar: ");
        try {
            int id = Integer.parseInt(sc.nextLine());
            System.out.print("Nuevo nombre: ");
            String nombre = sc.nextLine();
            System.out.print("Nuevo email: ");
            String email = sc.nextLine();
            System.out.print("Nuevo rol: ");
            String rol = sc.nextLine();
            System.out.print("Nueva password: ");
            String password = sc.nextLine();
            usuarioController.actualizar(id, nombre, email, rol, password);
        } catch (NumberFormatException e) {
            System.out.println("El ID tiene que ser un numero");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error: " + e.getMessage());
        }
    }

    private void eliminarUsuario() {
        System.out.print("Escribe el ID del usuario a eliminar: ");
        try {
            int id = Integer.parseInt(sc.nextLine());
            usuarioController.eliminar(id);
        } catch (NumberFormatException e) {
            System.out.println("El ID tiene que ser un numero");
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error: " + e.getMessage());
        }
    }
}
