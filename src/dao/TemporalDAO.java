/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.model.Temporal;
import java.util.ArrayList;

/**
 *
 * @author jcapax
 */
public interface TemporalDAO {
    public void insertarProductoTemp(Temporal detTransTemp);
    public void eliminarProdcutoTemp(int idProducto, int idUnidadMedida);
    public void vaciarProductoTemp();
    public ArrayList<Temporal> getListaTemporal();
    public double totalProductosTemp();
    
}
