package com.mycompany.gestion.juegos.service;
import com.mycompany.gestion.juegos.dao.BibliotecaDAO;
import com.mycompany.gestion.juegos.dao.GrupoUsuarioDAO;
import com.mycompany.gestion.juegos.model.Biblioteca;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author ronald
 */
public class BibliotecaService {
    private final BibliotecaDAO bibliotecaDAO = new BibliotecaDAO();
    private final GrupoUsuarioDAO grupoUsuarioDAO = new GrupoUsuarioDAO();
    
    public boolean agregarJuego(int idUsuario, int idJuego, boolean esPropio) {

        // Regla 1: no duplicar juegos
        if (bibliotecaDAO.existeEnBiblioteca(idUsuario, idJuego)) {
            return false;
        }

        Biblioteca b = new Biblioteca();
        b.setIdUsuario(idUsuario);
        b.setIdJuego(idJuego);
        b.setEsPropio(esPropio);

        return bibliotecaDAO.insertar(b);
    }

    public boolean tieneJuego(int idUsuario, int idJuego) {
        return bibliotecaDAO.existeEnBiblioteca(idUsuario, idJuego);
    }

    public List<Biblioteca> obtenerBibliotecaUsuario(int idUsuario) {
        return bibliotecaDAO.listarPorUsuario(idUsuario);
    }
    
    public List<Biblioteca> obtenerBibliotecaCompartida(int idUsuario) {

        List<Biblioteca> resultado = new ArrayList<>();

        // 1. Grupos del usuario
        List<Integer> grupos = grupoUsuarioDAO.listarGruposPorUsuario(idUsuario);

        for (int idGrupo : grupos) {

            // 2. Usuarios del grupo
            List<Integer> usuarios = grupoUsuarioDAO.listarUsuariosPorGrupo(idGrupo);

            for (int otroUsuario : usuarios) {

                if (otroUsuario == idUsuario) continue;

                // 3. Juegos propios del otro usuario
                resultado.addAll(
                        bibliotecaDAO.listarPorUsuario(otroUsuario)
                );
            }
        }
        return resultado;
    }

}
