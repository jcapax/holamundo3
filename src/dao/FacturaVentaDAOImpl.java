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
        String sql = "insert into factura_venta(id_sucursal, especificacion, correlativo_sucursal, "
                + "fecha_factura, nro_factura, nro_autorizacion, estado, nit, razon_social, "
                + "importe_total, importe_ice, importe_exportaciones, importe_ventas_tasa_cero, "
                + "importe_sub_total, importe_rebajas, importe_base_debito_fiscal, debito_fiscal, "
                + "codigo_control, id_transaccion, fecha_limite_emision, id_dosificacion) "
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
        String sql = "select nro_autorizacion from dosificacion where estado = 1";
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                nroAutorizacion = rs.getString("nro_autorizacion");
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturaVentaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return nroAutorizacion;
    }

    @Override
    public String getLlaveDosificacion(String nroAutorizacion) {
        String llaveDosificacion = null;
        String sql = "select llave_dosificacion from dosificacion where nro_autorizacion = ?";
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setString(1, nroAutorizacion);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                llaveDosificacion = rs.getString("llave_dosificacion");
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturaVentaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return llaveDosificacion;
    }

    @Override
    public int getNewNroFactura(String nroAutorizacion) {
        int nroFactura = 0;
        
        String sql = "select nro_factura from factura_venta where nro_autorizacion = ? order by nro_factura desc limit 1";
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setString(1, nroAutorizacion);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                nroFactura = rs.getInt("nro_factura");
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
        String sql = "select nro_inicio_factura from dosificacion where nro_autorizacion = ?";
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setString(1, nroAutorizacion);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                nroInicioFactura = rs.getInt("nro_inicio_factura");
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturaVentaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return nroInicioFactura;
    }

    @Override
    public Date getFechaLimiteEmision(String nroAutorizacion) {
        Date fechaLimiteEmision=null;
        String sql = "select fecha_limite_emision from dosificacion where nro_autorizacion = ?";
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setString(1, nroAutorizacion);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                fechaLimiteEmision = rs.getDate("fecha_limite_emision");
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturaVentaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fechaLimiteEmision;
    }

    @Override
    public String getRazonSocialFactura(String ni) {
        String razonSocial = "";
        String sql = "select razon_social from factura_venta where nit = ? order by id DESC limit 1";
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setString(1, ni);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                razonSocial = rs.getString("razon_social");
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturaVentaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return razonSocial;
               
    }

    @Override
    public String getCadenaCodigoQr(int idTransaccion) {
        String cadenaQR = "";
        
        String sql = "select * from factura_venta where id_transaccion = ?";
        
        LugarDAO lug = new LugarDAOImpl(connectionDB);
        SucursalDAO suc = new SucursalDAOImpl(connectionDB);
        
        byte idLugar = lug.getIdLugarTransaccion(idTransaccion);
        String nitSucursal = suc.getNitSucursal(idLugar);
               
        String nitLocal = nitSucursal;
        String nroFactura = null, nroAutorizacion = null;
        String fechaFactura = null, importeTotal = null, importeBaseDebitoFiscal = null;
        String codigoControl = null, nit = null;
        String ice = null, importeVentasTasaCero = null, importeRebajas = null;
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, idTransaccion);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                
                Date fechaFactDate = rs.getDate("fecha_factura");
                java.util.Date fechaFactUtil = new Date(fechaFactDate.getTime());
                Format formatDate = new SimpleDateFormat("yyyyMMdd");
                fechaFactura = formatDate.format(fechaFactUtil).trim();
                
//                nitLocal = String.valueOf(rs.getInt("nit_sucursal"));
                nroFactura = String.valueOf(rs.getInt("nro_factura"));
                nroAutorizacion = rs.getString("nro_autorizacion");
                importeTotal = String.valueOf(rs.getDouble("importe_total"));
                importeBaseDebitoFiscal = String.valueOf(rs.getDouble("importe_base_debito_fiscal"));
                codigoControl = rs.getString("codigo_control");
                nit = rs.getString("nit");
                ice = String.valueOf(rs.getDouble("importe_ice"));
                importeVentasTasaCero = String.valueOf(rs.getDouble("importe_ventas_tasa_cero"));
                importeRebajas = String.valueOf(rs.getDouble("importe_rebajas"));
            }
            
            cadenaQR = nitLocal+"|"+nroFactura+"|"+nroAutorizacion+"|"+fechaFactura+"|"+importeTotal+
                    "|"+importeBaseDebitoFiscal+"|"+codigoControl+"|"+nit+"|"+ice+"|"+importeVentasTasaCero+
                    "|"+importeRebajas;
        } catch (SQLException ex) {
            Logger.getLogger(FacturaVentaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return cadenaQR;
        
    }

    @Override
    public int getIdSucursal(int idLugar) {
        int idSucursal = 0;
        String sql = "select id from sucursal where estado = 1 and id_lugar = ?";
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, idLugar);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                idSucursal = rs.getInt("id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturaVentaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return idSucursal;
    }

    @Override
    public int getIdDosificacion(int idSucursal) {
        int idDosificacion = 0;
        String sql = "select id from dosificacion where estado = 1 and id_sucursal = ?";
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, idSucursal);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                idDosificacion = rs.getInt("id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturaVentaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return idDosificacion;
    }
    
}
