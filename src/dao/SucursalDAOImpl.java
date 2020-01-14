/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.Sucursal;
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
public class SucursalDAOImpl implements SucursalDAO{
    
    private DatabaseUtils databaseUtils;
    private Connection connectionDB;

    public SucursalDAOImpl(Connection connectionDB) {
        this.connectionDB = connectionDB;
        this.databaseUtils = new DatabaseUtils();
    }

    @Override
    public void insertarSucursal(Sucursal s) {
        String sql = "INSERT INTO sucursal(nit_sucursal, nombre_sucursal, direccion, tipo_productos, "
                + "id_lugar, actividad_economica, estado, sucursal_actividad) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setString(1, s.getNitSucursal());
            ps.setString(2, s.getNombreSucursal());
            ps.setString(3, s.getDireccion());
            ps.setString(4, s.getTipoProductos());
            ps.setByte(5, s.getIdLugar());
            ps.setString(6, s.getActividadEconomica());
            ps.setByte(7, s.getEstado());
            ps.setString(8, s.getActividadSucursal());
            ResultSet rs = ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(SucursalDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void eliminarSucursal(byte idSucursal) {
        String sql = "delete from sucursal where id = "+String.valueOf(idSucursal);
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SucursalDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void editarSucursal(Sucursal s) {
        String sql = "update sucursal "
                + "set nit_sucursal =?,"
                + "nombre_sucursal=?,"
                + "direccion=?,"
                + "tipo_productos=?,"
                + "id_lugar=?,"
                + "actividad_economica=?,"
                + "estado=?,"
                + "sucursal_actividad=? "
                + "where id = ?";
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setString(1, s.getNitSucursal());
            ps.setString(2, s.getNombreSucursal());
            ps.setString(3, s.getDireccion());
            ps.setString(4, s.getTipoProductos());
            ps.setByte(5, s.getIdLugar());
            ps.setString(6, s.getActividadEconomica());
            ps.setByte(7, s.getEstado());
            ps.setString(8, s.getActividadSucursal());
            ps.setInt(9, s.getId());
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(SucursalDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public ArrayList<Sucursal> getListSucursal() {
        ArrayList<Sucursal> listaSucursal = new ArrayList<>();
        String sql = "select s.id, s.nit_sucursal, s.nombre_sucursal, s.direccion, s.tipo_productos, "
                + "s.id_lugar, l.descripcion as nombre_lugar, s.actividad_economica, s.estado, s.sucursal_actividad "
                + "from sucursal s join lugar l on s.id_lugar = l.id";
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Sucursal suc = new Sucursal();
                
                suc.setActividadEconomica(rs.getString("actividad_economica"));
                suc.setActividadSucursal(rs.getString("sucursal_actividad"));
                suc.setDireccion(rs.getString("direccion"));
                suc.setEstado(rs.getByte("estado"));
                suc.setId(rs.getInt("id"));
                suc.setIdLugar(rs.getByte("id_lugar"));
                suc.setNombreLugar(rs.getString("nombre_lugar"));
                suc.setNitSucursal(rs.getString("nit_sucursal"));
                suc.setNombreSucursal(rs.getString("nombre_sucursal"));
                suc.setTipoProductos(rs.getString("tipo_productos"));
                
                listaSucursal.add(suc);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SucursalDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaSucursal;
    }

    @Override
    public ArrayList<Sucursal> getSucursal(byte idSucursal) {
        ArrayList<Sucursal> listaSucursal = new ArrayList<>();
        String sql = "select s.id, s.nit_sucursal, s.nombre_sucursal, s.direccion, s.tipo_productos, "
                + "s.id_lugar, l.descripcion as nombre_lugar, s.actividad_economica, s.estado, s.sucursal_actividad "
                + "from sucursal s join lugar l on s.id_lugar = l.id where s.id = ?";
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setByte(1, idSucursal);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Sucursal suc = new Sucursal();
                
                suc.setActividadEconomica(rs.getString("actividad_economica"));
                suc.setActividadSucursal(rs.getString("sucursal_actividad"));
                suc.setDireccion(rs.getString("direccion"));
                suc.setEstado(rs.getByte("estado"));
                suc.setId(rs.getInt("id"));
                suc.setIdLugar(rs.getByte("id_lugar"));
                suc.setNombreLugar(rs.getString("nombre_lugar"));
                suc.setNitSucursal(rs.getString("nit_sucursal"));
                suc.setNombreSucursal(rs.getString("nombre_sucursal"));
                suc.setTipoProductos(rs.getString("tipo_productos"));
                
                listaSucursal.add(suc);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SucursalDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaSucursal;
    }

    @Override
    public HashMap<String, Integer> sucursalClaveValor() {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        String sql = null;
        
        sql = "Select id, nombre_sucursal From sucursal Where estado = 1";
//         sql = "SELECT s.id, s.nombre_sucursal \n" +
//                "FROM sucursal s join dosificacion d on s.id = d.id_sucursal \n" +
//                "WHERE d.estado = 1 and d.fecha_limite_emision > now() \n" +
//                "ORDER BY s.nombre_sucursal";
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ResultSet rs  = ps.executeQuery();
            
            Sucursal sucursal;
            while(rs.next()){
//                sucursal = new Sucursal(rs.getInt("id"), rs.getString("descripcion"));                
            map.put(rs.getString("nombre_sucursal"), rs.getInt("id"));
            }
        } catch (Exception e) {
        }
        return map;
    }

    @Override
    public String getNitSucursal(byte idLugar) {
        String nitSucursal = null;
        String sql = "select nit_sucursal from sucursal where id_lugar = ?";
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setByte(1, idLugar);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                nitSucursal = rs.getString("nit_sucursal");
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(SucursalDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nitSucursal;
    }

    @Override
    public byte getIdSucursal(byte idLugar) {
        byte idSucursal = 0;
        String sql = "select id from sucursal where id_lugar = ?";
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setByte(1, idLugar);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                idSucursal = rs.getByte("id");
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(SucursalDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idSucursal;
    }

    @Override
    public HashMap<String, Integer> sucursalClaveValorActiva() {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        String sql = null;
        
        //sql = "Select id, nombre_sucursal From sucursal Where estado = 1";
         sql = "SELECT s.id, s.nombre_sucursal \n" +
                "FROM sucursal s join dosificacion d on s.id = d.id_sucursal \n" +
                "WHERE d.estado = 1 and d.fecha_limite_emision > now() \n" +
                "ORDER BY s.nombre_sucursal";
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ResultSet rs  = ps.executeQuery();
            
            Sucursal sucursal;
            while(rs.next()){
//                sucursal = new Sucursal(rs.getInt("id"), rs.getString("descripcion"));                
            map.put(rs.getString("nombre_sucursal"), rs.getInt("id"));
            }
        } catch (Exception e) {
        }
        return map;
    }
    
}
