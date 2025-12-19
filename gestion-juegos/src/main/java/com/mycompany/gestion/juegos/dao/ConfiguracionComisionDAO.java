package com.mycompany.gestion.juegos.dao;
import com.mycompany.gestion.juegos.model.ConfiguracionComision;
import com.mycompany.gestion.juegos.resources.DBConnectionSingleton;

import java.sql.*;
/**
 *
 * @author ronald
 */
public class ConfiguracionComisionDAO {
    // Inserta una nueva configuración (histórico)
    public boolean insertar(ConfiguracionComision config) {

        String sql = """
            INSERT INTO configuracion_comision (porcentaje_global, fecha_cambio)
            VALUES (?, NOW())
        """;

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBigDecimal(1, config.getPorcentajeGlobal());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar configuración de comisión: " + e.getMessage());
            return false;
        }
    }

    // Obtiene la configuración vigente (la última)
    public ConfiguracionComision obtenerActual() {

        String sql = """
            SELECT * FROM configuracion_comision
            ORDER BY fecha_cambio DESC
            LIMIT 1
        """;

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                ConfiguracionComision c = new ConfiguracionComision();
                c.setIdConfig(rs.getInt("id_config"));
                c.setPorcentajeGlobal(rs.getBigDecimal("porcentaje_global"));
                c.setFechaCambio(rs.getTimestamp("fecha_cambio"));
                return c;
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener configuración actual: " + e.getMessage());
        }

        return null;
    }
}
