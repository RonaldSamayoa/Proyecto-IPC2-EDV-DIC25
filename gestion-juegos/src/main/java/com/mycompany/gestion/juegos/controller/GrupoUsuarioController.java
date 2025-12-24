package com.mycompany.gestion.juegos.controller;

import com.google.gson.Gson;
import com.mycompany.gestion.juegos.service.GrupoUsuarioService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/grupo-usuario")
public class GrupoUsuarioController extends HttpServlet {

    private GrupoUsuarioService service;
    private Gson gson;
    
    @Override
    public void init() {
        service = new GrupoUsuarioService();
        gson = new Gson();
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
        
        if (ok) {
            resp.getWriter().write("{\"mensaje\":\"Usuario eliminado del grupo\"}");
        } else {
            resp.getWriter().write("{\"error\":\"No se pudo eliminar usuario del grupo\"}");
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");

        String tipo = req.getParameter("tipo");

        if ("usuarios".equals(tipo)) {
            int idGrupo = Integer.parseInt(req.getParameter("idGrupo"));
            List<Integer> usuarios = service.obtenerUsuariosDeGrupo(idGrupo);
            resp.getWriter().write(gson.toJson(usuarios));

        } else if ("grupos".equals(tipo)) {
            int idUsuario = Integer.parseInt(req.getParameter("idUsuario"));
            List<Integer> grupos = service.obtenerGruposDeUsuario(idUsuario);
            resp.getWriter().write(gson.toJson(grupos));

        } else {
            resp.setStatus(400);
            resp.getWriter().write("{\"error\":\"Parámetro tipo inválido\"}");
        }
    }
}
