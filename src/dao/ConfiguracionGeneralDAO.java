/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.model.ConfiguracionGeneral;
import java.util.ArrayList;

/**
 *
 * @author jcapax
 */
public interface ConfiguracionGeneralDAO {
       public ArrayList<ConfiguracionGeneral> getConfiguracionGeneral();
       public String getRutaExcelLibroVentas();
       public int getNroDiasNullTransaccion();
    
}
