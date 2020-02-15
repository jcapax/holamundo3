/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package almacenes.model;

/**
 *
 * @author juanito
 */
public class ListaIngresosEgresos {
    private int id;
    private String descripcionTipoTransaccion;
    private String fecha;
    private int idLugar;
    private Double valorTotal;
    private String usuario;
    private String descripcionIngresoEgreso;
    private String cuenta;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcionTipoTransaccion() {
        return descripcionTipoTransaccion;
    }

    public void setDescripcionTipoTransaccion(String descripcionTipoTransaccion) {
        this.descripcionTipoTransaccion = descripcionTipoTransaccion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getIdLugar() {
        return idLugar;
    }

    public void setIdLugar(int idLugar) {
        this.idLugar = idLugar;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getDescripcionIngresoEgreso() {
        return descripcionIngresoEgreso;
    }

    public void setDescripcionIngresoEgreso(String descripcionIngresoEgreso) {
        this.descripcionIngresoEgreso = descripcionIngresoEgreso;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }
}
