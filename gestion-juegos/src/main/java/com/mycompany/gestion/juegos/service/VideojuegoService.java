package com.mycompany.gestion.juegos.service;
import com.mycompany.gestion.juegos.dao.EmpresaDAO;
import com.mycompany.gestion.juegos.dao.VideojuegoDAO;
import com.mycompany.gestion.juegos.dto.VideojuegoDetalleDTO;
import com.mycompany.gestion.juegos.model.Empresa;
import com.mycompany.gestion.juegos.model.Videojuego;
import java.math.BigDecimal;
import java.util.List;
/**
 *
 * @author ronald
 */
public class VideojuegoService {
    private final VideojuegoDAO videojuegoDAO;
    private final EmpresaDAO empresaDAO;

    public VideojuegoService() {
        this.videojuegoDAO = new VideojuegoDAO();
        this.empresaDAO = new EmpresaDAO();
    }

    public boolean registrar(Videojuego v) {

        if (v.getTitulo() == null || v.getTitulo().isBlank()) {
            return false;
        }

        if (v.getPrecio() == null || v.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }

        Empresa empresa = empresaDAO.buscarPorId(v.getIdEmpresa());
        if (empresa == null || !empresa.isEstado()) {
            return false;
        }

        return videojuegoDAO.insertar(v);
    }

    public Videojuego obtenerPorId(int idJuego) {
        return videojuegoDAO.buscarPorId(idJuego);
    }

    public List<Videojuego> buscarPorTitulo(String titulo) {
        return videojuegoDAO.buscarPorTitulo(titulo);
    }

    public List<Videojuego> listarActivos() {
        return videojuegoDAO.listarActivos();
    }

    public List<Videojuego> listarPorEmpresa(int idEmpresa) {
        return videojuegoDAO.listarPorEmpresa(idEmpresa);
    }

    public boolean actualizar(Videojuego v) {
        return videojuegoDAO.actualizar(v);
    }

    public boolean cambiarEstado(int idJuego, boolean activo) {
        return videojuegoDAO.cambiarEstado(idJuego, activo);
    }
    
    //detalles de juego con categoria incluida
    public VideojuegoDetalleDTO obtenerDetalle(int idJuego) {
        return videojuegoDAO.obtenerDetallePorId(idJuego);
    }
    
    public boolean actualizarParcial(Videojuego nuevo) {
        Videojuego actual = videojuegoDAO.buscarPorId(nuevo.getIdJuego());
        if (actual == null || !actual.isEstadoVenta()) {
            return false;
        }

        // Solo reemplazamos si viene algo
        if (nuevo.getTitulo() != null) {
            actual.setTitulo(nuevo.getTitulo());
        }
        if (nuevo.getDescripcion() != null) {
            actual.setDescripcion(nuevo.getDescripcion());
        }
        if (nuevo.getPrecio() != null) {
            actual.setPrecio(nuevo.getPrecio());
        }
        if (nuevo.getRequisitosMinimos() != null) {
            actual.setRequisitosMinimos(nuevo.getRequisitosMinimos());
        }
        if (nuevo.getClasificacionEdad() != null) {
            actual.setClasificacionEdad(nuevo.getClasificacionEdad());
        }
        if (nuevo.getFechaLanzamiento() != null) {
            actual.setFechaLanzamiento(nuevo.getFechaLanzamiento());
        }

        return videojuegoDAO.actualizar(actual);
    }

}
