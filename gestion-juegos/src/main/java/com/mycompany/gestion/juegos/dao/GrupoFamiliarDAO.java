package com.mycompany.gestion.juegos.dao;
import com.mycompany.gestion.juegos.model.GrupoFamiliar;
import com.mycompany.gestion.juegos.resources.DBConnectionSingleton;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author ronald
 */
public class GrupoFamiliarDAO {
     public int insertar(GrupoFamiliar g) {

        String sql = """
            INSERT INTO grupo_familiar (nombre, id_creador)
            VALUES (?, ?)
        """;

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, g.getNombre());
            ps.setInt(2, g.getIdCreador());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            System.err.println("Error al insertar grupo familiar");
        }

        return -1;
    }
     
     public GrupoFamiliar buscarPorId(int idGrupo) {
        String sql = "SELECT * FROM grupo_familiar WHERE id_grupo = ?";

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idGrupo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                GrupoFamiliar g = new GrupoFamiliar();
                g.setIdGrupo(rs.getInt("id_grupo"));
                g.setNombre(rs.getString("nombre"));
                g.setIdCreador(rs.getInt("id_creador"));
                return g;
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar grupo familiar");
        }
        return null;
    }

    public boolean eliminar(int idGrupo) {
        String sql = "DELETE FROM grupo_familiar WHERE id_grupo = ?";

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idGrupo);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar grupo familiar");
            return false;
        }
    }

    public List<GrupoFamiliar> listarPorUsuario(int idUsuario) {
        String sql = """
            SELECT gf.*
            FROM grupo_familiar gf
            JOIN grupo_usuario gu ON gf.id_grupo = gu.id_grupo
            WHERE gu.id_usuario = ?
        """;

        List<GrupoFamiliar> lista = new ArrayList<>();

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                GrupoFamiliar g = new GrupoFamiliar();
                g.setIdGrupo(rs.getInt("id_grupo"));
                g.setNombre(rs.getString("nombre"));
                g.setIdCreador(rs.getInt("id_creador"));
                lista.add(g);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar grupos por usuario");
        }

        return lista;
    }
}
