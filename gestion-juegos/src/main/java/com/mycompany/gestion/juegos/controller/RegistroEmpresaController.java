package com.mycompany.gestion.juegos.controller;
import com.mycompany.gestion.juegos.model.Empresa;
import com.mycompany.gestion.juegos.model.Usuario;
import com.mycompany.gestion.juegos.service.RegistroEmpresaService;
import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map;
/**
 *
 * @author ronald
 */
@WebServlet("/registro-empresa")
public class RegistroEmpresaController extends HttpServlet {
    private RegistroEmpresaService registroService;
    private Gson gson;

    @Override
    public void init() {
        registroService = new RegistroEmpresaService();
        gson = new Gson();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            BufferedReader reader = request.getReader();
            Map<String, Object> body = gson.fromJson(reader, Map.class);

            Map<String, Object> empresaJson = (Map<String, Object>) body.get("empresa");
            Map<String, Object> usuarioJson = (Map<String, Object>) body.get("usuario");

            /* Construir empresa */
            Empresa empresa = new Empresa();
            empresa.setNombre((String) empresaJson.get("nombre"));
            empresa.setDescripcion((String) empresaJson.get("descripcion"));
            empresa.setLogo(null); // se podrá actualizar después
            empresa.setPorcentajeComisionEspecifico(null); //al registrar una empresa no define por su cuenta el % de comision
            empresa.setEstado(true);

            /* Construir usuario administrador */
            Usuario usuario = new Usuario();
            usuario.setNickname((String) usuarioJson.get("nickname"));
            usuario.setCorreo((String) usuarioJson.get("correo"));
            usuario.setPassword((String) usuarioJson.get("password"));
            
            String fechaStr = (String) usuarioJson.get("fechaNacimiento");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            
            usuario.setFechaNacimiento(sdf.parse(fechaStr));
            usuario.setPais((String) usuarioJson.get("pais"));
            usuario.setImagenPerfil(null);//se puede actualizar despues
            usuario.setBibliotecaPublica(true);

            boolean ok = registroService.registrarEmpresaConUsuario(empresa, usuario);

            if (ok) {
                response.setStatus(HttpServletResponse.SC_CREATED);
                response.getWriter().write(
                        "Empresa y usuario administrador registrados correctamente"
                );
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(
                        "No se pudo completar el registro"
                );
            }

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(
                    "Error interno del servidor"
            );
            e.printStackTrace();
        }
    }
}