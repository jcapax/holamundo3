/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jcapax
 */
public class ProductoDAOImpl implements ProductoDAO{

    private DatabaseUtils databaseUtils;
    private Connection connectionDB;

    public ProductoDAOImpl(Connection _connectionDB) {
        this.databaseUtils = new DatabaseUtils();
        this.connectionDB = _connectionDB;
    }
    
    @Override
    public ArrayList<Producto> getListaProductos() {
        String sql = "SELECT * FROM v_productos";
        
        ArrayList<Producto> list = new ArrayList<Producto>();
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Producto p = new Producto();
                
                p.setCaducidad(rs.getByte("caducidad"));
                p.setClaseProducto(rs.getString("clase_producto"));
                p.setControlStock(rs.getByte("control_stock"));
                p.setDescripcion(rs.getString("nombre_producto"));
                p.setEstado(rs.getString("estado"));
                p.setId(rs.getInt("id_producto"));
                p.setIdFamilia(rs.getInt("id_familia"));
                p.setIdLaboratorio(rs.getInt("id_laboratorio"));
                p.setIndicaciones(rs.getString("indicaciones"));
                p.setNombreFamilia(rs.getString("nombre_familia"));
                p.setNombreLaboratorio(rs.getString("nombre_laboratorio"));
                p.setPrincipioActivo(rs.getString("principio_activo"));
                p.setSimbolo(rs.getString("simbolo"));
                p.setPrecioCompra(rs.getDouble("precio_compra"));
                p.setPrecioVenta(rs.getDouble("precio_venta"));
                p.setIdUnidadMedida(rs.getInt("id_unidad_medida"));
                p.setStock(rs.getInt("stock"));
                
                list.add(p);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(RubroDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
    }
    

    @Override
    public void insertarProducto(Producto p) {
        String sqlProd = "INSERT INTO producto ("
                + "id_laboratorio, id_familia, clase_producto, "                
                + "descripcion, principio_activo, indicaciones, "
                + "tipo_cuenta, estado, control_stock, "                
                + "fecha_hora_registro, usuario) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, current_timestamp(), ?)";
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sqlProd);
            
            ps.setInt(1, p.getIdLaboratorio());
            ps.setInt(2, p.getIdFamilia());
            ps.setString(3, p.getClaseProducto());
            ps.setString(4, p.getDescripcion());
            ps.setString(5, p.getPrincipioActivo());
            ps.setString(6, p.getIndicaciones());
            ps.setString(7, p.getTipoCuenta());
            ps.setString(8, p.getEstado());            
            ps.setInt(9, p.getControlStock());
            ps.setString(10, p.getUsuario());
            
            int n = ps.executeUpdate();
            if(n > 0){
//                System.out.println("producto insertado");
            }
            else{
//                System.out.println("error en el registro");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ArrayList<Producto> getListaProductosVenta(String criterio, byte idLugar) {
        String sql = "SELECT * FROM v_productos "
                + "WHERE estado = 'V' and nombre_producto like '%"+criterio+"%' "
                + "and id_unidad_producto is not null";
        
        ArrayList<Producto> lproducto = new ArrayList<Producto>();
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Producto p = new Producto();
                
                p.setCaducidad(rs.getByte("caducidad"));
                p.setClaseProducto(rs.getString("clase_producto"));
                p.setControlStock(rs.getByte("control_stock"));
                p.setDescripcion(rs.getString("nombre_producto"));
                p.setEstado(rs.getString("estado"));
                p.setId(rs.getInt("id_producto"));
                p.setIdFamilia(rs.getInt("id_familia"));
                p.setIdUnidadProducto(rs.getInt("id_unidad_producto"));
                p.setIdLaboratorio(rs.getInt("id_laboratorio"));
                p.setIndicaciones(rs.getString("indicaciones"));
                p.setNombreFamilia(rs.getString("nombre_familia"));
                p.setNombreLaboratorio(rs.getString("nombre_laboratorio"));
                p.setPrincipioActivo(rs.getString("principio_activo"));
                p.setSimbolo(rs.getString("simbolo"));
                p.setPrecioCompra(rs.getDouble("precio_compra"));
                p.setPrecioVenta(rs.getDouble("precio_venta"));
                p.setIdUnidadMedida(rs.getInt("id_unidad_medida"));
                p.setStock(rs.getInt("stock"));
                
                lproducto.add(p);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(RubroDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lproducto;
    }
/*
    @Override
    public ArrayList<StockProducto> getListaStockProducto(byte idLugar, String criterio) {
        ArrayList<StockProducto> lStockProducto = new ArrayList<StockProducto>();
        
        String sql = "SELECT s.id_producto, s.id_unidad_medida, p.descripcion, p.nombre_unidad_medida, s.stock "
                + "FROM v_stock s"
                + " JOIN v_productos_lugar p ON p.id_producto = s.id_producto"
                + "  AND p.id_unidad_medida = s.id_unidad_medida "
                + "WHERE s.id_lugar = ? and p.descripcion like '%"+criterio+"%'"
                + " ORDER BY s.stock, p.descripcion";
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setByte(1, idLugar);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                StockProducto sp = new StockProducto();
                
                sp.setIdProducto(rs.getInt("id_producto"));
                sp.setIdUnidadMedida(rs.getByte("id_unidad_medida"));
                sp.setNombreProducto(rs.getString("descripcion"));
                sp.setNombreUnidadMedida(rs.getString("nombre_unidad_medida"));
                sp.setStock(rs.getDouble("stock"));
                
                lStockProducto.add(sp);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lStockProducto;
    }

    @Override
    public byte getControlStock(int idProducto) {
        byte controlStock = 0;
        String sql = "select control_stock from producto where id = ?";
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, idProducto);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                controlStock = rs.getByte("control_stock");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return controlStock;
        
    }
*/
    @Override
    public void editarProducto(Producto p) {
        String sql = "update producto "
                + "set id_laboratorio = ?, id_familia = ?, descripcion = ?, "
                + "principio_activo = ?, indicaciones = ?, estado = ?, "
                + "control_stock = ?, caducidad = ?, usuario = ? "
                + "where id = ?";
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, p.getIdLaboratorio());
            ps.setInt(2, p.getIdFamilia());            
            ps.setString(3, p.getDescripcion());
            ps.setString(4, p.getPrincipioActivo());
            ps.setString(5, p.getIndicaciones());
            ps.setString(6, p.getEstado());
            ps.setInt(7, p.getControlStock());
            ps.setInt(8, p.getCaducidad());
            ps.setString(9, p.getUsuario());
            ps.setInt(10, p.getId());
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(ProductoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
/*
    @Override
    public ArrayList<StockVencimiento> getListStockVencimiento(byte idLugar, int idProducto, byte idUnidadMedida) {
        ArrayList<StockVencimiento> list = new ArrayList<>();
        
        String sql = "SELECT nombre_unidad_medida, fecha_vencimiento, stock " +
                        "FROM v_stock_vencimiento " +
                        "WHERE id_lugar = ? and id_producto = ? and id_unidad_medida = ?";
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setByte(1, idLugar);
            ps.setInt(2, idProducto);
            ps.setByte(3, idUnidadMedida);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                StockVencimiento stock = new StockVencimiento();
                stock.setCantidad(rs.getDouble("stock"));
                stock.setNombreUnidadMedida(rs.getString("nombre_unidad_medida"));
                stock.setFechaVencimiento(rs.getDate("fecha_vencimiento"));
                list.add(stock);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
*/
    @Override
    public HashMap<String, Integer> getProductoClaveValor() {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        
        String sql = "select u.id_producto, p.descripcion, count(u.id_producto) \n" +
                        "from unidad_producto u \n" +
                        "	join producto p on u.id_producto = p.id \n" +
                        "group by u.id_producto, p.descripcion \n" +
                        "having count(u.id_producto) > 1";        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Producto p = new Producto();
                
                p.setId(rs.getInt("id_producto"));
                p.setDescripcion(rs.getString("descripcion"));
                
                map.put(p.getDescripcion(), p.getId());
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return map;
    }

    @Override
    public Producto getProductoById(int id) {
        String sql = "SELECT * FROM v_productos Where id_producto = "+id;
        Producto p = new Producto();        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();            
            while(rs.next()){
                p.setCaducidad(rs.getByte("caducidad"));
                p.setClaseProducto(rs.getString("clase_producto"));
                p.setControlStock(rs.getByte("control_stock"));
                p.setDescripcion(rs.getString("nombre_producto"));
                p.setEstado(rs.getString("estado"));
                p.setId(rs.getInt("id_producto"));
                p.setIdFamilia(rs.getInt("id_familia"));
                p.setIdLaboratorio(rs.getInt("id_laboratorio"));
                p.setIndicaciones(rs.getString("indicaciones"));
                p.setNombreFamilia(rs.getString("nombre_familia"));
                p.setNombreLaboratorio(rs.getString("nombre_laboratorio"));
                p.setPrincipioActivo(rs.getString("principio_activo"));
                p.setSimbolo(rs.getString("simbolo"));
                p.setPrecioCompra(rs.getDouble("precio_compra"));
                p.setPrecioVenta(rs.getDouble("precio_venta"));  
                p.setIdUnidadMedida(rs.getInt("id_unidad_medida"));
                p.setStock(rs.getInt("stock"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(RubroDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return p;
    }
    
}
