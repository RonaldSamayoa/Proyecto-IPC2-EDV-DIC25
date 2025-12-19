package com.mycompany.gestion.juegos.service;
import com.mycompany.gestion.juegos.dao.GrupoFamiliarDAO;
import com.mycompany.gestion.juegos.dao.GrupoUsuarioDAO;
import com.mycompany.gestion.juegos.model.GrupoFamiliar;
/**
 *
 * @author ronald
 */
public class GrupoFamiliarService {
    private final GrupoFamiliarDAO grupoDAO = new GrupoFamiliarDAO();
    private final GrupoUsuarioDAO grupoUsuarioDAO = new GrupoUsuarioDAO();

    /**
     * Crea un grupo familiar y agrega automáticamente
     * al creador como miembro del grupo.
     */
    public boolean crearGrupo(String nombre, int idCreador) {

        GrupoFamiliar grupo = new GrupoFamiliar();
        grupo.setNombre(nombre);
        grupo.setIdCreador(idCreador);

        int idGrupo = grupoDAO.insertar(grupo);
        if (idGrupo <= 0) {
            return false;
        }

        // El creador siempre pertenece al grupo
        return grupoUsuarioDAO.insertar(idGrupo, idCreador);
    }

    /**
     * Agrega un usuario a un grupo existente
     */
    public boolean agregarUsuario(int idGrupo, int idUsuario) {
        //evitar duplicados
        if (grupoUsuarioDAO.existeUsuarioEnGrupo(idGrupo, idUsuario)) {
            return false;
        }
        
        //maximo 5 integrantes
        int cantidadActual = grupoUsuarioDAO.contarUsuariosPorGrupo(idGrupo);
        if (cantidadActual >= 5) {
            return false;
        }

        return grupoUsuarioDAO.insertar(idGrupo, idUsuario);
    }
}
