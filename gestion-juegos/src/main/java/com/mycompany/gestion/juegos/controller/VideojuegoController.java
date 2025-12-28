package com.mycompany.gestion.juegos.controller;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mycompany.gestion.juegos.dto.VideojuegoDetalleDTO;
import com.mycompany.gestion.juegos.model.Videojuego;
import com.mycompany.gestion.juegos.service.VideojuegoService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author ronald
 */
@WebServlet("/videojuegos/*")
public class VideojuegoController extends HttpServlet {
    private VideojuegoService service;
    private Gson gson;

    @Override
    public void init() {
        service = new VideojuegoService();

        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd") 
                .create();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        Videojuego v = gson.fromJson(req.getReader(), Videojuego.class);

        if (service.registrar(v)) {
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write("Videojuego registrado");
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Datos inválidos");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String path = req.getPathInfo(); // null, /detalle, etc.

        // detalle con categorias
        if ("/detalle".equals(path)) {
            int id = Integer.parseInt(req.getParameter("id"));
            VideojuegoDetalleDTO dto = service.obtenerDetalle(id);

            if (dto == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("No existe el videojuego");
                return;
            }

            resp.getWriter().write(gson.toJson(dto));
            return;
        }

        // videojuego por id
        if (req.getParameter("id") != null) {
            int id = Integer.parseInt(req.getParameter("id"));
            resp.getWriter().write(
                    gson.toJson(service.obtenerPorId(id))
            );
            return;
        }

        // buscar por titulo
        if (req.getParameter("titulo") != null) {
            resp.getWriter().write(
                    gson.toJson(service.buscarPorTitulo(req.getParameter("titulo")))
            );
            return;
        }

        // listado general de activos
        resp.getWriter().write(
                gson.toJson(service.listarActivos())
        );
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String path = req.getPathInfo(); 

        if ("/estado".equals(path)) {
            cambiarEstado(req, resp);
            return;
        }

        // actualización normal
        actualizarParcial(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        int id = Integer.parseInt(req.getParameter("id"));
        boolean activo = Boolean.parseBoolean(req.getParameter("activo"));

        if (service.cambiarEstado(id, activo)) {
            resp.getWriter().write("Estado actualizado");
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("No se pudo cambiar estado");
        }
    }
    
    private void cambiarEstado(HttpServletRequest req, HttpServletResponse resp)
        throws IOException {

        int id = Integer.parseInt(req.getParameter("id"));
        boolean activo = Boolean.parseBoolean(req.getParameter("activo"));

        boolean ok = service.cambiarEstado(id, activo);

        if (ok) {
            resp.getWriter().write("Estado actualizado");
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("No se pudo cambiar estado");
        }
    }

    private void actualizarParcial(HttpServletRequest req, HttpServletResponse resp)
        throws IOException {
        Videojuego v = gson.fromJson(req.getReader(), Videojuego.class);

        if (service.actualizarParcial(v)) {
            resp.getWriter().write("Videojuego actualizado");
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("No se pudo actualizar");
        }
    }

    private void obtenerDetalle(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        int id = Integer.parseInt(req.getParameter("id"));
        VideojuegoDetalleDTO dto = service.obtenerDetalle(id);

        if (dto == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("{\"error\":\"No encontrado\"}");
            return;
        }

        resp.getWriter().write(gson.toJson(dto));
    }
}
