/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.model.ClienteProveedor;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 *
 * @author jcarlos.porcel
 */
public interface ClienteProveedorDAO {
    public ArrayList<ClienteProveedor> getListaClienteProveedor(String tipo);
    public void insertarClienteProveedor(ClienteProveedor clienteProveedor);
    public void eliminarClienteProveedor(int idCliente);
    public void actualizarClienteProveedor(ClienteProveedor clienteProveedor);    
    public boolean validarEliminacionClienteProveedor();
    public TreeMap<String, Integer> clienteProveedorClaveValor(String tipo); 
}
