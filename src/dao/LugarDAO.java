/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.model.Lugar;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author jcapax
 */
public interface LugarDAO {
    public void insertarLugar(Lugar lugar);
    public ArrayList<Lugar> getListLugar();
    public void eliminarLugar(int idLugar);
    public void editarLugar(Lugar lugar);
    public HashMap<String, Integer> lugarClaveValor();
    public boolean existsLugar();
    public byte getIdLugar(byte idTerminal);
    public byte getIdLugarTransaccion(int idTransaccion);
    public byte getIdLugarFactura(int idFactura);
}
