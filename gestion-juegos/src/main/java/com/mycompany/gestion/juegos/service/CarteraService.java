package com.mycompany.gestion.juegos.service;
import com.mycompany.gestion.juegos.dao.CarteraDAO;
import com.mycompany.gestion.juegos.model.Cartera;

import java.math.BigDecimal;
/**
 *
 * @author ronald
 */
public class CarteraService {
    private final CarteraDAO dao = new CarteraDAO();

    public boolean crearCarteraSiNoExiste(int idUsuario) {
        return dao.obtenerPorUsuario(idUsuario) != null
                || dao.crearParaUsuario(idUsuario);
    }

    public boolean recargar(int idUsuario, BigDecimal monto) {
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }

        Cartera c = dao.obtenerPorUsuario(idUsuario);
        if (c == null) return false;

        BigDecimal nuevoSaldo = c.getSaldo().add(monto);
        return dao.actualizarSaldo(idUsuario, nuevoSaldo);
    }

    public boolean descontar(int idUsuario, BigDecimal monto) {
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }

        Cartera c = dao.obtenerPorUsuario(idUsuario);
        if (c == null) return false;

        if (c.getSaldo().compareTo(monto) < 0) {
            return false; // saldo insuficiente
        }

        BigDecimal nuevoSaldo = c.getSaldo().subtract(monto);
        return dao.actualizarSaldo(idUsuario, nuevoSaldo);
    }

    public Cartera obtenerCartera(int idUsuario) {
        return dao.obtenerPorUsuario(idUsuario);
    }
}
