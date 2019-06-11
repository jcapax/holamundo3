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
 * @author jcapax
 */
public class ReporteComprasDAOImpl implements ReporteComprasDAO{
    
    private Connection connectionDB;
    private String idUsuario;
    private Imprimir imprimir;
    
    public ReporteComprasDAOImpl(Connection _connectionDB, String _idUsuario) {
        this.connectionDB = _connectionDB;
        this.idUsuario = _idUsuario;
        this.imprimir = new Imprimir(this.connectionDB, this.idUsuario);
    }

    @Override
    public void vistaPreviaCompras(int idTransaccion) {
        Map parametros = new HashMap<>();
        
        parametros.put("id_transaccion", idTransaccion);

        this.imprimir.vistaPreviaReporte("Reporte Compra", "reporte_compra.jrxml", parametros);
    }
    
}
