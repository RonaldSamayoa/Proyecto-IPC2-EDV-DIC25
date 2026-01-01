package com.mycompany.gestion.juegos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.gestion.juegos.dto.UsuarioResponseDTO;
import com.mycompany.gestion.juegos.model.Usuario;
import com.mycompany.gestion.juegos.model.enums.RolUsuario;
import com.mycompany.gestion.juegos.service.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/usuarios/*")
public class UsuarioController extends HttpServlet{
    private UsuarioService usuarioService;
    private ObjectMapper mapper;

    @Override
    public void init() {
        usuarioService = new UsuarioService();
        mapper = new ObjectMapper();
    }

    /**
     * Maneja las peticiones POST relacionadas con usuarios
     * Se define la acción mediante el parámetro "accion"
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String accion = req.getParameter("accion");

        if ("registro".equalsIgnoreCase(accion)) {
            registrarUsuario(req, resp);
        } else if ("login".equalsIgnoreCase(accion)) {
            loginUsuario(req, resp);
        } else if ("crear-admin".equalsIgnoreCase(accion)) {
            crearAdmin(req, resp);
        }  else if ("crear-empresa".equalsIgnoreCase(accion)) {
            crearEmpresa(req, resp);
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Acción no válida.");
        }
    }
    
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String pathInfo = req.getPathInfo(); // /{id}
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        int idUsuario = Integer.parseInt(pathInfo.substring(1));

        Usuario cambios = mapper.readValue(req.getInputStream(), Usuario.class);

        boolean ok = usuarioService.actualizarPerfilParcial(idUsuario, cambios);

        if (!ok) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("No se pudo actualizar el perfil");
            return;
        }

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write("Perfil actualizado correctamente");
    }

    //Registra un nuevo usuario en el sistema
    private void registrarUsuario(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        Usuario usuario = mapper.readValue(req.getInputStream(), Usuario.class);
        boolean registrado = usuarioService.registrarUsuario(usuario);

        if (!registrado) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("No fue posible registrar el usuario.");
            return;
        }

        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().write("Usuario registrado correctamente.");
    }

    //Autentica a un usuario mediante correo y contraseña
    private void loginUsuario(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        Usuario datos = mapper.readValue(req.getInputStream(), Usuario.class);
        Usuario autenticado = usuarioService.autenticar(
                datos.getCorreo(),
                datos.getPassword()
        );

        if (autenticado == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("Credenciales inválidas.");
            return;
        }

        UsuarioResponseDTO response = convertirAResponse(autenticado);
        resp.setContentType("application/json");
        mapper.writeValue(resp.getOutputStream(), response);
    }

    private void crearAdmin(HttpServletRequest req, HttpServletResponse resp)
        throws IOException {

        Usuario adminNuevo = mapper.readValue(req.getInputStream(), Usuario.class);

        adminNuevo.setRol(RolUsuario.ADMIN);
        adminNuevo.setBibliotecaPublica(false);
        adminNuevo.setEstado(true);

        boolean ok = usuarioService.registrarAdmin(adminNuevo);

        if (!ok) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("No se pudo crear el administrador.");
            return;
        }

        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().write("Administrador creado correctamente.");
    }

    private void crearEmpresa(HttpServletRequest req, HttpServletResponse resp)
        throws IOException {
        Usuario empresa = mapper.readValue(req.getInputStream(), Usuario.class);

        empresa.setRol(RolUsuario.EMPRESA);
        empresa.setBibliotecaPublica(false);
        empresa.setEstado(true);

        boolean ok = usuarioService.registrarEmpresa(empresa);

        if (!ok) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("No se pudo crear el usuario empresa.");
            return;
        }

        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().write("Usuario empresa creado correctamente.");
    }

    //Convierte un objeto Usuario en un DTO seguro para la respuesta
    private UsuarioResponseDTO convertirAResponse(Usuario usuario) {

        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setNickname(usuario.getNickname());
        dto.setCorreo(usuario.getCorreo());
        dto.setFechaNacimiento(usuario.getFechaNacimiento());
        dto.setPais(usuario.getPais());
        dto.setRol(usuario.getRol());
        dto.setBibliotecaPublica(usuario.isBibliotecaPublica());

        return dto;
    }
}
