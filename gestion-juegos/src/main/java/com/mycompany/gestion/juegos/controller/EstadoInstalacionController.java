package com.mycompany.gestion.juegos.controller;
import com.google.gson.Gson;
import com.mycompany.gestion.juegos.service.InstalacionJuegoService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author ronald
 */
@WebServlet("/instalacion/estado")
public class EstadoInstalacionController extends HttpServlet  {
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
        int idJuego = Integer.parseInt(req.getParameter("idJuego"));

        boolean instalado = service.estaInstalado(idUsuario, idJuego);

        Map<String, Boolean> response = new HashMap<>();
        response.put("instalado", instalado);

        resp.getWriter().write(gson.toJson(response));
    }
}
