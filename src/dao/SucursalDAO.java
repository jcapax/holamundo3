/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.model.Sucursal;
import java.util.ArrayList;

/**
 *
 * @author jcapax
 */
public interface SucursalDAO {
    public void insertarSucursal(Sucursal s);
    public void eliminarSucursal(byte idSucursal);
    public void editarSucursal(Sucursal s);
    public ArrayList<Sucursal> getListSucursal();
    
}
