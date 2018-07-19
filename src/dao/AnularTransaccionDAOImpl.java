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

            String sql = "SELECT e.id_entrega_transaccion, e.id_transaccion, t.fecha, t.id_tipo_transaccion, t.valor_total, "
                    + "f.nit, f.razon_social, f.nro_factura, f.importe_total "
                    + "FROM entrega_transaccion e "
                    + " join v_transaccion t on e.id_transaccion = t.ID "
                    + " left join factura_venta f on f.id_transaccion = t.ID "
                    + "WHERE datediff(now(),t.fecha) <= ?"
                    + "  and t.id_tipo_transaccion = ? ";
            if (rol != 1) {
                String aux = "and t.usuario = '" + usuario + "'";
                sql = sql.concat(aux);
            }

            try {
                PreparedStatement ps = connectionDB.prepareStatement(sql);
                ps.setInt(1, nro);
                ps.setByte(2, idTipoTransaccion);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    AnularTransaccion at = new AnularTransaccion();
                    at.setFecha(rs.getDate("fecha"));
                    at.setIdEntregaTransaccion(rs.getInt("id_entrega_transaccion"));
                    at.setIdTipoTransaccion(rs.getByte("id_tipo_transaccion"));
                    at.setIdTransaccion(rs.getInt("id_transaccion"));
                    at.setImporteTotalFactura(rs.getDouble("importe_total"));
                    at.setNit(rs.getString("nit"));
                    at.setNroFactura(rs.getInt("nro_factura"));
                    at.setRazonSocial(rs.getString("razon_social"));
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
