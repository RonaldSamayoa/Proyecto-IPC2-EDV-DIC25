package com.mycompany.gestion.juegos.service;
import com.mycompany.gestion.juegos.dao.CategoriaDAO;
import com.mycompany.gestion.juegos.model.Categoria;

import java.util.List;
/**
 *
 * @author ronald
 */
public class CategoriaService {
    private final CategoriaDAO categoriaDAO;

    public CategoriaService() {
        this.categoriaDAO = new CategoriaDAO();
    }

    public boolean crearCategoria(String nombre) {

        Categoria categoria = new Categoria();
        categoria.setNombre(nombre);
        return categoriaDAO.insertar(categoria);
    }

    public List<Categoria> listarCategoriasActivas() {
        return categoriaDAO.listarActivas();
    }

    public boolean actualizarNombre(int idCategoria, String nuevoNombre) {
        return categoriaDAO.actualizarNombre(idCategoria, nuevoNombre);
    }

    public boolean inactivarCategoria(int idCategoria) {
        return categoriaDAO.inactivar(idCategoria);
    }

    public boolean activarCategoria(int idCategoria) {
        return categoriaDAO.activar(idCategoria);
    }
}
