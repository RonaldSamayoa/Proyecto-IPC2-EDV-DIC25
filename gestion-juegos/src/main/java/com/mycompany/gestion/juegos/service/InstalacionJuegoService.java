package com.mycompany.gestion.juegos.service;
import com.mycompany.gestion.juegos.dao.*;
import com.mycompany.gestion.juegos.model.InstalacionJuego;

import java.util.Date;
import java.util.List;
/**
 *
 * @author ronald
 */
public class InstalacionJuegoService {
    private final int DIAS_PRESTAMO = 7;
    
    private final InstalacionJuegoDAO instalacionDAO = new InstalacionJuegoDAO();
    private final BibliotecaDAO bibliotecaDAO = new BibliotecaDAO();
    private final GrupoUsuarioDAO grupoUsuarioDAO = new GrupoUsuarioDAO();

    public boolean instalarJuego(int idUsuario, int idJuego, boolean esPrestado, Integer idDueno) {

        // 1. Ya tiene este juego instalado 
        if (instalacionDAO.existeInstalacionActiva(idUsuario, idJuego)) {
            return false;
        }

        // 2. Juego propio
        if (!esPrestado) {
            //debe existir en su biblioteca
            if (!bibliotecaDAO.existeEnBiblioteca(idUsuario, idJuego)) {
                return false;
            }
        } 
        
        // 3. Juego prestado
        else {
            //debe existir un dueño
            if (idDueno == null) {
                return false;
            }

             // no puedo prestarme mi propio juego
            if (idUsuario == idDueno) {
                return false;
            }
            
            // deben compartir grupo
            if (!grupoUsuarioDAO.compartenGrupo(idUsuario, idDueno)) {
                return false;
            }
            
            // el dueño debe tener el juego en su biblioteca
            if (!bibliotecaDAO.existeEnBiblioteca(idDueno, idJuego)) {
                return false;
            }
            
            // el usuario puede tener solo un juego prestado instalado
            if (instalacionDAO.usuarioTienePrestadoActivo(idUsuario)) {
                int juegoActivo = instalacionDAO.obtenerJuegoPrestadoActivo(idUsuario);

                // Si el préstamo ha expirado se desinstala automaticamente
                if (instalacionDAO.prestamoExpirado(idUsuario, juegoActivo, DIAS_PRESTAMO)) {
                    instalacionDAO.desinstalarPrestadoActivo(idUsuario);
                }
                
                //Si el prestamo no ha expirado pero quiere instalar otro, se hace un cambio automatico
                else if (juegoActivo != idJuego) {
                    instalacionDAO.desinstalarPrestadoActivo(idUsuario);
                }
                // si es el MISMO juego y no está expirado → no se desinstala
            }
        }

        //registrar instalacion
        InstalacionJuego i = new InstalacionJuego();
        i.setIdUsuario(idUsuario);
        i.setIdJuego(idJuego);
        i.setFechaInstalacion(new Date());
        i.setEsPrestado(esPrestado);

        return instalacionDAO.insertar(i);
    }

    public boolean desinstalarJuego(int idUsuario, int idJuego) {
        return instalacionDAO.desinstalar(idUsuario, idJuego);
    }
    
    public boolean estaInstalado(int idUsuario, int idJuego) {
        return instalacionDAO.usuarioTieneInstalado(idUsuario, idJuego);
    }
    
    public List<InstalacionJuego> listarInstaladosPorUsuario(int idUsuario) {
        return instalacionDAO.listarInstaladosPorUsuario(idUsuario);
    }
}
