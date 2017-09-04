/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.FacturaVenta;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jcapax
 */
public class FacturaVentaDAOImpl implements FacturaVentaDAO{
    
    private DatabaseUtils databaseUtils;
    private Connection connectionDB;
    
    public FacturaVentaDAOImpl(Connection _connectionDB) {
            this.databaseUtils = new DatabaseUtils();
            this.connectionDB = _connectionDB;
	}

    @Override
    public void insertarFacturaVenta(FacturaVenta facturaVenta) {
        String sql = "insert into facturaVenta(idSucursal, especificacion, correlativoSucursal, "
                + "fechaFactura, nroFactura, nroAutorizacion, estado, nit, razonSocial, "
                + "importeTotal, importeIce, importeExportaciones, importeVentasTasaCero, "
                + "importeSubtotal, importeRebajas, importeBaseDebitoFiscal, debitoFiscal, "
                + "codigoControl, idTransaccion, fechaLimiteEmision, idDosificacion) "
                + "values(?, ?, ?, ?, ?, ?, ?,"
                      + " ?, ?, ?, ?, ?, ?, ?, "
                       + "?, ?, ?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, facturaVenta.getIdSucursal());
            ps.setInt(2, facturaVenta.getEspecificacion());
            ps.setInt(3, facturaVenta.getCorrelativoSucursal());
            ps.setDate(4, facturaVenta.getFechaFactura());
            ps.setInt(5, facturaVenta.getNroFactura());
            ps.setString(6, facturaVenta.getNroAutorizacion());
            ps.setString(7, facturaVenta.getEstado());
            ps.setString(8, facturaVenta.getNit());
            ps.setString(9, facturaVenta.getRazonSocial());
            ps.setDouble(10, facturaVenta.getImporteTotal());
            ps.setDouble(11, facturaVenta.getImporteIce());
            ps.setDouble(12, facturaVenta.getImporteExportaciones());
            ps.setDouble(13, facturaVenta.getImporteVentasTasaCero());
            ps.setDouble(14, facturaVenta.getImporteSubtotal());
            ps.setDouble(15, facturaVenta.getImporteRebajas());
            ps.setDouble(16, facturaVenta.getImporteBaseDebitoFiscal());
            ps.setDouble(17, facturaVenta.getDebitoFiscal());
            ps.setString(18, facturaVenta.getCodigoControl());
            ps.setInt(19, facturaVenta.getIdTransaccion());
            ps.setDate(20, facturaVenta.getFechaLimiteEmision());
            ps.setInt(21, facturaVenta.getIdDosificacion());
            
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(FacturaVentaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }

    @Override
    public String getNroAutorizacion(int idSucursal) {
        String nroAutorizacion = null;
        String sql = "select nroAutorizacion from dosificacion where estado = 1";
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                nroAutorizacion = rs.getString("nroAutorizacion");
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturaVentaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return nroAutorizacion;
    }

    @Override
    public String getLlaveDosificacion(String nroAutorizacion) {
        String llaveDosificacion = null;
        String sql = "select llaveDosificacion from dosificacion where nroAutorizacion = ?";
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setString(1, nroAutorizacion);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                llaveDosificacion = rs.getString("llaveDosificacion");
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturaVentaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return llaveDosificacion;
    }

    @Override
    public int getNewNroFactura(String nroAutorizacion) {
        int nroFactura = 0;
        
        String sql = "select nroFactura from facturaVenta where nroAutorizacion = ? order by nroFactura desc limit 1";
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setString(1, nroAutorizacion);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                nroFactura = rs.getInt("nroFactura");
                nroFactura++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturaVentaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(nroFactura == 0){
            nroFactura = getNroInicioFacturaDosificacion(nroAutorizacion);
        }
        return nroFactura;
    }

    @Override
    public int getNroInicioFacturaDosificacion(String nroAutorizacion) {
        int nroInicioFactura = 0;
        String sql = "select nroInicioFactura from dosificacion where nroAutorizacion = ?";
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setString(1, nroAutorizacion);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                nroInicioFactura = rs.getInt("nroInicioFactura");
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturaVentaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return nroInicioFactura;
    }

    @Override
    public Date getFechaLimiteEmision(String nroAutorizacion) {
        Date fechaLimiteEmision=null;
        String sql = "select fechaLimiteEmision from dosificacion where nroAutorizacion = ?";
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setString(1, nroAutorizacion);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                fechaLimiteEmision = rs.getDate("fechaLimiteEmision");
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturaVentaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fechaLimiteEmision;
    }

    @Override
    public String getRazonSocialFactura(String ni) {
        String razonSocial = "";
        String sql = "select razonSocial from facturaVenta where nit = ? order by id DESC limit 1";
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setString(1, ni);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                razonSocial = rs.getString("razonSocial");
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturaVentaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return razonSocial;
               
    }

    @Override
    public String getCadenaCodigoQr(int idTransaccion) {
        String cadenaQR = "";
        
        String sql = "select * from facturaVenta where idTransaccion = ?";
        
        String nitLocal = "1065443018";
        String nroFactura = null, nroAutorizacion = null;
        String fechaFactura = null, importeTotal = null, importeBaseDebitoFiscal = null;
        String codigoControl = null, nit = null;
        String ice = null, importeVentasTasaCero = null, importeRebajas = null;
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, idTransaccion);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                
                Date fechaFactDate = rs.getDate("fechaFactura");
                java.util.Date fechaFactUtil = new Date(fechaFactDate.getTime());
                Format formatDate = new SimpleDateFormat("yyyyMMdd");
                fechaFactura = formatDate.format(fechaFactUtil).trim();
                
                
                nroFactura = String.valueOf(rs.getInt("nroFactura"));
                nroAutorizacion = rs.getString("nroAutorizacion");
                importeTotal = String.valueOf(rs.getDouble("importeTotal"));
                importeBaseDebitoFiscal = String.valueOf(rs.getDouble("importeBaseDebitoFiscal"));
                codigoControl = rs.getString("codigoControl");
                nit = rs.getString("nit");
                ice = String.valueOf(rs.getDouble("importeIce"));
                importeVentasTasaCero = String.valueOf(rs.getDouble("importeVentasTasaCero"));
                importeRebajas = String.valueOf(rs.getDouble("importeRebajas"));
            }
            
            cadenaQR = nitLocal+"|"+nroFactura+"|"+nroAutorizacion+"|"+fechaFactura+"|"+importeTotal+
                    "|"+importeBaseDebitoFiscal+"|"+codigoControl+"|"+nit+"|"+ice+"|"+importeVentasTasaCero+
                    "|"+importeRebajas;
        } catch (SQLException ex) {
            Logger.getLogger(FacturaVentaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return cadenaQR;
        
    }
    
}
