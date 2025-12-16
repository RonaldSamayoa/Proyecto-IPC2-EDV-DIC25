package com.mycompany.gestion.juegos.service;

import com.mycompany.gestion.juegos.model.UsuarioEmpresa;
import com.mycompany.gestion.juegos.dao.UsuarioEmpresaDAO;
import com.mycompany.gestion.juegos.model.enums.RolEmpresa;
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

    /**
     * Asigna un usuario como ADMIN de una empresa.
     * Usado SOLO para el primer usuario.
     */
    public boolean asignarAdminEmpresa(int idUsuario, int idEmpresa) {

        UsuarioEmpresa ue = new UsuarioEmpresa();
        ue.setIdUsuario(idUsuario);
        ue.setIdEmpresa(idEmpresa);
        ue.setRolEmpresa(RolEmpresa.ADMIN);

        return dao.insertar(ue);
    }

    //Asigna un empleado a la empresa
    public boolean asignarEmpleado(int idUsuario, int idEmpresa) {

        UsuarioEmpresa ue = new UsuarioEmpresa();
        ue.setIdUsuario(idUsuario);
        ue.setIdEmpresa(idEmpresa);
        ue.setRolEmpresa(RolEmpresa.EMPLEADO);

        return dao.insertar(ue);
    }

    // Verifica si un usuario es administrador de una empresa
    public boolean esAdmin(int idUsuario, int idEmpresa) {
        return dao.esAdminEmpresa(idUsuario, idEmpresa);
    }

    //Lista los usuarios de una empresa
    public List<UsuarioEmpresa> listarUsuarios(int idEmpresa) {
        return dao.listarPorEmpresa(idEmpresa);
    }
}
