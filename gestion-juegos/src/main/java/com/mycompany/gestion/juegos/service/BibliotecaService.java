package com.mycompany.gestion.juegos.service;
import com.mycompany.gestion.juegos.dao.BibliotecaDAO;
import com.mycompany.gestion.juegos.model.Biblioteca;
import java.util.List;
/**
 *
 * @author ronald
 */
public class BibliotecaService {
    private final BibliotecaDAO dao = new BibliotecaDAO();

    public boolean agregarJuego(int idUsuario, int idJuego, boolean esPropio) {

        // Regla 1: no duplicar juegos
        if (dao.existeEnBiblioteca(idUsuario, idJuego)) {
            return false;
        }

        Biblioteca b = new Biblioteca();
        b.setIdUsuario(idUsuario);
        b.setIdJuego(idJuego);
        b.setEsPropio(esPropio);

        return dao.insertar(b);
    }

    public boolean tieneJuego(int idUsuario, int idJuego) {
        return dao.existeEnBiblioteca(idUsuario, idJuego);
    }

    public List<Biblioteca> obtenerBibliotecaUsuario(int idUsuario) {
        return dao.listarPorUsuario(idUsuario);
    }
}
