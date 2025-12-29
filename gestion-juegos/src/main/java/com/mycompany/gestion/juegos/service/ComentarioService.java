package com.mycompany.gestion.juegos.service;

import com.mycompany.gestion.juegos.dao.ComentarioDAO;
import com.mycompany.gestion.juegos.dao.UsuarioDAO;
import com.mycompany.gestion.juegos.model.Comentario;

import java.util.Date;
import java.util.List;

public class ComentarioService {
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final ComentarioDAO comentarioDAO = new ComentarioDAO();
    private final BibliotecaService bibliotecaService = new BibliotecaService();

    public boolean comentar(
            int idUsuario,
            int idJuego,
            String contenido,
            Integer idComentarioPadre
    ) {

        // 1. Verificar que el usuario tenga el juego
        boolean tieneJuego = bibliotecaService.tieneJuego(idUsuario, idJuego);
        if (!tieneJuego) {
            return false;
        }

        // 2. Validar contenido
        if (contenido == null || contenido.trim().isEmpty()) {
            return false;
        }

        // 3. Crear comentario
        Comentario c = new Comentario();
        c.setContenido(contenido);
        c.setFecha(new Date());
        c.setVisible(true);
        c.setIdUsuario(idUsuario);
        c.setIdJuego(idJuego);
        c.setIdComentarioPadre(idComentarioPadre);

        return comentarioDAO.insertar(c);
    }

    public List<Comentario> listarComentariosJuego(int idJuego) {
        return comentarioDAO.listarPorJuego(idJuego);
    }

    public boolean ocultarComentarioConRespuestas(int idComentario, int idUsuario) {
        boolean permitido = comentarioDAO.puedeOcultar(idComentario, idUsuario);
        if (!permitido) {
            return false;
        }

        comentarioDAO.ocultarConRespuestas(idComentario);
        return true;
    }
    
    public boolean reactivarComentario(int idComentario, int idUsuario) {
        boolean permitido = comentarioDAO.puedeOcultar(idComentario, idUsuario);
        if (!permitido) {
            return false;
        }

        return comentarioDAO.reactivar(idComentario);
    }

}

