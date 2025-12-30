package com.mycompany.gestion.juegos.controller;
import com.google.gson.Gson;
import com.mycompany.gestion.juegos.service.GrupoFamiliarService;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Map;
/**
 *
 * @author ronald
 */
@WebServlet("/grupo-familiar")
public class GrupoFamiliarController extends HttpServlet {
    private GrupoFamiliarService service;
    private Gson gson;

    @Override
    public void init() {
        service = new GrupoFamiliarService();
        gson = new Gson();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");
        Map<String, Object> body = gson.fromJson(req.getReader(), Map.class);

        String nombre = (String) body.get("nombre");
        int idCreador = ((Number) body.get("idCreador")).intValue();

        boolean ok = service.crearGrupo(nombre, idCreador);

        resp.setStatus(ok ? HttpServletResponse.SC_CREATED
                      : HttpServletResponse.SC_BAD_REQUEST);
        
        if (ok) {
            resp.getWriter().write("Grupo familiar creado con exito");
        } else {
            resp.getWriter().write("No se pudo crear el grupo familiar");
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        int idUsuario = Integer.parseInt(req.getParameter("idUsuario"));

        resp.setContentType("application/json");
        resp.getWriter().write(
            gson.toJson(service.listarGruposDelUsuario(idUsuario))
        );
    }
   
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");
        int idGrupo = Integer.parseInt(req.getParameter("idGrupo"));
        int idUsuario = Integer.parseInt(req.getParameter("idUsuario"));

        boolean ok = service.eliminarGrupo(idGrupo, idUsuario);
        resp.setStatus(ok ? HttpServletResponse.SC_OK
                      : HttpServletResponse.SC_FORBIDDEN);
        
        if (ok) {
            resp.getWriter().write("Grupo familiar eliminado con exito");
        } else {
            resp.getWriter().write("No se pudo eliminar grupo familiar");
        }
    }
}
