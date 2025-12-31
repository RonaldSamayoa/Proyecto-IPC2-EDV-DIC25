package com.mycompany.gestion.juegos.dao;
import com.mycompany.gestion.juegos.model.Biblioteca;
import com.mycompany.gestion.juegos.resources.DBConnectionSingleton;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ronald
 */
public class BibliotecaDAO {
    public boolean insertar(Biblioteca b) {
        String sql = """
            INSERT INTO biblioteca
            (id_usuario, id_juego, es_propio)
            VALUES (?, ?, ?)
        """;

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, b.getIdUsuario());
            ps.setInt(2, b.getIdJuego());
            ps.setBoolean(3, b.isEsPropio());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar biblioteca: " + e.getMessage());
            return false;
        }
    }
    
    //verificar que el usuario posee el juego
    public boolean existeEnBiblioteca(int idUsuario, int idJuego) {
        String sql = """
            SELECT 1
            FROM biblioteca
            WHERE id_usuario = ? AND id_juego = ?
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

    public List<Biblioteca> listarPorUsuario(int idUsuario) {
        String sql = "SELECT * FROM biblioteca WHERE id_usuario = ?";
        List<Biblioteca> lista = new ArrayList<>();

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Biblioteca b = new Biblioteca();
                b.setIdBiblioteca(rs.getInt("id_biblioteca"));
                b.setIdUsuario(rs.getInt("id_usuario"));
                b.setIdJuego(rs.getInt("id_juego"));
                b.setEsPropio(rs.getBoolean("es_propio"));
                lista.add(b);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar biblioteca: " + e.getMessage());
        }

        return lista;
    }
    
    // Juegos propios de un usuario
    public List<Biblioteca> listarPropiosPorUsuario(int idUsuario) {
        String sql = """
            SELECT *
            FROM biblioteca
            WHERE id_usuario = ? AND es_propio = 1
        """;

        List<Biblioteca> lista = new ArrayList<>();

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Biblioteca b = new Biblioteca();
                b.setIdBiblioteca(rs.getInt("id_biblioteca"));
                b.setIdUsuario(rs.getInt("id_usuario"));
                b.setIdJuego(rs.getInt("id_juego"));
                b.setEsPropio(rs.getBoolean("es_propio"));
                lista.add(b);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar juegos propios");
        }

        return lista;
    }
    
    public List<Integer> listarJuegosPropiosPorUsuario(int idUsuario) {
        String sql = """
            SELECT id_juego
            FROM biblioteca
            WHERE id_usuario = ? AND es_propio = 1
        """;

        List<Integer> juegos = new ArrayList<>();

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                juegos.add(rs.getInt("id_juego"));
            }

        } catch (SQLException e) {
            System.err.println("Error al listar juegos propios del usuario");
        }

        return juegos;
    }

}
