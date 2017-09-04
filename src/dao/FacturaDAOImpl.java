/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.FacturaVenta;
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
        String sql = "select year(fechaFactura) anno "
                + "from facturaVenta "
                + "group by year(fechaFactura) "
                + "order by year(fechaFactura) desc";
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
        
        String sql = "select * from facturaVenta "
                + "where year(fechaFactura) = ? and month(fechaFactura) = ?";
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, anno);
            ps.setByte(2, mes);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                FacturaVenta fv = new FacturaVenta();
                
                fv.setCodigoControl(rs.getString("codigoControl"));
                fv.setCorrelativoSucursal(rs.getInt("correlativoSucursal"));
                fv.setDebitoFiscal(rs.getDouble("debitoFiscal"));
                fv.setEspecificacion(rs.getInt("especificacion"));
                fv.setEstado(rs.getString("estado"));
                fv.setFechaFactura(rs.getDate("fechaFactura"));
                fv.setFechaLimiteEmision(rs.getDate("fechaLimiteEmision"));
                fv.setIdDosificacion(rs.getInt("idDosificacion"));
                fv.setIdSucursal(rs.getInt("idSucursal"));
                fv.setIdTransaccion(rs.getInt("idtransaccion"));
                fv.setImporteBaseDebitoFiscal(rs.getDouble("importeBaseDebitoFiscal"));
                fv.setImporteExportaciones(rs.getDouble("importeExportaciones"));
                fv.setImporteIce(rs.getDouble("importeICE"));
                fv.setImporteRebajas(rs.getDouble("importeRebajas"));
                fv.setImporteSubtotal(rs.getDouble("importeSubTotal"));
                fv.setImporteTotal(rs.getDouble("importeTotal"));
                fv.setImporteVentasTasaCero(rs.getDouble("importeVentasTasaCero"));
                fv.setNit(rs.getString("nit"));
                fv.setNroAutorizacion(rs.getString("nroAutorizacion"));
                fv.setNroFactura(rs.getInt("nroFactura"));
                fv.setRazonSocial(rs.getString("razonSocial"));
                
                lFactura.add(fv);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return lFactura;
    }
    
}
