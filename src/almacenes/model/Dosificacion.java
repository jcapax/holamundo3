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
public class Dosificacion {
    private int id;
    private String llaveDosificacion;
    private Date fecha;
    private int idSucursal;
    private String nombreSucursal;
    private String nroAutorizacion;
    private int nroInicioFactura;
    private Date fechaLimiteEmision;
    private String pieFactura;
    private int estado;

    public String getNombreSucursal() {
        return nombreSucursal;
    }

    public void setNombreSucursal(String nombreSucursal) {
        this.nombreSucursal = nombreSucursal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLlaveDosificacion() {
        return llaveDosificacion;
    }

    public void setLlaveDosificacion(String llaveDosificacion) {
        this.llaveDosificacion = llaveDosificacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getNroAutorizacion() {
        return nroAutorizacion;
    }

    public void setNroAutorizacion(String nroAutorizacion) {
        this.nroAutorizacion = nroAutorizacion;
    }

    public int getNroInicioFactura() {
        return nroInicioFactura;
    }

    public void setNroInicioFactura(int nroInicioFactura) {
        this.nroInicioFactura = nroInicioFactura;
    }

    public Date getFechaLimiteEmision() {
        return fechaLimiteEmision;
    }

    public void setFechaLimiteEmision(Date fechaLimiteEmision) {
        this.fechaLimiteEmision = fechaLimiteEmision;
    }

    public String getPieFactura() {
        return pieFactura;
    }

    public void setPieFactura(String pieFactura) {
        this.pieFactura = pieFactura;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}