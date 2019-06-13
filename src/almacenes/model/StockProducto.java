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
public class StockProducto {
    private int idProducto;
    private byte idUnidadMedida;
    private String nombreProducto;
    private String nombreUnidadMedida;
    private double stock;

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public byte getIdUnidadMedida() {
        return idUnidadMedida;
    }

    public void setIdUnidadMedida(byte idUnidadMedida) {
        this.idUnidadMedida = idUnidadMedida;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombrePRoducto) {
        this.nombreProducto = nombrePRoducto;
    }

    public String getNombreUnidadMedida() {
        return nombreUnidadMedida;
    }

    public void setNombreUnidadMedida(String nombreUnidadMedida) {
        this.nombreUnidadMedida = nombreUnidadMedida;
    }

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }
    
    
    
}
