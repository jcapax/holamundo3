/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.Terminal;
import java.net.InetAddress;
import java.net.UnknownHostException;
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

    @Override
    public void insertarTerminal(Terminal terminal) {
        String sql = "insert into terminal(id, idLugar, descripcion, usuario) "
                + "values(?, ?, ?, ?)";
        PreparedStatement ps;
        try {
            ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, getIdterminalNueva());
            ps.setInt(2, terminal.getIdLugar());
            ps.setString(3, terminal.getDescripcion());
            ps.setString(4, terminal.getUsuario());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TerminalDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }

    @Override
    public ArrayList<Terminal> getListTerminal() {
        ArrayList<Terminal> lTerminal = new ArrayList<>();
        String sql = "SELECT t.id, t.idLugar, l.descripcion nombreLugar, t.descripcion "
                + "FROM terminal t join lugar l on t.idLugar = l.id";
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Terminal t = new Terminal();
                
                t.setDescripcion(rs.getString("descripcion"));
                t.setId(rs.getInt("id"));
                t.setIdLugar(rs.getInt("idLugar"));
                t.setNombreLugar(rs.getString("nombreLugar"));
                
                lTerminal.add(t);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TerminalDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lTerminal;
    }

    @Override
    public void eliminarTerminal(int idTerminal) {
        String sql = "delete from terminal where id = ?";
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, idTerminal);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TerminalDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public boolean existsTerminal(String hostname) {
        boolean aux = false;
        String sql = "select descripcion from terminal where descripcion = '"+hostname+"'";
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
//            ps.setString(1, hostname);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                System.out.println("description: "+rs.getString("descripcion"));
                aux = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(TerminalDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return aux;
    }

    @Override
    public int getIdterminalNueva() {
        int id = 1;
        String sql = "select id from terminal order by id desc limit 1";
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                id = id + rs.getInt("id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(TerminalDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
        
    }
    
}
