/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package almacenes.model;

import java.sql.Date;

/**
 *
 * @author juanito
 */
public class ListaTransaccion {
    private int id;
    private String descripcion;
    private Date fecha;
    private int nroTransaccion;
    private double valorTotal;
    private int idTipoTransaccion;
    private String detalle;
    private String usuario;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
    
    public int getIdTipoTransaccion() {
        return idTipoTransaccion;
    }

    public void setIdTipoTransaccion(int idTipoTransaccion) {
        this.idTipoTransaccion = idTipoTransaccion;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getNroTransaccion() {
        return nroTransaccion;
    }

    public void setNroTransaccion(int nroTransaccion) {
        this.nroTransaccion = nroTransaccion;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
    
    
}
