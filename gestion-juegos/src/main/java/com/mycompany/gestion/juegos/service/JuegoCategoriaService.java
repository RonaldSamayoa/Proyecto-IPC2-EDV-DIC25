package com.mycompany.gestion.juegos.service;
import com.mycompany.gestion.juegos.dao.JuegoCategoriaDAO;
import com.mycompany.gestion.juegos.model.JuegoCategoria;
import java.util.List;

/**
 *
 * @author ronald
 */
public class JuegoCategoriaService {
    private final JuegoCategoriaDAO dao = new JuegoCategoriaDAO();

    public boolean asignarCategoriaAJuego(int idJuego, int idCategoria) {

        JuegoCategoria jc = new JuegoCategoria();
        jc.setIdJuego(idJuego);
        jc.setIdCategoria(idCategoria);

        return dao.insertar(jc);
    }

    public boolean quitarCategoriaDeJuego(int idJuego, int idCategoria) {
        return dao.eliminar(idJuego, idCategoria);
    }

    public boolean quitarTodasLasCategorias(int idJuego) {
        return dao.eliminarPorJuego(idJuego);
    }

    public List<JuegoCategoria> obtenerCategoriasDeJuego(int idJuego) {
        return dao.listarPorJuego(idJuego);
    }

    public List<JuegoCategoria> obtenerJuegosPorCategoria(int idCategoria) {
        return dao.listarPorCategoria(idCategoria);
    }
}
