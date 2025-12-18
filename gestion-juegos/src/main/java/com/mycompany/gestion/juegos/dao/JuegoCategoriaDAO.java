package com.mycompany.gestion.juegos.dao;
import com.mycompany.gestion.juegos.model.JuegoCategoria;
import com.mycompany.gestion.juegos.resources.DBConnectionSingleton;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author ronald
 */
public class JuegoCategoriaDAO {
    public boolean insertar(JuegoCategoria jc) {

        String sql = """
            INSERT INTO juego_categoria (id_juego, id_categoria)
            VALUES (?, ?)
        """;

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, jc.getIdJuego());
            ps.setInt(2, jc.getIdCategoria());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar juego_categoria: " + e.getMessage());
            return false;
        }
    }

    // Eliminar relación específica
    public boolean eliminar(int idJuego, int idCategoria) {

        String sql = """
            DELETE FROM juego_categoria
            WHERE id_juego = ? AND id_categoria = ?
        """;

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idJuego);
            ps.setInt(2, idCategoria);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar juego_categoria: " + e.getMessage());
            return false;
        }
    }

    // Eliminar TODAS las categorías de un juego
    public boolean eliminarPorJuego(int idJuego) {

        String sql = "DELETE FROM juego_categoria WHERE id_juego = ?";

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idJuego);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar categorías del juego");
            return false;
        }
    }

    // Listar categorías de un juego
    public List<JuegoCategoria> listarPorJuego(int idJuego) {

        String sql = "SELECT * FROM juego_categoria WHERE id_juego = ?";
        List<JuegoCategoria> lista = new ArrayList<>();

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idJuego);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(mapear(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error al listar categorías por juego");
        }

        return lista;
    }

    // Listar juegos por categoría
    public List<JuegoCategoria> listarPorCategoria(int idCategoria) {

        String sql = "SELECT * FROM juego_categoria WHERE id_categoria = ?";
        List<JuegoCategoria> lista = new ArrayList<>();

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCategoria);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(mapear(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error al listar juegos por categoría");
        }

        return lista;
    }

    private JuegoCategoria mapear(ResultSet rs) throws SQLException {

        JuegoCategoria jc = new JuegoCategoria();
        jc.setIdJuegoCategoria(rs.getInt("id_juego_categoria"));
        jc.setIdJuego(rs.getInt("id_juego"));
        jc.setIdCategoria(rs.getInt("id_categoria"));

        return jc;
    }
}
