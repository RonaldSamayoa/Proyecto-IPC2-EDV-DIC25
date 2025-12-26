package com.mycompany.gestion.juegos.model;

import java.util.Date;

/**
 *
 * @author ronald
 */
public class InstalacionJuego {
    private int idInstalacion;
    private int idUsuario;
    private int idJuego;
    private Date fechaInstalacion;
    private Date fechaDesinstalacion;
    private boolean esPrestado;

    public int getIdInstalacion() {
        return idInstalacion;
    }

    public void setIdInstalacion(int idInstalacion) {
        this.idInstalacion = idInstalacion;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdJuego() {
        return idJuego;
    }

    public void setIdJuego(int idJuego) {
        this.idJuego = idJuego;
    }

    public Date getFechaInstalacion() {
        return fechaInstalacion;
    }

    public void setFechaInstalacion(Date fechaInstalacion) {
        this.fechaInstalacion = fechaInstalacion;
    }

    public Date getFechaDesinstalacion() {
        return fechaDesinstalacion;
    }

    public void setFechaDesinstalacion(Date fechaDesinstalacion) {
        this.fechaDesinstalacion = fechaDesinstalacion;
    }

    public boolean isEsPrestado() {
        return esPrestado;
    }

    public void setEsPrestado(boolean esPrestado) {
        this.esPrestado = esPrestado;
    }
}
