/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.Laboratorio;

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
public class LaboratorioDAOImpl implements LaboratorioDAO{
    
    private DatabaseUtils databaseUtils;
    private Connection connectionDB;
    
    public LaboratorioDAOImpl(Connection _connectionDB) {
            this.databaseUtils = new DatabaseUtils();
            this.connectionDB = _connectionDB;
	}

    @Override
    public ArrayList<Laboratorio> getListaLaboratorios() {
        String sql = "SELECT * FROM laboratorio";
        
        ArrayList<Laboratorio> llaboratorio = new ArrayList<Laboratorio>();
        
        try {
            PreparedStatement pst = connectionDB.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                Laboratorio laboratorio = new Laboratorio();
                
                laboratorio.setId(rs.getInt("id"));
                laboratorio.setDescripcion(rs.getString("descripcion"));                
                
                llaboratorio.add(laboratorio);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(RubroDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return llaboratorio;
    }

    @Override
    public void insertarLaboratorio(Laboratorio laboratorio) {
        String sql = "insert into `laboratorio`(`descripcion`) values (?)";
        
        try {
             PreparedStatement ps = connectionDB.prepareStatement(sql);
             ps.setString(1, laboratorio.getDescripcion());             
             int n = ps.executeUpdate();
             
             if(n != 0){
//                 System.out.println("registrado");
             }
             
        } catch (SQLException ex) {
            Logger.getLogger(RubroDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void eliminarLaboratorio(int id) {
        String sql = "delete from laboratorio where id = ?";
        
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
    public HashMap<String, Integer> laboratorioClaveValor() {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        
        String sql = "SELECT id, descripcion FROM laboratorio ORDER BY descripcion";
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ResultSet rs  = ps.executeQuery();
            
            Laboratorio laboratorio;
            while(rs.next()){
                laboratorio = new Laboratorio(rs.getInt("id"), rs.getString("descripcion"));
                
                map.put(laboratorio.getDescripcion(), laboratorio.getId());
            }
            
        } catch (Exception e) {
        }
        return map;    
    }
    
}
