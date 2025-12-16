package com.mycompany.gestion.juegos.model;
import java.math.BigDecimal;
/**
 *
 * @author ronald
 */
public class Empresa {
    private int idEmpresa;
    private String nombre;
    private String descripcion;
    private byte[] logo;
    private BigDecimal porcentajeComisionEspecifico;
    private boolean estado;

    public Empresa() {
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public BigDecimal getPorcentajeComisionEspecifico() {
        return porcentajeComisionEspecifico;
    }

    public void setPorcentajeComisionEspecifico(BigDecimal porcentajeComisionEspecifico) {
        this.porcentajeComisionEspecifico = porcentajeComisionEspecifico;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
