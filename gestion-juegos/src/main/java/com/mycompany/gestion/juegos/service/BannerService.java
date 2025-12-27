package com.mycompany.gestion.juegos.service;
import com.mycompany.gestion.juegos.dao.BannerDAO;
import com.mycompany.gestion.juegos.model.Banner;

import java.util.List;
/**
 *
 * @author ronald
 */
public class BannerService {
    private final BannerDAO dao = new BannerDAO();

    public boolean crearBanner(String titulo, byte[] imagen, Integer idJuego) {

        Banner b = new Banner();
        b.setTitulo(titulo);
        b.setImagen(imagen);
        b.setIdJuego(idJuego);
        b.setActivo(true);

        return dao.insertar(b);
    }

    public List<Banner> listarActivos() {
        return dao.listarActivos();
    }

    public boolean activar(int idBanner) {
        return dao.cambiarEstado(idBanner, true);
    }

    public boolean desactivar(int idBanner) {
        return dao.cambiarEstado(idBanner, false);
    }

    public boolean eliminar(int idBanner) {
        return dao.eliminar(idBanner);
    }
}
