package com.mycompany.gestion.juegos.service;
import com.mycompany.gestion.juegos.dao.ConfiguracionComisionDAO;
import com.mycompany.gestion.juegos.dao.EmpresaDAO;
import com.mycompany.gestion.juegos.dao.UsuarioDAO;
import com.mycompany.gestion.juegos.model.ConfiguracionComision;
import com.mycompany.gestion.juegos.model.Empresa;
import com.mycompany.gestion.juegos.model.Usuario;
import com.mycompany.gestion.juegos.model.enums.RolUsuario;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
/**
 *
 * @author ronald
 */
public class ConfiguracionComisionService {
    private final ConfiguracionComisionDAO dao = new ConfiguracionComisionDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    // Solo ADMIN del sistema puede cambiar la comisión global
    public boolean registrarComisionGlobal(int idUsuarioAdmin, BigDecimal porcentaje) {

        if (porcentaje == null || porcentaje.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }

        Usuario u = usuarioDAO.buscarPorId(idUsuarioAdmin);
        if (u == null || u.getRol() != RolUsuario.ADMIN) {
            return false;
        }

        ConfiguracionComision config = new ConfiguracionComision();
        config.setPorcentajeGlobal(porcentaje);

        return dao.insertar(config);
    }

    public ConfiguracionComision obtenerComisionActual() {
        return dao.obtenerActual();
    }
    
    public boolean cambiarComisionGlobalConRecalculo(int idUsuarioAdmin, BigDecimal nuevaGlobal) {
    if (nuevaGlobal == null || nuevaGlobal.compareTo(BigDecimal.ZERO) <= 0) {
        return false;
    }

    Usuario u = usuarioDAO.buscarPorId(idUsuarioAdmin);
    if (u == null || u.getRol() != RolUsuario.ADMIN) {
        return false;
    }

    //Obtener comisión global actual
    ConfiguracionComision actual = dao.obtenerActual();
    if (actual == null) {
        return false;
    }

    BigDecimal globalAnterior = actual.getPorcentajeGlobal();

    //Obtener empresas con comisión específica
    EmpresaDAO empresaDAO = new EmpresaDAO();
    List<Empresa> empresas = empresaDAO.listarConComisionEspecifica();

    //Recalcular proporcionalmente
    for (Empresa e : empresas) {
        BigDecimal proporcion =
                e.getPorcentajeComisionEspecifico()
                 .divide(globalAnterior, 10, RoundingMode.HALF_UP);

        BigDecimal nuevaEspecifica =
                nuevaGlobal.multiply(proporcion).setScale(2, RoundingMode.HALF_UP);

        empresaDAO.actualizarComision(
                e.getIdEmpresa(),
                nuevaEspecifica
        );
    }

    //Guardar nueva comisión global (histórico)
    ConfiguracionComision nueva = new ConfiguracionComision();
    nueva.setPorcentajeGlobal(nuevaGlobal);

    return dao.insertar(nueva);
}

    
}
