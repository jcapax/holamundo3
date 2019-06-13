/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package almacenes.model;

import java.math.BigInteger;
import java.util.Date;

/**
 *
 * @author jcapax
 */
public class AnularTransaccion {
    private int idEntregaTransaccion;
    private int idTransaccion;
    private Date fecha;
    private byte idTipoTransaccion;  
    private String nombreTransaccion;
    private int nroTipoTransaccion;
    private byte idLugar; 
    private String nombreLugar; 
    private byte idTerminal;
    private String nombreTerminal;
    private String estado; 
    private String descripcionTransaccion; 
    private String usuario;
    private int nroFactura; 
    private String nit;
    private String razonSocial;
    private Double valorTotal;

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public int getIdEntregaTransaccion() {
        return idEntregaTransaccion;
    }

    public int getIdTransaccion() {
        return idTransaccion;
    }

    public Date getFecha() {
        return fecha;
    }

    public byte getIdTipoTransaccion() {
        return idTipoTransaccion;
    }

    public String getNombreTransaccion() {
        return nombreTransaccion;
    }

    public int getNroTipoTransaccion() {
        return nroTipoTransaccion;
    }

    public byte getIdLugar() {
        return idLugar;
    }

    public String getNombreLugar() {
        return nombreLugar;
    }

    public byte getIdTerminal() {
        return idTerminal;
    }

    public String getNombreTerminal() {
        return nombreTerminal;
    }

    public String getEstado() {
        return estado;
    }

    public String getDescripcionTransaccion() {
        return descripcionTransaccion;
    }

    public String getUsuario() {
        return usuario;
    }

    public int getNroFactura() {
        return nroFactura;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setIdEntregaTransaccion(int idEntregaTransaccion) {
        this.idEntregaTransaccion = idEntregaTransaccion;
    }

    public void setIdTransaccion(int idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setIdTipoTransaccion(byte idTipoTransaccion) {
        this.idTipoTransaccion = idTipoTransaccion;
    }

    public void setNombreTransaccion(String nombreTransaccion) {
        this.nombreTransaccion = nombreTransaccion;
    }

    public void setNroTipoTransaccion(int nroTipoTransaccion) {
        this.nroTipoTransaccion = nroTipoTransaccion;
    }

    public void setIdLugar(byte idLugar) {
        this.idLugar = idLugar;
    }

    public void setNombreLugar(String nombreLugar) {
        this.nombreLugar = nombreLugar;
    }

    public void setIdTerminal(byte idTerminal) {
        this.idTerminal = idTerminal;
    }

    public void setNombreTerminal(String nombreTerminal) {
        this.nombreTerminal = nombreTerminal;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setDescripcionTransaccion(String descripcionTransaccion) {
        this.descripcionTransaccion = descripcionTransaccion;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setNroFactura(int nro_factura) {
        this.nroFactura = nro_factura;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    
}
