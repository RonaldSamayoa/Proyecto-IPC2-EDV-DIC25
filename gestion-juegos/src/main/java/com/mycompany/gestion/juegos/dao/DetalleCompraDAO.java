package com.mycompany.gestion.juegos.dao;

import com.mycompany.gestion.juegos.model.DetalleCompra;
import com.mycompany.gestion.juegos.resources.DBConnectionSingleton;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetalleCompraDAO {

    public boolean insertar(DetalleCompra detalle) {

        String sql = """
            INSERT INTO detalle_compra
            (id_compra, id_juego, precio_unitario)
            VALUES (?, ?, ?)
        """;

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, detalle.getIdCompra());
            ps.setInt(2, detalle.getIdJuego());
            ps.setBigDecimal(3, detalle.getPrecioUnitario());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar detalle_compra: " + e.getMessage());
            return false;
        }
    }

    public List<DetalleCompra> listarPorCompra(int idCompra) {

        String sql = "SELECT * FROM detalle_compra WHERE id_compra = ?";
        List<DetalleCompra> lista = new ArrayList<>();

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCompra);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                DetalleCompra d = new DetalleCompra();
                d.setIdDetalle(rs.getInt("id_detalle"));
                d.setIdCompra(rs.getInt("id_compra"));
                d.setIdJuego(rs.getInt("id_juego"));
                d.setPrecioUnitario(rs.getBigDecimal("precio_unitario"));
                lista.add(d);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar detalle_compra: " + e.getMessage());
        }

        return lista;
    }
}

