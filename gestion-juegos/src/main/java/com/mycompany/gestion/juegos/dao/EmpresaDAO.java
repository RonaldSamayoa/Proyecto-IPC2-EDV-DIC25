package com.mycompany.gestion.juegos.dao;
import com.mycompany.gestion.juegos.model.Empresa;
import com.mycompany.gestion.juegos.resources.DBConnectionSingleton;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author ronald
 */
public class EmpresaDAO {
    /**
     * Inserta una nueva empresa.
     */
    public boolean insertar(Empresa empresa) {

        String sql = """
            INSERT INTO empresa
            (nombre, descripcion, logo, porcentaje_comision_especifico, estado)
            VALUES (?, ?, ?, ?, 1)
        """;

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, empresa.getNombre());
            ps.setString(2, empresa.getDescripcion());
            ps.setBytes(3, empresa.getLogo());
            ps.setBigDecimal(4, empresa.getPorcentajeComisionEspecifico());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar empresa: " + e.getMessage());
            return false;
        }
    }

    /**
     * Busca una empresa por ID.
     */
    public Empresa buscarPorId(int idEmpresa) {

        String sql = "SELECT * FROM empresa WHERE id_empresa = ? AND estado = 1";

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idEmpresa);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapear(rs);
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar empresa por ID: " + e.getMessage());
        }
        return null;
    }

    /**
     * Busca empresas por nombre (búsqueda parcial).
     */
    public List<Empresa> buscarPorNombre(String nombre) {

        String sql = """
            SELECT * FROM empresa
            WHERE nombre LIKE ? AND estado = 1
        """;

        List<Empresa> empresas = new ArrayList<>();

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + nombre + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                empresas.add(mapear(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar empresas por nombre: " + e.getMessage());
        }
        return empresas;
    }

    /**
     * Actualiza los datos generales de la empresa.
     */
    public boolean actualizar(Empresa empresa) {

        String sql = """
            UPDATE empresa
            SET descripcion = ?, logo = ?
            WHERE id_empresa = ? AND estado = 1
        """;

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, empresa.getDescripcion());
            ps.setBytes(2, empresa.getLogo());
            ps.setInt(3, empresa.getIdEmpresa());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar empresa: " + e.getMessage());
            return false;
        }
    }

    /**
     * Actualiza el porcentaje de comisión específico de la empresa.
     */
    public boolean actualizarComision(int idEmpresa, java.math.BigDecimal porcentaje) {

        String sql = """
            UPDATE empresa
            SET porcentaje_comision_especifico = ?
            WHERE id_empresa = ? AND estado = 1
        """;

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBigDecimal(1, porcentaje);
            ps.setInt(2, idEmpresa);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar comisión: " + e.getMessage());
            return false;
        }
    }

    /**
     * Inactiva una empresa.
     */
    public boolean inactivar(int idEmpresa) {

        String sql = """
            UPDATE empresa
            SET estado = 0
            WHERE id_empresa = ?
        """;

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idEmpresa);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al inactivar empresa: " + e.getMessage());
            return false;
        }
    }

    /**
     * Mapea ResultSet a Empresa.
     */
    private Empresa mapear(ResultSet rs) throws SQLException {

        Empresa e = new Empresa();
        e.setIdEmpresa(rs.getInt("id_empresa"));
        e.setNombre(rs.getString("nombre"));
        e.setDescripcion(rs.getString("descripcion"));
        e.setLogo(rs.getBytes("logo"));
        e.setPorcentajeComisionEspecifico(
                rs.getBigDecimal("porcentaje_comision_especifico")
        );
        e.setEstado(rs.getBoolean("estado"));

        return e;
    }
}
