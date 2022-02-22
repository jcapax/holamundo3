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
    public void vistaPreviaReporte(byte idLugar, Date fechaInicial, Date fechaFinal) {
        Map parametros = new HashMap<>();
        
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        parametros.put("fecha_reporte", dateFormat.format(cal.getTime()));

        dateFormat = new SimpleDateFormat("HH:mm:ss");
        parametros.put("hora_reporte", dateFormat.format(cal.getTime()));
        
        parametros.put("id_lugar", Integer.valueOf(idLugar));
        parametros.put("fecha_inicio", fechaInicial);
        parametros.put("fecha_final", fechaFinal);
        parametros.put("nombreLogo", "logo_reporte.png");        
        
        this.imprimir.vistaPreviaReporte("Reporte de Ventas", "reporte_ventas.jrxml", parametros);
    }

    @Override
    public void vistaPreviaEntregas(byte idLugar, Date fechaInicial, Date fechaFinal) {
        Map parametros = new HashMap<>();
        
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        parametros.put("fecha_reporte", dateFormat.format(cal.getTime()));

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        
        parametros.put("id_lugar", Integer.valueOf(idLugar));
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
    public void vistaPreviaMovimientoCaja(byte idLugar, Date fechaInicial, Date fechaFinal) {
        Map parametros = new HashMap<>();
        
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        parametros.put("fecha_reporte", dateFormat.format(cal.getTime()));

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        
        parametros.put("id_lugar", Integer.valueOf(idLugar));
        parametros.put("fecha_inicio", df.format(fechaInicial));
        parametros.put("fecha_final", df.format(fechaFinal));
//        parametros.put("nombreLogo", "logo_reporte.png");        
        
        this.imprimir.vistaPreviaReporte("Reporte Movimiento Caja General", "reporte_movimiento_caja_por_fecha.jrxml", parametros);
    }

    @Override
    public void stockProductosLugar(byte idLugar) {
        Map parametros = new HashMap<>();
        
        parametros.put("id_lugar", Integer.valueOf(idLugar));
        
        this.imprimir.vistaPreviaReporte("Report Stock de Productos Lugar", "reporte_stock_por_lugar.jrxml", parametros);
//        this.imprimir.vistaPreviaReporte("Report Stock de Productos Lugar", "reporte_stock_lugar.jasper", parametros);
    }

    @Override
    public void listaArqueosMes(Integer anno, byte mes, String nombre_mes, byte idLugar) {
        Map parametros = new HashMap<>();
        
        parametros.put("anno", anno);
        parametros.put("mes", mes);
        parametros.put("id_lugar", idLugar);
        parametros.put("nombre_mes", nombre_mes);
        
        //System.out.println(parametros);
        
        this.imprimir.vistaPreviaReporte("Reporte Lista Arqueos Mes", "reporte_arqueos_mes.jrxml", parametros);
    }

    @Override
    public void vistaPreviaReciboVenta(int idTransaccion) {
        Map parametros = new HashMap<>();
        
        String logo = "//reportes//img//logo_bk.jpg";
        
        parametros.put("id_transaccion", idTransaccion);
        parametros.put("logo", this.getClass().getResourceAsStream(logo));
        
        this.imprimir.vistaPreviaReporte("Report Recibo de Ventas", "reporte_recibo_venta.jrxml", parametros);
    }
}
