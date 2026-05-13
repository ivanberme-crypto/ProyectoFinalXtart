package com.javierberme.Repositories;

import com.javierberme.Entities.Cliente;
import com.javierberme.Utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClienteRepositoryImpl implements ClienteRepository {

    @Override
    public void guardar(Cliente cliente) {

        String sql = "INSERT INTO clientes (nombre, email, telefono, direccion) VALUES (?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getEmail());
            ps.setString(3, cliente.getTelefono());
            ps.setString(4, cliente.getDireccion());

            ps.executeUpdate();
            System.out.println("Cliente guardado correctamente");

        } catch (Exception e) {
            System.out.println("Error al guardar el cliente: " + e.getMessage());
        }
    }

    @Override
    public List<Cliente> listar() {

        ArrayList<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes";

        try (Connection con = DatabaseConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id_cliente");
                String nombre = rs.getString("nombre");
                String email = rs.getString("email");
                String telefono = rs.getString("telefono");
                String direccion = rs.getString("direccion");

                lista.add(new Cliente(id, nombre, email, telefono, direccion));
            }

        } catch (Exception e) {
            System.out.println("Error al listar clientes: " + e.getMessage());
        }

        return lista;
    }

    @Override
    public Cliente buscarPorId(int id) {

        Cliente cliente = null;
        String sql = "SELECT * FROM clientes WHERE id_cliente = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String email = rs.getString("email");
                    String telefono = rs.getString("telefono");
                    String direccion = rs.getString("direccion");

                    cliente = new Cliente(id, nombre, email, telefono, direccion);
                }
            }

        } catch (Exception e) {
            System.out.println("Error al buscar el cliente: " + e.getMessage());
        }

        return cliente;
    }

    @Override
    public void actualizar(Cliente cliente) {

        String sql = "UPDATE clientes SET nombre = ?, email = ?, telefono = ?, direccion = ? WHERE id_cliente = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getEmail());
            ps.setString(3, cliente.getTelefono());
            ps.setString(4, cliente.getDireccion());
            ps.setInt(5, cliente.getIdCliente());

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Cliente actualizado correctamente");
            } else {
                System.out.println("No se encontro ningun cliente con ese ID");
            }

        } catch (Exception e) {
            System.out.println("Error al actualizar el cliente: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {

        String sql = "DELETE FROM clientes WHERE id_cliente = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Cliente eliminado correctamente");
            } else {
                System.out.println("No se encontro ningun cliente con ese ID");
            }

        } catch (Exception e) {
            System.out.println("Error al eliminar el cliente: " + e.getMessage());
        }
    }
}
