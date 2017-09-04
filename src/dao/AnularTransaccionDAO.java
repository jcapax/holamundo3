/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.model.AnularTransaccion;
import java.util.ArrayList;

/**
 *
 * @author jcapax
 */
public interface AnularTransaccionDAO {
    public ArrayList<AnularTransaccion> getListaTransaccionesAnular(byte idTipoTransaccion, String usuario);
    public void anularTrans(int idTransaccion, int idEntregaTransaccion);
    public void anularFactura(int idTransaccion);
    public void anularCaja(int idTransaccion);
    
}
