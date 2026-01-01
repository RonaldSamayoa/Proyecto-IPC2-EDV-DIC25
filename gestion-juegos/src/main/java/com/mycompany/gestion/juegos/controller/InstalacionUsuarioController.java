package com.mycompany.gestion.juegos.controller;
import com.google.gson.Gson;
import com.mycompany.gestion.juegos.service.InstalacionJuegoService;
import jakarta.servlet.http.*;
import java.io.IOException;
import jakarta.servlet.annotation.WebServlet;

/**
 *
 * @author ronald
 */
@WebServlet("/instalacion/usuario")
public class InstalacionUsuarioController extends HttpServlet {

    private InstalacionJuegoService service;
    private Gson gson;

    @Override
    public void init() {
        service = new InstalacionJuegoService();
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        int idUsuario = Integer.parseInt(req.getParameter("idUsuario"));

        resp.getWriter().write(
            gson.toJson(service.listarInstaladosPorUsuario(idUsuario))
        );
    }
}