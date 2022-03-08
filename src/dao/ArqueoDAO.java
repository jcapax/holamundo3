/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.model.Arqueo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jcapax
 */
public interface ArqueoDAO {
    public void insertarCajaInicial(Arqueo arqueo);
    public int getIdArqueo(byte idLugar, byte idTerminal, String usuario);
    public List<Integer> getListaTransaccionArqueoPorUsuarioMaquina(byte idLugar, byte idTerminal, String usuario);
    public double getCajaInicial(int idArqueo);
    public String getEstadoCaja(int idArqueo);
    public double getImportePorArqueoUsuarioMaquina(List<Integer> lTrans);
    public double getImportePorArqueoUsuarioMaquina(byte idLugar, byte idTerminal, String usuario);
    public List<Arqueo> getListaArqueos(byte idLugar, byte mes, int anno);
    public List<Integer> getListaAnnosArqueos();
    
    public void cerrarCaja(List<Integer> lTrans, int idArqueo);
    public void cerrarCreditoCaja(byte idLugar, byte idTerminal, String usuario, int idArqueo);
    public void cerrarArqueo(double importeCierre, int idArqueo);
    public void cerrarTransacciones(List<Integer> lTrans);
    
    
}
