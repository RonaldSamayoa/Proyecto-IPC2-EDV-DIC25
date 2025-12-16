package com.mycompany.gestion.juegos.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.gestion.juegos.model.Empresa;
import com.mycompany.gestion.juegos.service.EmpresaService;
import com.mycompany.gestion.juegos.service.UsuarioEmpresaService;

import jakarta.servlet.ServletException;
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

        String accion = req.getParameter("accion");

        int idUsuario = Integer.parseInt(req.getParameter("idUsuario"));
        int idEmpresa = Integer.parseInt(req.getParameter("idEmpresa"));

        boolean ok = false;

        if ("admin".equalsIgnoreCase(accion)) {
            ok = service.asignarAdminEmpresa(idUsuario, idEmpresa);
        } else if ("empleado".equalsIgnoreCase(accion)) {
            ok = service.asignarEmpleado(idUsuario, idEmpresa);
        }

        resp.setStatus(ok ? 201 : 400);
    }
}
