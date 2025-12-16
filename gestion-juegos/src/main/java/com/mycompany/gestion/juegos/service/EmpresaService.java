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

    public boolean actualizarEmpresa(Empresa empresa) {

        if (empresa.getIdEmpresa() <= 0) {
            return false;
        }

        return empresaDAO.actualizar(empresa);
    }

    public boolean actualizarComision(int idEmpresa, BigDecimal porcentaje) {

        if (porcentaje == null || porcentaje.compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }

        return empresaDAO.actualizarComision(idEmpresa, porcentaje);
    }

    public boolean inactivarEmpresa(int idEmpresa) {
        return empresaDAO.inactivar(idEmpresa);
    }
}
