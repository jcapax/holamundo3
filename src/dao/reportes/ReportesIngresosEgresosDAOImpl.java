/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.reportes;

import almacenes.Imprimir;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jcarlos.porcel
 */
public class ReportesIngresosEgresosDAOImpl implements ReporteIngresosEgresosDAO{

    private Connection connectionDB;
    private String idUsuario;
    private Imprimir imprimir;

    public ReportesIngresosEgresosDAOImpl(Connection connectionDB, String idUsuario) {
        this.connectionDB = connectionDB;
        this.idUsuario = idUsuario;
        this.imprimir = new Imprimir(this.connectionDB, this.idUsuario);
    }
    
    @Override
    public void vistaPreviaReporte(String fechaInicio, String fechaFin) {
        Map parametros = new HashMap<>();
        parametros.put("fecha_inicio", fechaInicio);
        parametros.put("fecha_fin", fechaFin);
        this.imprimir.vistaPreviaReporte("Reporte de Ventas", "reporte_ingresos_egresos.jrxml", parametros);
    }

    
}
