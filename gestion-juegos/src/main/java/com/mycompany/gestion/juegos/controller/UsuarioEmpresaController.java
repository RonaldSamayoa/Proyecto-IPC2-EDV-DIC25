package com.mycompany.gestion.juegos.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.gestion.juegos.service.UsuarioEmpresaService;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 *
 * @author ronald
 */
@WebServlet("/usuario-empresa")
public class UsuarioEmpresaController extends HttpServlet {
    private UsuarioEmpresaService service;
    private ObjectMapper mapper;

    @Override
    public void init() {
        service = new UsuarioEmpresaService();
        mapper = new ObjectMapper();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        int idUsuario = Integer.parseInt(req.getParameter("idUsuario"));
        int idEmpresa = Integer.parseInt(req.getParameter("idEmpresa"));

        boolean ok = service.asignarUsuarioEmpresa(idUsuario, idEmpresa);

        resp.setStatus(ok ? 201 : 400);
        if (!ok) {
            resp.getWriter().write("Error al asignar usuario a empresa");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        
        String accion = req.getParameter("accion");
        
        if ("verificar".equals(accion)) {
            int idUsuario = Integer.parseInt(req.getParameter("idUsuario"));
            int idEmpresa = Integer.parseInt(req.getParameter("idEmpresa"));
            
            boolean pertenece = service.perteneceAEmpresa(idUsuario, idEmpresa);
            
            resp.setContentType("application/json");
            mapper.writeValue(resp.getWriter(), pertenece);
            
        } else if ("listar".equals(accion)) {
            int idEmpresa = Integer.parseInt(req.getParameter("idEmpresa"));
            
            resp.setContentType("application/json");
            mapper.writeValue(resp.getWriter(), service.listarUsuarios(idEmpresa));
        }
    }
}
