/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.FacturaVenta;
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
public class FacturaDAOImpl implements FacturaDAO{
    
    private DatabaseUtils databaseUtils;
    private Connection connectionDB;
        
    public FacturaDAOImpl(Connection _connectionDB) {
        this.databaseUtils = new DatabaseUtils();
        this.connectionDB = _connectionDB;
    }

    @Override
    public ArrayList<Integer> getListaAnnosFacturacion() {
        String sql = "select year(fecha_factura) anno "
                + "from factura_venta "
                + "group by year(fecha_factura) "
                + "order by year(fecha_factura) desc";
        ArrayList<Integer> lanno = new ArrayList<>();
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            lanno.add(0);
            while(rs.next()){
                lanno.add(rs.getInt("anno"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lanno;
    }

    @Override
    public ArrayList<FacturaVenta> getListaFacturasLibroVenta(byte mes, int anno) {
        ArrayList<FacturaVenta> lFactura = new ArrayList<FacturaVenta>();
        
        String sql = "select * from factura_venta "
                + "where year(fecha_factura) = ? and month(fecha_factura) = ?";
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, anno);
            ps.setByte(2, mes);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                FacturaVenta fv = new FacturaVenta();
                
                fv.setCodigoControl(rs.getString("codigo_control"));
                fv.setCorrelativoSucursal(rs.getInt("correlativo_sucursal"));
                fv.setDebitoFiscal(rs.getDouble("debito_fiscal"));
                fv.setEspecificacion(rs.getInt("especificacion"));
                fv.setEstado(rs.getString("estado"));
                fv.setFechaFactura(rs.getDate("fecha_factura"));
                fv.setFechaLimiteEmision(rs.getDate("fecha_limite_emision"));
                fv.setIdDosificacion(rs.getInt("id_dosificacion"));
                fv.setIdSucursal(rs.getInt("id_sucursal"));
                fv.setIdTransaccion(rs.getInt("id_transaccion"));
                fv.setImporteBaseDebitoFiscal(rs.getDouble("importe_base_debito_fiscal"));
                fv.setImporteExportaciones(rs.getDouble("importe_exportaciones"));
                fv.setImporteIce(rs.getDouble("importe_ice"));
                fv.setImporteRebajas(rs.getDouble("importe_rebajas"));
                fv.setImporteSubtotal(rs.getDouble("importe_sub_total"));
                fv.setImporteTotal(rs.getDouble("importe_total"));
                fv.setImporteVentasTasaCero(rs.getDouble("importe_ventas_tasa_cero"));
                fv.setNit(rs.getString("nit"));
                fv.setNroAutorizacion(rs.getString("nro_autorizacion"));
                fv.setNroFactura(rs.getInt("nro_factura"));
                fv.setRazonSocial(rs.getString("razon_social"));
                
                lFactura.add(fv);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return lFactura;
    }

    @Override
    public ArrayList<PendientePago> getListaCreditoPorFacturar() {
        ArrayList<PendientePago> listaCreditoProFacturar = new ArrayList<PendientePago>();
        
        String sql = "select id_transaccion, fecha, nro_tipo_transaccion, importe_caja, valor_total, diferencia, "
                + "detalle, cedula_identidad, nit, nombre_completo, razon_social "
                + "from v_pago_credito "
                + "where diferencia = 0 and "
                + "id_transaccion not in (select id_transaccion from factura_venta)";        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                PendientePago pp = new PendientePago();
                
                pp.setIdTransaccion(rs.getInt("id_transaccion"));
                pp.setFecha(rs.getDate("fecha"));
                pp.setValorTotal(rs.getDouble("valor_total"));
                pp.setDetalle(rs.getString("detalle"));
                pp.setNombreCompleto(rs.getString("nombre_completo"));
                pp.setRazonSocial(rs.getString("razon_social"));
                
                listaCreditoProFacturar.add(pp);
            }
                    
        } catch (SQLException ex) {
            Logger.getLogger(FacturaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return listaCreditoProFacturar;
    }

    @Override
    public int getIdTransacion(int nroFactura, String Autorizacion) {
        int idtransaccion = 0;
        
        String sql = "select id_transaccion from factura_venta where nro_factura = ? and nro_autorizacion = ?";
        PreparedStatement ps;
        try {
            ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, nroFactura);
            ps.setString(2, Autorizacion);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                idtransaccion = rs.getInt("id_transaccion");
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idtransaccion;
    }

    @Override
    public double getImporteTotal(int idTransaccion) {
        double importeTotal = 0;
        
        String sql = "select importe_total from factura_venta where id_transaccion = ?";
        PreparedStatement ps;
        try {
            ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, idTransaccion);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                importeTotal = rs.getInt("importe_total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return importeTotal;
    }

    @Override
    public boolean getEstadoDosificacion(byte idSucursal) {
        boolean estado = false;
        String sql = "select id from dosificacion where id_sucursal = ? and estado is true";
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setByte(1, idSucursal);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                estado = true;
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(SucursalDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return estado;
    }

    @Override
    public boolean getFechaLimiteEmisionVigente(byte idSucursal) {
        boolean estado = false;
        String sql = "Select id From dosificacion Where fecha_limite_emision >= DATE(now()) and id_sucursal = ?";
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setByte(1, idSucursal);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                estado = true;
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(SucursalDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return estado;
    }
    
}
