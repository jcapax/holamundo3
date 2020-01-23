/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.model.EntregaPendiente;
import java.util.ArrayList;

/**
 *
 * @author jcarlos.porcel
 */
public interface EntregasDAO {
    public ArrayList<EntregaPendiente> getListaEntregasPendientes();
    public ArrayList<EntregaPendiente> getProductosPendientes(int idtransaccion);
    public void abonoProducto(EntregaPendiente entregaPendiente);
}
