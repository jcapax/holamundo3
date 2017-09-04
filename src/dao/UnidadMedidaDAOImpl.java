/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;


import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.UnidadMedida;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jcapax
 */
public class UnidadMedidaDAOImpl implements UnidadMedidaDAO{
    
    private DatabaseUtils databaseUtils;
    private Connection connectionDB;

    
    public UnidadMedidaDAOImpl(Connection _connectionDB) {
        this.databaseUtils = new DatabaseUtils();
        this.connectionDB = _connectionDB;
    }
    @Override
    public ArrayList<UnidadMedida> getListUnidadMedida() {
        String sql = "SELECT * FROM unidadmedida";
        
        ArrayList<UnidadMedida> lunimed = new ArrayList<UnidadMedida>();
        
        try {
            PreparedStatement pst = connectionDB.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                UnidadMedida umedida = new UnidadMedida();
                
                umedida.setId(rs.getInt("id"));
                umedida.setDescripcion(rs.getString("descripcion"));
                umedida.setSimbolo(rs.getString("simbolo"));
                umedida.setUsuario(rs.getString("usuario"));
                
                lunimed.add(umedida);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(RubroDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lunimed;
    }

    @Override
    public void insertarUnidadMedida(UnidadMedida uMedida) {
        String sql = "INSERT INTO unidadMedida(DESCRIPCION, SIMBOLO, USUARIO) VALUES (?, ?, ?)";
        
        try {
             PreparedStatement ps = connectionDB.prepareStatement(sql);
             ps.setString(1, uMedida.getDescripcion());
             ps.setString(2, uMedida.getSimbolo());
             ps.setString(3, uMedida.getUsuario());
             
             int n = ps.executeUpdate();
             
             if(n != 0){
//                 System.out.println("registrado");
             }
             
        } catch (SQLException ex) {
            Logger.getLogger(RubroDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void eliminarUnidadMedida(int id) {
        String sql = "DELETE FROM unidadMedida WHERE id = ?";
        
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
    public HashMap<String, Integer> unidadMedidaClaveValor() {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        
        String sql = "SELECT id, descripcion FROM unidadMedida ORDER BY simbolo";
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ResultSet rs  = ps.executeQuery();
            
            UnidadMedida unidadMedida;
            while(rs.next()){
                unidadMedida = new UnidadMedida(rs.getInt("id"), rs.getString("descripcion"));
                
                map.put(unidadMedida.getSimbolo(), unidadMedida.getId());
            }
            
        } catch (Exception e) {
        }
        
        return map;    
    }
    
}
