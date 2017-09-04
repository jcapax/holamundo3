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
public class ConfiguracionGeneral {
    private String rutaExcel;
    private int tiempoAnulacionTransaccion;

    public String getRutaExcel() {
        return rutaExcel;
    }

    public void setRutaExcel(String rutaExcel) {
        this.rutaExcel = rutaExcel;
    }

    public int getTiempoAnulacionTransaccion() {
        return tiempoAnulacionTransaccion;
    }

    public void setTiempoAnulacionTransaccion(int tiempoAnulacionTransaccion) {
        this.tiempoAnulacionTransaccion = tiempoAnulacionTransaccion;
    }
    
    
}
