/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.model.ListaIngresosEgresos;
import almacenes.model.ListaTransaccion;
import almacenes.model.Transaccion;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author jcapax
 */
public interface TransaccionDAO {
    public int insertarTransaccion(Transaccion transaccion);
    public int getNroTipoTransaccion(int idTipoTransaccion);
    public int getTipoMovimiento(int idTipoTransaccion);
    public void insertarEntregaTransaccion(int idTransaccion, int idEntregaTransaccion);
    public double getValorTotalTransaccion(int idTransaccion);
    public Date getFechaTransaccion(int idTransaccion);
    public ArrayList<ListaTransaccion> getlistaTransacciones(Date fecha, String usuario); 
    public ArrayList<ListaTransaccion> getlistaCotizacionesPendientes(int idLugar);
    public int getIdTransaccionOriginalDeEntregaPendiente(int idTransaccion);
    public void crearTemporalEntrega();
    public void eliminarDatosTemporalEntrega();
    public void insertarEntregaTemporal(int idTransaccionInicial, int idTransaccionEntrega);
    
    public void saveCreditoTransaccionEntrega(int idTransaccion);
    public boolean isCreditoEntrega(int idTransaccion);
    
    public void insertarAtencionCotizacion(int 	idTransaccionCotizacion, int idTransaccionAtencion);
    
}
