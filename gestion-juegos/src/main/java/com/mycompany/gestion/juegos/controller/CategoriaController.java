package com.mycompany.gestion.juegos.controller;
import com.google.gson.Gson;
import com.mycompany.gestion.juegos.service.CategoriaService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
/**
 *
 * @author ronald
 */
@WebServlet("/categorias/*")
public class CategoriaController extends HttpServlet {
    private CategoriaService categoriaService;
    private Gson gson;

    @Override
    public void init() {
        categoriaService = new CategoriaService();
        gson = new Gson();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Map<String, String> body = gson.fromJson(request.getReader(), Map.class);
        boolean ok = categoriaService.crearCategoria(body.get("nombre"));

        response.setContentType("application/json");
        response.getWriter().write(ok
                ? "Categoría creada"
                : "No se pudo crear la categoria");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");
        response.getWriter().write(
                gson.toJson(categoriaService.listarCategoriasActivas())
        );
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String pathInfo = request.getPathInfo(); 

        // activar o reactivar una categoria (por parametro) /categorias/activar 
        if ("/activar".equals(pathInfo)) {
            activarCategoria(request, response);
            return;
        }

        // actualizar nombre /categorias
        Map<String, Object> body = gson.fromJson(request.getReader(), Map.class);
        int id = ((Number) body.get("idCategoria")).intValue();
        String nombre = (String) body.get("nombre");

        boolean ok = categoriaService.actualizarNombre(id, nombre);

        response.setContentType("application/json");
        response.getWriter().write(ok
                ? "{\"mensaje\":\"Categoría actualizada\"}"
                : "{\"error\":\"No se pudo actualizar\"}");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        boolean ok = categoriaService.inactivarCategoria(id);

        response.setContentType("application/json");
        response.getWriter().write(ok
                ? "Categoría inactivada"
                : "No se pudo inactivar");
    }
    
    private void activarCategoria(HttpServletRequest request, HttpServletResponse response)
        throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        boolean ok = categoriaService.activarCategoria(id);

        response.setContentType("application/json");
        response.getWriter().write(ok
                ? "{\"mensaje\":\"Categoría activada\"}"
                : "{\"error\":\"No se pudo activar\"}");
    }

}
