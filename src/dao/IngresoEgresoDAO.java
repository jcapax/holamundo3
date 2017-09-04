/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.model.IngresoEgreso;
import java.util.ArrayList;

/**
 *
 * @author jcapax
 */
public interface IngresoEgresoDAO {
    public ArrayList<IngresoEgreso> getListaCuentasIngresoEgreso(String tipoCuenta);
    public void registrarNuevaCuenta(String nombreCuenta, String idTipoCuenta);
    
}
