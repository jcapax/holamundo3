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
    private String nombrePRoducto;
    private String nombreUnidadMedida;
    private double stock;

    public String getNombrePRoducto() {
        return nombrePRoducto;
    }

    public void setNombrePRoducto(String nombrePRoducto) {
        this.nombrePRoducto = nombrePRoducto;
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
