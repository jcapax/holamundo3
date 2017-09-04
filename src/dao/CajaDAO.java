/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.model.Caja;
import almacenes.model.ListaCaja;
import java.util.ArrayList;

/**
 *
 * @author jcapax
 */
public interface CajaDAO {
    public void insertarCaja(Caja caja);
    public ArrayList<ListaCaja> getListaCaja(int idArqueo);
    
}
