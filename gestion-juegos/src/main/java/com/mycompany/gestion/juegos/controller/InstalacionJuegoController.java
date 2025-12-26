package com.mycompany.gestion.juegos.controller;
import com.google.gson.Gson;
import com.mycompany.gestion.juegos.service.InstalacionJuegoService;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Map;

@WebServlet("/instalacion")
public class InstalacionJuegoController extends HttpServlet {
    private InstalacionJuegoService service;
    private Gson gson;

    @Override
    public void init() {
        service = new InstalacionJuegoService();
        gson = new Gson();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        Map<String, Object> body = gson.fromJson(req.getReader(), Map.class);

        int idUsuario = ((Number) body.get("idUsuario")).intValue();
        int idJuego = ((Number) body.get("idJuego")).intValue();

        Boolean esPrestadoObj = (Boolean) body.get("esPrestado");
        boolean esPrestado = esPrestadoObj != null && esPrestadoObj;

        Integer idDueno = null;
        if (esPrestado && body.get("idDueno") != null) {
            idDueno = ((Number) body.get("idDueno")).intValue();
        }

        boolean ok = service.instalarJuego(idUsuario, idJuego, esPrestado, idDueno);

        resp.setStatus(ok ? 201 : 400);
        resp.getWriter().write(ok
                ? "Juego instalado correctamente"
                : "No se pudo instalar el juego");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        int idUsuario = Integer.parseInt(req.getParameter("idUsuario"));
        int idJuego = Integer.parseInt(req.getParameter("idJuego"));

        boolean ok = service.desinstalarJuego(idUsuario, idJuego);

        resp.setStatus(ok ? 200 : 400);
        resp.getWriter().write(ok
                ? "Juego desinstalado"
                : "No se pudo desinstalar");
    }
}
