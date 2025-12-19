package com.mycompany.gestion.juegos.service;

import com.mycompany.gestion.juegos.model.UsuarioEmpresa;
import com.mycompany.gestion.juegos.dao.UsuarioEmpresaDAO;
import java.util.List;

/**
 *
 * @author ronald
 */
public class UsuarioEmpresaService {
    private UsuarioEmpresaDAO dao;

    public UsuarioEmpresaService() {
        dao = new UsuarioEmpresaDAO();
    }

    public boolean asignarUsuarioEmpresa(int idUsuario, int idEmpresa) {
        UsuarioEmpresa ue = new UsuarioEmpresa();
        ue.setIdUsuario(idUsuario);
        ue.setIdEmpresa(idEmpresa);
        return dao.insertar(ue);
    }

    // Verifica si un usuario pertenece a una empresa
    public boolean perteneceAEmpresa(int idUsuario, int idEmpresa) {
        return dao.perteneceAEmpresa(idUsuario, idEmpresa);
    }
    
    //Lista los usuarios de una empresa
    public List<UsuarioEmpresa> listarUsuarios(int idEmpresa) {
        return dao.listarPorEmpresa(idEmpresa);
    }
}
