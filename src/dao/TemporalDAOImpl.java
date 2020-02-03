/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.DetalleFacturaFacil;
import almacenes.model.Temporal;
import java.sql.Connection;
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
                + "cantidad, valor_unitario, valor_sub_total, descuento, valor_total, tipo_valor) "
                + "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement ps = sqlite.prepareStatement(sql);
            ps.setInt(1, detTransTemp.getIdProducto());
            ps.setString(2, detTransTemp.getNombreProducto());
            ps.setInt(3, detTransTemp.getIdUnidadMedida());
            ps.setString(4, detTransTemp.getSimbolo());
            ps.setDouble(5, detTransTemp.getCantidad());
            ps.setDouble(6, detTransTemp.getValorUnitario());
            ps.setDouble(7, detTransTemp.getValorSubTotal());
            ps.setDouble(8, detTransTemp.getDescuento());
            ps.setDouble(9, detTransTemp.getValorTotal());
            ps.setString(10, detTransTemp.getTipoValor());
            
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
                temporal.setValorSubTotal(rs.getDouble("valor_sub_total"));
                temporal.setDescuento(rs.getDouble("descuento"));
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

    @Override
    public void reducir10() {
        try {
            String sql = "Update detalle_transaccion_temp set valor_total = valor_total * 0.9, valor_unitario = valor_unitario * 0.9";
            PreparedStatement ps = sqlite.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TemporalDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void insertarDetalleFacturaFacilTemp(DetalleFacturaFacil facil) {
        String sql = "INSERT INTO detalle_factura_facil_temp("
                + "detalle, cantidad, valor_unitario, valor_total) "
                + "values(?, ?, ?, ?)";
        
        try {
            PreparedStatement ps = sqlite.prepareStatement(sql);
            ps.setString(1, facil.getDetalle());
            ps.setDouble(2, facil.getCantidad());
            ps.setDouble(3, facil.getValorUnitario());
            ps.setDouble(4, facil.getValorTotal());
            
            
            int n = ps.executeUpdate();
            if(n!=0){
//                System.out.println("registrado con exito");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(TemporalDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ArrayList<DetalleFacturaFacil> getListaDetalleFacturaFacilTemporal() {
        String sql = "SELECT * FROM detalle_factura_facil_temp order by id";
        
        ArrayList<DetalleFacturaFacil> lTemporal = new ArrayList<DetalleFacturaFacil>();
        
        try {
            PreparedStatement pst = sqlite.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                DetalleFacturaFacil temporal = new DetalleFacturaFacil();
                
                temporal.setId(rs.getInt("id"));
                temporal.setDetalle(rs.getString("detalle"));
                temporal.setCantidad(rs.getDouble("cantidad"));                
                temporal.setValorTotal(rs.getDouble("valor_total"));
                temporal.setValorUnitario(rs.getDouble("valor_unitario"));               
                
                lTemporal.add(temporal);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(RubroDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lTemporal;
    }

    @Override
    public void vaciarDetalleFacturaFacilTemp() {
        String sqlVaciarDetalleFacturaTemp = "delete from detalle_factura_facil_temp";
        
        PreparedStatement ps;
        try {
            ps = sqlite.prepareStatement(sqlVaciarDetalleFacturaTemp);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TemporalDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void eliminarProductoDetalleFacturaFacil(int id) {
        String sql = "delete from detalle_factura_facil_temp "
                + "where id = ?";
        
        try {
            PreparedStatement ps = sqlite.prepareStatement(sql);
            ps.setInt(1, id);
            
            ps.executeUpdate();                        
        } catch (SQLException ex) {
            Logger.getLogger(TemporalDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Double totalTemporal() {        
        Double total = 0.0;
        String sql = "Select sum(valor_total) as total From detalle_factura_facil_temp";
        try {
            PreparedStatement ps = sqlite.prepareStatement(sql);            
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                total = rs.getDouble("total");
            }        
        } catch (SQLException ex) {
            Logger.getLogger(SucursalDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return total;    
    }

    @Override
    public ArrayList<Temporal> getListEntregaTemporal() {
        String sql = "SELECT * FROM entrega_temp";
        
        ArrayList<Temporal> lTemporal = new ArrayList<Temporal>();
        
        try {
            PreparedStatement pst = sqlite.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                Temporal temporal = new Temporal();
                
                temporal.setIdTransaccion(rs.getInt("id_transaccion"));
                temporal.setIdProducto(rs.getInt("id_producto"));
                temporal.setIdUnidadMedida(rs.getInt("id_unidad_medida"));
                temporal.setNombreProducto(rs.getString("nombre_producto"));
                temporal.setSimbolo(rs.getString("nombre_unidad_medida"));
                temporal.setCantidad(rs.getDouble("cantidad"));           
                
                lTemporal.add(temporal);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(RubroDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return lTemporal;
    }

    @Override
    public void saveEntregaTemporal(Temporal temporal) {
        String sql = "INSERT INTO entrega_temp("
                + "id_transaccion, id_producto, id_unidad_medida, nombre_producto, nombre_unidad_medida, cantidad) "
                + "values(?, ?, ?, ?, ?, ?)";        
        try {
            PreparedStatement ps = sqlite.prepareStatement(sql);
            ps.setInt(1, temporal.getIdTransaccion());
            ps.setInt(2, temporal.getIdProducto());
            ps.setInt(3, temporal.getIdUnidadMedida());
            ps.setString(4, temporal.getNombreProducto());
            ps.setString(5, temporal.getSimbolo());
            ps.setDouble(6, temporal.getCantidad());
            
            int n = ps.executeUpdate();
            if(n!=0){
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(TemporalDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteEntregaTemporal(Temporal temporal) {
        String sql = "delete from entrega_temp "
                + "where id_transaccion = "+String.valueOf(temporal.getIdTransaccion())+" and "
                + "id_producto = "+String.valueOf(temporal.getIdProducto())+" and "
                + "id_unidad_medida = "+String.valueOf(temporal.getIdUnidadMedida());
        
        try {
            PreparedStatement ps = sqlite.prepareStatement(sql);
            ps.executeUpdate();                        
        } catch (SQLException ex) {
            Logger.getLogger(TemporalDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void emptyEntregaTemporal() {
        String sql = "delete from entrega_temp";                
        try {
            PreparedStatement ps = sqlite.prepareStatement(sql);
            ps.executeUpdate();                        
        } catch (SQLException ex) {
            Logger.getLogger(TemporalDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public byte getNroTemporalEntrega() {
        String sql = "SELECT count() cant FROM entrega_temp";
        
        byte cant = 0;
        
        try {
            PreparedStatement pst = sqlite.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                cant = rs.getByte("cant");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(RubroDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return cant;
    }

    @Override
    public void deleteProductoEntregaTemporal(String nombreProducto, String nombreUnidadMedida) {
        String sql = "delete from entrega_temp "
                + "where nombre_producto = '"+nombreProducto+"' and "
                + "nombre_unidad_medida = '"+nombreUnidadMedida+"'";
        
        try {
            PreparedStatement ps = sqlite.prepareStatement(sql);
            ps.executeUpdate();                        
        } catch (SQLException ex) {
            Logger.getLogger(TemporalDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}
