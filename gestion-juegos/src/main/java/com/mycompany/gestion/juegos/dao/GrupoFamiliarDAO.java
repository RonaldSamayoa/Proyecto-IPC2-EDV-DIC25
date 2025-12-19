package com.mycompany.gestion.juegos.dao;
import com.mycompany.gestion.juegos.model.GrupoFamiliar;
import com.mycompany.gestion.juegos.resources.DBConnectionSingleton;

import java.sql.*;
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
}
