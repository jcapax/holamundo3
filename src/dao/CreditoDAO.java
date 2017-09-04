/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.model.Caja;
import almacenes.model.PendientePago;
import java.util.ArrayList;

/**
 *
 * @author jcapax
 */
public interface CreditoDAO {
    public void insertarCredito(int idTransaccion, int idClienteProveedor, String detalle);
    public ArrayList<PendientePago> getListaPendientesPago(int idTipoTransaccion);
    public ArrayList<Caja> getHistorialPagos(int idTransaccion);
    public double getSaldoTransaccion(int idtransaccion);
    
}
