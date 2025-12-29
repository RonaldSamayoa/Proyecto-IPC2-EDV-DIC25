package com.mycompany.gestion.juegos.controller;

import com.google.gson.Gson;
import com.mycompany.gestion.juegos.service.ComentarioService;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

@WebServlet("/comentarios/*")
public class ComentarioController extends HttpServlet {

    private ComentarioService service;
    private Gson gson;

    @Override
    public void init() {
        service = new ComentarioService();
        gson = new Gson();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");

        try {
            Map<String, Object> body = gson.fromJson(req.getReader(), Map.class);

            int idUsuario = ((Number) body.get("idUsuario")).intValue();
            int idJuego = ((Number) body.get("idJuego")).intValue();
            String contenido = (String) body.get("contenido");

            Integer idComentarioPadre = body.get("idComentarioPadre") != null
                    ? ((Number) body.get("idComentarioPadre")).intValue()
                    : null;

            boolean ok = service.comentar(
                    idUsuario,
                    idJuego,
                    contenido,
                    idComentarioPadre
            );

            if (ok) {
                resp.setStatus(201);
                resp.getWriter().write("Comentario publicado");
            } else {
                resp.setStatus(400);
                resp.getWriter().write(
                        "No puede comentar este juego"
                );
            }

        } catch (Exception e) {
            resp.setStatus(400);
            resp.getWriter().write(
                    "Datos inválidos"
            );
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        int idJuego = Integer.parseInt(req.getParameter("idJuego"));

        resp.setContentType("application/json");
        resp.getWriter().write(
                gson.toJson(service.listarComentariosJuego(idJuego))
        );
    }
    
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");

        try {
            // /comentarios/{id}/ocultar  o  /comentarios/{id}/reactivar
            String path = req.getPathInfo();
            if (path == null || path.split("/").length < 3) {
                resp.setStatus(400);
                resp.getWriter().write("Ruta inválida");
                return;
            }

            String[] partes = path.split("/");
            int idComentario = Integer.parseInt(partes[1]);
            String accion = partes[2];

            Map<String, Object> body =
                    gson.fromJson(req.getReader(), Map.class);

            int idUsuario = ((Number) body.get("idUsuario")).intValue();

            boolean ok;

            switch (accion) {
                case "ocultar":
                    ok = service.ocultarComentarioConRespuestas(
                            idComentario, idUsuario
                    );
                    break;

                case "reactivar":
                    ok = service.reactivarComentario(
                            idComentario, idUsuario
                    );
                    break;

                default:
                    resp.setStatus(400);
                    resp.getWriter().write("Acción no válida");
                    return;
            }

            if (ok) {
                resp.getWriter().write("Operación exitosa");
            } else {
                resp.setStatus(403);
                resp.getWriter().write(
                        "No tiene permisos para realizar esta accion"
                );
            }

        } catch (Exception e) {
            resp.setStatus(400);
            resp.getWriter().write("Datos inválidos");
        }
    }

}

