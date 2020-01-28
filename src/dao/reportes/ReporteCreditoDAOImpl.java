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
public class ReporteCreditoDAOImpl implements ReporteCreditoDAO{
    
    private Connection connectionDB;
    private String idUsuario;
    private Imprimir imprimir;
    
    public ReporteCreditoDAOImpl(Connection _connectionDB, String _idUsuario) {
        this.connectionDB = _connectionDB;
        this.idUsuario = _idUsuario;
        this.imprimir = new Imprimir(this.connectionDB, this.idUsuario);
    }


    @Override
    public void vistaPreviaPagoCredito(int idTransaccion, String detalle) {
        Map parametros = new HashMap<>();
        
        parametros.put("id_transaccion", idTransaccion);
        parametros.put("detalle", detalle);
        
        this.imprimir.vistaPreviaReporte("Reporte Pago Credito", "reporte_pago_credito.jrxml", parametros);
    }

    @Override
    public void vistaPreviaCredito(int idTransaccion) {
        Map parametros = new HashMap<>();
        
        parametros.put("id_transaccion", idTransaccion);
        
        this.imprimir.vistaPreviaReporte("Reporte Pago Credito", "reporte_credito.jrxml", parametros);
    }

    @Override
    public void vistaPreviaEntregaPendiente(int idTransaccion) {
        Map parametros = new HashMap<>();
        
        parametros.put("id_transaccion", idTransaccion);
        
        this.imprimir.vistaPreviaReporte("Reporte Entrega Pendiente", "reporte_credito_producto.jrxml", parametros);
    }

    @Override
    public void vistaPreviaEntregaProductosCredito(int idTransaccion, int idTransaccionEntrega) {
        Map parametros = new HashMap<>();
        
        parametros.put("idTransaccion", idTransaccion);
        parametros.put("idTransaccionEntrega", idTransaccionEntrega);
        
        this.imprimir.vistaPreviaReporte("Reporte Entrega Pendiente", "reporte_entrega_producto.jrxml", parametros);
    }

    @Override
    public void vistaPreviaEntregaProductosCredito() {
        Map parametros = new HashMap<>();
        
        this.imprimir.vistaPreviaReporte("Reporte Entrega Pendiente", "reporte_entrega_producto.jrxml", parametros);
    }
    
}
