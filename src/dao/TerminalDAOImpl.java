/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.conectorDB.DatabaseUtils;
import java.net.InetAddress;
import java.net.UnknownHostException;
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
public class TerminalDAOImpl implements TerminalDAO{
    
    private DatabaseUtils databaseUtils;
    private Connection connectionDB;

    public TerminalDAOImpl(Connection connectionDB) {
        this.databaseUtils = new DatabaseUtils();
        this.connectionDB = connectionDB;
    }
    
    @Override
    public byte getIdTerminal(String hostName) {
        byte idTerminal = 0;
        try {
            
            String sql = "Select id From terminal Where descripcion = '"+hostName+"'";
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                idTerminal = rs.getByte("id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(TerminalDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return idTerminal;
    }

    @Override
    public String getNameHost() {
        String hostName = null;
        
        InetAddress address;
		
        try {
                address = InetAddress.getLocalHost();
                hostName = address.getHostName();
        } catch (UnknownHostException e) {
                e.printStackTrace();
        }
        
        return hostName;
    }
    
}
