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
public class Transaccion {
    private int id;
    private Date fecha;
    private int idTipoTransaccion;
    private int nroTipoTransaccion;
    private byte idLugar;
    private int idTerminal;
    private int tipoMovimiento;
    private String estado;
    private String usuario;
    private String descripcionTransaccion;

    public Transaccion(Date fecha, int idTipoTransaccion, int nroTipoTransaccion, byte idLugar, int idTerminal, int tipoMovimiento, String estado, String usuario, String descripcionTransaccion) {
        this.fecha = fecha;
        this.idTipoTransaccion = idTipoTransaccion;
        this.nroTipoTransaccion = nroTipoTransaccion;
        this.idLugar = idLugar;
        this.idTerminal = idTerminal;
        this.tipoMovimiento = tipoMovimiento;
        this.estado = estado;
        this.usuario = usuario;
        this.descripcionTransaccion = descripcionTransaccion;
    }

    public Transaccion() {
    }

    public String getDescripcionTransaccion() {
        return descripcionTransaccion;
    }

    public void setDescripcionTransaccion(String descripcionTransaccion) {
        this.descripcionTransaccion = descripcionTransaccion;
    }
    
    

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public byte getIdLugar() {
        return idLugar;
    }

    public void setIdLugar(byte idLugar) {
        this.idLugar = idLugar;
    }

    public int getIdTerminal() {
        return idTerminal;
    }

    public void setIdTerminal(int idTerminal) {
        this.idTerminal = idTerminal;
    }

    public int getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(int tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    
    
    
}
