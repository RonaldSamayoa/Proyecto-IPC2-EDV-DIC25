package com.mycompany.gestion.juegos.dao;
import com.mycompany.gestion.juegos.model.Calificacion;
import com.mycompany.gestion.juegos.resources.DBConnectionSingleton;
import java.math.BigDecimal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author ronald
 */
public class CalificacionDAO {
    public boolean insertar(Calificacion c) {

        String sql = """
            INSERT INTO calificacion
            (valor, fecha, id_usuario, id_juego)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, c.getValor());
            ps.setTimestamp(2, new Timestamp(c.getFecha().getTime()));
            ps.setInt(3, c.getIdUsuario());
            ps.setInt(4, c.getIdJuego());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            return false;
        }
    }

    public boolean actualizar(Calificacion c) {

        String sql = """
            UPDATE calificacion
            SET valor = ?, fecha = ?
            WHERE id_calificacion = ?
        """;

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, c.getValor());
            ps.setTimestamp(2, new Timestamp(c.getFecha().getTime()));
            ps.setInt(3, c.getIdCalificacion());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            return false;
        }
    }

    private Calificacion mapear(ResultSet rs) throws SQLException {

        Calificacion c = new Calificacion();
        c.setIdCalificacion(rs.getInt("id_calificacion"));
        c.setValor(rs.getInt("valor"));
        c.setFecha(rs.getTimestamp("fecha"));
        c.setIdUsuario(rs.getInt("id_usuario"));
        c.setIdJuego(rs.getInt("id_juego"));

        return c;
    }
    
    //calificaciones de un usuario
    public List<Calificacion> listarPorUsuario(int idUsuario) {
        String sql = """
            SELECT *
            FROM calificacion
            WHERE id_usuario = ?
            ORDER BY fecha DESC
        """;

        List<Calificacion> lista = new ArrayList<>();

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(mapear(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error al listar calificaciones por usuario");
        }

        return lista;
    }
    
    // calificaciones de un juego
    public List<Calificacion> listarPorJuego(int idJuego) {
        String sql = """
            SELECT *
            FROM calificacion
            WHERE id_juego = ?
            ORDER BY fecha DESC
        """;

        List<Calificacion> lista = new ArrayList<>();

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idJuego);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(mapear(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error al listar calificaciones por juego");
        }

        return lista;
    }
    
    //conteo de calificaciones de un juego (estadistica)
    public int contarPorJuego(int idJuego) {
        String sql = """
            SELECT COUNT(*) 
            FROM calificacion
            WHERE id_juego = ?
        """;

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idJuego);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            System.err.println("Error al contar calificaciones");
        }

        return 0;
    }
    
    //promedio de un juego
    public BigDecimal promedioPorJuego(int idJuego) {
        String sql = """
            SELECT AVG(valor)
            FROM calificacion
            WHERE id_juego = ?
        """;

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idJuego);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getBigDecimal(1);
            }

        } catch (SQLException e) {
            System.err.println("Error al calcular promedio");
        }

        return BigDecimal.ZERO;
    }
}
