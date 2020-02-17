/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.EntregaPendiente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jcarlos.porcel
 */
public class EntregasDAOImpl implements EntregasDAO{
    
    private DatabaseUtils databaseUtils;
    private Connection connectionDB;

    public EntregasDAOImpl(Connection connectionDB) {
        this.connectionDB = connectionDB;
        this.databaseUtils = new DatabaseUtils();
    }
    
    @Override
    public ArrayList<EntregaPendiente> getListaEntregasPendientes() {
        ArrayList<EntregaPendiente> listPendiente = new ArrayList<>();
        String sql = "SELECT id_transaccion_credito, nombre_completo, direccion, telefonos, fecha, nro_tipo_transaccion, detalle " +
                        "FROM v_entregas_pendientes " +
                        "WHERE id_transaccion_credito NOT IN (Select id_transaccion From transaccion_cierre Where producto = 1) " +
                        "GROUP BY id_transaccion_credito, nombre_completo, direccion, telefonos, fecha, nro_tipo_transaccion";
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                EntregaPendiente pendiente = new EntregaPendiente();
                
                pendiente.setIdTransaccionCredito(rs.getInt("id_transaccion_credito"));
                pendiente.setNroTipoTransaccion(rs.getInt("nro_tipo_transaccion"));
                pendiente.setNombreCompleto(rs.getString("nombre_completo"));
                pendiente.setDireccion(rs.getString("direccion"));
                pendiente.setTelefonos(rs.getString("telefonos"));
                pendiente.setFecha(rs.getDate("fecha"));
                pendiente.setDetalle(rs.getString("detalle"));
                
                listPendiente.add(pendiente);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DosificacionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return listPendiente;
    }

    @Override
    public void abonoProducto(EntregaPendiente entregaPendiente) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<EntregaPendiente> getProductosPendientes(int idtransaccion) {
        ArrayList<EntregaPendiente> listProductosPendientes = new ArrayList<>();
        String sql = "SELECT id_transaccion_credito, id_producto, nombre_producto, id_unidad_medida, "
                + "nombre_unidad_medida, cant_credito, cant_entrega, diferencia " +
            "FROM v_entregas_pendientes " +
            "WHERE id_transaccion_credito = "+String.valueOf(idtransaccion);
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                EntregaPendiente pendiente = new EntregaPendiente();
                
                pendiente.setIdTransaccionCredito(rs.getInt("id_transaccion_credito"));
                pendiente.setIdProducto(rs.getInt("id_producto"));
                pendiente.setNombreProducto(rs.getString("nombre_producto"));
                pendiente.setIdUnidadMedida(rs.getInt("id_unidad_medida"));
                pendiente.setNombreUnidadMedida(rs.getString("nombre_unidad_medida"));
                pendiente.setCantidadCredido(rs.getDouble("cant_credito"));
                pendiente.setCantidadEntrega(rs.getDouble("cant_entrega"));
                pendiente.setDiferencia(rs.getDouble("diferencia"));
                
                listProductosPendientes.add(pendiente);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DosificacionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listProductosPendientes;
    }
    
}
