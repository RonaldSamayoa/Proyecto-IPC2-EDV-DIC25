package com.mycompany.gestion.juegos.service;
import com.mycompany.gestion.juegos.dao.MultimediaDAO;
import com.mycompany.gestion.juegos.model.Multimedia;
import com.mycompany.gestion.juegos.model.enums.TipoMultimedia;

import java.util.List;
/**
 *
 * @author ronald
 */
public class MultimediaService {
    private final MultimediaDAO dao = new MultimediaDAO();

    public boolean agregarMultimedia(byte[] archivo, TipoMultimedia tipo, int idJuego) {

        Multimedia m = new Multimedia();
        m.setArchivo(archivo);
        m.setTipo(tipo);
        m.setIdJuego(idJuego);

        return dao.insertar(m);
    }

    public List<Multimedia> obtenerPorJuego(int idJuego) {
        return dao.listarPorJuego(idJuego);
    }

    public boolean eliminar(int idMultimedia) {
        return dao.eliminar(idMultimedia);
    }
}
