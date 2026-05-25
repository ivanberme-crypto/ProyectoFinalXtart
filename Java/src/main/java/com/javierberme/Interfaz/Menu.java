package com.javierberme.Interfaz;

import java.util.Scanner;

public class Menu {

    private static Scanner sc = new Scanner(System.in);

    private static MenuClientes menuClientes = new MenuClientes(sc);
    private static MenuProductos menuProductos = new MenuProductos(sc);
    private static MenuVentas menuVentas = new MenuVentas(sc);
    private static MenuUsuarios menuUsuarios = new MenuUsuarios(sc);
    private static MenuDetalles menuDetalles = new MenuDetalles(sc);

    public static void start() {

        int opcion = -1;

        while (opcion != 0) {

            System.out.println("╔══════════════════════════╗");
            System.out.println("║      TIENDA  ZAPNN       ║");
            System.out.println("╠══════════════════════════╣");
            System.out.println("║  1. Gestión de clientes  ║");
            System.out.println("║  2. Gestión de productos ║");
            System.out.println("║  3. Gestión de ventas    ║");
            System.out.println("║  4. Gestión de usuarios  ║");
            System.out.println("║  5. Gestión de detalles  ║");
            System.out.println("║  0. Salir                ║");
            System.out.println("╚══════════════════════════╝");
            System.out.print("Elige una opción: ");

            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Escribe un numero valido");
                continue;
            }

            switch (opcion) {
                case 1 -> menuClientes.mostrar();
                case 2 -> menuProductos.mostrar();
                case 3 -> menuVentas.mostrar();
                case 4 -> menuUsuarios.mostrar();
                case 5 -> menuDetalles.mostrar();
                case 0 -> System.out.println("Hasta luego!");
                default -> System.out.println("Opcion no valida");
            }
        }

        sc.close();
    }
}