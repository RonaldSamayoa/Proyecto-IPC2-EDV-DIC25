package com.mycompany.gestion.juegos.controller;
import com.google.gson.Gson;
import com.mycompany.gestion.juegos.model.Cartera;
import com.mycompany.gestion.juegos.service.CarteraService;
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
@WebServlet("/cartera")
public class CarteraController extends HttpServlet {
    private CarteraService service;
    private Gson gson;

    @Override
    public void init() {
        service = new CarteraService();
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");

        int idUsuario = Integer.parseInt(req.getParameter("idUsuario"));

        //crear cartera en caso de no existir
        service.crearCarteraSiNoExiste(idUsuario);

        Cartera c = service.obtenerCartera(idUsuario);
        resp.getWriter().write(gson.toJson(c));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");

        Map<String, Object> body = gson.fromJson(req.getReader(), Map.class);
        int idUsuario = ((Number) body.get("idUsuario")).intValue();
        BigDecimal monto = new BigDecimal(body.get("monto").toString());
        String tipo = (String) body.get("tipo");

        boolean ok;

        if ("RECARGA".equalsIgnoreCase(tipo)) {
            ok = service.recargar(idUsuario, monto);
        } else if ("DESCONTO".equalsIgnoreCase(tipo)) {
            ok = service.descontar(idUsuario, monto);
        } else {
            ok = false;
        }

        if (ok) {
            resp.getWriter().write("{\"mensaje\":\"Operacion realizada\"}");
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Operacion invalida\"}");
        }
    }
}
