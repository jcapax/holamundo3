/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.AnularTransaccion;
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
public class AnularTransaccionDAOImpl implements AnularTransaccionDAO{

    private DatabaseUtils databaseUtils;
    private Connection connectionDB;
        
    public AnularTransaccionDAOImpl(Connection _connectionDB) {
        this.databaseUtils = new DatabaseUtils();
        this.connectionDB = _connectionDB;
    }
    
    @Override
    public ArrayList<AnularTransaccion> getListaTransaccionesAnular(byte idTipoTransaccion, String usuario) {
        
        UsuariosDAOImpl us = new UsuariosDAOImpl(connectionDB);
        int rol = us.getRolUsuario(usuario);       
        
        
        ArrayList<AnularTransaccion> listaTrans = new ArrayList<>();
        
        ConfiguracionGeneralDAOImpl conf = new ConfiguracionGeneralDAOImpl(connectionDB);
        int nro = 0;
        nro = conf.getNroDiasNullTransaccion();
        
        String sql = "SELECT e.idEntregaTransaccion, e.idTransaccion, t.fecha, t.idTipoTransaccion, t.valorTotal, "
                + "f.nit, f.razonSocial, f.nroFactura, f.importeTotal "
                + "FROM entregaTransaccion e "
                + " join vtransaccion t on e.idTransaccion = t.ID "
                + " left join facturaVenta f on f.idTransaccion = t.ID "
                + "WHERE datediff(now(),t.fecha) <= ?"
                + "  and t.idTipoTransaccion = ? ";
        if(rol != 1){
            String aux = "and t.usuario = '"+usuario+"'";
            sql = sql.concat(aux);
        }
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, nro);
            ps.setByte(2, idTipoTransaccion);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                AnularTransaccion at = new AnularTransaccion();
                    at.setFecha(rs.getDate("fecha"));
                    at.setIdEntregaTransaccion(rs.getInt("idEntregaTransaccion"));
                    at.setIdTipoTransaccion(rs.getByte("idTipoTransaccion"));
                    at.setIdTransaccion(rs.getInt("idTransaccion"));
                    at.setImporteTotalFactura(rs.getDouble("importeTotal"));
                    at.setNit(rs.getString("nit"));
                    at.setNroFactura(rs.getInt("nroFactura"));
                    at.setRazonSocial(rs.getString("razonSocial"));
                    at.setValorTotal(rs.getDouble("valorTotal"));
                listaTrans.add(at);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AnularTransaccionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listaTrans;
    }

    @Override
    public void anularTrans(int idTransaccion, int idEntregaTransaccion) {
        String sql = "update transaccion set estado = 'N' "
                + "where id in(?, ?)";
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, idTransaccion);
            ps.setInt(2, idEntregaTransaccion);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AnularTransaccionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void anularFactura(int idTransaccion) {
        String sql = "update facturaVenta "
                + "set nit = 0, razonSocial = 'ANULADA', estado = 'A', importeTotal = 0, "
                + "importeIce = 0, importeExportaciones = 0, importeVentasTasaCero= 0, "
                + "importeSubtotal = 0, importeRebajas = 0, importeBaseDebitoFiscal = 0, "
                + "debitoFiscal = 0, codigoControl = '0' "
                + "where idTransaccion = ?";
        
        PreparedStatement ps;
        try {
            ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, idTransaccion);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AnularTransaccionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }

    @Override
    public void anularCaja(int idTransaccion) {
        String sql = "update caja set estado = 'N' where idTransaccion = ?";
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, idTransaccion);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AnularTransaccionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
