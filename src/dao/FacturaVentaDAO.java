/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.model.FacturaVenta;
import java.sql.Date;

/**
 *
 * @author jcapax
 */
public interface FacturaVentaDAO {
    public void insertarFacturaVenta(FacturaVenta facturaVenta);
    public String getNroAutorizacion(int idSucursal);
    public String getLlaveDosificacion(String nroAutorizacion);
    public int getNewNroFactura(String nroAutorizacion);
    public int getNroInicioFacturaDosificacion(String nroAutorizacion);
    public Date getFechaLimiteEmision(String nroAutorizacion);
    public String getRazonSocialFactura(String ni);
    public String getCadenaCodigoQr(int idTransaccion);
    
    
}
