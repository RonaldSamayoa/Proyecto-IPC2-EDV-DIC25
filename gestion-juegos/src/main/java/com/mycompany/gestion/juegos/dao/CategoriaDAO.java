package com.mycompany.gestion.juegos.dao;
import com.mycompany.gestion.juegos.model.Categoria;
import com.mycompany.gestion.juegos.resources.DBConnectionSingleton;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author ronald
 */
public class CategoriaDAO {
    public boolean insertar(Categoria categoria) {

        String sql = """
            INSERT INTO categoria (nombre, estado)
            VALUES (?, 1)
        """;

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, categoria.getNombre());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar categoría: " + e.getMessage());
            return false;
        }
    }

    //Busca una categoría activa por ID
    public Categoria buscarPorId(int idCategoria) {

        String sql = """
            SELECT * FROM categoria
            WHERE id_categoria = ? AND estado = 1
        """;

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCategoria);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapear(rs);
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar categoría por ID: " + e.getMessage());
        }

        return null;
    }

    //Lista todas las categorías activas
    public List<Categoria> listarActivas() {

        String sql = "SELECT * FROM categoria WHERE estado = 1";
        List<Categoria> lista = new ArrayList<>();

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error al listar categorías: " + e.getMessage());
        }

        return lista;
    }

    // Actualiza el nombre de una categoría activa
    public boolean actualizarNombre(int idCategoria, String nuevoNombre) {

        String sql = """
            UPDATE categoria
            SET nombre = ?
            WHERE id_categoria = ? AND estado = 1
        """;

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nuevoNombre);
            ps.setInt(2, idCategoria);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar categoría: " + e.getMessage());
            return false;
        }
    }

    //Inactiva lógicamente una categoría
    public boolean inactivar(int idCategoria) {

        String sql = "UPDATE categoria SET estado = 0 WHERE id_categoria = ?";

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCategoria);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al inactivar categoría: " + e.getMessage());
            return false;
        }
    }

    //Activa o reactiva una categoría
    public boolean activar(int idCategoria) {

        String sql = "UPDATE categoria SET estado = 1 WHERE id_categoria = ?";

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCategoria);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al activar categoría: " + e.getMessage());
            return false;
        }
    }

    //Mapea a Categoria
    private Categoria mapear(ResultSet rs) throws SQLException {

        Categoria c = new Categoria();
        c.setIdCategoria(rs.getInt("id_categoria"));
        c.setNombre(rs.getString("nombre"));
        c.setEstado(rs.getBoolean("estado"));
        return c;
    }
}
