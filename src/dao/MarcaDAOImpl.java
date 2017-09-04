/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.Marca;

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
public class MarcaDAOImpl implements MarcaDAO{
    
    private DatabaseUtils databaseUtils;
    private Connection connectionDB;
    
    public MarcaDAOImpl(Connection _connectionDB) {
            this.databaseUtils = new DatabaseUtils();
            this.connectionDB = _connectionDB;
	}

    @Override
    public ArrayList<Marca> getListaMarcas() {
        String sql = "SELECT * FROM marca";
        
        ArrayList<Marca> lmarca = new ArrayList<Marca>();
        
        try {
            PreparedStatement pst = connectionDB.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                Marca marca = new Marca();
                
                marca.setId(rs.getInt("id"));
                marca.setDescripcion(rs.getString("descripcion"));
                marca.setUsuario(rs.getString("usuario"));
                
                lmarca.add(marca);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(RubroDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lmarca;
    }

    @Override
    public void insertarMarca(Marca marca) {
        String sql = "INSERT INTO `marca`(`DESCRIPCION`, `USUARIO`) VALUES (?, ?)";
        
        try {
             PreparedStatement ps = connectionDB.prepareStatement(sql);
             ps.setString(1, marca.getDescripcion());
             ps.setString(2, marca.getUsuario());
             int n = ps.executeUpdate();
             
             if(n != 0){
//                 System.out.println("registrado");
             }
             
        } catch (SQLException ex) {
            Logger.getLogger(RubroDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void eliminarMarca(int id) {
        String sql = "DELETE FROM marca WHERE id = ?";
        
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
    public HashMap<String, Integer> marcaClaveValor() {
    HashMap<String, Integer> map = new HashMap<String, Integer>();
        
        String sql = "SELECT id, descripcion FROM marca ORDER BY descripcion";
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ResultSet rs  = ps.executeQuery();
            
            Marca marca;
            while(rs.next()){
                marca = new Marca(rs.getInt("id"), rs.getString("descripcion"));
                
                map.put(marca.getDescripcion(), marca.getId());
            }
            
        } catch (Exception e) {
        }
        
        
        return map;    
    }
    
}
