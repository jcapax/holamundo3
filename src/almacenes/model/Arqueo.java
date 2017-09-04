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
public class Arqueo {
    private int id;
    private Date fechaApertura;
    private Date fechaCierre;
    private Double cajaInicial;
    private Double importeCierre;
    private String estado;
    private byte idLugar;
    private byte idTerminal;
    private String usuario;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    
    public byte getIdLugar() {
        return idLugar;
    }

    public void setIdLugar(byte idLugar) {
        this.idLugar = idLugar;
    }

    public byte getIdTerminal() {
        return idTerminal;
    }

    public void setIdTerminal(byte idTerminal) {
        this.idTerminal = idTerminal;
    }

    public Date getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(Date fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public Double getCajaInicial() {
        return cajaInicial;
    }

    public void setCajaInicial(Double cajaInicial) {
        this.cajaInicial = cajaInicial;
    }

    public Double getImporteCierre() {
        return importeCierre;
    }

    public void setImporteCierre(Double importeCierre) {
        this.importeCierre = importeCierre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    
            
}
