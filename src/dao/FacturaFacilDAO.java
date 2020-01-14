/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.model.DetalleFacturaFacil;
import almacenes.model.FacturaFacil;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jcarlos.porcel
 */
public interface FacturaFacilDAO {
    public List<String> getListaProductosAutocompletado();
    public void insertarDetalleFacturaFacil(ArrayList<DetalleFacturaFacil> facil, int idFacturaFacil);
    public int getIdFacturaUltima();  
    public ArrayList<Integer> getListaAnnosFacturaFacil();
    public ArrayList<FacturaFacil> getlistaFacturaFacil(int anno, int mes);
    public double getUltimoValorProductoFacturaFacil(String nombreProducto);
    public void anularFacturaFacil(int id);
    public void anularDetalleFacturaFacil(int id);
}
