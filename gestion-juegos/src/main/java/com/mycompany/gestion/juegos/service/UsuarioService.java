package com.mycompany.gestion.juegos.service;
import com.mycompany.gestion.juegos.dao.UsuarioDAO;
import com.mycompany.gestion.juegos.model.Usuario;
import com.mycompany.gestion.juegos.model.enums.RolUsuario;
/**
 *
 * @author ronald
 */
public class UsuarioService {
    private final UsuarioDAO usuarioDAO;

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO();
    }

    //Registra un nuevo usuario en el sistema.
    //Valida que el correo y el nickname no estén en uso
    public boolean registrarUsuario(Usuario usuario) {

        // Validar correo duplicado
        if (usuarioDAO.buscarPorCorreo(usuario.getCorreo()) != null) {
            System.err.println("El correo ya se encuentra registrado.");
            return false;
        }

        // Validar nickname duplicado
        if (usuarioDAO.buscarPorNickname(usuario.getNickname()) != null) {
            System.err.println("El nickname ya se encuentra registrado.");
            return false;
        }

        // Asignar rol por defecto si no viene definido
        if (usuario.getRol() == null) {
            usuario.setRol(RolUsuario.GAMER);
        }

        // Activar usuario por defecto
        usuario.setEstado(true);

        return usuarioDAO.insertarUsuario(usuario);
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
}
