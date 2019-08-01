/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.AnularTransaccion;
import almacenes.model.Configuracion;
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
public class AnularTransaccionDAOImpl implements AnularTransaccionDAO {

    private DatabaseUtils databaseUtils;
    private Connection connectionDB;

    public AnularTransaccionDAOImpl(Connection _connectionDB) {
        this.databaseUtils = new DatabaseUtils();
        this.connectionDB = _connectionDB;
    }

    @Override
    public ArrayList<AnularTransaccion> getListaTransaccionesAnular(byte idTipoTransaccion, String usuario) {
        SistemaDAO sistemaDAO = new SistemaDAOImpl(connectionDB);
        ArrayList<AnularTransaccion> listaTrans = new ArrayList<>();
        Configuracion conf;
        try {
            conf = sistemaDAO.getGestionConfiguraciones();

            UsuariosDAOImpl us = new UsuariosDAOImpl(connectionDB);
            int rol = us.getRolUsuario(usuario);

            int nro = 0;
            nro = conf.getTiempoAnulacionTransaccion();

            String sql = "Select e.id_entrega_transaccion, \n" +
                        "    t.id as id_transaccion, \n" +
                        "    t.fecha, \n" +
                        "    t.id_tipo_transaccion,  \n" +
                        "	tt.descripcion as nombre_transaccion,\n" +
                        "    t.nro_tipo_transaccion, \n" +
                        "    t.id_lugar, \n" +
                        "    l.descripcion as nombre_lugar, \n" +
                        "    t.id_terminal, \n" +
                        "    ter.descripcion as nombre_terminal, \n" +
                        "    t.estado, \n" +
                        "    t.descripcion_transaccion, \n" +
                        "    t.usuario,\n" +
                        "    f.nro_factura, \n" +
                        "    f.nit, \n" +
                        "    f.razon_social,\n" +
                        "    t.valor_total \n" +
                        "From v_transaccion t \n" +
                        "	join lugar l on t.id_lugar = t.id_lugar \n" +
                        "    join terminal ter on ter.id = t.id_terminal \n" +
                        "    join tipo_transaccion tt on tt.id = t.id_tipo_transaccion \n" +
                        "    join entrega_transaccion e on e.id_transaccion = t.id \n" +
                        "    left join factura_venta f on f.id_transaccion = t.id \n";
            String aux = "";
            if (rol != 1) {
                aux = "Where datediff(now(), t.fecha) <= '"+ String.valueOf(nro)+"' "
                        + "and t.estado <> 'N' and t.usuario = '"+usuario+"' ORDER by t.id desc";
                sql = sql.concat(aux);
            }else{
                aux = "Where datediff(now(), t.fecha) <= '"+ String.valueOf(nro)+"' "
                        + "and t.estado <> 'N' ORDER by t.id desc";
                sql = sql.concat(aux);
            }
            
            try {
                PreparedStatement ps = connectionDB.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    AnularTransaccion at = new AnularTransaccion();
                    
                    at.setDescripcionTransaccion(rs.getString("descripcion_transaccion"));
                    at.setEstado(rs.getString("estado"));
                    at.setFecha(rs.getDate("fecha"));
                    at.setIdEntregaTransaccion(rs.getInt("id_entrega_transaccion"));
                    at.setIdLugar(rs.getByte("id_lugar"));
                    at.setIdTerminal(rs.getByte("id_terminal"));
                    at.setIdTipoTransaccion(rs.getByte("id_tipo_transaccion"));
                    at.setIdTransaccion(rs.getInt("id_transaccion"));
                    at.setNombreLugar(rs.getString("nombre_lugar"));
                    at.setNombreTerminal(rs.getString("nombre_terminal"));
                    at.setNombreTransaccion(rs.getString("nombre_transaccion"));
                    at.setNroFactura(rs.getInt("nro_factura"));
                    at.setNroTipoTransaccion(rs.getInt("nro_tipo_transaccion"));
                    at.setNit(rs.getString("nit"));
                    at.setRazonSocial(rs.getString("razon_social"));
                    at.setUsuario(rs.getString("usuario"));
                    at.setValorTotal(rs.getDouble("valor_total"));
                    
                    listaTrans.add(at);
                }
            } catch (SQLException ex) {
                Logger.getLogger(AnularTransaccionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (SQLException ex) {
            Logger.getLogger(AnularTransaccionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaTrans;
    }

    @Override
    public void anularTrans(int idTransaccion, int idEntregaTransaccion) {
        String sql = "update transaccion set estado = 'N' "
                + "where id in(?, ?)";
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, idTransaccion);
            ps.setInt(2, idEntregaTransaccion);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AnularTransaccionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void anularFactura(int idTransaccion) {
        String sql = "update factura_venta "
                + "set nit = 0, razon_social = 'ANULADA', estado = 'A', importe_total = 0, "
                + "importe_ice = 0, importe_exportaciones = 0, importe_ventas_tasa_cero= 0, "
                + "importe_sub_total = 0, importe_rebajas = 0, importe_base_debito_fiscal = 0, "
                + "debito_fiscal = 0, codigo_control = '0' "
                + "where id_transaccion = ?";

        PreparedStatement ps;
        try {
            ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, idTransaccion);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AnularTransaccionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void anularCaja(int idTransaccion) {
        String sql = "update caja set estado = 'N' where id_transaccion = ?";
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, idTransaccion);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AnularTransaccionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
