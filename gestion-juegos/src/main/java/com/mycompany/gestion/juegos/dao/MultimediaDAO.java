package com.mycompany.gestion.juegos.dao;
import com.mycompany.gestion.juegos.model.Multimedia;
import com.mycompany.gestion.juegos.model.enums.TipoMultimedia;
import com.mycompany.gestion.juegos.resources.DBConnectionSingleton;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author ronald
 */
public class MultimediaDAO {
    public boolean insertar(Multimedia m) {

        String sql = """
            INSERT INTO multimedia (archivo, tipo, id_juego)
            VALUES (?, ?, ?)
        """;

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBytes(1, m.getArchivo());
            ps.setString(2, m.getTipo().name().toLowerCase()); // imagen | video
            ps.setInt(3, m.getIdJuego());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar multimedia");
            e.printStackTrace();
            return false;
        }
    }

    public List<Multimedia> listarPorJuego(int idJuego) {

        String sql = """
            SELECT *
            FROM multimedia
            WHERE id_juego = ?
        """;

        List<Multimedia> lista = new ArrayList<>();

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idJuego);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Multimedia m = new Multimedia();
                m.setIdMultimedia(rs.getInt("id_multimedia"));
                m.setArchivo(rs.getBytes("archivo"));
                m.setTipo(TipoMultimedia.from(rs.getString("tipo")));
                m.setIdJuego(rs.getInt("id_juego"));
                lista.add(m);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar multimedia");
        }

        return lista;
    }

    public boolean eliminar(int idMultimedia) {

        String sql = "DELETE FROM multimedia WHERE id_multimedia = ?";

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idMultimedia);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            return false;
        }
    }
}
