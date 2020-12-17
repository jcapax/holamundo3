/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package almacenes.model;

import java.sql.Date;

/**
 *
 * @author jcarlos.porcel
 */
public class PendientePago {
    private int idTransaccion;
    private Date fecha;
    private int idTipoTransaccion;
    private int nroTipoTransaccion;
    private double importeCaja;
    private double valorTotal;
    private double diferencia;
    private String detalle;
    private String nombreCompleto;
    private String razonSocial;
    private String telefonos;

    public int getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(int idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getIdTipoTransaccion() {
        return idTipoTransaccion;
    }

    public void setIdTipoTransaccion(int idTipoTransaccion) {
        this.idTipoTransaccion = idTipoTransaccion;
    }

    public int getNroTipoTransaccion() {
        return nroTipoTransaccion;
    }

    public void setNroTipoTransaccion(int nroTipoTransaccion) {
        this.nroTipoTransaccion = nroTipoTransaccion;
    }

    public double getImporteCaja() {
        return importeCaja;
    }

    public void setImporteCaja(double importeCaja) {
        this.importeCaja = importeCaja;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public double getDiferencia() {
        return diferencia;
    }

    public void setDiferencia(double diferencia) {
        this.diferencia = diferencia;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(String telefonos) {
        this.telefonos = telefonos;
    }
    
    
    
}
