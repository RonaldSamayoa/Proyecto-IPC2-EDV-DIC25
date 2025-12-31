package com.mycompany.gestion.juegos.dto;

/**
 *
 * @author ronald
 */
public class JuegoCompartidoDTO {
    private int idJuego;
    private int idUsuario;

    public JuegoCompartidoDTO() {}

    public JuegoCompartidoDTO(int idJuego, int idUsuario) {
        this.idJuego = idJuego;
        this.idUsuario = idUsuario;
    }

    public int getIdJuego() {
        return idJuego;
    }

    public void setIdJuego(int idJuego) {
        this.idJuego = idJuego;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
