package com.mycompany.gestion.juegos.model;

import java.util.Date;

public class Comentario {

    private int idComentario;
    private String contenido;
    private Date fecha;
    private boolean visible;
    private int idUsuario;
    private int idJuego;
    private Integer idComentarioPadre; // puede ser null

    public Comentario() {}

    public int getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(int idComentario) {
        this.idComentario = idComentario;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
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

    public Integer getIdComentarioPadre() {
        return idComentarioPadre;
    }

    public void setIdComentarioPadre(Integer idComentarioPadre) {
        this.idComentarioPadre = idComentarioPadre;
    }
}

