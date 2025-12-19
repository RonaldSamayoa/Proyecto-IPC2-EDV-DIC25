package com.mycompany.gestion.juegos.service;
import com.mycompany.gestion.juegos.dao.*;
import com.mycompany.gestion.juegos.model.*;
import java.math.BigDecimal;
import java.util.Date;
/**
 *
 * @author ronald
 */
public class CompraService {
    private final CompraDAO compraDAO = new CompraDAO();
    private final VideojuegoDAO videojuegoDAO = new VideojuegoDAO();
    private final EmpresaDAO empresaDAO = new EmpresaDAO();
    private final ConfiguracionComisionDAO configDAO = new ConfiguracionComisionDAO();
    private final CarteraDAO carteraDAO = new CarteraDAO();
    private final DetalleCompraDAO detalleDAO = new DetalleCompraDAO();
    private final BibliotecaDAO bibliotecaDAO = new BibliotecaDAO();

    public boolean comprarJuego(int idUsuario, int idJuego) {

        // 1. Obtener juego
        Videojuego juego = videojuegoDAO.buscarPorId(idJuego);
        if (juego == null || !juego.isEstadoVenta()) {
            return false;
        }

        // 2. Obtener empresa
        Empresa empresa = empresaDAO.buscarPorId(juego.getIdEmpresa());
        if (empresa == null) return false;

        // 3. Determinar comisión
        BigDecimal porcentaje;

        if (empresa.getPorcentajeComisionEspecifico() != null) {
            porcentaje = empresa.getPorcentajeComisionEspecifico();
        } else {
            porcentaje = configDAO.obtenerActual().getPorcentajeGlobal();
        }

        // 4. Calcular comisión
        BigDecimal comision = juego.getPrecio()
                .multiply(porcentaje)
                .divide(BigDecimal.valueOf(100));

        // 5. Verificar saldo
        Cartera cartera = carteraDAO.obtenerPorUsuario(idUsuario);
        if (cartera == null || cartera.getSaldo().compareTo(juego.getPrecio()) < 0) {
            return false;
        }

        // 6. Descontar saldo
        carteraDAO.actualizarSaldo(
                idUsuario,
                cartera.getSaldo().subtract(juego.getPrecio())
        );

        // 7. Crear compra
        Compra compra = new Compra();
        compra.setFechaCompra(new Date());
        compra.setTotalPagado(juego.getPrecio());
        compra.setComisionPlataforma(comision);
        compra.setIdUsuario(idUsuario);

        int idCompra = compraDAO.insertar(compra);
        if (idCompra <= 0) return false;

        // 8. Crear detalle
        DetalleCompra detalle = new DetalleCompra();
        detalle.setIdCompra(idCompra);
        detalle.setIdJuego(idJuego);
        detalle.setPrecioUnitario(juego.getPrecio());
        detalleDAO.insertar(detalle);

        // 9. Agregar a biblioteca
        bibliotecaDAO.insertar(idUsuario, idJuego, true);

        return true;
    }
}
