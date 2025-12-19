package com.mycompany.gestion.juegos.model;
/**
 *
 * @author ronald
 */
public class UsuarioEmpresa {
    private int idUsuarioEmpresa;
    private int idUsuario;
    private int idEmpresa;

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
}
