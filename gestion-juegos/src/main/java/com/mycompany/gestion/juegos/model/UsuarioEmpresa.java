package com.mycompany.gestion.juegos.model;
import com.mycompany.gestion.juegos.model.enums.RolEmpresa;
/**
 *
 * @author ronald
 */
public class UsuarioEmpresa {
    private int idUsuarioEmpresa;
    private int idUsuario;
    private int idEmpresa;
    private RolEmpresa rolEmpresa;

    public UsuarioEmpresa() {
    }

    public int getIdUsuarioEmpresa() {
        return idUsuarioEmpresa;
    }

    public void setIdUsuarioEmpresa(int idUsuarioEmpresa) {
        this.idUsuarioEmpresa = idUsuarioEmpresa;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public RolEmpresa getRolEmpresa() {
        return rolEmpresa;
    }

    public void setRolEmpresa(RolEmpresa rolEmpresa) {
        this.rolEmpresa = rolEmpresa;
    }
}
