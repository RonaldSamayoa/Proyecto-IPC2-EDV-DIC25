package com.mycompany.gestion.juegos.model;
import java.util.Date;
import com.mycompany.gestion.juegos.model.enums.RolUsuario;

/**
 *
 * @author ronald
 */
public class Usuario {
    private int idUsuario;
    private String nickname;
    private String password;
    private String correo;
    private Date fechaNacimiento;
    private String pais;
    private byte[] imagenPerfil;
    private RolUsuario rol;
    private boolean bibliotecaPublica;
    private boolean estado;

    public Usuario() {}

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

    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
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

    public byte[] getImagenPerfil() {
        return imagenPerfil;
    }

    public void setImagenPerfil(byte[] imagenPerfil) {
        this.imagenPerfil = imagenPerfil;
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

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
