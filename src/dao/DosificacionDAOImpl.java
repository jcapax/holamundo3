/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.Dosificacion;
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
public class DosificacionDAOImpl  implements DosificacionDAO{
    
    private DatabaseUtils databaseUtils;
    private Connection connectionDB;

    public DosificacionDAOImpl(Connection connectionDB) {
        this.connectionDB = connectionDB;
        this.databaseUtils = new DatabaseUtils();
    }
    
    

    @Override
    public int insertarDosificacion(Dosificacion dosificacion) {
        
            int aux = 0;
            String sql = "INSERT INTO dosificacion(llave_dosificacion, fecha, id_sucursal, nro_autorizacion, "
                    + "nro_inicio_factura, fecha_limite_emision, pie_factura, estado) "
                    + "values(?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setString(1, dosificacion.getLlaveDosificacion());
            ps.setDate(2, dosificacion.getFecha());
            ps.setInt(3, dosificacion.getIdSucursal());
            ps.setString(4, dosificacion.getNroAutorizacion());
            ps.setInt(5, dosificacion.getNroInicioFactura());
            ps.setDate(6, dosificacion.getFechaLimiteEmision());
            ps.setString(7, dosificacion.getPieFactura());
            ps.setInt(7, dosificacion.getEstado());
            
            ps.executeUpdate();
            
            aux = 1;
            
        } catch (SQLException ex) {
            Logger.getLogger(DosificacionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return aux;
    }

    @Override
    public ArrayList<Dosificacion> getListDosificacion() {
        ArrayList<Dosificacion> listDosif = new ArrayList<>();
        String sql = "select d.id, llave_dosificacion, fecha, d.id_sucursal, s.nombre_sucursal, nro_autorizacion, "
                + "nro_inicio_factura, fecha_limite_emision, pie_factura, d.estado "
                + "from dosificacion d join sucursal s on d.id_sucursal = s.id "
                + "order by id desc";
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Dosificacion d = new Dosificacion();
                
                d.setEstado(rs.getInt("estado"));
                d.setFecha(rs.getDate("fecha"));
                d.setFechaLimiteEmision(rs.getDate("fecha_limite_emision"));
                d.setId(rs.getInt("id"));
                d.setIdSucursal(rs.getInt("id_sucursal"));
                d.setNombreSucursal(rs.getString("nombre_sucursal"));
                d.setLlaveDosificacion(rs.getString("llave_dosificacion"));
                d.setNroAutorizacion(rs.getString("nro_autorizacion"));
                d.setNroInicioFactura(rs.getInt("nro_inicio_factura"));
                d.setPieFactura(rs.getString("pie_factura"));
                
                listDosif.add(d);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DosificacionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return listDosif;
    }
    
}
