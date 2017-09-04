/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.reportes;

import almacenes.Imprimir;
import java.sql.Connection;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author georgeguitar
 */
public class ReporteVentasDAOImpl implements ReporteVentasDAO {
    private Connection connectionDB;
    private String idUsuario;
    private Imprimir imprimir;
    
    public ReporteVentasDAOImpl(Connection _connectionDB, String _idUsuario) {
        this.connectionDB = _connectionDB;
        this.idUsuario = _idUsuario;
        this.imprimir = new Imprimir(this.connectionDB, this.idUsuario);
    }
    
    @Override
    public void vistaPreviaReporte(Date fechaInicial, Date fechaFinal) {
        Map parametros = new HashMap<>();
        
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        parametros.put("fecha_reporte", dateFormat.format(cal.getTime()));

        dateFormat = new SimpleDateFormat("HH:mm:ss");
        parametros.put("hora_reporte", dateFormat.format(cal.getTime()));
        
        parametros.put("fecha_inicio", fechaInicial);
        parametros.put("fecha_final", fechaFinal);
        parametros.put("nombreLogo", "logo_reporte.png");        
        
        this.imprimir.vistaPreviaReporte("Reporte de Ventas", "reporte_ventas.jrxml", parametros);
    }

    @Override
    public void vistaPreviaEntregas(Date fechaInicial, Date fechaFinal) {
        Map parametros = new HashMap<>();
        
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        parametros.put("fecha_reporte", dateFormat.format(cal.getTime()));

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        
        parametros.put("fecha_inicio", df.format(fechaInicial));
        parametros.put("fecha_final", df.format(fechaFinal));
//        parametros.put("nombreLogo", "logo_reporte.png");        
        
        this.imprimir.vistaPreviaReporte("Reporte de Ventas", "reporte_entregas_por_fecha.jrxml", parametros);
    }
    
    @Override
    public void impresionDirecta(Boolean cuadroConfirmacion) {
        Map parametros = new HashMap<>();
        
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        parametros.put("fecha_reporte", dateFormat.format(cal.getTime()));

        parametros.put("fecha_inicio", null);
        parametros.put("fecha_final", null);
        parametros.put("nombreLogo", "logo_reporte.png");        

        this.imprimir.impresionDirecta("Reporte de Ventas", "reporte_ventas.jrxml", parametros, cuadroConfirmacion);
    }
    
    @Override
    public void impresionPDF(String rutaExportarPDF) {
        Map parametros = new HashMap<>();
        
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        parametros.put("fecha_reporte", dateFormat.format(cal.getTime()));

        parametros.put("fecha_inicio", null);
        parametros.put("fecha_final", null);
        parametros.put("nombreLogo", "logo_reporte.png");        
        
        this.imprimir.impresionEnPDF("Reporte de Ventas", "reporte_ventas.jrxml", parametros, rutaExportarPDF);
    }

    @Override
    public void arqueo(int id) {
        Map parametros = new HashMap<>();
        parametros.put("id", id);
        this.imprimir.vistaPreviaReporte("Reporte Arqueo", "reporte_arqueo.jrxml", parametros);
        
    }

    @Override
    public void vistaPreviaMovimientoCaja(Date fechaInicial, Date fechaFinal) {
        Map parametros = new HashMap<>();
        
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        parametros.put("fecha_reporte", dateFormat.format(cal.getTime()));

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        
        parametros.put("fecha_inicio", df.format(fechaInicial));
        parametros.put("fecha_final", df.format(fechaFinal));
//        parametros.put("nombreLogo", "logo_reporte.png");        
        
        this.imprimir.vistaPreviaReporte("Reporte Movimiento Caja General", "reporte_movimiento_caja_por_fecha.jrxml", parametros);
    }
}
