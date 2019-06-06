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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jcapax
 */
public class VencimientoDAOImpl implements VencimientoDAO{
    
    private DatabaseUtils databaseUtils;
    private Connection connectionDB;

    public VencimientoDAOImpl(DatabaseUtils databaseUtils, Connection connectionDB) {
        this.databaseUtils = databaseUtils;
        this.connectionDB = connectionDB;
    }
    
    @Override
    public void insertarVencimiento(Vencimiento v) {
            String sql = "Insert Into vencimiento(id_transaccion, id_producto, id_unidad_medida, fecha, cantidad)"
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
    public ArrayList<Vencimiento> getRegistrosSalidasProductos(int idTransaccion) {
        ArrayList<Vencimiento> listVenc = new ArrayList<>();
            
        String sql = "Select id_producto, id_unidad_medida, cantidad From detalle_transaccion "
                + "Where id_transaccion = ? and "
                + "id_producto in (Select id_producto From producto Where caducidad = 1)";
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            
            
        } catch (SQLException ex) {
            Logger.getLogger(VencimientoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listVenc;
    }
    
}
