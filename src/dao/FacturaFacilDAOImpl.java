/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.DetalleFacturaFacil;
import almacenes.model.FacturaFacil;
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
 * @author jcarlos.porcel
 */
public class FacturaFacilDAOImpl implements FacturaFacilDAO{
    
    private DatabaseUtils databaseUtils;
    private Connection connectionDB;

    public FacturaFacilDAOImpl(Connection connectionDB) {
        this.connectionDB = connectionDB;
        this.databaseUtils = new DatabaseUtils();
    }

    @Override
    public List<String> getListaProductosAutocompletado() {
        List<String> list = new ArrayList<>();
        
        String sql = "Select detalle from detalle_factura_facil group by detalle order by detalle";
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                list.add(rs.getString("detalle"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturaFacilDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
    }   

    @Override
    public void insertarDetalleFacturaFacil(ArrayList<DetalleFacturaFacil> facil, int idFacturaFacil) {
        String sql = null;
        PreparedStatement ps;
        ResultSet rs;
        for(int i=0; i<facil.size(); i++){
            sql = "INSERT INTO detalle_factura_facil (id_factura_facil, detalle, cantidad, "
                    + "valor_unitario, valor_total) "
                        + "VALUES ("+idFacturaFacil
                        +", '"+facil.get(i).getDetalle()
                        +"', "+facil.get(i).getCantidad()
                        +", "+facil.get(i).getValorUnitario()
                        +", "+facil.get(i).getValorTotal()
                        +")";
            try {                
                ps = connectionDB.prepareStatement(sql);
                rs = ps.executeQuery();
            } catch (SQLException ex) {
                Logger.getLogger(FacturaFacilDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
    }

    @Override
    public int getIdFacturaUltima() {
        int id = 0;
        String sql = "Select id From factura_venta Order by id Desc Limit 1";
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);            
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                id = rs.getInt("id");
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(SucursalDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    @Override
    public ArrayList<Integer> getListaAnnosFacturaFacil() {
        ArrayList<Integer> anno = new ArrayList<>();
        String sql = "select year(fecha_factura) anno "
                + "from factura_venta "
                + "where id IN (Select id_factura_facil From detalle_factura_facil)"
                + "group by year(fecha_factura) "
                + "order by year(fecha_factura)";
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);            
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int a = rs.getInt("anno");
                anno.add(a);
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(SucursalDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return anno;        
    }

    @Override
    public ArrayList<FacturaFacil> getlistaFacturaFacil(int anno, int mes) {
        String sql = "SELECT f.id, f.id_sucursal, s.nombre_sucursal, f.fecha_factura, "
                + " f.nro_factura, f.nro_autorizacion, f.estado, f.nit, "
                + " f.razon_social, f.importe_total, f.codigo_control"
                + " FROM factura_venta f "
                + " JOIN sucursal s on s.id = f.id_sucursal "
                + "WHERE f.id IN (Select id_factura_facil From detalle_factura_facil) "
                + "AND year(fecha_factura) = "+String.valueOf(anno)+" "
                + "AND month(fecha_factura) = "+ String.valueOf(mes);
        
        ArrayList<FacturaFacil> lista = new ArrayList<>();
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);            
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                FacturaFacil ff = new FacturaFacil();
                
                ff.setId(rs.getInt("id"));
                ff.setIdSucursal(rs.getInt("id_sucursal"));
                ff.setNombreSucursal(rs.getString("nombre_sucursal"));
                ff.setFechaFactura(rs.getDate("fecha_factura"));
                ff.setNroFactura(rs.getInt("nro_factura"));
                ff.setNroAutorizacion(rs.getString("nro_autorizacion"));
                ff.setEstado(rs.getString("estado"));
                ff.setNit(rs.getString("nit"));
                ff.setRazonSocial(rs.getString("razon_social"));
                ff.setImporteTotal(rs.getDouble("importe_total"));
                ff.setCodigoControl(rs.getString("codigo_control"));
                
                lista.add(ff);
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(SucursalDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista;
    }
    
}
