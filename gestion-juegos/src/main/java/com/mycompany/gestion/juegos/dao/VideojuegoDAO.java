package com.mycompany.gestion.juegos.dao;
import com.mycompany.gestion.juegos.dto.VideojuegoDetalleDTO;
import com.mycompany.gestion.juegos.model.Categoria;
import com.mycompany.gestion.juegos.model.Videojuego;
import com.mycompany.gestion.juegos.resources.DBConnectionSingleton;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author ronald
 */
public class VideojuegoDAO {
    // Inserta un nuevo videojuego
    public boolean insertar(Videojuego v) {

        String sql = """
            INSERT INTO videojuego
            (titulo, descripcion, precio, requisitos_minimos,
             clasificacion_edad, fecha_lanzamiento, estado_venta, id_empresa)
            VALUES (?, ?, ?, ?, ?, ?, 1, ?)
        """;

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, v.getTitulo());
            ps.setString(2, v.getDescripcion());
            ps.setBigDecimal(3, v.getPrecio());
            ps.setString(4, v.getRequisitosMinimos());
            ps.setString(5, v.getClasificacionEdad());

            if (v.getFechaLanzamiento() != null) {
                ps.setDate(6, new java.sql.Date(v.getFechaLanzamiento().getTime()));
            } else {
                ps.setNull(6, Types.DATE);
            }

            ps.setInt(7, v.getIdEmpresa());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar videojuego: " + e.getMessage());
            return false;
        }
    }

    // Busca videojuego por ID
    public Videojuego buscarPorId(int idJuego) {

        String sql = "SELECT * FROM videojuego WHERE id_juego = ?";

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idJuego);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapear(rs);
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar videojuego por id");
        }
        return null;
    }

    // Busca videojuegos por título (búsqueda parcial)
    public List<Videojuego> buscarPorTitulo(String titulo) {

        String sql = """
            SELECT * FROM videojuego
            WHERE titulo LIKE ? AND estado_venta = 1
        """;

        List<Videojuego> lista = new ArrayList<>();

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + titulo + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(mapear(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar videojuegos por título");
        }
        return lista;
    }

    // Lista todos los videojuegos activos
    public List<Videojuego> listarActivos() {

        String sql = "SELECT * FROM videojuego WHERE estado_venta = 1";
        List<Videojuego> lista = new ArrayList<>();

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error al listar videojuegos activos");
        }
        return lista;
    }

    // Lista videojuegos de una empresa (solo activos)
    public List<Videojuego> listarPorEmpresa(int idEmpresa) {

        String sql = """
            SELECT * FROM videojuego
            WHERE id_empresa = ? AND estado_venta = 1
        """;

        List<Videojuego> lista = new ArrayList<>();

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idEmpresa);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(mapear(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error al listar videojuegos por empresa");
        }
        return lista;
    }

    // Actualiza datos del videojuego (solo si está activo)
    public boolean actualizar(Videojuego v) {

        String sql = """
            UPDATE videojuego
            SET titulo = ?, descripcion = ?, precio = ?, requisitos_minimos = ?,
                clasificacion_edad = ?, fecha_lanzamiento = ?
            WHERE id_juego = ? AND estado_venta = 1
        """;

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, v.getTitulo());
            ps.setString(2, v.getDescripcion());
            ps.setBigDecimal(3, v.getPrecio());
            ps.setString(4, v.getRequisitosMinimos());
            ps.setString(5, v.getClasificacionEdad());

            if (v.getFechaLanzamiento() != null) {
                ps.setDate(6, new java.sql.Date(v.getFechaLanzamiento().getTime()));
            } else {
                ps.setNull(6, Types.DATE);
            }

            ps.setInt(7, v.getIdJuego());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar videojuego");
            return false;
        }
    }

    // Activa o desactiva videojuego
    public boolean cambiarEstado(int idJuego, boolean activo) {

        String sql = "UPDATE videojuego SET estado_venta = ? WHERE id_juego = ?";

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBoolean(1, activo);
            ps.setInt(2, idJuego);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al cambiar estado del videojuego");
            return false;
        }
    }

    // Mapea ResultSet a Videojuego
    private Videojuego mapear(ResultSet rs) throws SQLException {

        Videojuego v = new Videojuego();
        v.setIdJuego(rs.getInt("id_juego"));
        v.setTitulo(rs.getString("titulo"));
        v.setDescripcion(rs.getString("descripcion"));
        v.setPrecio(rs.getBigDecimal("precio"));
        v.setRequisitosMinimos(rs.getString("requisitos_minimos"));
        v.setClasificacionEdad(rs.getString("clasificacion_edad"));
        v.setFechaLanzamiento(rs.getDate("fecha_lanzamiento"));
        v.setEstadoVenta(rs.getBoolean("estado_venta"));
        v.setIdEmpresa(rs.getInt("id_empresa"));

        return v;
    }
    
    public VideojuegoDetalleDTO obtenerDetallePorId(int idJuego) {
        String sql = """
            SELECT 
                v.id_juego,
                v.titulo,
                v.descripcion,
                v.precio,
                v.requisitos_minimos,
                v.clasificacion_edad,
                v.fecha_lanzamiento,
                v.id_empresa,
                e.nombre AS nombre_empresa,
                c.id_categoria,
                c.nombre
            FROM videojuego v
            JOIN empresa e ON v.id_empresa = e.id_empresa
            LEFT JOIN juego_categoria jc ON v.id_juego = jc.id_juego
            LEFT JOIN categoria c ON jc.id_categoria = c.id_categoria AND c.estado = 1
            WHERE v.id_juego = ? AND v.estado_venta = 1
        """;

        VideojuegoDetalleDTO dto = null;
        List<Categoria> categorias = new ArrayList<>();

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idJuego);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                if (dto == null) {
                    dto = new VideojuegoDetalleDTO();
                    dto.setIdJuego(rs.getInt("id_juego"));
                    dto.setTitulo(rs.getString("titulo"));
                    dto.setDescripcion(rs.getString("descripcion"));
                    dto.setPrecio(rs.getBigDecimal("precio"));
                    dto.setRequisitosMinimos(rs.getString("requisitos_minimos"));
                    dto.setClasificacionEdad(rs.getString("clasificacion_edad"));
                    dto.setFechaLanzamiento(rs.getDate("fecha_lanzamiento"));
                    dto.setIdEmpresa(rs.getInt("id_empresa"));
                    dto.setNombreEmpresa(rs.getString("nombre_empresa"));
                }

                int idCategoria = rs.getInt("id_categoria");
                if (!rs.wasNull()) {
                    Categoria c = new Categoria();
                    c.setIdCategoria(idCategoria);
                    c.setNombre(rs.getString("nombre"));
                    categorias.add(c);
                }
            }

            if (dto != null) {
                dto.setCategorias(categorias);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener detalle del videojuego: " + e.getMessage());
        }

        return dto;
    }

}
