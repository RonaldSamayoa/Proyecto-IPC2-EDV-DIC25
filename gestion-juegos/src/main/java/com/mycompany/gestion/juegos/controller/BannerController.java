package com.mycompany.gestion.juegos.controller;
import com.google.gson.Gson;
import com.mycompany.gestion.juegos.service.BannerService;
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
@WebServlet("/banner")
public class BannerController extends HttpServlet {
    private BannerService service;
    private Gson gson;

    @Override
    public void init() {
        service = new BannerService();
        gson = new Gson();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        String titulo = req.getParameter("titulo");
        String idJuegoStr = req.getParameter("idJuego");
        Part imagenPart = req.getPart("imagen");

        Integer idJuego = (idJuegoStr == null || idJuegoStr.isBlank())
                ? null
                : Integer.parseInt(idJuegoStr);

        byte[] imagen = null;
        if (imagenPart != null) {
            try (InputStream is = imagenPart.getInputStream()) {
                imagen = is.readAllBytes();
            }
        }

        boolean ok = service.crearBanner(titulo, imagen, idJuego);

        resp.setStatus(ok ? 201 : 400);
        resp.getWriter().write(ok
                ? "Banner creado correctamente"
                : "Error al crear banner");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");
        resp.getWriter().write(
                gson.toJson(service.listarActivos())
        );
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        int idBanner = Integer.parseInt(req.getParameter("idBanner"));
        boolean activo = Boolean.parseBoolean(req.getParameter("activo"));

        boolean ok = activo
                ? service.activar(idBanner)
                : service.desactivar(idBanner);

        resp.setStatus(ok ? 200 : 400);
        resp.getWriter().write(ok
                ? "Estado actualizado"
                : "Error al actualizar estado");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        int idBanner = Integer.parseInt(req.getParameter("idBanner"));
        boolean ok = service.eliminar(idBanner);

        resp.setStatus(ok ? 200 : 400);
        resp.getWriter().write(ok
                ? "Banner eliminado"
                : "No se pudo eliminar");
    }
}
