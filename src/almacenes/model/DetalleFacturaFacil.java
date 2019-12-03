/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package almacenes.model;

/**
 *
 * @author jcarlos.porcel
 */
public class DetalleFacturaFacil {
    
    private int id;
    private int idFacturaFacil;
    private String detalle;
    private Double cantidad;
    private Double valorUnitario;
    private Double valorTotal;

    public int getIdFacturaFacil() {
        return idFacturaFacil;
    }

    public void setIdFacturaFacil(int idFacturaFacil) {
        this.idFacturaFacil = idFacturaFacil;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }
    
    
    
}
