/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.model.FacturaVenta;
import almacenes.model.PendientePago;
import java.util.ArrayList;

/**
 *
 * @author jcapax
 */
public interface FacturaDAO {
    public ArrayList<Integer> getListaAnnosFacturacion();
    public ArrayList<FacturaVenta> getListaFacturasLibroVenta(byte mes, int anno);
    public ArrayList<PendientePago> getListaCreditoPorFacturar();
    
}
