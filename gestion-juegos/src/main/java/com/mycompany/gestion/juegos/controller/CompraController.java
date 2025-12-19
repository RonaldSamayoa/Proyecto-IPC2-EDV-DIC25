package com.mycompany.gestion.juegos.controller;
import com.google.gson.Gson;
import com.mycompany.gestion.juegos.service.CompraService;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

@WebServlet("/compras")
public class CompraController extends HttpServlet{
    private CompraService service;
    private Gson gson;

    @Override
    public void init() {
        service = new CompraService();
        gson = new Gson();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");

        Map<String, Object> body = gson.fromJson(req.getReader(), Map.class);

        int idUsuario = ((Number) body.get("idUsuario")).intValue();
        int idJuego = ((Number) body.get("idJuego")).intValue();

        boolean ok = service.comprarJuego(idUsuario, idJuego);

        if (ok) {
            resp.setStatus(201);
            resp.getWriter().write("{\"mensaje\":\"Compra realizada\"}");
        } else {
            resp.setStatus(400);
            resp.getWriter().write("{\"error\":\"No se pudo completar la compra\"}");
        }
    }
}
