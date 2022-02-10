/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;


import almacenes.model.Familia;
import almacenes.model.Laboratorio;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author jcapax
 */
public interface FamiliaDAO {
    
    public ArrayList<Familia> getListaFamilias();
    public HashMap<String, Integer> familiaClaveValor();
    public void insertarFamilia(Familia familia);
    public void eliminarFamilia(int id);
    
}
