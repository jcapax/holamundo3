/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;


import almacenes.model.UnidadMedida;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author jcapax
 */
public interface UnidadMedidaDAO {
    
    public ArrayList<UnidadMedida> getListUnidadMedida();
    public HashMap<String, Integer> unidadMedidaClaveValor();
    public void insertarUnidadMedida(UnidadMedida uMedida);
    public void eliminarUnidadMedida(int id);
    
    
    
}
