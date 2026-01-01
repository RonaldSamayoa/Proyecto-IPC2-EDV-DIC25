package com.mycompany.gestion.juegos.dao;
import com.mycompany.gestion.juegos.model.InstalacionJuego;
import com.mycompany.gestion.juegos.resources.DBConnectionSingleton;

import java.sql.*;
import java.util.Date;
/**
 *
 * @author ronald
 */
public class InstalacionJuegoDAO {
    public boolean insertar(InstalacionJuego i) {

        String sql = """
            INSERT INTO instalacion_juego
            (id_usuario, id_juego, fecha_instalacion, es_prestado)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, i.getIdUsuario());
            ps.setInt(2, i.getIdJuego());
            ps.setTimestamp(3, new Timestamp(i.getFechaInstalacion().getTime()));
            ps.setBoolean(4, i.isEsPrestado());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al instalar juego");
            return false;
        }
    }

    // Verificar si el usuario ya tiene el juego instalado
    public boolean existeInstalacionActiva(int idUsuario, int idJuego) {

        String sql = """
            SELECT 1
            FROM instalacion_juego
            WHERE id_usuario = ?
              AND id_juego = ?
              AND fecha_desinstalacion IS NULL
        """;

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.setInt(2, idJuego);

            return ps.executeQuery().next();

        } catch (SQLException e) {
            return false;
        }
    }

    // Verificar si el juego está instalado por alguien (regla de préstamo)
    public boolean juegoInstaladoGlobal(int idJuego) {

        String sql = """
            SELECT 1
            FROM instalacion_juego
            WHERE id_juego = ?
              AND fecha_desinstalacion IS NULL
        """;

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idJuego);
            return ps.executeQuery().next();

        } catch (SQLException e) {
            return false;
        }
    }
    
    public boolean usuarioTienePrestadoActivo(int idUsuario) {
        String sql = """
            SELECT 1
            FROM instalacion_juego
            WHERE id_usuario = ?
              AND es_prestado = 1
              AND fecha_desinstalacion IS NULL
        """;

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            return ps.executeQuery().next();

        } catch (SQLException e) {
            return false;
        }
    }
    
    public int obtenerJuegoPrestadoActivo(int idUsuario) {
        String sql = """
            SELECT id_juego
            FROM instalacion_juego
            WHERE id_usuario = ?
              AND es_prestado = 1
              AND fecha_desinstalacion IS NULL
            LIMIT 1
        """;

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("id_juego");
            }

        } catch (SQLException e) {
            return -1;
        }

        return -1;
    }

    //
    public boolean prestamoExpirado(int idUsuario, int idJuego, int dias) {
        String sql = """
            SELECT fecha_instalacion
            FROM instalacion_juego
            WHERE id_usuario = ?
              AND id_juego = ?
              AND es_prestado = 1
              AND fecha_desinstalacion IS NULL
        """;

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.setInt(2, idJuego);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Date fecha = rs.getTimestamp("fecha_instalacion");
                long diff = new Date().getTime() - fecha.getTime();
                long diasTranscurridos = diff / (1000 * 60 * 60 * 24);
                return diasTranscurridos >= dias;
            }

        } catch (SQLException e) {
            return false;
        }

        return false;
    }

    // Desinstalar
    public boolean desinstalar(int idUsuario, int idJuego) {

        String sql = """
            UPDATE instalacion_juego
            SET fecha_desinstalacion = ?
            WHERE id_usuario = ?
              AND id_juego = ?
              AND fecha_desinstalacion IS NULL
        """;

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, new Timestamp(new Date().getTime()));
            ps.setInt(2, idUsuario);
            ps.setInt(3, idJuego);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            return false;
        }
    }
    
    public boolean desinstalarPrestadoActivo(int idUsuario) {
        String sql = """
            UPDATE instalacion_juego
            SET fecha_desinstalacion = ?
            WHERE id_usuario = ?
              AND es_prestado = 1
              AND fecha_desinstalacion IS NULL
        """;

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, new Timestamp(new Date().getTime()));
            ps.setInt(2, idUsuario);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            return false;
        }
    }
    
    public boolean usuarioTieneInstalado(int idUsuario, int idJuego) {
        String sql = """
            SELECT 1
            FROM instalacion_juego
            WHERE id_usuario = ?
              AND id_juego = ?
              AND fecha_desinstalacion IS NULL
        """;

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.setInt(2, idJuego);
            return ps.executeQuery().next();

        } catch (SQLException e) {
            return false;
        }
    }

}
