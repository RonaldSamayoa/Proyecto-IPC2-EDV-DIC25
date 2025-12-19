package com.mycompany.gestion.juegos.controller;
import com.google.gson.Gson;
import com.mycompany.gestion.juegos.service.CalificacionService;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Map;
/**
 *
 * @author ronald
 */
@WebServlet("/calificaciones")
public class CalificacionController extends HttpServlet {
    private CalificacionService service;
    private Gson gson;

    @Override
    public void init() {
        service = new CalificacionService();
        gson = new Gson();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        Map<String, Object> body = gson.fromJson(req.getReader(), Map.class);

        int idUsuario = ((Number) body.get("idUsuario")).intValue();
        int idJuego   = ((Number) body.get("idJuego")).intValue();
        int valor     = ((Number) body.get("valor")).intValue();

        boolean ok = service.crear(idUsuario, idJuego, valor);

        resp.setContentType("application/json");

        if (ok) {
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write("{\"mensaje\":\"Calificación creada\"}");
        } else {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            resp.getWriter().write(
                "{\"error\":\"El usuario no ha comprado el juego\"}"
            );
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        Map<String, Object> body = gson.fromJson(req.getReader(), Map.class);

        int idCalificacion = ((Number) body.get("idCalificacion")).intValue();
        int valor          = ((Number) body.get("valor")).intValue();

        boolean ok = service.actualizar(idCalificacion, valor);

        resp.setContentType("application/json");

        if (ok) {
            resp.getWriter().write("{\"mensaje\":\"Calificación actualizada\"}");
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"No se pudo actualizar\"}");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String tipo = req.getParameter("tipo");
        int id = Integer.parseInt(req.getParameter("id"));

        resp.setContentType("application/json");

        if ("usuario".equals(tipo)) {
            resp.getWriter().write(
                gson.toJson(service.obtenerPorUsuario(id))
            );
        } else if ("juego".equals(tipo)) {
            resp.getWriter().write(
                gson.toJson(service.obtenerPorJuego(id))
            );
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Parámetros inválidos\"}");
        }
    }
}
