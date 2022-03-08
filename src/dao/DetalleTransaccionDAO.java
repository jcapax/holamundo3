/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.model.DetalleTransaccion;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jcapax
 */
public interface DetalleTransaccionDAO {
    public void insertarDetalleTransaccion(List<DetalleTransaccion> detalleTransaccion);
    public List<DetalleTransaccion> getDetalleTransaccion(int idTransaccion);
    
}
