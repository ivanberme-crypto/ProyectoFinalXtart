package com.javierberme.Utils;

import com.javierberme.Entities.Cliente;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExportadorCSV {

    public static void exportarClientes(List<Cliente> lista) {

        String nombreFichero = "clientes.csv";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreFichero))) {

            bw.write("ID,Nombre,Email,Telefono,Direccion");
            bw.newLine();

            for (Cliente c : lista) {

                bw.write(c.getIdCliente() + "," + sanitizar(c.getNombre()) + ","
                        + sanitizar(c.getEmail()) + "," + sanitizar(c.getTelefono())
                        + "," + sanitizar(c.getDireccion()));
                bw.newLine();
            }

            System.out.println("Exportado correctamente en el fichero: " + nombreFichero);

        } catch (IOException e) {
            System.out.println("Error al exportar: " + e.getMessage());
        }
    }

    private static String sanitizar(String texto) {
        if (texto == null) return "";
        if (texto.contains(",") || texto.contains("\"") || texto.contains("\n")) {
            return "\"" + texto.replace("\"", "\"\"") + "\"";
        }
        return texto;
    }
}