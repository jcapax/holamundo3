/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package almacenes.model;

/**
 *
 * @author georgeguitar
 */
public class Configuracion {
  private Integer idConfiguracion;
  private String rutaVisorPdf;
  private String rutaDestinoArchivosPDF;
  private Boolean soloGuadarArchivosPDF;
  private String rutaProgramasPG;
  private String rutaExcel;
  private int tiempoAnulacionTransaccion;
  private int impresionDirectaFactura;

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

    public int getImpresionDirectaFactura() {
        return impresionDirectaFactura;
    }

    public void setImpresionDirectaFactura(int impresionDirectaFactura) {
        this.impresionDirectaFactura = impresionDirectaFactura;
    }
  
  
  
    public Integer getIdConfiguracion() {
        return idConfiguracion;
    }
    public void setIdConfiguracion(Integer idConfiguracion) {
        this.idConfiguracion = idConfiguracion;
    }

    public String getRutaVisorPdf() {
        return rutaVisorPdf;
    }
    public void setRutaVisorPdf(String rutaVisorPdf) {
        this.rutaVisorPdf = rutaVisorPdf;
    }

    public String getRutaDestinoArchivosPDF() {
        return rutaDestinoArchivosPDF;
    }
    public void setRutaDestinoArchivosPDF(String rutaDestinoArchivosPDF) {
        this.rutaDestinoArchivosPDF = rutaDestinoArchivosPDF;
    }

    public Boolean getSoloGuadarArchivosPDF() {
        return soloGuadarArchivosPDF;
    }
    public void setSoloGuadarArchivosPDF(Boolean soloGuadarArchivosPDF) {
        this.soloGuadarArchivosPDF = soloGuadarArchivosPDF;
    }

    public String getRutaProgramasPG() {
        return rutaProgramasPG;
    }
    public void setRutaProgramasPG(String rutaProgramasPG) {
        this.rutaProgramasPG = rutaProgramasPG;
    }
}
