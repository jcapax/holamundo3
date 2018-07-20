/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.Arqueo;
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
public class ArqueoDAOImpl implements ArqueoDAO {

    private DatabaseUtils databaseUtils;
    private Connection connectionDB;

    public ArqueoDAOImpl(Connection _connectionDB) {
        this.databaseUtils = new DatabaseUtils();
        this.connectionDB = _connectionDB;
    }

    @Override
    public void insertarCajaInicial(Arqueo arqueo) {
        String sql = "INSERT INTO arqueo(fecha_apertura, "
                + "caja_inicial, estado, id_terminal, id_lugar, usuario) "
                + "values(now(), ?, 'A', ?, ?, ?)";

        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setDouble(1, arqueo.getCajaInicial());
            ps.setByte(2, arqueo.getIdTerminal());
            ps.setByte(3, arqueo.getIdLugar());
            ps.setString(4, arqueo.getUsuario());
            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ArqueoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getEstadoCaja(int idArqueo) {
        String estado = "0";

        String sql = "select estado from arqueo where id = ?";

        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, idArqueo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                estado = rs.getString("estado");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ArqueoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return estado;
    }

    @Override
    public void cerrarArqueo(double importeCierre, int idArqueo) {
        String sql = "update arqueo "
                + "set importe_cierre = " + String.valueOf(importeCierre) + " , estado = 'C', "
                + "fecha_cierre = now() "
                + "where id = " + String.valueOf(idArqueo);

        try {
            PreparedStatement ps;

            ps = connectionDB.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ArqueoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public double getCajaInicial(int idArqueo) {
        double cajaInicial = 0;

        String sql = "select caja_inicial from arqueo where id = ?";

        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, idArqueo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cajaInicial = rs.getDouble("caja_inicial");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ArqueoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cajaInicial;
    }

    @Override
    public int getIdArqueo(byte idLugar, byte idTerminal, String usuario) {
        int id = 0;

        String sql = "select id from arqueo where id_lugar = ? and id_terminal = ? and usuario = ? "
                + "and estado = 'A'";

        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setByte(1, idLugar);
            ps.setByte(2, idTerminal);
            ps.setString(3, usuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ArqueoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    @Override
    public ArrayList<Integer> getListaTransaccionArqueoPorUsuarioMaquina(byte idLugar, byte idTerminal, String usuario) {
        ArrayList<Integer> lTrans = new ArrayList<>();

        String sql = "select id from transaccion "
                + "where id_lugar = ? and id_terminal = ? and usuario = ? AND estado = 'A'";

        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setByte(1, idLugar);
            ps.setByte(2, idTerminal);
            ps.setString(3, usuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lTrans.add(rs.getInt("id"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ArqueoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lTrans;
    }
  
    @Override
    public void cerrarCaja(ArrayList<Integer> lTrans, int idArqueo) {
        String listaTrans = "0";
        for (int i = 0; i < lTrans.size(); i++) {
            if (i == 0) {
                listaTrans = String.valueOf(lTrans.get(i));
            } else {
                listaTrans = listaTrans + ", " + String.valueOf(lTrans.get(i));
            }
        }
        String sql = "update caja set estado = 'C', id_arqueo = " + String.valueOf(idArqueo)
                + " where id_transaccion in (" + listaTrans + ")";
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ArqueoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void cerrarCreditoCaja(byte idLugar, byte idTerminal, String usuario, int idArqueo) {
        String sql = "update caja c, transaccion t "
                + "set c.estado = 'C', id_arqueo = ? " 
                + "where c.id_transaccion = t.id and "
                + "c.estado = 'A' and "
                + "t.id_terminal = ? and "
                + "c.usuario = ? and "
                + "t.id_lugar = ? and "
                + "c.id_transaccion in(select id_transaccion from credito)";
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, idArqueo);
            ps.setInt(2, idTerminal);
            ps.setString(3, usuario);
            ps.setInt(4, idLugar);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ArqueoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public double getImportePorArqueoUsuarioMaquina(ArrayList<Integer> lTrans) {
        double importeTotal = 0;

        String listaTrans = "0";
        for (int i = 0; i < lTrans.size(); i++) {
            if (i == 0) {
                listaTrans = String.valueOf(lTrans.get(i));
            } else {
                listaTrans = listaTrans + ", " + String.valueOf(lTrans.get(i));
            }
        }

        String sql = "select sum(c.importe * tipo_movimiento) importe_total "
                + "from caja c join transaccion t on c.id_transaccion = t.id "
                + "where t.id in (" + listaTrans + ")";

        PreparedStatement ps;
        try {
            ps = connectionDB.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                importeTotal = rs.getDouble("importe_total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ArqueoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return importeTotal;
    }
@Override
    public double getImportePorArqueoUsuarioMaquina(byte idLugar, byte idTerminal, String usuario) {
        double importeTotal = 0;
        
        String sql = "select sum(c.importe * tipo_movimiento) importe_total "
                + "from caja c join transaccion t on c.id_transaccion = t.id "
                + "where t.id_lugar = ? and "
                + "t.id_terminal = ? and "
                + "c.usuario = ? and c.estado = 'A'";

        PreparedStatement ps;
        try {
            ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, idLugar);
            ps.setInt(2, idTerminal);
            ps.setString(3, usuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                importeTotal = rs.getDouble("importe_total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ArqueoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return importeTotal;
    }

    
    @Override
    public void cerrarTransacciones(ArrayList<Integer> lTrans) {
        String listaTrans = "0";
        for (int i = 0; i < lTrans.size(); i++) {
            if (i == 0) {
                listaTrans = String.valueOf(lTrans.get(i));
            } else {
                listaTrans = listaTrans + ", " + String.valueOf(lTrans.get(i));
            }
        }

        String sql = "update transaccion set estado = 'C' where id in (" + listaTrans + ")";
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ArqueoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    
    @Override
    public ArrayList<Arqueo> getListaArqueos(byte mes, int anno) {
        ArrayList<Arqueo> listaArqueos = new ArrayList<Arqueo>();

        String sql = "select * from arqueo "
                + "where estado = 'C' and year(fecha_cierre) = ? and month(fecha_cierre)=? "
                + "order by id desc";

        PreparedStatement ps;
        try {
            ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, anno);
            ps.setByte(2, mes);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Arqueo arq = new Arqueo();
                arq.setId(rs.getInt("id"));
                arq.setCajaInicial(rs.getDouble("caja_inicial"));
                arq.setEstado(rs.getString("estado"));
                arq.setFechaApertura(rs.getDate("fecha_apertura"));
                arq.setFechaCierre(rs.getDate("fecha_cierre"));
                arq.setIdLugar(rs.getByte("id_lugar"));
                arq.setIdTerminal(rs.getByte("id_terminal"));
                arq.setImporteCierre(rs.getDouble("importe_cierre"));
                arq.setUsuario(rs.getString("usuario"));
                
                listaArqueos.add(arq);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ArqueoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaArqueos;
    }

    @Override
    public ArrayList<Integer> getListaAnnosArqueos() {
    String sql = "select year(fecha_apertura) anno "
                + "from arqueo "
                + "group by year(fecha_apertura) "
                + "order by year(fecha_apertura) desc";
        ArrayList<Integer> lanno = new ArrayList<>();
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            lanno.add(0);
            while(rs.next()){
                lanno.add(rs.getInt("anno"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lanno;
    }

    

}
