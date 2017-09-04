/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.model.Arqueo;
import java.util.ArrayList;

/**
 *
 * @author jcapax
 */
public interface ArqueoDAO {
    public void insertarCajaInicial(Arqueo arqueo);
    public int getIdArqueo(byte idLugar, byte idTerminal, String usuario);
    public ArrayList<Integer> getListaTransaccionArqueoPorUsuarioMaquina(byte idLugar, byte idTerminal, String usuario);
    public double getCajaInicial(int idArqueo);
    public String getEstadoCaja(int idArqueo);
    public double getImportePorArqueoUsuarioMaquina(ArrayList<Integer> lTrans);
    public double getImportePorArqueoUsuarioMaquina(byte idLugar, byte idTerminal, String usuario);
    public ArrayList<Arqueo> getListaArqueos(byte mes, int anno);
    public ArrayList<Integer> getListaAnnosArqueos();
    
    public void cerrarCaja(ArrayList<Integer> lTrans, int idArqueo);
    public void cerrarCreditoCaja(byte idLugar, byte idTerminal, String usuario, int idArqueo);
    public void cerrarArqueo(double importeCierre, int idArqueo);
    public void cerrarTransacciones(ArrayList<Integer> lTrans);
    
    
}
