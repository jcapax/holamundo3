/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.reportes;

/**
 *
 * @author jcapax
 */
public interface ReporteFacturacionDAO {
    public void VistaPreviaFacturaVenta(int idTransaccion, String cadenaQr, double valorTotal);
    
}
