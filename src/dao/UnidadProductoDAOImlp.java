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
        String sql = "SELECT * FROM v_productos WHERE id_producto = " + String.valueOf(idProducto);
        
        ArrayList<UnidadProducto> lUnidadProducto = new ArrayList<UnidadProducto>();
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UnidadProducto uProd = new UnidadProducto();
                uProd.setId(rs.getInt("id_unidad_producto"));
                uProd.setIdProdcuto(rs.getInt("id_producto"));
                uProd.setIdUnidadMedida(rs.getInt("id_unidad_medida"));
                uProd.setNombreUnidadMedida(rs.getString("nombre_unidad_medida"));
                uProd.setNombreUnidadPrincipal(rs.getString("nombre_unidad_principal"));
                uProd.setPrecioCompra(rs.getDouble("precio_compra"));
                uProd.setPrecioVenta(rs.getDouble("precio_venta"));
                uProd.setPrecioVentaAumento(rs.getDouble("precio_venta_aumento"));
                uProd.setPrecioVentaRebaja(rs.getDouble("precio_venta_rebaja"));
                uProd.setStockMinimo(rs.getDouble("stock_minimo"));
                uProd.setUnidadPrincipal(rs.getInt("unidad_principal"));
                uProd.setGarantiaMeses(rs.getInt("garantia_meses"));
                
                lUnidadProducto.add(uProd);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UnidadProductoDAOImlp.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lUnidadProducto;
    }
    
    @Override
    public void insertarUnidadProducto(UnidadProducto unidadProducto) {
        String sql = "INSERT INTO unidad_producto"
                + "(id_producto, id_unidad_medida, unidad_principal, stock_minimo, precio_venta, "
                + "precio_venta_rebaja, precio_venta_aumento, precio_compra, actualizacion, usuario, garantia_meses, codigo_adjunto) "
                + "VALUES(? ,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
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
            ps.setInt(11, unidadProducto.getGarantiaMeses());
            ps.setString(12, unidadProducto.getCodigoAdjunto());
            
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
        String sql = "DELETE FROM unidad_producto WHERE id = ?";
        
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
        String sql = "select stock from v_stock where id_producto = ? and id_unidad_medida = ? and id_lugar = ?";
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
        String sql = "update unidad_producto "
                + "set id_unidad_medida = ?, unidad_principal = ?, stock_minimo = ?, "
                + "precio_venta = ?, precio_venta_rebaja = ?, "
                + "precio_venta_aumento = ?, precio_compra = ?, usuario = ?, "
                + "garantia_meses = ?, codigo_adjunto = ? "
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
            ps.setInt(9, unidadProducto.getGarantiaMeses());
            ps.setInt(10, unidadProducto.getId());
            ps.setString(11, unidadProducto.getCodigoAdjunto());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UnidadProductoDAOImlp.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
