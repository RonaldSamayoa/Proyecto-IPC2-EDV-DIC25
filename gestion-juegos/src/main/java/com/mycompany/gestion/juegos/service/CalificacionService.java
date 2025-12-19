package com.mycompany.gestion.juegos.service;
import com.mycompany.gestion.juegos.dao.CalificacionDAO;
import com.mycompany.gestion.juegos.dao.BibliotecaDAO;
import com.mycompany.gestion.juegos.model.Calificacion;
import java.math.BigDecimal;

import java.util.Date;
import java.util.List;
/**
 *
 * @author ronald
 */
public class CalificacionService {
    private final CalificacionDAO calificacionDAO = new CalificacionDAO();
    private final BibliotecaDAO bibliotecaDAO = new BibliotecaDAO();

    /**
     * Crear o actualizar una calificación
     * Regla: el usuario DEBE tener el juego en su biblioteca
     */
    // Crear calificación (solo si compró el juego)
    public boolean crear(int idUsuario, int idJuego, int valor) {
        //verificar que el usuario tenga el juego
        if (!bibliotecaDAO.existeEnBiblioteca(idUsuario, idJuego)) {
            return false;
        }
            
        Calificacion c = new Calificacion();
        c.setValor(valor);
        c.setFecha(new Date());
        c.setIdUsuario(idUsuario);
        c.setIdJuego(idJuego);

        return calificacionDAO.insertar(c);
    }

    // Actualizar calificación existente
    public boolean actualizar(int idCalificacion, int valor) {

        Calificacion c = new Calificacion();
        c.setIdCalificacion(idCalificacion);
        c.setValor(valor);
        c.setFecha(new Date());

        return calificacionDAO.actualizar(c);
    }
    
    // Calificaciones hechas por un usuario
    public List<Calificacion> obtenerPorUsuario(int idUsuario) {
        return calificacionDAO.listarPorUsuario(idUsuario);
    }

    // Calificaciones de un juego
    public List<Calificacion> obtenerPorJuego(int idJuego) {
        return calificacionDAO.listarPorJuego(idJuego);
    }

    // Estadísticas
    public int contarPorJuego(int idJuego) {
        return calificacionDAO.contarPorJuego(idJuego);
    }

    public BigDecimal promedioPorJuego(int idJuego) {
        return calificacionDAO.promedioPorJuego(idJuego);
    }
}
