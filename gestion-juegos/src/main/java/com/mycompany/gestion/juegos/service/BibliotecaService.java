package com.mycompany.gestion.juegos.service;
import com.mycompany.gestion.juegos.dao.BibliotecaDAO;
import com.mycompany.gestion.juegos.dao.GrupoUsuarioDAO;
import com.mycompany.gestion.juegos.dao.VideojuegoDAO;
import com.mycompany.gestion.juegos.dto.JuegoCompartidoDTO;
import com.mycompany.gestion.juegos.dto.VideojuegoDetalleDTO;
import com.mycompany.gestion.juegos.model.Biblioteca;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

/**
 *
 * @author ronald
 */
public class BibliotecaService {
    private final BibliotecaDAO bibliotecaDAO = new BibliotecaDAO();
    private final GrupoUsuarioDAO grupoUsuarioDAO = new GrupoUsuarioDAO();
    private final VideojuegoDAO videojuegoDAO = new VideojuegoDAO();
    
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
        return bibliotecaDAO.listarPropiosPorUsuario(idUsuario);
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

    public List<VideojuegoDetalleDTO> obtenerBibliotecaCompartidaPorGrupo(int idGrupo, int idUsuarioActual) {
        List<VideojuegoDetalleDTO> resultado = new ArrayList<>();

        // Juegos propios del usuario actual (para excluirlos)
        Set<Integer> juegosUsuarioActual =
                new HashSet<>(bibliotecaDAO.listarJuegosPropiosPorUsuario(idUsuarioActual));

        // Para evitar duplicados entre usuarios del grupo
        Set<Integer> juegosAgregados = new HashSet<>();

        // Usuarios del grupo
        List<Integer> usuarios = grupoUsuarioDAO.listarUsuariosPorGrupo(idGrupo);

        for (int idUsuario : usuarios) {

            if (idUsuario == idUsuarioActual) continue;

            List<Integer> juegosPropios =
                    bibliotecaDAO.listarJuegosPropiosPorUsuario(idUsuario);

            for (int idJuego : juegosPropios) {

                // ❌ Si el usuario ya lo tiene, no mostrar
                if (juegosUsuarioActual.contains(idJuego)) continue;

                // ❌ Si ya fue agregado por otro usuario, no duplicar
                if (juegosAgregados.contains(idJuego)) continue;

                VideojuegoDetalleDTO dto =
                        videojuegoDAO.obtenerDetallePorId(idJuego);

                if (dto != null) {
                    resultado.add(dto);
                    juegosAgregados.add(idJuego);
                }
            }
        }

        return resultado;
    }

}
