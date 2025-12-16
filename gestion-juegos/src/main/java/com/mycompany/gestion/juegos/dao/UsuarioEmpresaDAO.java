package com.mycompany.gestion.juegos.dao;
import com.mycompany.gestion.juegos.model.UsuarioEmpresa;
import com.mycompany.gestion.juegos.model.enums.RolEmpresa;
import com.mycompany.gestion.juegos.resources.DBConnectionSingleton;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author ronald
 */
public class UsuarioEmpresaDAO {
    public boolean insertar(UsuarioEmpresa ue) {

        String sql = """
            INSERT INTO usuario_empresa
            (id_usuario, id_empresa, rol_empresa)
            VALUES (?, ?, ?)
        """;

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ue.getIdUsuario());
            ps.setInt(2, ue.getIdEmpresa());
            ps.setString(3, ue.getRolEmpresa().name());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar usuario_empresa: " + e.getMessage());
            return false;
        }
    }

    //Obtiene los usuarios de una empresa
    public List<UsuarioEmpresa> listarPorEmpresa(int idEmpresa) {

        String sql = "SELECT * FROM usuario_empresa WHERE id_empresa = ?";

        List<UsuarioEmpresa> lista = new ArrayList<>();

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idEmpresa);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                UsuarioEmpresa ue = new UsuarioEmpresa();
                ue.setIdUsuarioEmpresa(rs.getInt("id_usuario_empresa"));
                ue.setIdUsuario(rs.getInt("id_usuario"));
                ue.setIdEmpresa(rs.getInt("id_empresa"));
                ue.setRolEmpresa(
                        RolEmpresa.valueOf(rs.getString("rol_empresa"))
                );
                lista.add(ue);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar usuario_empresa: " + e.getMessage());
        }

        return lista;
    }

    // Verifica si un usuario es ADMIN de una empresa
    public boolean esAdminEmpresa(int idUsuario, int idEmpresa) {

        String sql = """
            SELECT 1
            FROM usuario_empresa
            WHERE id_usuario = ?
              AND id_empresa = ?
              AND rol_empresa = 'ADMIN'
        """;

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.setInt(2, idEmpresa);

            return ps.executeQuery().next();

        } catch (SQLException e) {
            System.err.println("Error al verificar admin de empresa");
            return false;
        }
    }

    //Mapea ResultSet a UsuarioEmpresa
    private UsuarioEmpresa mapear(ResultSet rs) throws SQLException {

        UsuarioEmpresa ue = new UsuarioEmpresa();
        ue.setIdUsuarioEmpresa(rs.getInt("id_usuario_empresa"));
        ue.setIdUsuario(rs.getInt("id_usuario"));
        ue.setIdEmpresa(rs.getInt("id_empresa"));
        ue.setRolEmpresa(RolEmpresa.valueOf(rs.getString("rol_empresa")));

        return ue;
    }
}
