/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.Lugar;
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
public class LugarDAOImpl implements LugarDAO{
    private DatabaseUtils databaseUtils;
    private Connection connectionDB;

    public LugarDAOImpl(Connection connectionDB) {
        this.connectionDB = connectionDB;
        this.databaseUtils = new DatabaseUtils();
    }
    
    @Override
    public void insertarLugar(Lugar lugar) {
        String sql = "insert into lugar(descripcion) values(?)";
        PreparedStatement ps;
        try {
            ps = connectionDB.prepareStatement(sql);
            ps.setString(1, lugar.getDescripcion());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LugarDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ArrayList<Lugar> getListLugar() {
        ArrayList<Lugar> listLugar = new ArrayList<>();
        String sql = "select id, descripcion from lugar";
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Lugar l = new Lugar();
                l.setId(rs.getInt("id"));
                l.setDescripcion(rs.getString("descripcion"));
                listLugar.add(l);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LugarDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listLugar;
    }

    @Override
    public void eliminarLugar(int idLugar) {
        String sql = "delete from lugar where id = ?";
        PreparedStatement ps;
        try {
            ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, idLugar);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LugarDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void editarLugar(Lugar lugar) {
        String sql = "update lugar set descripcion = ?";
        PreparedStatement ps;
        try {
            ps = connectionDB.prepareStatement(sql);
            ps.setString(1, lugar.getDescripcion());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LugarDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public HashMap<String, Integer> lugarClaveValor() {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        
        String sql = "SELECT id, descripcion FROM lugar ORDER BY descripcion";
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ResultSet rs  = ps.executeQuery();
            Lugar lugar;
            while(rs.next()){
                lugar = new Lugar(rs.getInt("id"), rs.getString("descripcion"));
                map.put(lugar.getDescripcion(), lugar.getId());
            }
            
        } catch (Exception e) {
        }
        return map;
    }

    @Override
    public boolean existsLugar() {
        boolean aux = false;
        String sql = "select id from lugar limit 1";
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                aux = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LugarDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return aux;
    }

    @Override
    public byte getIdLugar(byte idTerminal) {
        byte idLugar = 0;
        String sql = "SELECT t.id_lugar FROM terminal t join lugar l on t.id_lugar = l.id "
                + "WHERE t.id = ?";
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setByte(1, idTerminal);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                idLugar = rs.getByte("id_lugar");
            }
        } catch (SQLException ex) {
            Logger.getLogger(LugarDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idLugar;
    }

    @Override
    public byte getIdLugarTransaccion(int idTransaccion) {
        byte idLugar = 0;
        String sql = "Select id_lugar From transaccion Where id = "+String.valueOf(idTransaccion);
            PreparedStatement ps;
        try {
            ps = connectionDB.prepareStatement(sql);
//            ps.setInt(1, idTransaccion);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                idLugar = rs.getByte("id_lugar");
            }

        } catch (SQLException ex) {
            Logger.getLogger(LugarDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idLugar;        
    }

    @Override
    public byte getIdLugarFactura(int idFactura) {
        byte idLugar = 0;
        String sql = "SELECT a.id,a.id_dosificacion, l.id as id_lugar \n" +
                    "FROM factura_venta a \n" +
                    "	join dosificacion b on a.id_dosificacion = b.id \n" +
                    "    join sucursal s on b.id_sucursal = s.id \n" +
                    "    join lugar l on l.id = s.id_lugar \n" +
                    "WHERE a.id = "+String.valueOf(idFactura);
            PreparedStatement ps;
        try {
            ps = connectionDB.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                idLugar = rs.getByte("id_lugar");
            }

        } catch (SQLException ex) {
            Logger.getLogger(LugarDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idLugar;
    }
}
