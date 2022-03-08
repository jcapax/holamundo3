/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.model.DetalleFacturaFacil;
import almacenes.model.Temporal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jcapax
 */
public interface TemporalDAO {
    public void insertarProductoTemp(Temporal detTransTemp);
    public void insertarDetalleFacturaFacilTemp(DetalleFacturaFacil facil);
    public void insertarProductoTempFechaVencimiento(Temporal detTransTemp);
    public void eliminarProdcutoTemp(int idProducto, int idUnidadMedida);
    public void eliminarProductoDetalleFacturaFacil(int id);
    public void vaciarProductoTemp();
    public void vaciarDetalleFacturaFacilTemp();
    public List<Temporal> getListaTemporal();
    public List<DetalleFacturaFacil> getListaDetalleFacturaFacilTemporal();
    public double totalProductosTemp();
    public void reducir10();
    public Double totalTemporal();
    
    public List<Temporal> getListEntregaTemporal();
    public void saveEntregaTemporal(Temporal temporal);
    public void deleteEntregaTemporal(Temporal temporal);
    public void deleteProductoEntregaTemporal(String nombreProducto, String nombreUnidadMedida);
    public void deleteProductoEntregaTemporal();
    public void emptyEntregaTemporal();
    public byte getNroTemporalEntrega();
    
}
