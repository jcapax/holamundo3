/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;


import almacenes.model.Vencimiento;
import java.util.ArrayList;
/**
 *
 * @author jcapax
 */
public interface VencimientoDAO {
    public void insertarVencimiento(ArrayList<Vencimiento> listaVencimiento);
    public void registrosSalidasProductosVencimiento(int idTransaccion);
    public boolean isVencimiento(int idProducto);
    
}