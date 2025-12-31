package com.mycompany.gestion.juegos.controller;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.google.gson.Gson;
import com.mycompany.gestion.juegos.service.BibliotecaService;
/**
 *
 * @author ronald
 */
@WebServlet("/biblioteca/grupo")
public class BibliotecaGrupoController extends HttpServlet {

    private BibliotecaService service;
    private Gson gson;

    @Override
    public void init() {
        service = new BibliotecaService();
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        int idGrupo = Integer.parseInt(req.getParameter("idGrupo"));
        int idUsuario = Integer.parseInt(req.getParameter("idUsuario"));

        resp.getWriter().write(
            gson.toJson(
                service.obtenerBibliotecaCompartidaPorGrupo(idGrupo, idUsuario)
            )
        );
    }
}
