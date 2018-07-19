/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.ConfiguracionGeneral;
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
public class ConfiguracionGeneralDAOImpl implements ConfiguracionGeneralDAO {

    private DatabaseUtils databaseUtils;
    private Connection connectionDB;

    public ConfiguracionGeneralDAOImpl(Connection _connectionDB) {
        this.databaseUtils = new DatabaseUtils();
        this.connectionDB = _connectionDB;
    }

    @Override
    public ArrayList<ConfiguracionGeneral> getConfiguracionGeneral() {
        ArrayList<ConfiguracionGeneral> listaConf = new ArrayList<ConfiguracionGeneral>();
        String sql = "select * from configuracion";

        PreparedStatement ps;
        try {
            ps = connectionDB.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ConfiguracionGeneral cf = new ConfiguracionGeneral();
                
                cf.setRutaExcel(rs.getString("ruta_excel"));
                cf.setTiempoAnulacionTransaccion(rs.getInt("tiempo_anulacion_transaccion"));
                
                listaConf.add(cf);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConfiguracionGeneralDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listaConf;
    }

    @Override
    public String getRutaExcelLibroVentas() {
        String ruta = null;
        String sql = "select ruta_excel from configuracion";
        PreparedStatement ps;
        try {
            ps = connectionDB.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ruta = rs.getString("ruta_excel");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConfiguracionGeneralDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ruta;
    }

    @Override
    public int getNroDiasNullTransaccion() {
        int nro = 0;
        String sql = "select tiempo_anulacion_transaccion from configuracion";
        PreparedStatement ps;
        try {
            ps = connectionDB.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                nro = rs.getInt("tiempo_anulacion_transaccion");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConfiguracionGeneralDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nro;
    }

    @Override
    public int getImpresionDirectaFactura() {
        int impDirec= 0;
        String sql = "select impresion_directa_factura from configuracion";
        PreparedStatement ps;
        try {
            ps = connectionDB.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                impDirec = rs.getInt("impresion_directa_factura");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConfiguracionGeneralDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return impDirec;
    }

}
