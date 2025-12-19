package com.mycompany.gestion.juegos.controller;

import com.mycompany.gestion.juegos.service.GrupoUsuarioService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/grupo-usuario")
public class GrupoUsuarioController extends HttpServlet {

    private GrupoUsuarioService service;

    @Override
    public void init() {
        service = new GrupoUsuarioService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        int idGrupo = Integer.parseInt(req.getParameter("idGrupo"));
        int idUsuario = Integer.parseInt(req.getParameter("idUsuario"));

        boolean ok = service.agregarUsuarioAGrupo(idGrupo, idUsuario);

        resp.setContentType("application/json");
        resp.setStatus(ok ? 201 : 400);

        if (ok) {
            resp.getWriter().write("{\"mensaje\":\"Usuario agregado al grupo\"}");
        } else {
            resp.getWriter().write("{\"error\":\"No se pudo agregar (límite o duplicado)\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        int idGrupo = Integer.parseInt(req.getParameter("idGrupo"));
        int idUsuario = Integer.parseInt(req.getParameter("idUsuario"));

        boolean ok = service.quitarUsuarioDeGrupo(idGrupo, idUsuario);

        resp.setStatus(ok ? 200 : 400);
    }
}
