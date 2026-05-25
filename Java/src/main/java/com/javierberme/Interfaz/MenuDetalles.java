package com.javierberme.Interfaz;

import com.javierberme.Controllers.DetalleVentaController;
import com.javierberme.Entities.DetalleVenta;

import java.util.List;
import java.util.Scanner;

public class MenuDetalles {

    private Scanner sc;
    private DetalleVentaController detalleVentaController;

    public MenuDetalles(Scanner sc) {
        this.sc = sc;
        this.detalleVentaController = new DetalleVentaController();
    }

    public void mostrar() {

        int opcion = -1;

        while (opcion != 0) {

            System.out.println("╔═══════════════════════════════════╗");
            System.out.println("║        DETALLES DE VENTA          ║");
            System.out.println("╠═══════════════════════════════════╣");
            System.out.println("║  1. Añadir detalle                ║");
            System.out.println("║  2. Ver todos los detalles        ║");
            System.out.println("║  3. Buscar detalle por ID         ║");
            System.out.println("║  4. Eliminar detalle              ║");
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
                case 1 -> añadirDetalle();
                case 2 -> listarDetalles();
                case 3 -> buscarDetalle();
                case 4 -> eliminarDetalle();
                case 0 -> System.out.println("Volviendo...");
                default -> System.out.println("Opcion no valida");
            }
        }
    }

    private void añadirDetalle() {
        int idVenta = 0;
        System.out.print("ID de la venta: ");
        try { idVenta = Integer.parseInt(sc.nextLine()); }
        catch (NumberFormatException e) { System.out.println("ID no valido"); return; }

        int idProducto = 0;
        System.out.print("ID del producto: ");
        try { idProducto = Integer.parseInt(sc.nextLine()); }
        catch (NumberFormatException e) { System.out.println("ID no valido"); return; }

        int cantidad = 0;
        System.out.print("Cantidad: ");
        try { cantidad = Integer.parseInt(sc.nextLine()); }
        catch (NumberFormatException e) { System.out.println("Cantidad no valida, se usara 0"); }

        double precioUnitario = 0;
        System.out.print("Precio unitario: ");
        try { precioUnitario = Double.parseDouble(sc.nextLine()); }
        catch (NumberFormatException e) { System.out.println("Precio no valido, se usara 0"); }

        try {
            detalleVentaController.crear(idVenta, idProducto, cantidad, precioUnitario);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error: " + e.getMessage());
        }
    }

    private void listarDetalles() {
        List<DetalleVenta> lista = detalleVentaController.listar();
        if (lista.isEmpty()) {
            System.out.println("No hay detalles en la base de datos");
        } else {
            System.out.println("Lista de detalles:");
            for (DetalleVenta d : lista) {
                System.out.println(d);
            }
        }
    }

    private void buscarDetalle() {
        System.out.print("Escribe el ID del detalle: ");
        try {
            int id = Integer.parseInt(sc.nextLine());
            DetalleVenta d = detalleVentaController.buscar(id);
            System.out.println("Detalle encontrado: " + d);
        } catch (NumberFormatException e) {
            System.out.println("El ID tiene que ser un numero");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error: " + e.getMessage());
        }
    }

    private void eliminarDetalle() {
        System.out.print("Escribe el ID del detalle a eliminar: ");
        try {
            int id = Integer.parseInt(sc.nextLine());
            detalleVentaController.eliminar(id);
        } catch (NumberFormatException e) {
            System.out.println("El ID tiene que ser un numero");
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error: " + e.getMessage());
        }
    }
}
