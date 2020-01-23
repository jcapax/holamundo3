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
public class EntregaPendiente {
    private int idTransaccionCredito;
    private int idProducto;
    private String nombreCompleto;
    private String direccion;
    private String telefonos;
    private Date fecha;
    private int nroTipoTransaccion;
    private String nombreProducto;
    private int idUnidadMedida;
    private String nombreUnidadMedida;
    private double cantidadCredido;
    private double cantidadEntrega;
    private double diferencia;

    public int getIdTransaccionCredito() {
        return idTransaccionCredito;
    }

    public void setIdTransaccionCredito(int idTransaccionCredito) {
        this.idTransaccionCredito = idTransaccionCredito;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(String telefonos) {
        this.telefonos = telefonos;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getNroTipoTransaccion() {
        return nroTipoTransaccion;
    }

    public void setNroTipoTransaccion(int nroTipoTransaccion) {
        this.nroTipoTransaccion = nroTipoTransaccion;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public int getIdUnidadMedida() {
        return idUnidadMedida;
    }

    public void setIdUnidadMedida(int idUnidadMedida) {
        this.idUnidadMedida = idUnidadMedida;
    }

    public String getNombreUnidadMedida() {
        return nombreUnidadMedida;
    }

    public void setNombreUnidadMedida(String nombreUnidadMedida) {
        this.nombreUnidadMedida = nombreUnidadMedida;
    }

    public double getCantidadCredido() {
        return cantidadCredido;
    }

    public void setCantidadCredido(double cantidadCredido) {
        this.cantidadCredido = cantidadCredido;
    }

    public double getCantidadEntrega() {
        return cantidadEntrega;
    }

    public void setCantidadEntrega(double cantidadEntrega) {
        this.cantidadEntrega = cantidadEntrega;
    }

    public double getDiferencia() {
        return diferencia;
    }

    public void setDiferencia(double diferencia) {
        this.diferencia = diferencia;
    }
    
}
