package com.mycompany.gestion.juegos.service;
import com.mycompany.gestion.juegos.dao.EmpresaDAO;
import com.mycompany.gestion.juegos.dao.UsuarioDAO;
import com.mycompany.gestion.juegos.dao.UsuarioEmpresaDAO;
import com.mycompany.gestion.juegos.model.Empresa;
import com.mycompany.gestion.juegos.model.Usuario;
import com.mycompany.gestion.juegos.model.UsuarioEmpresa;
import com.mycompany.gestion.juegos.model.enums.RolUsuario;
import java.util.List;

/**
 * Servicio que coordina el registro de una empresa
 * junto con su primer usuario
 */
public class RegistroEmpresaService {
    private final EmpresaDAO empresaDAO;
    private final UsuarioDAO usuarioDAO;
    private final UsuarioEmpresaDAO usuarioEmpresaDAO;

    public RegistroEmpresaService() {
        this.empresaDAO = new EmpresaDAO();
        this.usuarioDAO = new UsuarioDAO();
        this.usuarioEmpresaDAO = new UsuarioEmpresaDAO();
    }

    // Registra una empresa y su usuario 
    public boolean registrarEmpresaConUsuario(Empresa empresa, Usuario usuario) {

        /* 1. Insertar empresa */
        boolean empresaInsertada = empresaDAO.insertar(empresa);
        if (!empresaInsertada) {
            return false;
        }

        /* 2. Obtener empresa recién creada */
        List<Empresa> empresas = empresaDAO.buscarPorNombre(empresa.getNombre());
        if (empresas.isEmpty()) {
            return false;
        }
        Empresa empresaBD = empresas.get(0);

        /* 3. Insertar usuario */
        usuario.setRol(RolUsuario.EMPRESA);
        usuario.setEstado(true);

        boolean usuarioInsertado = usuarioDAO.insertarUsuario(usuario);
        if (!usuarioInsertado) {
            return false;
        }

        /* 4. Obtener usuario recién creado */
        Usuario usuarioBD = usuarioDAO.buscarPorCorreo(usuario.getCorreo());
        if (usuarioBD == null) {
            return false;
        }

        /* 5. Relacionar usuario con empresa */
        UsuarioEmpresa ue = new UsuarioEmpresa();
        ue.setIdUsuario(usuarioBD.getIdUsuario());
        ue.setIdEmpresa(empresaBD.getIdEmpresa());

        return usuarioEmpresaDAO.insertar(ue);
    }
}
