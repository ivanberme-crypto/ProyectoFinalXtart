package com.javierberme.Repositories;

import com.javierberme.Entities.Usuario;
import com.javierberme.Utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepositoryImpl implements UsuarioRepository {

    @Override
    public void guardar(Usuario usuario) {

        String sql = "INSERT INTO usuarios (nombre, email, rol, password_hash) VALUES (?, ?, ?, ?)";

        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getRol());
            ps.setString(4, usuario.getPasswordHash());

            ps.executeUpdate();
            System.out.println("Usuario guardado correctamente");

            ps.close();
            con.close();

        } catch (Exception e) {
            System.out.println("Error al guardar el usuario: " + e.getMessage());
        }
    }

    @Override
    public List<Usuario> listar() {

        ArrayList<Usuario> lista = new ArrayList<Usuario>();
        String sql = "SELECT * FROM usuarios";

        try {
            Connection con = DatabaseConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id_usuario");
                String nombre = rs.getString("nombre");
                String email = rs.getString("email");
                String rol = rs.getString("rol");
                String passwordHash = rs.getString("password_hash");

                Usuario u = new Usuario(id, nombre, email, rol, passwordHash);
                lista.add(u);
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {
            System.out.println("Error al listar usuarios: " + e.getMessage());
        }

        return lista;
    }

    @Override
    public Usuario buscarPorId(int id) {

        Usuario usuario = null;
        String sql = "SELECT * FROM usuarios WHERE id_usuario = ?";

        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String nombre = rs.getString("nombre");
                String email = rs.getString("email");
                String rol = rs.getString("rol");
                String passwordHash = rs.getString("password_hash");

                usuario = new Usuario(id, nombre, email, rol, passwordHash);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            System.out.println("Error al buscar el usuario: " + e.getMessage());
        }

        return usuario;
    }

    @Override
    public void actualizar(Usuario usuario) {

        String sql = "UPDATE usuarios SET nombre = ?, email = ?, rol = ?, password_hash = ? WHERE id_usuario = ?";

        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getRol());
            ps.setString(4, usuario.getPasswordHash());
            ps.setInt(5, usuario.getIdUsuario());

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Usuario actualizado correctamente");
            } else {
                System.out.println("No se encontro ningun usuario con ese ID");
            }

            ps.close();
            con.close();

        } catch (Exception e) {
            System.out.println("Error al actualizar el usuario: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {

        String sql = "DELETE FROM usuarios WHERE id_usuario = ?";

        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Usuario eliminado correctamente");
            } else {
                System.out.println("No se encontro ningun usuario con ese ID");
            }

            ps.close();
            con.close();

        } catch (Exception e) {
            System.out.println("Error al eliminar el usuario: " + e.getMessage());
        }
    }
}
