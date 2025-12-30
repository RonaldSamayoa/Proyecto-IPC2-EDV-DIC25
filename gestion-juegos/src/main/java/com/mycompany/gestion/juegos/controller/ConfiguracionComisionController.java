package com.mycompany.gestion.juegos.controller;
import com.google.gson.Gson;
import com.mycompany.gestion.juegos.service.ConfiguracionComisionService;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
/**
 *
 * @author ronald
 */
@WebServlet("/configuracion-comision")
public class ConfiguracionComisionController extends HttpServlet {
    private ConfiguracionComisionService service;
    private Gson gson;

    @Override
    public void init() {
        service = new ConfiguracionComisionService();
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");
        resp.getWriter().write(gson.toJson(service.obtenerComisionActual()));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");

        Map<String, Object> body = gson.fromJson(req.getReader(), Map.class);

        int idUsuario = ((Number) body.get("idUsuario")).intValue();
        BigDecimal porcentaje = new BigDecimal(body.get("porcentaje").toString());

        boolean ok = service.registrarComisionGlobal(idUsuario, porcentaje);

        if (ok) {
            resp.getWriter().write("Comisión global actualizada");
        } else {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            resp.getWriter().write("No autorizado o datos inválidos");
        }
    }
    
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");

        Map<String, Object> body = gson.fromJson(req.getReader(), Map.class);

        int idUsuario = ((Number) body.get("idUsuario")).intValue();
        BigDecimal porcentaje = new BigDecimal(body.get("porcentaje").toString());

        boolean ok = service.cambiarComisionGlobalConRecalculo(idUsuario, porcentaje);

        if (ok) {
            resp.getWriter().write("Comisión global y específicas actualizadas"
            );
        } else {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            resp.getWriter().write("No autorizado o datos inválidos"
            );
        }
    }
}
