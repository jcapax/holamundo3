/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.IngresoEgreso;
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
        
        String sql = "select id, claseProducto, descripcion, tipoCuenta "
                + "from producto where estado = 'V' and tipoCuenta in('A', '"+tipoCuenta+"')";
        
//        System.out.println(sql);
        
        PreparedStatement ps;
        try {
            ps = connectionDB.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                IngresoEgreso cie = new IngresoEgreso();
                
                cie.setClaseProducto(rs.getString("claseProducto"));
                cie.setDescripcion(rs.getString("descripcion"));
                cie.setIdProducto(rs.getInt("id"));
                cie.setTipoCuenta(rs.getString("tipoCuenta"));
                
                listaCuentas.add(cie);
            }
        } catch (SQLException ex) {
            Logger.getLogger(IngresoEgresoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaCuentas;
    }

    @Override
    public void registrarNuevaCuenta(String nombreCuenta, String idTipoCuenta) {
        String sql = "insert into producto(claseProducto, descripcion, tipoCuenta, estado) "
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
    
}
