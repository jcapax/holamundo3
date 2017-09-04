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
        String sql = "INSERT INTO arqueo(fechaApertura, "
                + "cajaInicial, Estado, idTerminal, idLugar, usuario) "
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
                + "set importeCierre = " + String.valueOf(importeCierre) + " , estado = 'C', "
                + "fechaCierre = now() "
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

        String sql = "select cajaInicial from arqueo where id = ?";

        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, idArqueo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cajaInicial = rs.getDouble("cajaInicial");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ArqueoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cajaInicial;
    }

    @Override
    public int getIdArqueo(byte idLugar, byte idTerminal, String usuario) {
        int id = 0;

        String sql = "select id from arqueo where idLugar = ? and idTerminal = ? and usuario = ? "
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
                + "where idLugar = ? and idTerminal = ? and usuario = ? AND estado = 'A'";

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
        String sql = "update caja set estado = 'C', idArqueo = " + String.valueOf(idArqueo)
                + " where idTransaccion in (" + listaTrans + ")";
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
                + "set c.estado = 'C', idArqueo = ? " 
                + "where c.idTransaccion = t.id and "
                + "c.estado = 'A' and "
                + "t.idTerminal = ? and "
                + "c.usuario = ? and "
                + "t.idLugar = ? and "
                + "c.idTransaccion in(select idTransaccion from credito)";
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

        String sql = "select sum(c.importe * tipoMovimiento) importeTotal "
                + "from caja c join transaccion t on c.idTransaccion = t.id "
                + "where t.id in (" + listaTrans + ")";

        PreparedStatement ps;
        try {
            ps = connectionDB.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                importeTotal = rs.getDouble("importeTotal");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ArqueoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return importeTotal;
    }
@Override
    public double getImportePorArqueoUsuarioMaquina(byte idLugar, byte idTerminal, String usuario) {
        double importeTotal = 0;
        
        String sql = "select sum(c.importe * tipoMovimiento) importeTotal "
                + "from caja c join transaccion t on c.idTransaccion = t.id "
                + "where t.idLugar = ? and "
                + "t.idTerminal = ? and "
                + "c.usuario = ? and c.estado = 'A'";

        PreparedStatement ps;
        try {
            ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, idLugar);
            ps.setInt(2, idTerminal);
            ps.setString(3, usuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                importeTotal = rs.getDouble("importeTotal");
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
                + "where estado = 'C' and year(fechaCierre) = ? and month(fechaCierre)=? "
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
                arq.setCajaInicial(rs.getDouble("cajaInicial"));
                arq.setEstado(rs.getString("estado"));
                arq.setFechaApertura(rs.getDate("fechaApertura"));
                arq.setFechaCierre(rs.getDate("fechaCierre"));
                arq.setIdLugar(rs.getByte("idLugar"));
                arq.setIdTerminal(rs.getByte("idTerminal"));
                arq.setImporteCierre(rs.getDouble("importeCierre"));
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
    String sql = "select year(fechaApertura) anno "
                + "from arqueo "
                + "group by year(fechaApertura) "
                + "order by year(fechaApertura) desc";
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
