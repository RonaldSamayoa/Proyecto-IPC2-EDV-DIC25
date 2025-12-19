package com.mycompany.gestion.juegos.service;

import com.mycompany.gestion.juegos.dao.GrupoUsuarioDAO;

public class GrupoUsuarioService {

    private final GrupoUsuarioDAO dao = new GrupoUsuarioDAO();

    public boolean agregarUsuarioAGrupo(int idGrupo, int idUsuario) {

        // Regla 1: no duplicados
        if (dao.existeUsuarioEnGrupo(idGrupo, idUsuario)) {
            return false;
        }

        // Regla 2: máximo 5 integrantes
        int cantidad = dao.contarUsuariosPorGrupo(idGrupo);
        if (cantidad >= 5) {
            return false;
        }

        return dao.insertar(idGrupo, idUsuario);
    }

    public boolean quitarUsuarioDeGrupo(int idGrupo, int idUsuario) {
        return dao.eliminar(idGrupo, idUsuario);
    }
}

