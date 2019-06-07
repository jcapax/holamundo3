/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.Temporal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jcapax
 */
public class TemporalDAOImpl implements TemporalDAO{

    private DatabaseUtils databaseUtils;
    private Connection sqlite;
        
    public TemporalDAOImpl(Connection _connectionDB) {
        this.databaseUtils = new DatabaseUtils();
        this.sqlite = _connectionDB;
    }
    
    @Override
    public void insertarProductoTemp(Temporal detTransTemp) {
        
        String sql = "INSERT INTO detalle_transaccion_temp("
                + "id_producto, nombre_producto, id_unidad_medida, simbolo, "
                + "cantidad, valor_unitario, valor_total, tipo_valor) "
                + "values(?, ?, ?, ?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement ps = sqlite.prepareStatement(sql);
            ps.setInt(1, detTransTemp.getIdProducto());
            ps.setString(2, detTransTemp.getNombreProducto());
            ps.setInt(3, detTransTemp.getIdUnidadMedida());
            ps.setString(4, detTransTemp.getSimbolo());
            ps.setDouble(5, detTransTemp.getCantidad());
            ps.setDouble(6, detTransTemp.getValorUnitario());
            ps.setDouble(7, detTransTemp.getValorTotal());
            ps.setString(8, detTransTemp.getTipoValor());
            
            int n = ps.executeUpdate();
            if(n!=0){
//                System.out.println("registrado con exito");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(TemporalDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

@Override
    public void insertarProductoTempFechaVencimiento(Temporal detTransTemp) {
        
        String sql = "INSERT INTO detalle_transaccion_temp("
                + "id_producto, nombre_producto, id_unidad_medida, simbolo, "
                + "cantidad, valor_unitario, valor_total, tipo_valor, fecha_vencimiento) "
                + "values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement ps = sqlite.prepareStatement(sql);
            ps.setInt(1, detTransTemp.getIdProducto());
            ps.setString(2, detTransTemp.getNombreProducto());
            ps.setInt(3, detTransTemp.getIdUnidadMedida());
            ps.setString(4, detTransTemp.getSimbolo());
            ps.setDouble(5, detTransTemp.getCantidad());
            ps.setDouble(6, detTransTemp.getValorUnitario());
            ps.setDouble(7, detTransTemp.getValorTotal());
            ps.setString(8, detTransTemp.getTipoValor());
            ps.setString(9, detTransTemp.getFecha_vencimiento());
            
            int n = ps.executeUpdate();
            if(n!=0){
//                System.out.println("registrado con exito");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(TemporalDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void eliminarProdcutoTemp(int idProducto, int idUnidadMedida) {
        
        String sql = "delete from detalle_transaccion_temp "
                + "where id_producto = ? and id_unidad_medida = ?";
        
        try {
            PreparedStatement ps = sqlite.prepareStatement(sql);
            ps.setInt(1, idProducto);
            ps.setInt(2, idUnidadMedida);
            
            int n = ps.executeUpdate();
            if(n!=0){
//                System.out.println("eliminado con exito");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(TemporalDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void vaciarProductoTemp() {
        String sqlVaciarDetTransTemp = "delete from detalle_transaccion_temp";
        
        PreparedStatement ps;
        try {
            ps = sqlite.prepareStatement(sqlVaciarDetTransTemp);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TemporalDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        
    }

    @Override
    public ArrayList<Temporal> getListaTemporal() {
        String sql = "SELECT * FROM detalle_transaccion_temp";
        
        ArrayList<Temporal> lTemporal = new ArrayList<Temporal>();
        
        try {
            PreparedStatement pst = sqlite.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                Temporal temporal = new Temporal();
                
                temporal.setCantidad(rs.getDouble("cantidad"));
                temporal.setIdProducto(rs.getInt("id_producto"));
                temporal.setIdUnidadMedida(rs.getInt("id_unidad_medida"));
                temporal.setNombreProducto(rs.getString("nombre_producto"));
                temporal.setSimbolo(rs.getString("simbolo"));
                temporal.setTipoValor(rs.getString("tipo_valor"));
                temporal.setValorTotal(rs.getDouble("valor_total"));
                temporal.setValorUnitario(rs.getDouble("valor_unitario"));
                temporal.setFecha_vencimiento(rs.getString("fecha_vencimiento"));
                
                lTemporal.add(temporal);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(RubroDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lTemporal;
    }

    @Override
    public double totalProductosTemp() {
        double importeTotal = 0;
        
        String sql = "select sum(valor_total) as total_temp from detalle_transaccion_temp";
        
        PreparedStatement pst;
        try {
            pst = sqlite.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                importeTotal = rs.getDouble("total_temp");
            }
        } catch (SQLException ex) {
            Logger.getLogger(TemporalDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        return importeTotal;
    }
    
}
