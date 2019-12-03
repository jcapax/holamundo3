/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.conectorDB.DatabaseUtils;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jcarlos.porcel
 */
public class UtilsDAOImpl implements UtilsDAO{
    
    private DatabaseUtils databaseUtils;
    private Connection connectionDB;

    public UtilsDAOImpl(Connection connectionDB) {
        this.connectionDB = connectionDB;
    }
    
    @Override
    public Date getFechaActual() {
        String sql = "SELECT date(now()) as fecha_actual";
        Date fecha = null;
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                fecha = rs.getDate("fecha_actual");
            }                    
        } catch (SQLException ex) {
            Logger.getLogger(UtilsDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return fecha;        
    }    
}
