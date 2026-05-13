package com.javierberme.Repositories;

import com.javierberme.Entities.Producto;
import com.javierberme.Utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepositoryImpl implements ProductoRepository {

    @Override
    public void guardar(Producto producto) {

        String sql = "INSERT INTO productos (nombre, marca, modelo, talla, color, precio, stock, categoria) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getMarca());
            ps.setString(3, producto.getModelo());
            ps.setDouble(4, producto.getTalla());
            ps.setString(5, producto.getColor());
            ps.setDouble(6, producto.getPrecio());
            ps.setInt(7, producto.getStock());
            ps.setString(8, producto.getCategoria());

            ps.executeUpdate();
            System.out.println("Producto guardado correctamente");

            ps.close();
            con.close();

        } catch (Exception e) {
            System.out.println("Error al guardar el producto: " + e.getMessage());
        }
    }

    @Override
    public List<Producto> listar() {

        ArrayList<Producto> lista = new ArrayList<Producto>();
        String sql = "SELECT * FROM productos";

        try {
            Connection con = DatabaseConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id_producto");
                String nombre = rs.getString("nombre");
                String marca = rs.getString("marca");
                String modelo = rs.getString("modelo");
                double talla = rs.getDouble("talla");
                String color = rs.getString("color");
                double precio = rs.getDouble("precio");
                int stock = rs.getInt("stock");
                String categoria = rs.getString("categoria");

                Producto p = new Producto(id, nombre, marca, modelo, talla, color, precio, stock, categoria);
                lista.add(p);
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {
            System.out.println("Error al listar los productos: " + e.getMessage());
        }

        return lista;
    }

    @Override
    public Producto buscarPorId(int id) {

        Producto producto = null;
        String sql = "SELECT * FROM productos WHERE id_producto = ?";

        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String nombre = rs.getString("nombre");
                String marca = rs.getString("marca");
                String modelo = rs.getString("modelo");
                double talla = rs.getDouble("talla");
                String color = rs.getString("color");
                double precio = rs.getDouble("precio");
                int stock = rs.getInt("stock");
                String categoria = rs.getString("categoria");

                producto = new Producto(id, nombre, marca, modelo, talla, color, precio, stock, categoria);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            System.out.println("Error al buscar el producto: " + e.getMessage());
        }

        return producto;
    }

    @Override
    public void eliminar(int id) {

        String sql = "DELETE FROM productos WHERE id_producto = ?";

        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Producto eliminado correctamente");
            } else {
                System.out.println("No se encontro ningun producto con ese ID");
            }

            ps.close();
            con.close();

        } catch (Exception e) {
            System.out.println("Error al eliminar el producto: " + e.getMessage());
        }
    }
}
