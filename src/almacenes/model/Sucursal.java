/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package almacenes.model;

/**
 *
 * @author jcapax
 */
public class Sucursal {
    private int id;
    private String nitSucursal;
    private String nombreSucursal;
    private String direccion;
    private String tipoProductos;
    private byte idLugar;
    private String nombreLugar;
    private String actividadEconomica;
    private byte estado;
    private String actividadSucursal;

    public String getNombreLugar() {
        return nombreLugar;
    }

    public void setNombreLugar(String nombreLugar) {
        this.nombreLugar = nombreLugar;
    }

    public byte getIdLugar() {
        return idLugar;
    }

    public void setIdLugar(byte idLugar) {
        this.idLugar = idLugar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNitSucursal() {
        return nitSucursal;
    }

    public void setNitSucursal(String nitSucursal) {
        this.nitSucursal = nitSucursal;
    }

    public String getNombreSucursal() {
        return nombreSucursal;
    }

    public void setNombreSucursal(String nombreSucursal) {
        this.nombreSucursal = nombreSucursal;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTipoProductos() {
        return tipoProductos;
    }

    public void setTipoProductos(String tipoProductos) {
        this.tipoProductos = tipoProductos;
    }

    public String getActividadEconomica() {
        return actividadEconomica;
    }

    public void setActividadEconomica(String actividadEconomica) {
        this.actividadEconomica = actividadEconomica;
    }

    public byte getEstado() {
        return estado;
    }

    public void setEstado(byte estado) {
        this.estado = estado;
    }

    public String getActividadSucursal() {
        return actividadSucursal;
    }

    public void setActividadSucursal(String actividadSucursal) {
        this.actividadSucursal = actividadSucursal;
    }
    
    
    
    
}
