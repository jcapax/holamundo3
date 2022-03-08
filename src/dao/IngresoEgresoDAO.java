/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.model.IngresoEgreso;
import almacenes.model.ListaIngresosEgresos;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jcapax
 */
public interface IngresoEgresoDAO {
    public List<IngresoEgreso> getListaCuentasIngresoEgreso(String tipoCuenta);
    public void registrarNuevaCuenta(String nombreCuenta, String idTipoCuenta);
    public List<ListaIngresosEgresos> getListIngresosEgresosFechas(int idTipoTransaccion, Date fechaInicio, Date fechaFin, byte idLugar);
    public void eliminarIngresoEgreso(int idTransaccion);
    public boolean isIngresoEgresoAbierto(int idTransaccion);
    
    public void createTemporalIngresosEgresos();
    public void insertarIngresosEgresos(int idTipoTransaccion, String fechaInicio, String fechaFin, String usuario);
    
}
