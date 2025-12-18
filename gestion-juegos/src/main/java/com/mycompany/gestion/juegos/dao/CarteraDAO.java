package com.mycompany.gestion.juegos.dao;
import com.mycompany.gestion.juegos.model.Cartera;
import com.mycompany.gestion.juegos.resources.DBConnectionSingleton;

import java.math.BigDecimal;
import java.sql.*;
/**
 *
 * @author ronald
 */
public class CarteraDAO {
    public boolean crearParaUsuario(int idUsuario) {
        String sql = "INSERT INTO cartera (saldo, id_usuario) VALUES (0.00, ?)";

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al crear cartera: " + e.getMessage());
            return false;
        }
    }

    public Cartera obtenerPorUsuario(int idUsuario) {
        String sql = "SELECT * FROM cartera WHERE id_usuario = ?";

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Cartera c = new Cartera();
                c.setIdCartera(rs.getInt("id_cartera"));
                c.setSaldo(rs.getBigDecimal("saldo"));
                c.setIdUsuario(rs.getInt("id_usuario"));
                return c;
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener cartera: " + e.getMessage());
        }

        return null;
    }

    public boolean actualizarSaldo(int idUsuario, BigDecimal nuevoSaldo) {
        String sql = "UPDATE cartera SET saldo = ? WHERE id_usuario = ?";

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBigDecimal(1, nuevoSaldo);
            ps.setInt(2, idUsuario);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar saldo: " + e.getMessage());
            return false;
        }
    }
}
