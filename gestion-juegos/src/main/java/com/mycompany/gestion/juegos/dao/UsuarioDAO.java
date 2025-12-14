package com.mycompany.gestion.juegos.dao;
import com.mycompany.gestion.juegos.model.Usuario;
import com.mycompany.gestion.juegos.resources.DBConnectionSingleton;
import com.mycompany.gestion.juegos.model.enums.RolUsuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author ronald
 */
public class UsuarioDAO {
    // Inserta un nuevo usuario en el sistema (el usuario se registra siempre con estado activo)
    public boolean insertarUsuario(Usuario usuario) {

        String sql = "INSERT INTO usuario "
                   + "(nickname, password, correo, fecha_nacimiento, pais, imagen_perfil, rol, biblioteca_publica, estado) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, true)";

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario.getNickname());
            ps.setString(2, usuario.getPassword());
            ps.setString(3, usuario.getCorreo());
            ps.setDate(4, new java.sql.Date(usuario.getFechaNacimiento().getTime()));
            ps.setString(5, usuario.getPais());
            ps.setBytes(6, usuario.getImagenPerfil());
            ps.setString(7, usuario.getRol().name());
            ps.setBoolean(8, usuario.isBibliotecaPublica());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar usuario: " + e.getMessage());
            return false;
        }
    }

    //Busca un usuario activo a partir de su correo electrónico
    public Usuario buscarPorCorreo(String correo) {

        String sql = "SELECT * FROM usuario WHERE correo = ? AND estado = true";

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, correo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar usuario por correo: " + e.getMessage());
        }

        return null;
    }

    //Busca un usuario activo por su identificador único
    public Usuario buscarPorId(int idUsuario) {

        String sql = "SELECT * FROM usuario WHERE id_usuario = ? AND estado = true";

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar usuario por id: " + e.getMessage());
        }

        return null;
    }

    //Busca un usuario activo por su nickname
    public Usuario buscarPorNickname(String nickname) {

        String sql = "SELECT * FROM usuario WHERE nickname = ? AND estado = true";

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nickname);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar usuario por nickname: " + e.getMessage());
        }

        return null;
    }

    //Inactiva lógicamente un usuario del sistema
    public boolean desactivarUsuario(int idUsuario) {

        String sql = "UPDATE usuario SET estado = false WHERE id_usuario = ?";

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al desactivar usuario: " + e.getMessage());
            return false;
        }
    }

    //Mapea un ResultSet a un objeto Usuario
    private Usuario mapearUsuario(ResultSet rs) throws SQLException {

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(rs.getInt("id_usuario"));
        usuario.setNickname(rs.getString("nickname"));
        usuario.setPassword(rs.getString("password"));
        usuario.setCorreo(rs.getString("correo"));
        usuario.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
        usuario.setPais(rs.getString("pais"));
        usuario.setImagenPerfil(rs.getBytes("imagen_perfil"));
        usuario.setRol(RolUsuario.valueOf(rs.getString("rol")));
        usuario.setBibliotecaPublica(rs.getBoolean("biblioteca_publica"));
        usuario.setEstado(rs.getBoolean("estado"));

        return usuario;
    }
}
