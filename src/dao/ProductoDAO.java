/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.model.ListaProductos;
import almacenes.model.Producto;
import almacenes.model.StockProducto;
import almacenes.model.StockVencimiento;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author jcapax
 */
public interface ProductoDAO {
    
    public List<Producto> getListaProductos();
    public Producto getProductoById(int id);
    public List<Producto> getListaProductosVenta(String criterio, byte idLugar);
    public void insertarProducto(Producto producto);
    public List<StockProducto> getListaStockProducto(byte idLugar, String criterio);
    public byte getControlStock(int idProducto);
    //public ArrayList<StockVencimiento> getListStockVencimiento(byte idLugar, int idProducto, byte idUnidadMedida);
    public void editarProducto(Producto producto);
    
    public HashMap<String, Integer> getProductoClaveValor();
    
    
    
}
