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
    public void vistaPreviaReporte(Date fechaInicial, Date fechaFinal);
    public void vistaPreviaEntregas(Date fechaInicial, Date fechaFinal);
    public void vistaPreviaMovimientoCaja(Date fechaInicial, Date fechaFinal);
    public void impresionDirecta(Boolean cuadroConfirmacion);
    public void impresionPDF(String rutaExportarPDF);
    public void arqueo(int id);
}
