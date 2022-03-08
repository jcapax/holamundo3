/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.model.Caja;
import almacenes.model.PendientePago;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jcapax
 */
public interface CreditoDAO {
    public void insertarCredito(int idTransaccion, int idClienteProveedor, String detalle);
    public void insertarCredito(int idTransaccion, int idClienteProveedor, String detalle, int entregaPendiente);
    public List<PendientePago> getListaPendientesPago(int idTipoTransaccion);
    public List<Caja> getHistorialPagos(int idTransaccion);
    public double getSaldoTransaccion(int idtransaccion);
    
}
