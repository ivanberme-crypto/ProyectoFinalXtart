package com.javierberme.Interfaz;

import com.javierberme.Controllers.ClienteController;
import com.javierberme.Controllers.DetalleVentaController;
import com.javierberme.Controllers.ProductoController;
import com.javierberme.Controllers.UsuarioController;
import com.javierberme.Controllers.VentaController;
import com.javierberme.Entities.Cliente;
import com.javierberme.Entities.DetalleVenta;
import com.javierberme.Entities.Producto;
import com.javierberme.Entities.Usuario;
import com.javierberme.Entities.Venta;

import java.util.List;
import java.util.Scanner;

public class Menu {

    private static Scanner sc = new Scanner(System.in);

    private static ClienteController clienteController = new ClienteController();
    private static ProductoController productoController = new ProductoController();
    private static VentaController ventaController = new VentaController();
    private static UsuarioController usuarioController = new UsuarioController();
    private static DetalleVentaController detalleVentaController = new DetalleVentaController();

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

            if (opcion == 1) {
                menuClientes();
            } else if (opcion == 2) {
                menuProductos();
            } else if (opcion == 3) {
                menuVentas();
            } else if (opcion == 4) {
                menuUsuarios();
            } else if (opcion == 5) {
                menuDetalles();
            } else if (opcion == 0) {
                System.out.println("Hasta luego!");
            } else {
                System.out.println("Opcion no valida");
            }
        }

        sc.close();
    }

    private static void menuClientes() {

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

            if (opcion == 1) {

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

            } else if (opcion == 2) {

                List<Cliente> lista = clienteController.listar();
                if (lista.isEmpty()) {
                    System.out.println("No hay clientes en la base de datos");
                } else {
                    System.out.println("Lista de clientes:");
                    for (Cliente c : lista) {
                        System.out.println(c);
                    }
                }

            } else if (opcion == 3) {

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

            } else if (opcion == 4) {

                List<Cliente> clientes = clienteController.listar();
                if (clientes.isEmpty()) {
                    System.out.println("No hay clientes para actualizar.");
                    continue;
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

            } else if (opcion == 5) {

                System.out.print("Escribe el ID del cliente a eliminar: ");
                try {
                    int id = Integer.parseInt(sc.nextLine());
                    clienteController.eliminar(id);
                } catch (NumberFormatException e) {
                    System.out.println("El ID tiene que ser un numero");
                } catch (Exception e) {
                    System.out.println("Ha ocurrido un error: " + e.getMessage());
                }

            } else if (opcion == 6) {

                clienteController.exportarCSV();

            } else if (opcion == 0) {
                System.out.println("Volviendo...");
            } else {
                System.out.println("Opcion no valida, prueba otra vez");
            }
        }
    }

    private static void menuProductos() {

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

            if (opcion == 1) {

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

            } else if (opcion == 2) {

                List<Producto> lista = productoController.listar();
                if (lista.isEmpty()) {
                    System.out.println("No hay productos en la base de datos");
                } else {
                    System.out.println("Lista de productos:");
                    for (Producto p : lista) {
                        System.out.println(p);
                    }
                }

            } else if (opcion == 3) {

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

            } else if (opcion == 4) {

                System.out.print("Escribe el ID del producto a eliminar: ");
                try {
                    int id = Integer.parseInt(sc.nextLine());
                    productoController.eliminar(id);
                } catch (NumberFormatException e) {
                    System.out.println("El ID tiene que ser un numero");
                } catch (Exception e) {
                    System.out.println("Ha ocurrido un error: " + e.getMessage());
                }

            } else if (opcion == 0) {
                System.out.println("Volviendo...");
            } else {
                System.out.println("Opcion no valida");
            }
        }
    }

    private static void menuVentas() {

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

            if (opcion == 1) {

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

            } else if (opcion == 2) {

                List<Venta> lista = ventaController.listar();
                if (lista.isEmpty()) {
                    System.out.println("No hay ventas en la base de datos");
                } else {
                    System.out.println("Lista de ventas:");
                    for (Venta v : lista) {
                        System.out.println(v);
                    }
                }

            } else if (opcion == 3) {

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

            } else if (opcion == 4) {

                System.out.print("Escribe el ID de la venta a eliminar: ");
                try {
                    int id = Integer.parseInt(sc.nextLine());
                    ventaController.eliminar(id);
                } catch (NumberFormatException e) {
                    System.out.println("El ID tiene que ser un numero");
                } catch (Exception e) {
                    System.out.println("Ha ocurrido un error: " + e.getMessage());
                }

            } else if (opcion == 0) {
                System.out.println("Volviendo...");
            } else {
                System.out.println("Opcion no valida");
            }
        }
    }

    private static void menuUsuarios() {

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

            if (opcion == 1) {

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

            } else if (opcion == 2) {

                List<Usuario> lista = usuarioController.listar();
                if (lista.isEmpty()) {
                    System.out.println("No hay usuarios en la base de datos");
                } else {
                    System.out.println("Lista de usuarios:");
                    for (Usuario u : lista) {
                        System.out.println(u);
                    }
                }

            } else if (opcion == 3) {

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

            } else if (opcion == 4) {

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

            } else if (opcion == 5) {

                System.out.print("Escribe el ID del usuario a eliminar: ");
                try {
                    int id = Integer.parseInt(sc.nextLine());
                    usuarioController.eliminar(id);
                } catch (NumberFormatException e) {
                    System.out.println("El ID tiene que ser un numero");
                } catch (Exception e) {
                    System.out.println("Ha ocurrido un error: " + e.getMessage());
                }

            } else if (opcion == 0) {
                System.out.println("Volviendo...");
            } else {
                System.out.println("Opcion no valida");
            }
        }
    }

    private static void menuDetalles() {

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

            if (opcion == 1) {

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

            } else if (opcion == 2) {

                List<DetalleVenta> lista = detalleVentaController.listar();
                if (lista.isEmpty()) {
                    System.out.println("No hay detalles en la base de datos");
                } else {
                    System.out.println("Lista de detalles:");
                    for (DetalleVenta d : lista) {
                        System.out.println(d);
                    }
                }

            } else if (opcion == 3) {

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

            } else if (opcion == 4) {

                System.out.print("Escribe el ID del detalle a eliminar: ");
                try {
                    int id = Integer.parseInt(sc.nextLine());
                    detalleVentaController.eliminar(id);
                } catch (NumberFormatException e) {
                    System.out.println("El ID tiene que ser un numero");
                } catch (Exception e) {
                    System.out.println("Ha ocurrido un error: " + e.getMessage());
                }

            } else if (opcion == 0) {
                System.out.println("Volviendo...");
            } else {
                System.out.println("Opcion no valida");
            }
        }
    }
}