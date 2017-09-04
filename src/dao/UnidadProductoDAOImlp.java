/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.UnidadProducto;
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
public class UnidadProductoDAOImlp implements UnidadProductoDAO {
    
    private DatabaseUtils databaseUtils;
    private Connection connectionDB;
    
    public UnidadProductoDAOImlp(Connection _connectionDB) {
        this.databaseUtils = new DatabaseUtils();
        this.connectionDB = _connectionDB;
    }
    
    @Override
    public ArrayList<UnidadProducto> getListaUnidadProducto(int idProducto) {
        String sql = "SELECT * FROM vproductos WHERE idProducto = " + String.valueOf(idProducto);
        
        ArrayList<UnidadProducto> lUnidadProducto = new ArrayList<UnidadProducto>();
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UnidadProducto uProd = new UnidadProducto();
                uProd.setId(rs.getInt("idUnidadProducto"));
                uProd.setIdProdcuto(rs.getInt("idProducto"));
                uProd.setIdUnidadMedida(rs.getInt("idUnidadMedida"));
                uProd.setNombreUnidadMedida(rs.getString("nombreUnidadMedida"));
                uProd.setNombreUnidadPrincipal(rs.getString("nombreUnidadPrincipal"));
                uProd.setPrecioCompra(rs.getDouble("precioCompra"));
                uProd.setPrecioVenta(rs.getDouble("precioVenta"));
                uProd.setPrecioVentaAumento(rs.getDouble("precioVentaAumento"));
                uProd.setPrecioVentaRebaja(rs.getDouble("precioVentaRebaja"));
                uProd.setStockMinimo(rs.getDouble("stockMinimo"));
                uProd.setUnidadPrincipal(rs.getInt("unidadPrincipal"));
                
                lUnidadProducto.add(uProd);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UnidadProductoDAOImlp.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lUnidadProducto;
    }
    
    @Override
    public void insertarUnidadProducto(UnidadProducto unidadProducto) {
        String sql = "INSERT INTO unidadproducto"
                + "(IDPRODUCTO, IDUNIDADMEDIDA, UNIDADPRINCIPAL, STOCKMINIMO, PRECIOVENTA, "
                + "PRECIOVENTAREBAJA, PRECIOVENTAAUMENTO, PRECIOCOMPRA, ACTUALIZACION, USUARIO) "
                + "VALUES(? ,?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            
            ps.setInt(1, unidadProducto.getIdProdcuto());
            ps.setInt(2, unidadProducto.getIdUnidadMedida());
            ps.setInt(3, unidadProducto.getUnidadPrincipal());
            ps.setDouble(4, unidadProducto.getStockMinimo());
            ps.setDouble(5, unidadProducto.getPrecioVenta());
            ps.setDouble(6, unidadProducto.getPrecioVentaRebaja());
            ps.setDouble(7, unidadProducto.getPrecioVentaAumento());
            ps.setDouble(8, unidadProducto.getPrecioCompra());
            ps.setInt(9, unidadProducto.getActualizacion());
            ps.setString(10, "SYS");
            
            int n = ps.executeUpdate();
            if (n != 0) {
//                System.out.println("registro ingresado");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UnidadProductoDAOImlp.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Override
    public void eliminarUnidadProducto(int idUnidadProducto) {
        String sql = "DELETE FROM unidadProducto WHERE id = ?";
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, idUnidadProducto);
                        
            int n = ps.executeUpdate();
            if (n != 0) {
//                System.out.println("registro eliminado");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UnidadProductoDAOImlp.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Override
    public void actualizarUnidadProducto(UnidadProducto unidadProducto) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public double getStockProducto(int idProducto, int idUnidadMedida, int idLugar) {
        double stockProducto = 0;
        String sql = "select stock from vStock where idProducto = ? and idUnidadMedida = ? and idLugar = ?";
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, idProducto);
            ps.setInt(2, idUnidadMedida);
            ps.setInt(3, idLugar);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                stockProducto = rs.getDouble("stock");
            }
        } catch (SQLException ex) {
            Logger.getLogger(TransaccionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return stockProducto;
        
    }
    
    @Override
    public void editarUnidadProducto(UnidadProducto unidadProducto) {
        String sql = "update unidadProducto "
                + "set idUnidadMedida = ?, unidadPrincipal = ?, stockMinimo = ?, "
                + "precioVenta = ?, precioVentaRebaja = ?, "
                + "precioVentaAumento = ?, precioCompra = ?, usuario = ? "
                + "where id = ?";
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, unidadProducto.getIdUnidadMedida());
            ps.setInt(2, unidadProducto.getUnidadPrincipal());
            ps.setDouble(3, unidadProducto.getStockMinimo());
            ps.setDouble(4, unidadProducto.getPrecioVenta());
            ps.setDouble(5, unidadProducto.getPrecioVentaRebaja());
            ps.setDouble(6, unidadProducto.getPrecioVentaAumento());
            ps.setDouble(7, unidadProducto.getPrecioCompra());
            ps.setString(8, unidadProducto.getUsuario());
            ps.setInt(9, unidadProducto.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UnidadProductoDAOImlp.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
