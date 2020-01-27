/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.ListaTransaccion;
import almacenes.model.Transaccion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jcapax
 */
public class TransaccionDAOImpl implements TransaccionDAO{
    
    private DatabaseUtils databaseUtils;
    private Connection connectionDB;
        
    public TransaccionDAOImpl(Connection _connectionDB) {
        this.databaseUtils = new DatabaseUtils();
        this.connectionDB = _connectionDB;
    }

    @Override
    public int insertarTransaccion(Transaccion transaccion) {
        int n = 0;
        int idTransaccion = 0;
        String sql = "insert into transaccion(fecha, id_tipo_transaccion, nro_tipo_transaccion, id_lugar, id_terminal, "
                + "tipo_movimiento, estado, usuario, descripcion_transaccion) "
                + "values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
           
            ps.setDate(1, transaccion.getFecha());
            ps.setInt(2, transaccion.getIdTipoTransaccion());
            ps.setInt(3, transaccion.getNroTipoTransaccion());
            ps.setByte(4, transaccion.getIdLugar());
            ps.setInt(5, transaccion.getIdTerminal());
            ps.setInt(6, transaccion.getTipoMovimiento());
            ps.setString(7, transaccion.getEstado());
            ps.setString(8, transaccion.getUsuario());
            ps.setString(9, transaccion.getDescripcionTransaccion());
            
            n = ps.executeUpdate();
            if(n!=0){
//                System.out.println("transaccion generada con exito");
                
                String sql_id = "select id from transaccion order by id desc limit 1";
                
                PreparedStatement ps_id = connectionDB.prepareStatement(sql_id);
                ResultSet rs = ps_id.executeQuery();
                
                if(rs.next()){
                
//                    System.out.println("nro generado: "+rs.getInt("id"));
                
                    idTransaccion = rs.getInt("id");   
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(TransaccionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return idTransaccion;
    }

    @Override
    public int getNroTipoTransaccion(int idTipoTransaccion) {
        int nro = 0;
        String sql = "select nro_tipo_transaccion from transaccion where id_tipo_transaccion = ? "
                + "order by nro_tipo_transaccion DESC LIMIT 1";
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, idTipoTransaccion);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                nro = rs.getInt("nro_tipo_transaccion");
            }
        } catch (SQLException ex) {
            Logger.getLogger(TransaccionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        nro++;
        return nro;
    }

    @Override
    public int getTipoMovimiento(int idTipoTransaccion) {
        int tipoMovimiento = 0;
        
        String sql = "SELECT tipo_movimiento FROM tipo_transaccion WHERE id = ?";
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, idTipoTransaccion);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                tipoMovimiento = rs.getInt("tipo_movimiento");
            }
        } catch (SQLException ex) {
            Logger.getLogger(TransaccionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return tipoMovimiento;
    }

    @Override
    public void insertarEntregaTransaccion(int idTransaccion, int idEntregaTransaccion) {
        String sql = "insert into entrega_transaccion(id_entrega_transaccion, id_transaccion, "
                + "nro_entrega, nro_recepcion)"
                + "values(?, ?, 0, 0)";
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, idEntregaTransaccion);
            ps.setInt(2, idTransaccion);
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(TransaccionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public double getValorTotalTransaccion(int idTransaccion) {
        double valorTotal = 0;
        
        String sql = "select valor_total from v_transaccion where id = ?";
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, idTransaccion);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                valorTotal = rs.getDouble("valor_total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(TransaccionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        return valorTotal;
    }

    @Override
    public Date getFechaTransaccion(int idTransaccion) {
        Date fecha = null;
        
        String sql = "select fecha from transaccion where id = ?";
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, idTransaccion);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                fecha = rs.getDate("fecha");
            }
        } catch (SQLException ex) {
            Logger.getLogger(TransaccionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return fecha;
    }

    @Override
    public ArrayList<ListaTransaccion> getlistaTransacciones(Date fecha, String usuario) {
        
        ArrayList<ListaTransaccion> lista = new ArrayList<>();
        
        String sql = "SELECT id, descripcion_tipo_transaccion, fecha, "
                        + "nro_tipo_transaccion, valor_total, fecha_hora_registro, id_tipo_transaccion \n" +
                     "FROM v_transaccion " +
                     "WHERE id_tipo_transaccion in (1, 2, 3, 6) "
                        + "and usuario = '"+usuario+"'"
                        + "and fecha = '"+String.valueOf(fecha)+"' "
                + "UNION "
                   + "SELECT DISTINCT id, descripcion_transaccion as descripcion_tipo_transaccion, fecha, " +
                        "nro_tipo_transaccion, valor_total, fecha_hora_registro, 0 " +
                        "FROM v_entrega_productos_pendientes " +
                        "WHERE fecha = '"+String.valueOf(fecha)+"' " +
                        "and usuario = '"+usuario+"' " +
                      "ORDER BY id";
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                ListaTransaccion lt = new ListaTransaccion();
                
                lt.setDescripcion(rs.getString("descripcion_tipo_transaccion"));
                lt.setFecha(rs.getDate("fecha"));
                lt.setId(rs.getInt("id"));
                lt.setNroTransaccion(rs.getInt("nro_tipo_transaccion"));
                lt.setValorTotal(rs.getDouble("valor_total"));
                lt.setIdTipoTransaccion(rs.getInt("id_tipo_transaccion"));
                
                lista.add(lt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TransaccionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista;
    }

    @Override
    public void saveCreditoTransaccionEntrega(int idTransaccion) {
        String sql = "INSERT INTO pendiente_transaccion_entrega(id_transaccion) "
                + "values(?)";        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, idTransaccion);
            
            int n = ps.executeUpdate();
            if(n!=0){
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(TemporalDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean isCreditoEntrega(int idTransaccion) {
        boolean aux = false;
        
        String sql = "SELECT id_transaccion FROM credito "
                + "WHERE entrega_pendiente = 1 and id_transaccion = "+String.valueOf(idTransaccion);        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                aux = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(TemporalDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return aux;
    }
    
}
