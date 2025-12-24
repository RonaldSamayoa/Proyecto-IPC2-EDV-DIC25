package com.mycompany.gestion.juegos.dao;
import com.mycompany.gestion.juegos.resources.DBConnectionSingleton;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author ronald
 */
public class GrupoUsuarioDAO {
    public boolean insertar(int idGrupo, int idUsuario) {

        String sql = """
            INSERT INTO grupo_usuario (id_grupo, id_usuario)
            VALUES (?, ?)
        """;

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idGrupo);
            ps.setInt(2, idUsuario);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            return false;
        }
    }

    public boolean existeUsuarioEnGrupo(int idGrupo, int idUsuario) {

        String sql = """
            SELECT 1
            FROM grupo_usuario
            WHERE id_grupo = ? AND id_usuario = ?
        """;

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idGrupo);
            ps.setInt(2, idUsuario);
            return ps.executeQuery().next();

        } catch (SQLException e) {
            return false;
        }
    }
    
    public int contarUsuariosPorGrupo(int idGrupo) {
        String sql = """
            SELECT COUNT(*)
            FROM grupo_usuario
            WHERE id_grupo = ?
        """;

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idGrupo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            System.err.println("Error al contar usuarios del grupo");
        }

        return 0;
    }

    public boolean eliminar(int idGrupo, int idUsuario) {
        String sql = """
            DELETE FROM grupo_usuario
            WHERE id_grupo = ? AND id_usuario = ?
        """;

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idGrupo);
            ps.setInt(2, idUsuario);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario del grupo");
            return false;
        }
    }
    
    public List<Integer> listarUsuariosPorGrupo(int idGrupo) {
        String sql = """
            SELECT id_usuario
            FROM grupo_usuario
            WHERE id_grupo = ?
        """;

        List<Integer> usuarios = new ArrayList<>();

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idGrupo);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                usuarios.add(rs.getInt("id_usuario"));
            }

        } catch (SQLException e) {
            System.err.println("Error al listar usuarios del grupo");
        }

        return usuarios;
    }

    public List<Integer> listarGruposPorUsuario(int idUsuario) {
        String sql = """
            SELECT id_grupo
            FROM grupo_usuario
            WHERE id_usuario = ?
        """;

        List<Integer> grupos = new ArrayList<>();

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                grupos.add(rs.getInt("id_grupo"));
            }

        } catch (SQLException e) {
            System.err.println("Error al listar grupos del usuario");
        }

        return grupos;
    }
    
    public boolean compartenGrupo(int idUsuarioA, int idUsuarioB) {
        String sql = """
            SELECT 1
            FROM grupo_usuario gu1
            JOIN grupo_usuario gu2
              ON gu1.id_grupo = gu2.id_grupo
            WHERE gu1.id_usuario = ?
              AND gu2.id_usuario = ?
        """;

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuarioA);
            ps.setInt(2, idUsuarioB);

            return ps.executeQuery().next();

        } catch (SQLException e) {
            return false;
        }
    }
}
