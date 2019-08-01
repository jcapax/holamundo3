/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.model.Dosificacion;
import java.util.ArrayList;

/**
 *
 * @author jcapax
 */
public interface DosificacionDAO {
    public int insertarDosificacion(Dosificacion dosificacion);
    public void actualizarEstadoCeroDosificacion();
    public ArrayList<Dosificacion> getListDosificacion();
    
}
