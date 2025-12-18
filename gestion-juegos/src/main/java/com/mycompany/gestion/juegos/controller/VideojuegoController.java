package com.mycompany.gestion.juegos.controller;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mycompany.gestion.juegos.model.Videojuego;
import com.mycompany.gestion.juegos.service.VideojuegoService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author ronald
 */
@WebServlet("/videojuegos")
public class VideojuegoController extends HttpServlet {
    private VideojuegoService service;
    private Gson gson;

    @Override
    public void init() {
        service = new VideojuegoService();

        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd") 
                .create();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        Videojuego v = gson.fromJson(req.getReader(), Videojuego.class);

        if (service.registrar(v)) {
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write("{\"mensaje\":\"Videojuego registrado\"}");
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Datos inválidos\"}");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (req.getParameter("id") != null) {
            int id = Integer.parseInt(req.getParameter("id"));
            resp.getWriter().write(gson.toJson(service.obtenerPorId(id)));
            return;
        }

        if (req.getParameter("titulo") != null) {
            resp.getWriter().write(
                    gson.toJson(service.buscarPorTitulo(req.getParameter("titulo")))
            );
            return;
        }

        resp.getWriter().write(gson.toJson(service.listarActivos()));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        Videojuego v = gson.fromJson(req.getReader(), Videojuego.class);

        if (service.actualizar(v)) {
            resp.getWriter().write("{\"mensaje\":\"Actualizado\"}");
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"No se pudo actualizar\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        int id = Integer.parseInt(req.getParameter("id"));
        boolean activo = Boolean.parseBoolean(req.getParameter("activo"));

        if (service.cambiarEstado(id, activo)) {
            resp.getWriter().write("{\"mensaje\":\"Estado actualizado\"}");
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"No se pudo cambiar estado\"}");
        }
    }
}
