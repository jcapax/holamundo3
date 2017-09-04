/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;


import almacenes.model.Marca;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author jcapax
 */
public interface MarcaDAO {
    
    public ArrayList<Marca> getListaMarcas();
    public HashMap<String, Integer> marcaClaveValor();
    public void insertarMarca(Marca marca);
    public void eliminarMarca(int id);
    
}
