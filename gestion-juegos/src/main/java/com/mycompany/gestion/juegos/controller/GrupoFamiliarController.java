package com.mycompany.gestion.juegos.controller;
import com.google.gson.Gson;
import com.mycompany.gestion.juegos.service.GrupoFamiliarService;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Map;
/**
 *
 * @author ronald
 */
@WebServlet("/grupo-familiar")
public class GrupoFamiliarController extends HttpServlet {
    private GrupoFamiliarService service;
    private Gson gson;

    @Override
    public void init() {
        service = new GrupoFamiliarService();
        gson = new Gson();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        Map<String, Object> body = gson.fromJson(req.getReader(), Map.class);

        String nombre = (String) body.get("nombre");
        int idCreador = ((Number) body.get("idCreador")).intValue();

        boolean ok = service.crearGrupo(nombre, idCreador);

        resp.setStatus(ok ? 201 : 400);
    }
}
