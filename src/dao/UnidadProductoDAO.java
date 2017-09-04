/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.model.UnidadProducto;
import java.util.ArrayList;

/**
 *
 * @author jcapax
 */
public interface UnidadProductoDAO {
    public ArrayList<UnidadProducto> getListaUnidadProducto(int idProducto);
    public double getStockProducto(int idProducto, int idUnidadMedida, int idLugar);
    public void insertarUnidadProducto(UnidadProducto unidadProducto);
    public void eliminarUnidadProducto(int idUnidadProducto);
    public void actualizarUnidadProducto(UnidadProducto unidadProducto);
    public void editarUnidadProducto(UnidadProducto unidadProducto);
    
}
