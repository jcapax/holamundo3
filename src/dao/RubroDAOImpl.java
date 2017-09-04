/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.Rubro;

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
public class RubroDAOImpl implements RubroDAO{
   
    private DatabaseUtils databaseUtils;
    private Connection connectionDB;
    
    public RubroDAOImpl(Connection _connectionDB) {
            this.databaseUtils = new DatabaseUtils();
            this.connectionDB = _connectionDB;
	}

    @Override
    public ArrayList<Rubro> getListaRubros() {
        String sql = "SELECT * FROM rubroproducto";
        
        ArrayList<Rubro> lrubro = new ArrayList<Rubro>();
        
        try {
            PreparedStatement pst = connectionDB.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                Rubro rubro = new Rubro();
                
                rubro.setId(rs.getInt("id"));
                rubro.setDescripcion(rs.getString("descripcion"));
                rubro.setUsuario(rs.getString("usuario"));
                
                lrubro.add(rubro);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(RubroDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lrubro;
    }

    @Override
    public void insertarRubro(Rubro rubro) {
        String sql = "INSERT INTO `rubroproducto`(`DESCRIPCION`, `USUARIO`) VALUES (?, ?)";
        
        try {
             PreparedStatement ps = connectionDB.prepareStatement(sql);
             ps.setString(1, rubro.getDescripcion());
             ps.setString(2, rubro.getUsuario());
             int n = ps.executeUpdate();
             
             if(n != 0){
//                 System.out.println("registrado");
             }
             
        } catch (SQLException ex) {
            Logger.getLogger(RubroDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }        
        
    }

    @Override
    public void eliminarRubro(int id) {
        String sql = "DELETE FROM rubroproducto WHERE id = ?";
        
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
    public HashMap<String, Integer> rubroClaveValor() {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        String sql = null;
        
         sql = "SELECT id, descripcion FROM rubroProducto ORDER BY descripcion";
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ResultSet rs  = ps.executeQuery();
            
            Rubro rubro;
            while(rs.next()){
                rubro = new Rubro(rs.getInt("id"), rs.getString("descripcion"));                
                map.put(rubro.getDescripcion(), rubro.getId());
            }
            
        } catch (Exception e) {
        }
        
        return map;    }
    
}
