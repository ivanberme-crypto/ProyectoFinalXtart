package com.javierberme.Interfaz;

import com.javierberme.Controllers.VentaController;
import com.javierberme.Entities.Venta;

import java.util.List;
import java.util.Scanner;

public class MenuVentas {

    private Scanner sc;
    private VentaController ventaController;

    public MenuVentas(Scanner sc) {
        this.sc = sc;
        this.ventaController = new VentaController();
    }

    public void mostrar() {

        int opcion = -1;

        while (opcion != 0) {

            System.out.println("╔═══════════════════════════════════╗");
            System.out.println("║             VENTAS                ║");
            System.out.println("╠═══════════════════════════════════╣");
            System.out.println("║  1. Registrar venta               ║");
            System.out.println("║  2. Ver todas las ventas          ║");
            System.out.println("║  3. Buscar venta por ID           ║");
            System.out.println("║  4. Eliminar venta                ║");
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
                case 1 -> registrarVenta();
                case 2 -> listarVentas();
                case 3 -> buscarVenta();
                case 4 -> eliminarVenta();
                case 0 -> System.out.println("Volviendo...");
                default -> System.out.println("Opcion no valida");
            }
        }
    }

    private void registrarVenta() {
        int idCliente = 0;
        System.out.print("ID del cliente: ");
        try { idCliente = Integer.parseInt(sc.nextLine()); }
        catch (NumberFormatException e) { System.out.println("ID no valido"); return; }

        int idUsuario = 0;
        System.out.print("ID del usuario: ");
        try { idUsuario = Integer.parseInt(sc.nextLine()); }
        catch (NumberFormatException e) { System.out.println("ID no valido"); return; }

        System.out.print("Estado (pendiente/completada/cancelada): ");
        String estado = sc.nextLine();

        double total = 0;
        System.out.print("Total en euros: ");
        try { total = Double.parseDouble(sc.nextLine()); }
        catch (NumberFormatException e) { System.out.println("Total no valido, se usara 0"); }

        try {
            ventaController.crear(idCliente, idUsuario, estado, total);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error: " + e.getMessage());
        }
    }

    private void listarVentas() {
        List<Venta> lista = ventaController.listar();
        if (lista.isEmpty()) {
            System.out.println("No hay ventas en la base de datos");
        } else {
            System.out.println("Lista de ventas:");
            for (Venta v : lista) {
                System.out.println(v);
            }
        }
    }

    private void buscarVenta() {
        System.out.print("Escribe el ID de la venta: ");
        try {
            int id = Integer.parseInt(sc.nextLine());
            Venta v = ventaController.buscar(id);
            System.out.println("Venta encontrada: " + v);
        } catch (NumberFormatException e) {
            System.out.println("El ID tiene que ser un numero");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error: " + e.getMessage());
        }
    }

    private void eliminarVenta() {
        System.out.print("Escribe el ID de la venta a eliminar: ");
        try {
            int id = Integer.parseInt(sc.nextLine());
            ventaController.eliminar(id);
        } catch (NumberFormatException e) {
            System.out.println("El ID tiene que ser un numero");
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error: " + e.getMessage());
        }
    }
}
