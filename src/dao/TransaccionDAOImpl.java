/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.ListaIngresosEgresos;
import almacenes.model.ListaTransaccion;
import almacenes.model.Transaccion;
import java.sql.Connection;
import java.sql.Date;
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
                        + "nro_tipo_transaccion, valor_total, fecha_hora_registro, "
                        + "id_tipo_transaccion, descripcion_transaccion, usuario \n" +
                     "FROM v_transaccion " +
                     "WHERE id_tipo_transaccion in (1, 2, 3, 6, 10) "
                        + "and usuario = '"+usuario+"' "
                        + "and fecha = '"+String.valueOf(fecha)+"' "
                + "UNION "
                   + "SELECT DISTINCT id, descripcion_transaccion as descripcion_tipo_transaccion, fecha, " +
                        "nro_tipo_transaccion, valor_total, fecha_hora_registro, 8, 'XXX' as descripcion_transaccion, usuario " +
                        "FROM v_entrega_productos_pendientes " +
                        "WHERE fecha = '"+String.valueOf(fecha)+"' " +
                        "and usuario = '"+usuario+"' " +
                      "ORDER BY id";
        
        //System.out.println(sql);
        
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
                lt.setDetalle(rs.getString("descripcion_transaccion"));
                lt.setUsuario(rs.getString("usuario"));
                
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

    @Override
    public int getIdTransaccionOriginalDeEntregaPendiente(int idTransaccion) {
        int idTransaccionInicial = 0;
        String sql = "select id_transaccion from v_entrega_productos_pendientes "
                + "where id = ?";
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, idTransaccion);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                idTransaccionInicial = rs.getInt("id_transaccion");
            }
        } catch (SQLException ex) {
            Logger.getLogger(TransaccionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return idTransaccionInicial;
    }

    @Override
    public void crearTemporalEntrega() {
        String sql_temp = "CREATE TEMPORARY TABLE IF NOT EXISTS temp_entregas(\n" +
                                "    id_transaccion_credito      int,\n" +
                                "    id_producto                 int, \n" +
                                "    nombre_completo             varchar(100),\n" +
                                "    direccion                   varchar(100),\n" +
                                "    descripcion_credito         varchar(200),\n" +
                                "    telefonos                   varchar(100),\n" +
                                "    fecha                       varchar(100),\n" +
                                "    nro_tipo_transaccion        int,\n" +
                                "    usuario                     varchar(100),\n" +
                                "    nombre_producto             varchar(100),\n" +
                                "    id_unidad_medida            int,\n" +
                                "    nombre_unidad_medida        varchar(100),\n" +
                                "    cant_credito                decimal(12,2),\n" +
                                "    cant_entrega                decimal(12,2),\n" +
                                "    diferencia                  decimal(12,2),\n" +
                                "    cantidad_actual             decimal(12,2)\n" +
                                ");"; 
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql_temp);
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(TransaccionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void insertarEntregaTemporal(int idTransaccionInicial, int idTransaccionEntrega) {
        String sql = "SELECT vp.id_transaccion as id_transaccion_credito, vp.id_producto, vp.nombre_completo, \n" +
                    "    vp.direccion, vp.detalle, vp.telefonos, f_fecha_transaccion(?) fecha, " +
                    "    f_get_nro_tipo_transaccion(?) nro_tipo_transaccion , ve.usuario,     \n" +
                    "    vp.nombre_producto, vp.id_unidad_medida, vp.nombre_unidad_medida, vp.cantidad as cant_credito, \n" +
                    "    SUM(coalesce(ve.cantidad,0)) as cant_entrega,\n" +
                    "    (vp.cantidad - SUM(coalesce(ve.cantidad,0))) AS diferencia,\n" +
                    "    f_cantidad_entrega_transaccion(?, vp.id_producto, vp.id_unidad_medida) as cantidad_actual,\n" +
                    "    f_fecha_transaccion(?) as fecha\n" +
                    "FROM v_creditos vp\n" +
                    "    LEFT JOIN v_entrega_productos ve on vp.id_transaccion=ve.id_transaccion_entrega\n" +
                    "         AND vp.id_unidad_medida = ve.id_unidad_medida\n" +
                    "         AND vp.id_producto = ve.id_producto\n" +
                    "WHERE vp.id_transaccion = ?         \n" +
                    "GROUP BY vp.id_transaccion, vp.id_producto, vp.nombre_completo, \n" +
                    "    vp.direccion, vp.detalle, vp.telefonos, ve.usuario,    \n" +
                    "    vp.nombre_producto, vp.id_unidad_medida, vp.nombre_unidad_medida, vp.cantidad \n" +                
                    "ORDER BY vp.fecha DESC";   
//        System.out.println(sql);        
//        System.out.println(idTransaccionEntrega);
//        System.out.println(idTransaccionInicial);
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, idTransaccionEntrega);
            ps.setInt(2, idTransaccionEntrega);
            ps.setInt(3, idTransaccionEntrega);
            ps.setInt(4, idTransaccionEntrega);
            ps.setInt(5, idTransaccionInicial);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String sql_ins = "INSERT INTO temp_entregas(id_transaccion_credito, id_producto, "
                        + "nombre_completo, direccion, descripcion_credito, telefonos, fecha, nro_tipo_transaccion, usuario, "
                        + "nombre_producto, id_unidad_medida, nombre_unidad_medida, cant_credito, cant_entrega, "
                        + "diferencia, cantidad_actual) "
                        + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement ps_ins = connectionDB.prepareStatement(sql_ins);
                ps_ins.setInt(1, rs.getInt("id_transaccion_credito"));
                ps_ins.setInt(2, rs.getInt("id_producto"));
                ps_ins.setString(3, rs.getString("nombre_completo"));
                ps_ins.setString(4, rs.getString("direccion"));
                ps_ins.setString(5, rs.getString("detalle"));
                ps_ins.setString(6, rs.getString("telefonos"));
                ps_ins.setString(7, rs.getString("fecha"));
                ps_ins.setInt(8, rs.getInt("nro_tipo_transaccion"));
                ps_ins.setString(9, rs.getString("usuario"));
                ps_ins.setString(10, rs.getString("nombre_producto"));
                ps_ins.setInt(11, rs.getInt("id_unidad_medida"));
                ps_ins.setString(12, rs.getString("nombre_unidad_medida"));
                ps_ins.setDouble(13, rs.getDouble("cant_credito"));
                ps_ins.setDouble(14, rs.getDouble("cant_entrega"));
                ps_ins.setDouble(15, rs.getDouble("diferencia"));
                ps_ins.setDouble(16, rs.getDouble("cantidad_actual"));
                ps_ins.execute();
                       
            }            
        } catch (SQLException ex) {
            Logger.getLogger(TemporalDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void eliminarDatosTemporalEntrega() {
        String sql_temp = "DELETE FROM  temp_entregas"; 
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql_temp);
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(TransaccionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ArrayList<ListaTransaccion> getlistaCotizacionesPendientes(int idLugar) {
       ArrayList<ListaTransaccion> lista = new ArrayList<>();
        
        String sql = "SELECT id, fecha, nro_tipo_transaccion, valor_total, usuario, descripcion_transaccion " +
                    "FROM v_transaccion " +
                    "WHERE id_tipo_transaccion = 10 " +
                    "	AND id NOT IN (SELECT id_transaccion_cotizacion FROM atencion_cotizacion) " +
                    "   AND id_lugar = "+String.valueOf(idLugar) +
                    " ORDER BY id";
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                ListaTransaccion lt = new ListaTransaccion();
                
                lt.setFecha(rs.getDate("fecha"));
                lt.setId(rs.getInt("id"));
                lt.setNroTransaccion(rs.getInt("nro_tipo_transaccion"));
                lt.setValorTotal(rs.getDouble("valor_total"));                
                lt.setDetalle(rs.getString("descripcion_transaccion"));
                lt.setUsuario(rs.getString("usuario"));
                
                lista.add(lt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TransaccionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista;
    }

    @Override
    public void insertarAtencionCotizacion(int idTransaccionCotizacion, int idTransaccionAtencion) {
        String sql = "insert into atencion_cotizacion(id_transaccion_cotizacion, id_transaccion_atencion)"
                + " values(?, ?)";
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, idTransaccionCotizacion);
            ps.setInt(2, idTransaccionAtencion);
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(TransaccionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
