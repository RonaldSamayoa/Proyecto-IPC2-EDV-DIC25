package com.mycompany.gestion.juegos.model;
import com.mycompany.gestion.juegos.model.enums.TipoMultimedia;

/**
 *
 * @author ronald
 */
public class Multimedia {
    private int idMultimedia;
    private byte[] archivo; // BLOB
    private TipoMultimedia tipo; // "imagen" o "video"
    private int idJuego;

    public int getIdMultimedia() {
        return idMultimedia;
    }

    public void setIdMultimedia(int idMultimedia) {
        this.idMultimedia = idMultimedia;
    }

    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }

    public TipoMultimedia getTipo() {
        return tipo;
    }

    public void setTipo(TipoMultimedia tipo) {
        this.tipo = tipo;
    }

    public int getIdJuego() {
        return idJuego;
    }

    public void setIdJuego(int idJuego) {
        this.idJuego = idJuego;
    }
}
