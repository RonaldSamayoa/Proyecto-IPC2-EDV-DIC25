package com.mycompany.gestion.juegos.controller;
import com.google.gson.Gson;
import com.mycompany.gestion.juegos.model.enums.TipoMultimedia;
import com.mycompany.gestion.juegos.service.MultimediaService;
import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.InputStream;
/**
 *
 * @author ronald
 */
@MultipartConfig
@WebServlet("/multimedia")
public class MultimediaController extends HttpServlet {
    private MultimediaService service;
    private Gson gson;

    @Override
    public void init() {
        service = new MultimediaService();
        gson = new Gson();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        Part archivoPart = req.getPart("archivo");
        String tipoStr = req.getParameter("tipo");
        int idJuego = Integer.parseInt(req.getParameter("idJuego"));

        InputStream is = archivoPart.getInputStream();
        byte[] archivo = is.readAllBytes();

        TipoMultimedia tipo = TipoMultimedia.from(tipoStr);

        boolean ok = service.agregarMultimedia(archivo, tipo, idJuego);

        resp.setStatus(ok ? 201 : 400);
        resp.getWriter().write(ok
                ? "Multimedia subida correctamente"
                : "Error al subir multimedia");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        int idJuego = Integer.parseInt(req.getParameter("idJuego"));
        resp.setContentType("application/json");
        resp.getWriter().write(
                gson.toJson(service.obtenerPorJuego(idJuego))
        );
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        int idMultimedia = Integer.parseInt(req.getParameter("idMultimedia"));
        boolean ok = service.eliminar(idMultimedia);

        resp.setStatus(ok ? 200 : 400);
        resp.getWriter().write(ok
                ? "Multimedia eliminada"
                : "No se pudo eliminar");
    }
}
