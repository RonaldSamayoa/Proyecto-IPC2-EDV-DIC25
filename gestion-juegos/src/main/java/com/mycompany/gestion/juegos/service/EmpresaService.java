package com.mycompany.gestion.juegos.service;
import com.mycompany.gestion.juegos.dao.EmpresaDAO;
import com.mycompany.gestion.juegos.model.Empresa;

import java.math.BigDecimal;
import java.util.List;
/**
 *
 * @author ronald
 */
public class EmpresaService {
    private EmpresaDAO empresaDAO;

    public EmpresaService() {
        empresaDAO = new EmpresaDAO();
    }

    public boolean registrarEmpresa(Empresa empresa) {
        if (empresa.getNombre() == null || empresa.getNombre().isBlank()) {
            return false;
        }

        if (empresaDAO.existePorNombre(empresa.getNombre())) {
            return false; // nombre duplicado
        }

        return empresaDAO.insertar(empresa);
    }

    public Empresa obtenerPorId(int idEmpresa) {
        return empresaDAO.buscarPorId(idEmpresa);
    }

    public List<Empresa> buscarPorNombre(String nombre) {

        if (nombre == null || nombre.isBlank()) {
            return List.of();
        }

        return empresaDAO.buscarPorNombre(nombre);
    }

    public boolean actualizarEmpresa(Empresa nueva) {
        Empresa actual = empresaDAO.buscarPorId(nueva.getIdEmpresa());

        if (actual == null) {
            return false;
        }

        if (nueva.getDescripcion() != null) {
            actual.setDescripcion(nueva.getDescripcion());
        }

        if (nueva.getLogo() != null) {
            actual.setLogo(nueva.getLogo());
        }

        if (nueva.getPorcentajeComisionEspecifico() != null) {
            actual.setPorcentajeComisionEspecifico(
                nueva.getPorcentajeComisionEspecifico()
            );
        }

        return empresaDAO.actualizar(actual);
    }

    public boolean actualizarComision(int idEmpresa, BigDecimal porcentaje) {

        if (porcentaje == null || porcentaje.compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }

        return empresaDAO.actualizarComision(idEmpresa, porcentaje);
    }

    public boolean activarEmpresa(int idEmpresa) {
        return empresaDAO.activar(idEmpresa);
    }

    public boolean inactivarEmpresa(int idEmpresa) {
        return empresaDAO.inactivar(idEmpresa);
    }
}
