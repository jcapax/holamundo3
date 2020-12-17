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
public class ListaCaja {
    private int idTransaccion;
    private Date fecha;
    private double importeCaja;
    private String estado;
    private String movimiento;
    private String descripcionTransaccion;
    private int idArqueo;

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

    public double getImporteCaja() {
        return importeCaja;
    }

    public void setImporteCaja(double importeCaja) {
        this.importeCaja = importeCaja;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(String movimiento) {
        this.movimiento = movimiento;
    }

    public String getDescripcionTransaccion() {
        return descripcionTransaccion;
    }

    public void setDescripcionTransaccion(String descripcionTransaccion) {
        this.descripcionTransaccion = descripcionTransaccion;
    }

    public int getIdArqueo() {
        return idArqueo;
    }

    public void setIdArqueo(int idArqueo) {
        this.idArqueo = idArqueo;
    }
    
    
    
}
