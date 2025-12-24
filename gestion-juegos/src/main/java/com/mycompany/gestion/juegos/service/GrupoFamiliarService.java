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
    
    public boolean eliminarGrupo(int idGrupo, int idSolicitante) {

        GrupoFamiliar grupo = grupoDAO.buscarPorId(idGrupo);
        if (grupo == null) return false;

        //Solo el creador puede eliminar
        if (grupo.getIdCreador() != idSolicitante) {
            return false;
        }

        return grupoDAO.eliminar(idGrupo);
    }
}
