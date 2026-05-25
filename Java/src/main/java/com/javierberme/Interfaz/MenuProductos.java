package com.javierberme.Interfaz;

import com.javierberme.Controllers.ProductoController;
import com.javierberme.Entities.Producto;

import java.util.List;
import java.util.Scanner;

public class MenuProductos {

    private Scanner sc;
    private ProductoController productoController;

    public MenuProductos(Scanner sc) {
        this.sc = sc;
        this.productoController = new ProductoController();
    }

    public void mostrar() {

        int opcion = -1;

        while (opcion != 0) {

            System.out.println("╔═══════════════════════════════════╗");
            System.out.println("║            PRODUCTOS              ║");
            System.out.println("╠═══════════════════════════════════╣");
            System.out.println("║  1. Añadir producto               ║");
            System.out.println("║  2. Ver todos los productos       ║");
            System.out.println("║  3. Buscar producto por ID        ║");
            System.out.println("║  4. Eliminar producto             ║");
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
                case 1 -> añadirProducto();
                case 2 -> listarProductos();
                case 3 -> buscarProducto();
                case 4 -> eliminarProducto();
                case 0 -> System.out.println("Volviendo...");
                default -> System.out.println("Opcion no valida");
            }
        }
    }

    private void añadirProducto() {
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Marca: ");
        String marca = sc.nextLine();
        System.out.print("Modelo: ");
        String modelo = sc.nextLine();

        double talla = 0;
        System.out.print("Talla: ");
        try { talla = Double.parseDouble(sc.nextLine()); }
        catch (NumberFormatException e) { System.out.println("Talla no valida, se usara 0"); }

        System.out.print("Color: ");
        String color = sc.nextLine();

        double precio = 0;
        System.out.print("Precio: ");
        try { precio = Double.parseDouble(sc.nextLine()); }
        catch (NumberFormatException e) { System.out.println("Precio no valido, se usara 0"); }

        int stock = 0;
        System.out.print("Stock: ");
        try { stock = Integer.parseInt(sc.nextLine()); }
        catch (NumberFormatException e) { System.out.println("Stock no valido, se usara 0"); }

        System.out.print("Categoria: ");
        String categoria = sc.nextLine();

        try {
            productoController.crear(nombre, marca, modelo, talla, color, precio, stock, categoria);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error: " + e.getMessage());
        }
    }

    private void listarProductos() {
        List<Producto> lista = productoController.listar();
        if (lista.isEmpty()) {
            System.out.println("No hay productos en la base de datos");
        } else {
            System.out.println("Lista de productos:");
            for (Producto p : lista) {
                System.out.println(p);
            }
        }
    }

    private void buscarProducto() {
        System.out.print("Escribe el ID del producto: ");
        try {
            int id = Integer.parseInt(sc.nextLine());
            Producto p = productoController.buscar(id);
            System.out.println("Producto encontrado: " + p);
        } catch (NumberFormatException e) {
            System.out.println("El ID tiene que ser un numero");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error: " + e.getMessage());
        }
    }

    private void eliminarProducto() {
        System.out.print("Escribe el ID del producto a eliminar: ");
        try {
            int id = Integer.parseInt(sc.nextLine());
            productoController.eliminar(id);
        } catch (NumberFormatException e) {
            System.out.println("El ID tiene que ser un numero");
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error: " + e.getMessage());
        }
    }
}
