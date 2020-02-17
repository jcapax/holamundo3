/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.reportes;

import java.sql.Date;

/**
 *
 * @author georgeguitar
 */
public interface ReporteVentasDAO {
    public void vistaPreviaReporte(byte idLugar, Date fechaInicial, Date fechaFinal);
    public void vistaPreviaReciboVenta(int idTransaccion);
    public void vistaPreviaEntregas(byte idLugar, Date fechaInicial, Date fechaFinal);
    public void vistaPreviaMovimientoCaja(byte idLugar, Date fechaInicial, Date fechaFinal);
    public void impresionDirecta(Boolean cuadroConfirmacion);
    public void impresionPDF(String rutaExportarPDF);
    public void arqueo(int id);
    public void listaArqueosMes(Integer anno, byte mes, String nombre_mes);
    
    public void stockProductosLugar(int idLugar);
}
