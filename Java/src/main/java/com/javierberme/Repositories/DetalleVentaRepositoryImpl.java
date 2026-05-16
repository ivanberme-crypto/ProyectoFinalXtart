package com.javierberme.Repositories;

import com.javierberme.Entities.DetalleVenta;
import com.javierberme.Utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DetalleVentaRepositoryImpl implements DetalleVentaRepository {

    @Override
    public void guardar(DetalleVenta detalle) {

        String sql = "INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";

        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, detalle.getIdVenta());
            ps.setInt(2, detalle.getIdProducto());
            ps.setInt(3, detalle.getCantidad());
            ps.setDouble(4, detalle.getPrecioUnitario());

            ps.executeUpdate();
            System.out.println("Detalle de venta guardado correctamente");

            ps.close();
            con.close();

        } catch (Exception e) {
            System.out.println("Error al guardar el detalle: " + e.getMessage());
        }
    }

    @Override
    public List<DetalleVenta> listar() {

        ArrayList<DetalleVenta> lista = new ArrayList<DetalleVenta>();
        String sql = "SELECT * FROM detalle_venta";

        try {
            Connection con = DatabaseConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id_detalle");
                int idVenta = rs.getInt("id_venta");
                int idProducto = rs.getInt("id_producto");
                int cantidad = rs.getInt("cantidad");
                double precioUnitario = rs.getDouble("precio_unitario");

                DetalleVenta d = new DetalleVenta(id, idVenta, idProducto, cantidad, precioUnitario);
                lista.add(d);
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {
            System.out.println("Error al listar los detalles: " + e.getMessage());
        }

        return lista;
    }

    @Override
    public DetalleVenta buscarPorId(int id) {

        DetalleVenta detalle = null;
        String sql = "SELECT * FROM detalle_venta WHERE id_detalle = ?";

        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int idVenta = rs.getInt("id_venta");
                int idProducto = rs.getInt("id_producto");
                int cantidad = rs.getInt("cantidad");
                double precioUnitario = rs.getDouble("precio_unitario");

                detalle = new DetalleVenta(id, idVenta, idProducto, cantidad, precioUnitario);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            System.out.println("Error al buscar el detalle: " + e.getMessage());
        }

        return detalle;
    }

    @Override
    public void eliminar(int id) {

        String sql = "DELETE FROM detalle_venta WHERE id_detalle = ?";

        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Detalle eliminado correctamente");
            } else {
                System.out.println("No se encontro ningun detalle con ese ID");
            }

            ps.close();
            con.close();

        } catch (Exception e) {
            System.out.println("Error al eliminar el detalle: " + e.getMessage());
        }
    }
}
