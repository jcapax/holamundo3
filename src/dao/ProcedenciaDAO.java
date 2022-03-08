/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;


import almacenes.model.Procedencia;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author jcapax
 */
public interface ProcedenciaDAO {
    
    public List<Procedencia> getListaProcedencia();
    public HashMap<String, Integer> procedenciaClaveValor();
    public void insertarProcedencia(Procedencia procedencia);
    public void eliminarProcedencia(int id);
    
    
}
