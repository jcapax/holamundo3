/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.DetalleTransaccion;
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
public class DetalleTransaccionDAOImpl implements DetalleTransaccionDAO{
    
    private DatabaseUtils databaseUtils;
    private Connection connectionDB;
        
    public DetalleTransaccionDAOImpl(Connection _connectionDB) {
        this.databaseUtils = new DatabaseUtils();
        this.connectionDB = _connectionDB;
    }

    @Override
    public void insertarDetalleTransaccion(ArrayList<DetalleTransaccion> detTrans) {
        String sql = "insert into detalleTransaccion("
                + "idTransaccion, idProducto, idUnidadMedida, cantidad, valorUnitario, valorTotal, tipoValor) "
                + "values(?, ?, ?, ?, ?, ?, ?)";
        
        for(int i = 0; i< detTrans.size(); i++){
            try {
                PreparedStatement ps = connectionDB.prepareStatement(sql);
                ps.setInt(1, detTrans.get(i).getIdTransaccion());
                ps.setInt(2, detTrans.get(i).getIdProducto());
                ps.setInt(3, detTrans.get(i).getIdUnidadMedida());
                ps.setDouble(4, detTrans.get(i).getCantidad());
                ps.setDouble(5, detTrans.get(i).getValorUnitario());
                ps.setDouble(6, detTrans.get(i).getValorTotal());
                ps.setString(7, detTrans.get(i).getTipoValor());
                ps.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(DetalleTransaccionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }

    @Override
    public ArrayList<DetalleTransaccion> getDetalleTransaccion(int idTransaccion) {
        ArrayList<DetalleTransaccion> detTransaccion = new ArrayList<DetalleTransaccion>();
        
        String sql = "SELECT * FROM vDetalleTransaccion where idTransaccion = ?";
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, idTransaccion);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                DetalleTransaccion dt = new DetalleTransaccion();
                dt.setCantidad(rs.getDouble("cantidad"));
                dt.setNombreProducto(rs.getString("descripcion"));
                dt.setSimbolo(rs.getString("simbolo"));
                dt.setValorTotal(rs.getDouble("valorTotal"));
                
                detTransaccion.add(dt);                        
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DetalleTransaccionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return detTransaccion;
    }
    
}
