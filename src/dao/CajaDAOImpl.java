/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.Caja;
import almacenes.model.ListaCaja;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jcapax
 */
public class CajaDAOImpl implements CajaDAO{
    
    private DatabaseUtils databaseUtils;
    private Connection connectionDB;
        
    public CajaDAOImpl(Connection _connectionDB) {
        this.databaseUtils = new DatabaseUtils();
        this.connectionDB = _connectionDB;
    }

    @Override
    public void insertarCaja(Caja caja) {
        String sql = "insert into caja(fecha, importe, id_transaccion, nro_cobro, "
                + "nro_pago, estado, usuario) "
                + "values(?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setDate(1, caja.getFecha());
            ps.setDouble(2, caja.getImporte());
            ps.setInt(3, caja.getIdTransaccion());
            ps.setInt(4, caja.getNroCobro());
            ps.setInt(5, caja.getNroPago());
            ps.setString(6, caja.getEstado());
            ps.setString(7, caja.getUsuario());
            ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(CajaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }                
    }

    @Override
    public List<ListaCaja> getListaCaja(int idArqueo) {
       List<ListaCaja> listaCaja = new ArrayList<ListaCaja>();
       
       String sql = "select * from v_caja where id_arqueo = ?";
       
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, idArqueo);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                ListaCaja lc = new ListaCaja();
                lc.setDescripcionTransaccion(rs.getString("descripcion_transaccion"));
                lc.setEstado(rs.getString("estado"));
                lc.setFecha(rs.getDate("fecha_caja"));
                lc.setIdArqueo(rs.getInt("id_arqueo"));
                lc.setIdTransaccion(rs.getInt("id_transaccion"));
                lc.setImporteCaja(rs.getDouble("importe_caja"));
                lc.setMovimiento(rs.getString("movimiento"));
                
                listaCaja.add(lc);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CajaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       return listaCaja;
    }

    @Override
    public int getIdCaja() {
        int id = 0;
        String sql = "select id from caja order by id desc limit 1";       
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);            
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                id = rs.getInt("id");                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CajaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }          
        return id;
    }

    @Override
    public void registrarCajaDetalle(int idCaja, String detalle) {
        String sql = "insert into caja_detalle(id_caja, detalle) "
                + "values(?, ?)";
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, idCaja);
            ps.setString(2, detalle);
            ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(CajaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
}
