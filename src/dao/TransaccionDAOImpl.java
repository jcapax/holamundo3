/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.Transaccion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jcapax
 */
public class TransaccionDAOImpl implements TransaccionDAO{
    
    private DatabaseUtils databaseUtils;
    private Connection connectionDB;
        
    public TransaccionDAOImpl(Connection _connectionDB) {
        this.databaseUtils = new DatabaseUtils();
        this.connectionDB = _connectionDB;
    }

    @Override
    public int insertarTransaccion(Transaccion transaccion) {
        int n = 0;
        int idTransaccion = 0;
        String sql = "insert into transaccion(fecha, idTipoTransaccion, nroTipoTransaccion, idLugar, idTerminal, "
                + "tipoMovimiento, estado, usuario, descripcionTransaccion) "
                + "values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
           
            ps.setDate(1, transaccion.getFecha());
            ps.setInt(2, transaccion.getIdTipoTransaccion());
            ps.setInt(3, transaccion.getNroTipoTransaccion());
            ps.setByte(4, transaccion.getIdLugar());
            ps.setInt(5, transaccion.getIdTerminal());
            ps.setInt(6, transaccion.getTipoMovimiento());
            ps.setString(7, transaccion.getEstado());
            ps.setString(8, transaccion.getUsuario());
            ps.setString(9, transaccion.getDescripcionTransaccion());
            
            n = ps.executeUpdate();
            if(n!=0){
//                System.out.println("transaccion generada con exito");
                
                String sql_id = "select id from transaccion order by id desc limit 1";
                
                PreparedStatement ps_id = connectionDB.prepareStatement(sql_id);
                ResultSet rs = ps_id.executeQuery();
                
                if(rs.next()){
                
//                    System.out.println("nro generado: "+rs.getInt("id"));
                
                    idTransaccion = rs.getInt("id");   
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(TransaccionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return idTransaccion;
    }

    @Override
    public int getNroTipoTransaccion(int idTipoTransaccion) {
        int nro = 0;
        String sql = "select nroTipoTransaccion from transaccion where idTipoTransaccion = ? "
                + "order by nroTipoTransaccion DESC LIMIT 1";
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, idTipoTransaccion);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                nro = rs.getInt("nroTipoTransaccion");
            }
        } catch (SQLException ex) {
            Logger.getLogger(TransaccionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        nro++;
        return nro;
    }

    @Override
    public int getTipoMovimiento(int idTipoTransaccion) {
        int tipoMovimiento = 100;
        
        String sql = "SELECT tipoMovimiento FROM tipoTransaccion WHERE id = ?";
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, idTipoTransaccion);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                tipoMovimiento = rs.getInt("tipoMovimiento");
            }
        } catch (SQLException ex) {
            Logger.getLogger(TransaccionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return tipoMovimiento;
    }

    @Override
    public void insertarEntregaTransaccion(int idTransaccion, int idEntregaTransaccion) {
        String sql = "insert into entregaTransaccion(idEntregaTransaccion, idTransaccion, "
                + "nroEntrega, nroRecepcion)"
                + "values(?, ?, 0, 0)";
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, idEntregaTransaccion);
            ps.setInt(2, idTransaccion);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TransaccionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public double getValorTotalTransaccion(int idTransaccion) {
        double valorTotal = 0;
        
        String sql = "select valorTotal from vTransaccion where id = ?";
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, idTransaccion);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                valorTotal = rs.getDouble("valorTotal");
            }
        } catch (SQLException ex) {
            Logger.getLogger(TransaccionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        return valorTotal;
    }

    @Override
    public Date getFechaTransaccion(int idTransaccion) {
        Date fecha = null;
        
        String sql = "select fecha from transaccion where id = ?";
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, idTransaccion);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                fecha = rs.getDate("fecha");
            }
        } catch (SQLException ex) {
            Logger.getLogger(TransaccionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return fecha;
    }

    
    
}
