package com.mycompany.gestion.juegos.service;
import com.mycompany.gestion.juegos.dao.DetalleCompraDAO;
import com.mycompany.gestion.juegos.model.DetalleCompra;
import java.util.List;
/**
 *
 * @author ronald
 */
public class DetalleCompraService {
    private final DetalleCompraDAO dao = new DetalleCompraDAO();

    public boolean registrarDetalle(int idCompra, int idJuego, 
                                    java.math.BigDecimal precio) {

        DetalleCompra d = new DetalleCompra();
        d.setIdCompra(idCompra);
        d.setIdJuego(idJuego);
        d.setPrecioUnitario(precio);

        return dao.insertar(d);
    }

    public List<DetalleCompra> obtenerDetallesPorCompra(int idCompra) {
        return dao.listarPorCompra(idCompra);
    }
}
