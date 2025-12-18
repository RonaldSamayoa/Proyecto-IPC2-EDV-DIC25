package com.mycompany.gestion.juegos.controller;
import com.mycompany.gestion.juegos.service.JuegoCategoriaService;
import com.google.gson.Gson;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;
/**
 *
 * @author ronald
 */

@WebServlet("/juego-categoria")
public class JuegoCategoriaController extends HttpServlet {
    private JuegoCategoriaService service;
    private Gson gson;

    @Override
    public void init() {
        service = new JuegoCategoriaService();
        gson = new Gson();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Map<String, Double> body = gson.fromJson(request.getReader(), Map.class);

        int idJuego = body.get("idJuego").intValue();
        int idCategoria = body.get("idCategoria").intValue();

        boolean ok = service.asignarCategoriaAJuego(idJuego, idCategoria);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (ok) {
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"mensaje\":\"Categoría asignada\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"No se pudo asignar\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int idJuego = Integer.parseInt(request.getParameter("idJuego"));
        int idCategoria = Integer.parseInt(request.getParameter("idCategoria"));

        boolean ok = service.quitarCategoriaDeJuego(idJuego, idCategoria);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (ok) {
            response.getWriter().write("{\"mensaje\":\"Relación eliminada\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"No se pudo eliminar\"}");
        }
    }
}
