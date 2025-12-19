package com.mycompany.gestion.juegos.dao;
import com.mycompany.gestion.juegos.model.Compra;
import com.mycompany.gestion.juegos.resources.DBConnectionSingleton;

import java.sql.*;
/**
 *
 * @author ronald
 */
public class CompraDAO {
    public int insertar(Compra compra) {

        String sql = """
            INSERT INTO compra
            (fecha_compra, total_pagado, comision_plataforma, id_usuario)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setTimestamp(1, new Timestamp(compra.getFechaCompra().getTime()));
            ps.setBigDecimal(2, compra.getTotalPagado());
            ps.setBigDecimal(3, compra.getComisionPlataforma());
            ps.setInt(4, compra.getIdUsuario());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            System.err.println("Error al insertar compra: " + e.getMessage());
        }

        return -1;
    }
}
