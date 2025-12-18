package com.mycompany.gestion.juegos.model;
import java.math.BigDecimal;

/**
 *
 * @author ronald
 */
public class Cartera {
    private int idCartera;
    private BigDecimal saldo;
    private int idUsuario;

    public Cartera() {
    }

    public int getIdCartera() {
        return idCartera;
    }

    public void setIdCartera(int idCartera) {
        this.idCartera = idCartera;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
