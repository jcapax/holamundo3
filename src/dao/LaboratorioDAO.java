/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;


import almacenes.model.Laboratorio;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author jcapax
 */
public interface LaboratorioDAO {
    
    public ArrayList<Laboratorio> getListaLaboratorios();
    public HashMap<String, Integer> laboratorioClaveValor();
    public void insertarLaboratorio(Laboratorio laboratorio);
    public void eliminarLaboratorio(int id);
    
}
