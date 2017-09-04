/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package almacenes.model;

import java.sql.Date;

/**
 *
 * @author jcapax
 */
public class AnularTransaccion {
    private int idEntregaTransaccion;
    private int idTransaccion;
    private Date fecha;
    private byte idTipoTransaccion;
    private double valorTotal;
    private String nit;
    private String razonSocial;
    private int nroFactura;
    private double importeTotalFactura;

    public int getIdEntregaTransaccion() {
        return idEntregaTransaccion;
    }

    public void setIdEntregaTransaccion(int idEntregaTransaccion) {
        this.idEntregaTransaccion = idEntregaTransaccion;
    }

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

    public byte getIdTipoTransaccion() {
        return idTipoTransaccion;
    }

    public void setIdTipoTransaccion(byte idTipoTransaccion) {
        this.idTipoTransaccion = idTipoTransaccion;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public int getNroFactura() {
        return nroFactura;
    }

    public void setNroFactura(int nroFactura) {
        this.nroFactura = nroFactura;
    }

    public double getImporteTotalFactura() {
        return importeTotalFactura;
    }

    public void setImporteTotalFactura(double importeTotalFactura) {
        this.importeTotalFactura = importeTotalFactura;
    }
    
    
    
    
}
