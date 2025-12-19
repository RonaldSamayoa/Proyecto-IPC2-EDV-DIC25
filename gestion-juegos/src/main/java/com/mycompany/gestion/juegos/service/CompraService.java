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
    private final BibliotecaService bibliotecaService = new BibliotecaService();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public boolean comprarJuego(int idUsuario, int idJuego) {

        /* 1. Obtener videojuego */
        Videojuego juego = videojuegoDAO.buscarPorId(idJuego);
        if (juego == null || !juego.isEstadoVenta()) {
            return false;
        }
        
        /* 1.1 Obtener usuario */
        Usuario usuario = usuarioDAO.buscarPorId(idUsuario);
        if (usuario == null) {
            return false;
        }

        /* 1.2 Validar clasificación por edad */
        if (!cumpleClasificacionEdad(usuario, juego)) {
            return false; // No cumple edad mínima
        }
        
        /* 2. Obtener empresa desarrolladora */
        Empresa empresa = empresaDAO.buscarPorId(juego.getIdEmpresa());
        if (empresa == null) {
            return false;
        }

        /* 3. Determinar porcentaje de comisión */
        BigDecimal porcentaje;

        if (empresa.getPorcentajeComisionEspecifico() != null) {
            porcentaje = empresa.getPorcentajeComisionEspecifico();
        } else {
            ConfiguracionComision config = configDAO.obtenerActual();
            if (config == null) {
                return false;
            }
            porcentaje = config.getPorcentajeGlobal();
        }

        /* 4. Calcular comisión */
        BigDecimal comision = juego.getPrecio()
                .multiply(porcentaje)
                .divide(BigDecimal.valueOf(100));

        /* 5. Verificar cartera */
        Cartera cartera = carteraDAO.obtenerPorUsuario(idUsuario);
        if (cartera == null) {
            return false;
        }

        if (cartera.getSaldo().compareTo(juego.getPrecio()) < 0) {
            return false; // saldo insuficiente
        }

        /* 6. Descontar saldo */
        BigDecimal nuevoSaldo = cartera.getSaldo().subtract(juego.getPrecio());
        boolean saldoActualizado = carteraDAO.actualizarSaldo(idUsuario, nuevoSaldo);
        if (!saldoActualizado) {
            return false;
        }

        /* 7. Crear compra */
        Compra compra = new Compra();
        compra.setFechaCompra(new Date());
        compra.setTotalPagado(juego.getPrecio());
        compra.setComisionPlataforma(comision);
        compra.setIdUsuario(idUsuario);

        int idCompra = compraDAO.insertar(compra);
        if (idCompra <= 0) {
            return false;
        }

        /* 8. Crear detalle de compra */
        DetalleCompra detalle = new DetalleCompra();
        detalle.setIdCompra(idCompra);
        detalle.setIdJuego(idJuego);
        detalle.setPrecioUnitario(juego.getPrecio());

        boolean detalleInsertado = detalleDAO.insertar(detalle);
        if (!detalleInsertado) {
            return false;
        }

        /* 9. Agregar juego a biblioteca */
        boolean agregado = bibliotecaService.agregarJuego(
                idUsuario,
                idJuego,
                true
        );

        return agregado;
    }
    
    private int calcularEdad(Date fechaNacimiento) {
        java.util.Calendar nacimiento = java.util.Calendar.getInstance();
        nacimiento.setTime(fechaNacimiento);

        java.util.Calendar hoy = java.util.Calendar.getInstance();

        int edad = hoy.get(java.util.Calendar.YEAR)
                - nacimiento.get(java.util.Calendar.YEAR);

        if (hoy.get(java.util.Calendar.DAY_OF_YEAR)
                < nacimiento.get(java.util.Calendar.DAY_OF_YEAR)) {
            edad--;
        }

        return edad;
    }
    
    private int edadMinimaPorClasificacion(String clasificacion) {
        if (clasificacion == null) {
            return 0; // si no tiene clasificación, no se restringe
        }

        switch (clasificacion.toUpperCase()) {
            case "E":
                return 0;
            case "E10+":
                return 10;
            case "18+":
                return 18;
            default:
                return 0; // valor desconocido → no bloquea
        }
    }
    
    private boolean cumpleClasificacionEdad(Usuario usuario, Videojuego juego) {
        int edadUsuario = calcularEdad(usuario.getFechaNacimiento());
        int edadMinima = edadMinimaPorClasificacion(
                juego.getClasificacionEdad()
        );

        return edadUsuario >= edadMinima;
    }

}
