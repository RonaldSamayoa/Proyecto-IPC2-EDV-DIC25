package com.mycompany.gestion.juegos.service;
import com.mycompany.gestion.juegos.dao.ConfiguracionComisionDAO;
import com.mycompany.gestion.juegos.dao.UsuarioDAO;
import com.mycompany.gestion.juegos.model.ConfiguracionComision;
import com.mycompany.gestion.juegos.model.Usuario;
import com.mycompany.gestion.juegos.model.enums.RolUsuario;

import java.math.BigDecimal;
/**
 *
 * @author ronald
 */
public class ConfiguracionComisionService {
    private final ConfiguracionComisionDAO dao = new ConfiguracionComisionDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    // Solo ADMIN del sistema puede cambiar la comisión global
    public boolean actualizarComisionGlobal(int idUsuarioAdmin, BigDecimal porcentaje) {

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
}
