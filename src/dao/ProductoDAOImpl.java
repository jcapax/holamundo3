/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.ListaProductos;
import almacenes.model.Producto;
import almacenes.model.StockProducto;
import almacenes.model.StockVencimiento;
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
    public ArrayList<ListaProductos> getListaProductos() {
        String sql = "SELECT * FROM v_productos";
        
        ArrayList<ListaProductos> lproducto = new ArrayList<ListaProductos>();
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                ListaProductos lProd = new ListaProductos();
                
                lProd.setId(rs.getInt("id_producto"));
                lProd.setACTUALIZACION(rs.getInt("actualizacion"));
                lProd.setControlStock(rs.getInt("control_stock"));
                lProd.setDescripcion(rs.getString("descripcion"));
                lProd.setCaducidad(rs.getInt("caducidad"));
                lProd.setEstado(rs.getString("estado"));
                lProd.setIDUNIDADMEDIDA(rs.getInt("id_unidad_medida"));
                lProd.setIdMarca(rs.getInt("id_marca"));
                lProd.setIdProcedencia(rs.getInt("id_procedencia"));
                lProd.setIdRubroProducto(rs.getInt("id_rubro_producto"));
                lProd.setMarca(rs.getString("marca"));
                lProd.setNombreUnidadMedida(rs.getString("nombre_unidad_medida"));
                lProd.setPRECIOCOMPRA(rs.getDouble("precio_compra"));
                lProd.setPRECIOVENTA(rs.getDouble("precio_venta"));
                lProd.setPRECIOVENTAAUMENTO(rs.getDouble("precio_venta_aumento"));
                lProd.setPRECIOVENTAREBAJA(rs.getDouble("precio_venta_rebaja"));
                lProd.setProcedencia(rs.getString("procedencia"));
                lProd.setRubro(rs.getString("rubro"));
                lProd.setSTOCKMINIMO(rs.getDouble("stock_minimo"));
                lProd.setUNIDADPRINCIPAL(rs.getInt("unidad_principal"));
                lProd.setCodigoAdjunto(rs.getString("codigo_adjunto"));
                
                lproducto.add(lProd);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(RubroDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lproducto;
    }
    

    @Override
    public void insertarProducto(Producto producto) {
        String sqlProd = "insert into "+
                "producto(id_rubro_producto, id_marca, id_procedencia, descripcion, estado, control_stock, caducidad, usuario) "+
                "values(?, ?, ?, ?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sqlProd);
            
            ps.setInt(1, producto.getIdRubroProducto());
            ps.setInt(2, producto.getIdMarca());
            ps.setInt(3, producto.getIdProcedencia());
            ps.setString(4, producto.getDescripcion());
            ps.setString(5, producto.getEstado());
            ps.setInt(6, producto.getControlStock());
            ps.setInt(7, producto.getCaducidad());
            ps.setString(8, producto.getUsuario());
            
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
    public ArrayList<ListaProductos> getListaProductosVenta(String criterio) {
        String sql = "SELECT * FROM v_productos WHERE estado = 'V' and descripcion like '%"+criterio+"%' "
                + "and id_unidad_producto is not null";
        
        ArrayList<ListaProductos> lproducto = new ArrayList<ListaProductos>();
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                ListaProductos lProd = new ListaProductos();
                
                lProd.setId(rs.getInt("id_producto"));
                lProd.setACTUALIZACION(rs.getInt("actualizacion"));
                lProd.setControlStock(rs.getInt("control_stock"));
                lProd.setCaducidad(rs.getInt("caducidad"));
                lProd.setDescripcion(rs.getString("descripcion"));
                lProd.setEstado(rs.getString("estado"));
                lProd.setIDUNIDADMEDIDA(rs.getInt("id_unidad_medida"));
                lProd.setIdMarca(rs.getInt("id_marca"));
                lProd.setIdProcedencia(rs.getInt("id_procedencia"));
                lProd.setIdRubroProducto(rs.getInt("id_rubro_producto"));
                lProd.setMarca(rs.getString("marca"));
                lProd.setNombreUnidadMedida(rs.getString("nombre_unidad_medida"));
                lProd.setPRECIOCOMPRA(rs.getDouble("precio_compra"));
                lProd.setPRECIOVENTA(rs.getDouble("precio_venta"));
                lProd.setPRECIOVENTAAUMENTO(rs.getDouble("precio_venta_aumento"));
                lProd.setPRECIOVENTAREBAJA(rs.getDouble("precio_venta_rebaja"));
                lProd.setProcedencia(rs.getString("procedencia"));
                lProd.setRubro(rs.getString("rubro"));
                lProd.setSTOCKMINIMO(rs.getDouble("stock_minimo"));
                lProd.setUNIDADPRINCIPAL(rs.getInt("unidad_principal"));
                
                lproducto.add(lProd);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(RubroDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lproducto;
    }

    @Override
    public ArrayList<StockProducto> getListaStockProducto(byte idLugar, String criterio) {
        ArrayList<StockProducto> lStockProducto = new ArrayList<StockProducto>();
        
        String sql = "SELECT s.id_producto, s.id_unidad_medida, p.descripcion, p.nombre_unidad_medida, s.stock "
                + "FROM v_stock s"
                + " JOIN v_productos p ON p.id_producto = s.id_producto"
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

    @Override
    public void editarProducto(Producto producto) {
        String sql = "update producto "
                + "set id_rubro_producto = ?, id_procedencia = ?, "
                + "id_marca = ?, descripcion = ?, estado = ?, "
                + "control_stock = ?, caducidad = ?, usuario = ? "
                + "where id = ?";
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, producto.getIdRubroProducto());
            ps.setInt(2, producto.getIdProcedencia());
            ps.setInt(3, producto.getIdMarca());
            ps.setString(4, producto.getDescripcion());
            ps.setString(5, producto.getEstado());
            ps.setInt(6, producto.getControlStock());
            ps.setInt(7, producto.getCaducidad());
            ps.setString(8, producto.getUsuario());
            ps.setInt(9, producto.getId());
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(ProductoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

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
    
}
