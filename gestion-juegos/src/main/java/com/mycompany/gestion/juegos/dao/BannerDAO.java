package com.mycompany.gestion.juegos.dao;
import com.mycompany.gestion.juegos.model.Banner;
import com.mycompany.gestion.juegos.resources.DBConnectionSingleton;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author ronald
 */
public class BannerDAO {
    public boolean insertar(Banner b) {

        String sql = """
            INSERT INTO banner (titulo, imagen, id_juego, activo)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, b.getTitulo());
            ps.setBytes(2, b.getImagen());

            if (b.getIdJuego() != null) {
                ps.setInt(3, b.getIdJuego());
            } else {
                ps.setNull(3, Types.INTEGER);
            }

            ps.setBoolean(4, b.isActivo());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar banner");
            return false;
        }
    }

    public List<Banner> listarActivos() {

        String sql = "SELECT * FROM banner WHERE activo = 1";
        List<Banner> lista = new ArrayList<>();

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Banner b = new Banner();
                b.setIdBanner(rs.getInt("id_banner"));
                b.setTitulo(rs.getString("titulo"));
                b.setImagen(rs.getBytes("imagen"));
                b.setIdJuego((Integer) rs.getObject("id_juego"));
                b.setActivo(rs.getBoolean("activo"));
                lista.add(b);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar banners");
        }

        return lista;
    }

    public boolean cambiarEstado(int idBanner, boolean activo) {

        String sql = "UPDATE banner SET activo = ? WHERE id_banner = ?";

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBoolean(1, activo);
            ps.setInt(2, idBanner);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            return false;
        }
    }

    public boolean eliminar(int idBanner) {

        String sql = "DELETE FROM banner WHERE id_banner = ?";

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idBanner);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            return false;
        }
    }
}
