/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.model.DetalleTransaccion;
import java.util.ArrayList;

/**
 *
 * @author jcapax
 */
public interface DetalleTransaccionDAO {
    public void insertarDetalleTransaccion(ArrayList<DetalleTransaccion> detalleTransaccion);
    public ArrayList<DetalleTransaccion> getDetalleTransaccion(int idTransaccion);
    
}
