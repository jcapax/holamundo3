/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.model.ListaProductos;
import almacenes.model.Producto;
import almacenes.model.StockProducto;
import java.util.ArrayList;

/**
 *
 * @author jcapax
 */
public interface ProductoDAO {
    
    public ArrayList<ListaProductos> getListaProductos();
    public ArrayList<ListaProductos> getListaProductosVenta(String criterio);
    public void insertarProducto(Producto producto);
    public ArrayList<StockProducto> getListaStockProducto(byte idLugar, String criterio);
    public byte getControlStock(int idProducto);
    public void editarProducto(Producto producto);
    
    
    
}
