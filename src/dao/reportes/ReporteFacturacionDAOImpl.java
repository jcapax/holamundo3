/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.reportes;

import Literal.Numero_a_Letra;
import almacenes.Imprimir;
import almacenes.model.Configuracion;
import dao.SistemaDAO;
import dao.SistemaDAOImpl;
import java.sql.Connection;
import java.util.HashMap;

/**
 *
 * @author jcapax
 */
public class ReporteFacturacionDAOImpl implements ReporteFacturacionDAO{
    
    private Connection connectionDB;
    private String idUsuario;
    private Imprimir imprimir;
    
    public ReporteFacturacionDAOImpl(Connection _connectionDB, String _idUsuario) {
        this.connectionDB = _connectionDB;
        this.idUsuario = _idUsuario;
        this.imprimir = new Imprimir(this.connectionDB, this.idUsuario);
    }

    @Override
    public void VistaPreviaFacturaVenta(int idTransaccion, String cadenaQr, double valorTotal) {
        
        SistemaDAO sistemaDAO = new SistemaDAOImpl(connectionDB);
        Configuracion conf = new Configuracion();
        
        int imp = 0;
        imp = conf.getImpresionDirectaFactura();
        
        HashMap parametros = new HashMap<>();
        
        Numero_a_Letra lit = new Numero_a_Letra();
        
        String valorTotalLiteral = "0";
        
        valorTotalLiteral = lit.Convertir(String.valueOf(valorTotal), true);
       
        parametros.put("idTransaccion", idTransaccion);
        parametros.put("cadenaQr", cadenaQr);
        parametros.put("valorTotalLiteral", valorTotalLiteral);
        
        if(imp==0){
            this.imprimir.vistaPreviaReporte("Factura de Venta", "reporte_factura.jrxml", parametros);
        }
        else{
            this.imprimir.impresionDirecta("Factura de Venta", "reporte_factura.jrxml", parametros, false);
            this.imprimir.impresionDirecta("Factura de Venta", "reporte_factura_copia.jrxml", parametros, false);
        }
    }
    
}
