package com.mycompany.gestion.juegos.dao;

import com.mycompany.gestion.juegos.model.Comentario;
import com.mycompany.gestion.juegos.resources.DBConnectionSingleton;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComentarioDAO {

    public boolean insertar(Comentario c) {

        String sql = """
            INSERT INTO comentario
            (contenido, fecha, visible, id_usuario, id_juego, id_comentario_padre)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getContenido());
            ps.setTimestamp(2, new Timestamp(c.getFecha().getTime()));
            ps.setBoolean(3, c.isVisible());
            ps.setInt(4, c.getIdUsuario());
            ps.setInt(5, c.getIdJuego());

            if (c.getIdComentarioPadre() != null) {
                ps.setInt(6, c.getIdComentarioPadre());
            } else {
                ps.setNull(6, Types.INTEGER);
            }

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar comentario: " + e.getMessage());
            return false;
        }
    }

    public List<Comentario> listarPorJuego(int idJuego) {

        String sql = """
            SELECT * FROM comentario
            WHERE id_juego = ?
              AND visible = 1
            ORDER BY fecha ASC
        """;

        List<Comentario> lista = new ArrayList<>();

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idJuego);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(mapear(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error al listar comentarios: " + e.getMessage());
        }

        return lista;
    }

    public boolean ocultar(int idComentario) {

        String sql = "UPDATE comentario SET visible = 0 WHERE id_comentario = ?";

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idComentario);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            return false;
        }
    }

    public Integer obtenerEmpresaDelComentario(int idComentario) {
        String sql = """
            SELECT v.id_empresa
            FROM comentario c
            JOIN videojuego v ON c.id_juego = v.id_juego
            WHERE c.id_comentario = ?
        """;

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idComentario);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("id_empresa");
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener empresa del comentario");
        }

        return null;
    }
    
    public boolean puedeOcultar(int idComentario, int idUsuario) {
        String sql = """
            SELECT 1
            FROM comentario c
            JOIN videojuego v ON c.id_juego = v.id_juego
            JOIN usuario_empresa ue ON ue.id_empresa = v.id_empresa
            WHERE c.id_comentario = ?
              AND ue.id_usuario = ?
        """;

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idComentario);
            ps.setInt(2, idUsuario);

            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            return false;
        }
    }
    
    public void ocultarConRespuestas(int idComentario) {
        //Ocultar el comentario actual
        ocultar(idComentario);

        //Buscar respuestas directas
        String sql = """
            SELECT id_comentario
            FROM comentario
            WHERE id_comentario_padre = ?
        """;

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idComentario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int idHijo = rs.getInt("id_comentario");
                //Llamada recursiva
                ocultarConRespuestas(idHijo);
            }

        } catch (SQLException e) {
            System.err.println("Error al ocultar comentarios en cascada");
        }
    }

    private Comentario mapear(ResultSet rs) throws SQLException {

        Comentario c = new Comentario();
        c.setIdComentario(rs.getInt("id_comentario"));
        c.setContenido(rs.getString("contenido"));
        c.setFecha(rs.getTimestamp("fecha"));
        c.setVisible(rs.getBoolean("visible"));
        c.setIdUsuario(rs.getInt("id_usuario"));
        c.setIdJuego(rs.getInt("id_juego"));

        int padre = rs.getInt("id_comentario_padre");
        c.setIdComentarioPadre(rs.wasNull() ? null : padre);

        return c;
    }
}

