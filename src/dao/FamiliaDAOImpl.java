/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.Familia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
//import personaljava.Conectar;

/**
 *
 * @author jcapax
 */
public class FamiliaDAOImpl implements FamiliaDAO{
    
    private DatabaseUtils databaseUtils;
    private Connection connectionDB;
    
    public FamiliaDAOImpl(Connection _connectionDB) {
            this.databaseUtils = new DatabaseUtils();
            this.connectionDB = _connectionDB;
	}

    @Override
    public ArrayList<Familia> getListaFamilias() {
        String sql = "SELECT * FROM familia";
        
        ArrayList<Familia> lfamilia = new ArrayList<Familia>();
        
        try {
            PreparedStatement pst = connectionDB.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                Familia familia = new Familia();
                
                familia.setId(rs.getInt("id"));
                familia.setDescripcion(rs.getString("descripcion"));                
                
                lfamilia.add(familia);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(RubroDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lfamilia;
    }

    @Override
    public void insertarFamilia(Familia familia) {
        String sql = "insert into `familia`(`descripcion`) values (?)";
        
        try {
             PreparedStatement ps = connectionDB.prepareStatement(sql);
             ps.setString(1, familia.getDescripcion());             
             int n = ps.executeUpdate();
             
             if(n != 0){
//                 System.out.println("registrado");
             }
             
        } catch (SQLException ex) {
            Logger.getLogger(RubroDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void eliminarFamilia(int id) {
        String sql = "delete from familia where id = ?";
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setString(1, String.valueOf(id));
            int n = ps.executeUpdate();
            if(n != 0){
//                 System.out.println("eliminado");
             }
        } catch (SQLException ex) {
            Logger.getLogger(RubroDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public HashMap<String, Integer> familiaClaveValor() {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        
        String sql = "SELECT id, descripcion FROM Familia ORDER BY descripcion";
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ResultSet rs  = ps.executeQuery();
            
            Familia familia;
            while(rs.next()){
                familia = new Familia(rs.getInt("id"), rs.getString("descripcion"));
                
                map.put(familia.getDescripcion(), familia.getId());
            }
            
        } catch (Exception e) {
        }
        return map;    
    }
    
}
