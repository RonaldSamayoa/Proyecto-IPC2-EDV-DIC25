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
/**
 *
 * @author ronald
 */
@WebServlet("/empresas/*")
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
            throws IOException {

        Empresa empresa = mapper.readValue(req.getInputStream(), Empresa.class);

        boolean ok = empresaService.registrarEmpresa(empresa);

        resp.setStatus(ok ? 201 : 400);
        
        if (ok) {
            resp.getWriter().write("Empresa creada con exito");
        } else {
            resp.getWriter().write("No se pudo crear la empresa");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String nombre = req.getParameter("nombre");

        resp.setContentType("application/json");
        mapper.writeValue(
            resp.getOutputStream(),
            empresaService.buscarPorNombre(nombre)
        );
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String path = req.getPathInfo();

        if ("/activar".equals(path)) {
            activar(req, resp);
            return;
        }

        Empresa empresa = mapper.readValue(req.getInputStream(), Empresa.class);
        boolean ok = empresaService.actualizarEmpresa(empresa);

        resp.setStatus(ok ? 200 : 400);
        
        if (ok) {
            resp.getWriter().write("Actualizacion exitosa");
        } else {
            resp.getWriter().write("No se pudo actualizar");
        }
    }
    
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        int id = Integer.parseInt(req.getParameter("id"));
        boolean ok = empresaService.inactivarEmpresa(id);

        resp.setStatus(ok ? 200 : 400);
        
        if (ok) {
            resp.getWriter().write("Empresa inactiva");
        } else {
            resp.getWriter().write("No se pudo realizar la inactivacion");
        }
    }

    private void activar(HttpServletRequest req, HttpServletResponse resp)
        throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        boolean ok = empresaService.activarEmpresa(id);

        resp.setStatus(ok ? 200 : 400);
    }
}
