package com.mycompany.gestion.juegos.controller;
import com.google.gson.Gson;
import com.mycompany.gestion.juegos.service.DetalleCompraService;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 *
 * @author ronald
 */
@WebServlet("/detalle-compra")
public class DetalleCompraController extends HttpServlet {
    private DetalleCompraService service;
    private Gson gson;

    @Override
    public void init() {
        service = new DetalleCompraService();
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");

        int idCompra = Integer.parseInt(req.getParameter("idCompra"));
        resp.getWriter().write(
                gson.toJson(service.obtenerDetallesPorCompra(idCompra))
        );
    }
}
