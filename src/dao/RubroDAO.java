/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.model.Rubro;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author jcapax
 */
public interface RubroDAO {
    public List<Rubro> getListaRubros();
    public HashMap<String, Integer> rubroClaveValor();
    public void insertarRubro(Rubro rubro);
    public void eliminarRubro(int id);
    
}
