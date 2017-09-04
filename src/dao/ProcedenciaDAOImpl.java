/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.Procedencia;

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
public class ProcedenciaDAOImpl implements ProcedenciaDAO{
    
    private DatabaseUtils databaseUtils;
    private Connection connectionDB;
    
    String sql = null;
    
//    Conectar conn = new Conectar();
//    
//    Connection cnx = conn.conexion();
    
    public ProcedenciaDAOImpl(Connection _connectionDB) {
            this.databaseUtils = new DatabaseUtils();
            this.connectionDB = _connectionDB;
	}


    @Override
    public ArrayList<Procedencia> getListaProcedencia() {
        sql = "SELECT * FROM procedencia";
        
        ArrayList<Procedencia> lprocedencia = new ArrayList<Procedencia>();
        
        try {
            PreparedStatement pst = connectionDB.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                Procedencia procedencia = new Procedencia();
                
                procedencia.setId(rs.getInt("id"));
                procedencia.setDescripcion(rs.getString("descripcion"));
                procedencia.setUsuario(rs.getString("usuario"));
                
                lprocedencia.add(procedencia);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(RubroDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lprocedencia;    
    }

    @Override
    public void insertarProcedencia(Procedencia procedencia) {
        sql = "INSERT INTO `procedencia`(`DESCRIPCION`, `USUARIO`) VALUES (?, ?)";
        
        try {
             PreparedStatement ps = connectionDB.prepareStatement(sql);
             ps.setString(1, procedencia.getDescripcion());
             ps.setString(2, procedencia.getUsuario());
             int n = ps.executeUpdate();
             
             if(n != 0){
//                 System.out.println("registrado");
             }
             
        } catch (SQLException ex) {
            Logger.getLogger(RubroDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void eliminarProcedencia(int id) {
        sql = "DELETE FROM procedencia WHERE id = ?";
        
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
    public HashMap<String, Integer> procedenciaClaveValor() {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        
        sql = "SELECT id, descripcion FROM procedencia ORDER BY descripcion";
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ResultSet rs  = ps.executeQuery();
            
            Procedencia procedencia;
            while(rs.next()){
                procedencia = new Procedencia(rs.getInt("id"), rs.getString("descripcion"));
                
                map.put(procedencia.getDescripcion(), procedencia.getId());
            }
            
        } catch (Exception e) {
        }
        
        
        return map;    }
    
}
