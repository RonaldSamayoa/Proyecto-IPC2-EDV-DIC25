package com.mycompany.gestion.juegos.service;
import com.mycompany.gestion.juegos.dao.UsuarioDAO;
import com.mycompany.gestion.juegos.dto.UsuarioResponseDTO;
import com.mycompany.gestion.juegos.model.Usuario;
import com.mycompany.gestion.juegos.model.enums.RolUsuario;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author ronald
 */
public class UsuarioService {
    private final UsuarioDAO usuarioDAO;

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO();
    }

    //Registra un nuevo usuario en el sistema
    public boolean registrarUsuario(Usuario usuario) {

        // Validar correo duplicado
        if (usuarioDAO.buscarPorCorreo(usuario.getCorreo()) != null) {
            System.err.println("El correo ya se encuentra registrado.");
            return false;
        }

        // Asignar rol por defecto si no viene definido
        if (usuario.getRol() == null) {
            usuario.setRol(RolUsuario.GAMER);
        }

        // Activar usuario por defecto
        usuario.setEstado(true);

        boolean creado = usuarioDAO.insertarUsuario(usuario);
        if (!creado) return false;

        // 🔥 SOLO GAMER TIENE CARTERA
        if (usuario.getRol() == RolUsuario.GAMER) {

            Usuario creadoDb = usuarioDAO.buscarPorCorreo(usuario.getCorreo());
            if (creadoDb == null) return false;

            CarteraService carteraService = new CarteraService();
            return carteraService.crearCarteraSiNoExiste(creadoDb.getIdUsuario());
        }

        return true;
    }

    //Autentica a un usuario por correo y password
    public Usuario autenticar(String correo, String password) {

        Usuario usuario = usuarioDAO.buscarPorCorreo(correo);

        if (usuario == null) {
            return null;
        }

        // Comparación directa (hash se puede agregar después)
        if (!usuario.getPassword().equals(password)) {
            return null;
        }

        return usuario;
    }
    
    public boolean registrarAdmin(Usuario admin) {

        if (admin.getRol() != RolUsuario.ADMIN) {
            return false;
        }

        if (usuarioDAO.buscarPorCorreo(admin.getCorreo()) != null) {
            return false;
        }
        
        admin.setEstado(true);

        //No se crea cartera
        return usuarioDAO.insertarUsuario(admin);
    }

    public boolean registrarEmpresa(Usuario empresa) {

        // Validar rol correcto
        if (empresa.getRol() != RolUsuario.EMPRESA) {
            return false;
        }

        // Validar correo duplicado
        if (usuarioDAO.buscarPorCorreo(empresa.getCorreo()) != null) {
            return false;
        }

        // Reglas fijas para empresa
        empresa.setBibliotecaPublica(false);
        empresa.setEstado(true);

        //No se crea cartera
        return usuarioDAO.insertarUsuario(empresa);
    }

    // Obtiene un usuario activo por su identificador
    public Usuario obtenerPorId(int idUsuario) {
        return usuarioDAO.buscarPorId(idUsuario);
    }

    //Obtiene un usuario activo por su nickname
    public Usuario obtenerPorNickname(String nickname) {
        return usuarioDAO.buscarPorNickname(nickname);
    }

    public boolean desactivarUsuario(int idUsuario) {
        return usuarioDAO.desactivarUsuario(idUsuario);
    }
    
    public boolean actualizarPerfilParcial(int idUsuario, Usuario cambios) {
        Usuario actual = usuarioDAO.buscarPorId(idUsuario);

        if (actual == null) {
            return false;
        }

        // Mezcla controlada de campos
        if (cambios.getNickname() != null) {
            actual.setNickname(cambios.getNickname());
        }

        if (cambios.getPassword() != null) {
            actual.setPassword(cambios.getPassword());
        }

        if (cambios.getCorreo() != null) {
            actual.setCorreo(cambios.getCorreo());
        }

        if (cambios.getFechaNacimiento() != null) {
            actual.setFechaNacimiento(cambios.getFechaNacimiento());
        }

        if (cambios.getPais() != null) {
            actual.setPais(cambios.getPais());
        }

        if (cambios.getImagenPerfil() != null) {
            actual.setImagenPerfil(cambios.getImagenPerfil());
        }

        actual.setBibliotecaPublica(cambios.isBibliotecaPublica());

        return usuarioDAO.actualizarUsuario(actual);
    }

    public List<UsuarioResponseDTO> buscarUsuariosPorNickname(String q) {
        Usuario usuario = usuarioDAO.buscarPorNickname("%" + q + "%");

        if (usuario == null) return List.of();

        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setNickname(usuario.getNickname());
        dto.setCorreo(usuario.getCorreo());
        dto.setFechaNacimiento(usuario.getFechaNacimiento());
        dto.setPais(usuario.getPais());
        dto.setRol(usuario.getRol());
        dto.setBibliotecaPublica(usuario.isBibliotecaPublica());

        return List.of(dto);
    }

}
