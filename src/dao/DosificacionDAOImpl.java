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
            String sql = "INSERT INTO dosificacion(llaveDosificacion, fecha, idSucursal, nroAutorizacion, "
                    + "nroInicioFactura, fechaLimiteEmision, pieFactura, estado) "
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
            ps.setString(7, dosificacion.getEstado());
            
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
        String sql = "select id, llaveDosificacion, fecha, idSucursal, nroAutorizacion, "
                + "nroInicioFactura, fechaLimiteEmision, pieFactura, estado "
                + "from dosificacion "
                + "order by id desc";
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Dosificacion d = new Dosificacion();
                
                d.setEstado(rs.getString("estado"));
                d.setFecha(rs.getDate("fecha"));
                d.setFechaLimiteEmision(rs.getDate("fechaLimiteEmision"));
                d.setId(rs.getInt("id"));
                d.setIdSucursal(rs.getInt("idSucursal"));
                d.setLlaveDosificacion(rs.getString("llaveDosificacion"));
                d.setNroAutorizacion(rs.getString("nroAutorizacion"));
                d.setNroInicioFactura(rs.getInt("nroInicioFactura"));
                d.setPieFactura(rs.getString("pieFactura"));
                
                listDosif.add(d);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DosificacionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return listDosif;
    }
    
}
