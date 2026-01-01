package com.mycompany.gestion.juegos.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.gestion.juegos.dto.UsuarioResponseDTO;
import com.mycompany.gestion.juegos.service.UsuarioService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 *
 * @author ronald
 */
@WebServlet("/usuarios/perfil")
public class PerfilUsuarioController extends HttpServlet {

    private UsuarioService service;
    private ObjectMapper mapper;

    @Override
    public void init() {
        service = new UsuarioService();
        mapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        int idUsuario = Integer.parseInt(req.getParameter("idUsuario"));

        UsuarioResponseDTO dto = service.obtenerPerfil(idUsuario);

        if (dto == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        mapper.writeValue(resp.getOutputStream(), dto);
    }
}

