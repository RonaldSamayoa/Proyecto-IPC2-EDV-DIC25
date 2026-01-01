package com.mycompany.gestion.juegos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.gestion.juegos.service.UsuarioService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 *
 * @author ronald
 */
@WebServlet("/usuarios/buscar")
public class UsuarioBusquedaController extends HttpServlet {

    private UsuarioService usuarioService;
    private ObjectMapper mapper;

    @Override
    public void init() {
        usuarioService = new UsuarioService();
        mapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String q = req.getParameter("q");

        if (q == null || q.trim().isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        var resultado = usuarioService.buscarUsuariosPorNickname(q);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(HttpServletResponse.SC_OK);

        mapper.writeValue(resp.getOutputStream(), resultado);
    }
}

