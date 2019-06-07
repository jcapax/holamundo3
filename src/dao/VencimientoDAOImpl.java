/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.Vencimiento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jcapax
 */
public class VencimientoDAOImpl implements VencimientoDAO{
    
    private DatabaseUtils databaseUtils;
    private Connection connectionDB;

    public VencimientoDAOImpl(Connection connectionDB) {
        this.databaseUtils = new DatabaseUtils();
        this.connectionDB = connectionDB;
    }
    
    @Override
    public void insertarVencimiento(Vencimiento v) {
        String sql = "Insert Into vencimiento(id_transaccion, id_producto, id_unidad_medida, fecha_vencimiento, cantidad)"
                    + "Values(?,?,?,?,?)";
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, v.getId_transaccion());
            ps.setInt(2, v.getId_producto());
            ps.setInt(3, v.getId_unidad_medida());
            ps.setDate(4, v.getFecha_vencimiento());
            ps.setDouble(5, v.getCantidad());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(VencimientoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void registrosSalidasProductosVencimiento(int idTransaccion) {
        
        String sqlVen = null;    
        byte idLugar;
        int idProducto;
        byte idUnidadMedida;
        boolean flag = false;
        Double cantidad, cantidadAux;
        
        String sql = "Select id_lugar, id_producto, id_unidad_medida, cantidad "
                + "From detalle_transaccion d join transaccion t on d.id_transaccion = t.id "
                + "Where id_transaccion = ? and "
                + "id_producto in (Select id_producto From producto Where caducidad = 1)";
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, idTransaccion);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                idLugar = rs.getByte("id_lugar");
                idProducto = rs.getInt("id_producto");
                idUnidadMedida = rs.getByte("id_unidad_medida");
                cantidad = rs.getDouble("cantidad");
                
                sqlVen = "Select id_lugar, id_producto, id_unidad_medida, fecha_vencimiento, stock "
                        + "From v_stock_vencimiento "
                        + "Where id_lugar = ? and id_producto = ? and id_unidad_medida = ? "
                        + "Order by fecha_vencimiento";
                PreparedStatement psAux = connectionDB.prepareStatement(sqlVen);
                psAux.setByte(1, idLugar);
                psAux.setInt(2, idProducto);
                psAux.setByte(3, idUnidadMedida);
                ResultSet rsAux = psAux.executeQuery();
                cantidadAux = cantidad;
                
                while(rsAux.next()){
                    Vencimiento v = new Vencimiento();
                    if(cantidadAux > rsAux.getDouble("stock")){
                        v.setCantidad(rsAux.getDouble("stock"));
                        cantidadAux = cantidadAux - rsAux.getDouble("stock");
                    }else{
                        v.setCantidad(cantidadAux);
                        flag = true;
                    }
                    v.setId_transaccion(idTransaccion);
                    v.setFecha_vencimiento(rsAux.getDate("fecha_vencimiento"));
                    v.setId_producto(rsAux.getInt("id_producto"));
                    v.setId_unidad_medida(rsAux.getByte("id_unidad_medida"));
                    
                    insertarVencimiento(v);
                    
                    if(flag){
                        rsAux.last();
                    }
                }
                
//                auxiliarRegistro(idLugar, idProducto, idUnidadMedida, cantidad);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VencimientoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

//    private void auxiliarRegistro(byte idLugar, int idProducto, byte idUnidadMedida, Double cantidad) {
//        String sqlVen = "Select id_lugar, id_producto, id_unidad_medida, fecha_vencimiento, stock "
//                        + "From v_stock_vencimiento "
//                        + "Where id_lugar = ? and id_producto";
//    }

    @Override
    public boolean isVencimiento(int idProducto) {
        boolean aux = false;
        
        String sql = "select id from producto where id = ? and caducidad = 1";
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, idProducto);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                aux = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(VencimientoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return aux;
    }
    
}
