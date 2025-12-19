package com.mycompany.gestion.juegos.model;
import java.math.BigDecimal;
import java.util.Date;
/**
 *
 * @author ronald
 */
public class ConfiguracionComision {
    private int idConfig;
    private BigDecimal porcentajeGlobal;
    private Date fechaCambio;

    public int getIdConfig() {
        return idConfig;
    }

    public void setIdConfig(int idConfig) {
        this.idConfig = idConfig;
    }

    public BigDecimal getPorcentajeGlobal() {
        return porcentajeGlobal;
    }

    public void setPorcentajeGlobal(BigDecimal porcentajeGlobal) {
        this.porcentajeGlobal = porcentajeGlobal;
    }

    public Date getFechaCambio() {
        return fechaCambio;
    }

    public void setFechaCambio(Date fechaCambio) {
        this.fechaCambio = fechaCambio;
    }
}
