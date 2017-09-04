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
        String sql = "insert into caja(fecha, importe, idTransaccion, nroCobro, "
                + "nroPago, estado, usuario) "
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
    public ArrayList<ListaCaja> getListaCaja(int idArqueo) {
       ArrayList<ListaCaja> listaCaja = new ArrayList<ListaCaja>();
       
       String sql = "select * from vCaja where idArqueo = ?";
       
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, idArqueo);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                ListaCaja lc = new ListaCaja();
                lc.setDescripcionTransaccion(rs.getString("descripcionTransaccion"));
                lc.setEstado(rs.getString("estado"));
                lc.setFecha(rs.getDate("fechaCaja"));
                lc.setIdArqueo(rs.getInt("idArqueo"));
                lc.setIdTransaccion(rs.getInt("idtransaccion"));
                lc.setImporteCaja(rs.getDouble("importeCaja"));
                lc.setMovimiento(rs.getString("movimiento"));
                
                listaCaja.add(lc);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CajaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       return listaCaja;
    }
    
}
