/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.conectorDB.DatabaseUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jcarlos.porcel
 */
public class FacturaFacilDAOImpl implements FacturaFacilDAO{
    
    private DatabaseUtils databaseUtils;
    private Connection connectionDB;

    public FacturaFacilDAOImpl(Connection connectionDB) {
        this.connectionDB = connectionDB;
        this.databaseUtils = new DatabaseUtils();
    }

    @Override
    public List<String> getListaProductosAutocompletado() {
        List<String> list = new ArrayList<>();
        
        String sql = "Select detale from detalle_factura_facil group by detalle order by detalle";
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                list.add(rs.getString("detalle"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturaFacilDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
    }   
    
}
