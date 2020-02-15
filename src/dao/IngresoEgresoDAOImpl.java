/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.IngresoEgreso;
import almacenes.model.ListaIngresosEgresos;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jcapax
 */
public class IngresoEgresoDAOImpl implements IngresoEgresoDAO{
    
    private DatabaseUtils databaseUtils;
    private Connection connectionDB;
        
    public IngresoEgresoDAOImpl(Connection _connectionDB) {
        this.databaseUtils = new DatabaseUtils();
        this.connectionDB = _connectionDB;
    }
    

    @Override
    public ArrayList<IngresoEgreso> getListaCuentasIngresoEgreso(String tipoCuenta) {
        ArrayList<IngresoEgreso> listaCuentas = new ArrayList<IngresoEgreso>();
        
        String sql = "select id, clase_producto, descripcion, tipo_cuenta "
                + "from producto where estado = 'V' and tipo_cuenta in('A', '"+tipoCuenta+"')";
        
//        System.out.println(sql);
        
        PreparedStatement ps;
        try {
            ps = connectionDB.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                IngresoEgreso cie = new IngresoEgreso();
                
                cie.setClaseProducto(rs.getString("clase_producto"));
                cie.setDescripcion(rs.getString("descripcion"));
                cie.setIdProducto(rs.getInt("id"));
                cie.setTipoCuenta(rs.getString("tipo_cuenta"));
                
                listaCuentas.add(cie);
            }
        } catch (SQLException ex) {
            Logger.getLogger(IngresoEgresoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaCuentas;
    }

    @Override
    public void registrarNuevaCuenta(String nombreCuenta, String idTipoCuenta) {
        String sql = "insert into producto(clase_producto, descripcion, tipo_cuenta, estado) "
                + "values('M', ?, ?, 'V')";
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setString(1, nombreCuenta);
            ps.setString(2, idTipoCuenta);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(IngresoEgresoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Override
    public ArrayList<ListaIngresosEgresos> getListIngresosEgresosFechas(int idTipoTransaccion, Date fechaInicio, Date fechaFin) {
        ArrayList<ListaIngresosEgresos> lista = new ArrayList<>();
        
        String sql = "SELECT t.id, id_tipo_transaccion, t.descripcion_tipo_transaccion, t.fecha, t.id_lugar, "
                + "t.valor_total, t.usuario, "
                + "t.descripcion_transaccion as descripcion_ingreso_egreso, "
                + "d.descripcion as cuenta " +
            "FROM v_transaccion t JOIN v_detalle_transaccion d on t.id = d.id_transaccion " +
            "WHERE t.id_tipo_transaccion = 4 "
                + "AND t.fecha BETWEEN '"+String.valueOf(fechaInicio)+"' and '"+String.valueOf(fechaFin)+"'";
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                ListaIngresosEgresos lie = new ListaIngresosEgresos();
                
                lie.setCuenta(rs.getString("cuenta"));
                lie.setDescripcionIngresoEgreso(rs.getString("descripcion_ingreso_egreso"));
                lie.setDescripcionTipoTransaccion(rs.getString("descripcion_tipo_transaccion"));
                lie.setIdTipoTransaccion(rs.getInt("id_tipo_transaccion"));
                lie.setFecha(rs.getString("fecha"));
                lie.setIdLugar(rs.getInt("id_lugar"));
                lie.setUsuario(rs.getString("usuario"));
                lie.setValorTotal(rs.getDouble("valor_total"));
                lie.setId(rs.getInt("id"));
                
                lista.add(lie);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TransaccionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista;
    }

    @Override
    public void eliminarIngresoEgreso(int idTransaccion) {
        try {
            String sql_transaccion = "Delete From transaccion Where id = "+String.valueOf(idTransaccion);
            String sql_detalle = "Delete From detalle_transaccion Where id_transaccion = "+String.valueOf(idTransaccion);
            String sql_caja = "Delete From caja Where id_transaccion = "+String.valueOf(idTransaccion);
            
            PreparedStatement ps_caja = connectionDB.prepareStatement(sql_caja);
            ps_caja.executeUpdate();
            
            PreparedStatement ps_detalle = connectionDB.prepareStatement(sql_detalle);
            ps_detalle.executeUpdate();
            
            PreparedStatement ps_transaccion = connectionDB.prepareStatement(sql_transaccion);
            ps_transaccion.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(IngresoEgresoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean isIngresoEgresoAbierto(int idTransaccion) {
        boolean aux = false;
        
        String sql = "Select estado From transaccion Where estado = 'A' and id = "+String.valueOf(idTransaccion);
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                aux = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(IngresoEgresoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return aux;
    }
    
}
