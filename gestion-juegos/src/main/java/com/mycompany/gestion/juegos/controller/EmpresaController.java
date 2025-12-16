package com.mycompany.gestion.juegos.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.gestion.juegos.model.Empresa;
import com.mycompany.gestion.juegos.service.EmpresaService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
/**
 *
 * @author ronald
 */
@WebServlet("/empresas")
public class EmpresaController extends HttpServlet {

    private EmpresaService empresaService;
    private ObjectMapper mapper;

    @Override
    public void init() {
        empresaService = new EmpresaService();
        mapper = new ObjectMapper();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String accion = req.getParameter("accion");

        switch (accion) {
            case "registro" -> registrar(req, resp);
            case "actualizar" -> actualizar(req, resp);
            case "comision" -> actualizarComision(req, resp);
            case "inactivar" -> inactivar(req, resp);
            default -> {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Acción no válida.");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String accion = req.getParameter("accion");

        if ("buscar".equalsIgnoreCase(accion)) {
            buscar(req, resp);
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Acción no válida.");
        }
    }

    private void registrar(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        Empresa empresa = mapper.readValue(req.getInputStream(), Empresa.class);
        boolean ok = empresaService.registrarEmpresa(empresa);

        resp.setStatus(ok ? 201 : 400);
    }

    private void actualizar(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        Empresa empresa = mapper.readValue(req.getInputStream(), Empresa.class);
        boolean ok = empresaService.actualizarEmpresa(empresa);

        resp.setStatus(ok ? 200 : 400);
    }

    private void actualizarComision(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        int idEmpresa = Integer.parseInt(req.getParameter("idEmpresa"));
        BigDecimal porcentaje = new BigDecimal(req.getParameter("porcentaje"));

        boolean ok = empresaService.actualizarComision(idEmpresa, porcentaje);
        resp.setStatus(ok ? 200 : 400);
    }

    private void inactivar(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        int idEmpresa = Integer.parseInt(req.getParameter("idEmpresa"));
        boolean ok = empresaService.inactivarEmpresa(idEmpresa);

        resp.setStatus(ok ? 200 : 400);
    }

    private void buscar(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String nombre = req.getParameter("nombre");
        List<Empresa> empresas = empresaService.buscarPorNombre(nombre);

        resp.setContentType("application/json");
        mapper.writeValue(resp.getOutputStream(), empresas);
    }
}
