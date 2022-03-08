/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.model.DetalleFacturaFacil;
import almacenes.model.FacturaFacil;
import java.util.List;

/**
 *
 * @author jcarlos.porcel
 */
public interface FacturaFacilDAO {
    public List<String> getListaProductosAutocompletado();
    public void insertarDetalleFacturaFacil(List<DetalleFacturaFacil> facil, int idFacturaFacil);
    public int getIdFacturaUltima();  
    public List<Integer> getListaAnnosFacturaFacil();
    public List<FacturaFacil> getlistaFacturaFacil(int anno, int mes);
    public void registroFacturaVentaControl(String usuario);
    public int getIdFacturaVentaControl();
    public void cerrarFacturacionMensual(int anno, int mes, int idFacturaVentaControl);
    public boolean isFacturasAbiertas(int anno, int mes);
    public boolean isFacturaAbierta(int id);
    public double getUltimoValorProductoFacturaFacil(String nombreProducto);
    public void anularFacturaFacil(int id);
    public void anularDetalleFacturaFacil(int id);

}
