/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.Caja;
import almacenes.model.PendientePago;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jcapax
 */
public class CreditoDAOImpl implements CreditoDAO{

    private DatabaseUtils databaseUtils;
    private Connection connectionDB;

    public CreditoDAOImpl(Connection _connectionDB) {
        this.databaseUtils = new DatabaseUtils();
        this.connectionDB = _connectionDB;
    }
    
    @Override
    public void insertarCredito(int idTransaccion, int idClienteProveedor, String detalle) {
        try {
            String sql = "insert into credito values(?, ?, ?, 0)";
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, idTransaccion);
            ps.setInt(2, idClienteProveedor);
            ps.setString(3, detalle);
            ps.executeUpdate();
            
            } catch (SQLException ex) {
            Logger.getLogger(CreditoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ArrayList<PendientePago> getListaPendientesPago(int idTipoTransaccion) {
        ArrayList<PendientePago> lPendientePago = new ArrayList<PendientePago>();
        String sql = "select * from v_pendiente_pago where id_tipo_transaccion = ?";
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, idTipoTransaccion);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                PendientePago pp = new PendientePago();
                
                pp.setDetalle(rs.getString("detalle"));
                pp.setDiferencia(rs.getDouble("diferencia"));
                pp.setFecha(rs.getDate("fecha"));
                pp.setIdTipoTransaccion(rs.getInt("id_tipo_transaccion"));
                pp.setIdTransaccion(rs.getInt("id_transaccion"));
                pp.setImporteCaja(rs.getDouble("importe_caja"));
                pp.setNombreCompleto(rs.getString("nombre_completo"));
                pp.setNroTipoTransaccion(rs.getInt("nro_tipo_transaccion"));
                pp.setRazonSocial(rs.getString("razon_social"));
                pp.setTelefonos(rs.getString("telefonos"));
                pp.setValorTotal(rs.getDouble("valor_total"));
                
                lPendientePago.add(pp);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CreditoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return lPendientePago;
    }

    @Override
    public ArrayList<Caja> getHistorialPagos(int idTransaccion) {
        ArrayList<Caja> listaHistorialPagos = new ArrayList<Caja>();
        
        String sql = "select * from caja where id_transaccion = ? order by id_transaccion";
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, idTransaccion);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                
                Caja c = new Caja();
                c.setFecha(rs.getDate("fecha"));
                c.setImporte(rs.getDouble("importe"));
                
                listaHistorialPagos.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CreditoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaHistorialPagos;
    }

    @Override
    public double getSaldoTransaccion(int idtransaccion) {
        double saldo = 0;
        String sql = "select * from v_pendiente_pago where id_transaccion = ?";
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, idtransaccion);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                saldo = rs.getDouble("diferencia");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CreditoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return saldo;
    }

    @Override
    public void insertarCredito(int idTransaccion, int idClienteProveedor, String detalle, int entregaPendiente) {
        try {
            String sql = "insert into credito values(?, ?, ?, ?)";
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, idTransaccion);
            ps.setInt(2, idClienteProveedor);
            ps.setString(3, detalle);
            ps.setInt(4, entregaPendiente);
            ps.executeUpdate();
            
            } catch (SQLException ex) {
            Logger.getLogger(CreditoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
