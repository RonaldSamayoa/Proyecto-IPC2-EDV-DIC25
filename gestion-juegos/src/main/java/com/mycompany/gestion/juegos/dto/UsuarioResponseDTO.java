package com.mycompany.gestion.juegos.dto;
import com.mycompany.gestion.juegos.model.enums.RolUsuario;
import java.util.Date;
/**
 *
 * @author ronald
 */
public class UsuarioResponseDTO {
    private int idUsuario;
    private String nickname;
    private String correo;
    private Date fechaNacimiento;
    private String pais;
    private RolUsuario rol;
    private boolean bibliotecaPublica;

    public UsuarioResponseDTO() {}

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public RolUsuario getRol() {
        return rol;
    }

    public void setRol(RolUsuario rol) {
        this.rol = rol;
    }

    public boolean isBibliotecaPublica() {
        return bibliotecaPublica;
    }

    public void setBibliotecaPublica(boolean bibliotecaPublica) {
        this.bibliotecaPublica = bibliotecaPublica;
    }
}
