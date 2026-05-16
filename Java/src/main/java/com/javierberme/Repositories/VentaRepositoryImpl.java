package com.javierberme.Repositories;

import com.javierberme.Entities.Venta;
import com.javierberme.Utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class VentaRepositoryImpl implements VentaRepository {

    @Override
    public void guardar(Venta venta) {

        String sql = "INSERT INTO ventas (id_cliente, id_usuario, estado, total) VALUES (?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, venta.getIdCliente());
            ps.setInt(2, venta.getIdUsuario());
            ps.setString(3, venta.getEstado());
            ps.setDouble(4, venta.getTotal());

            ps.executeUpdate();
            System.out.println("Venta guardada correctamente");

        } catch (Exception e) {
            System.out.println("Error al guardar la venta: " + e.getMessage());
        }
    }

    @Override
    public List<Venta> listar() {

        List<Venta> lista = new ArrayList<>();
        String sql = "SELECT * FROM ventas";

        try (Connection con = DatabaseConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                int id       = rs.getInt("id_venta");
                int idCliente = rs.getInt("id_cliente");
                int idUsuario = rs.getInt("id_usuario");
                String fecha  = rs.getString("fecha");
                String estado = rs.getString("estado");
                double total  = rs.getDouble("total");

                lista.add(new Venta(id, idCliente, idUsuario, fecha, estado, total));
            }

        } catch (Exception e) {
            System.out.println("Error al listar las ventas: " + e.getMessage());
        }

        return lista;
    }

    @Override
    public Venta buscarPorId(int id) {

        Venta venta = null;
        String sql = "SELECT * FROM ventas WHERE id_venta = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int idCliente = rs.getInt("id_cliente");
                    int idUsuario = rs.getInt("id_usuario");
                    String fecha  = rs.getString("fecha");
                    String estado = rs.getString("estado");
                    double total  = rs.getDouble("total");

                    venta = new Venta(id, idCliente, idUsuario, fecha, estado, total);
                }
            }

        } catch (Exception e) {
            System.out.println("Error al buscar la venta: " + e.getMessage());
        }

        return venta;
    }

    @Override
    public void eliminar(int id) {

        String sql = "DELETE FROM ventas WHERE id_venta = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("Venta eliminada correctamente");
            } else {
                System.out.println("No se encontro ninguna venta con ese ID");
            }

        } catch (Exception e) {
            System.out.println("Error al eliminar la venta: " + e.getMessage());
        }
    }
}
