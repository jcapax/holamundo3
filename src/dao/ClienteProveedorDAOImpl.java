/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.ClienteProveedor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jcarlos.porcel
 */
public class ClienteProveedorDAOImpl implements ClienteProveedorDAO{
    
    
    private DatabaseUtils databaseUtils;
    private Connection connectionDB;

    public ClienteProveedorDAOImpl(Connection _connectionDB) {
        this.databaseUtils = new DatabaseUtils();
        this.connectionDB = _connectionDB;
    }


    @Override
    public ArrayList<ClienteProveedor> getListaClienteProveedor(String tipo) {
        String sql = "SELECT * FROM cliente_proveedor where tipo = ?";
        
        ArrayList<ClienteProveedor> cliPro = new ArrayList<ClienteProveedor>();
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setString(1, tipo);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                ClienteProveedor cp = new ClienteProveedor();
                
                cp.setCedulaIdentidad(rs.getString("cedula_identidad"));
                cp.setDireccion(rs.getString("direccion"));
                cp.setEstado(rs.getString("estado"));
                cp.setId(rs.getInt("id"));
                cp.setNit(rs.getString("nit"));
                cp.setNombreCompleto(rs.getString("nombre_completo"));
                cp.setOtrosDatos(rs.getString("otros_datos"));
                cp.setRazonSocial(rs.getString("razon_social"));
                cp.setTelefonos(rs.getString("telefonos"));
                cp.setTipo(rs.getString("tipo"));               
                
                cliPro.add(cp);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(RubroDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return cliPro;
    }

    @Override
    public void insertarClienteProveedor(ClienteProveedor clienteProveedor) {
        try {
            String sql = "insert into cliente_proveedor("
                    + "tipo, cedula_identidad, nombre_completo, razon_social, "
                    + "nit, direccion, telefonos, otros_datos, estado) "
                    + "values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setString(1, clienteProveedor.getTipo());
            ps.setString(2, clienteProveedor.getCedulaIdentidad());
            ps.setString(3, clienteProveedor.getNombreCompleto());
            ps.setString(4, clienteProveedor.getRazonSocial());
            if(clienteProveedor.getNit().equals("")){
                ps.setString(5, "0");
            }
            else{
                ps.setString(5, clienteProveedor.getNit());
            }
            ps.setString(6, clienteProveedor.getDireccion());
            ps.setString(7, clienteProveedor.getTelefonos());
            ps.setString(8, clienteProveedor.getOtrosDatos());
            ps.setString(9, clienteProveedor.getEstado());            
            ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(ClienteProveedorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void eliminarClienteProveedor(int idCliente) {
        String sql = "delete from cliente_proveedor where id = ?";
        PreparedStatement ps;
        try {
            ps = connectionDB.prepareStatement(sql);
            ps.setInt(1, idCliente);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ClienteProveedorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actualizarClienteProveedor(ClienteProveedor clienteProveedor) {
        String sql = "update cliente_proveedor "
                + "set cedula_identidad = ?, nombre_completo = ?, "
                + "razon_social = ?, nit = ?, direccion = ?, "
                + "telefonos = ?, otros_datos = ?, estado = ? "
                + "where id = ?";
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setString(1, clienteProveedor.getCedulaIdentidad());
            ps.setString(2, clienteProveedor.getNombreCompleto());
            ps.setString(3, clienteProveedor.getRazonSocial());
            ps.setString(4, clienteProveedor.getNit());
            ps.setString(5, clienteProveedor.getDireccion());
            ps.setString(6, clienteProveedor.getTelefonos());
            ps.setString(7, clienteProveedor.getOtrosDatos());
            ps.setString(8, clienteProveedor.getEstado());
            ps.setInt(9, clienteProveedor.getId());
            ps.executeUpdate();
                    
        } catch (SQLException ex) {
            Logger.getLogger(ClienteProveedorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean validarEliminacionClienteProveedor() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HashMap<String, Integer> clienteProveedorClaveValor(String tipo) {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        String sql = null;
        
         sql = "SELECT * FROM cliente_proveedor WHERE tipo = '"+tipo+"' ORDER BY razon_social, nombre_completo";
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ResultSet rs  = ps.executeQuery();
            
            String nombreCompleto = null;
            String razonSocial = null;
            String descripcion = null;
            
            while(rs.next()){
                ClienteProveedor cp = new ClienteProveedor(); 
                nombreCompleto = rs.getString("nombre_completo");
                razonSocial = rs.getString("razon_social");
                
                if(razonSocial.equals("")){
                    descripcion = nombreCompleto;
                }
                else{
                    if(nombreCompleto.equals("")){
                        descripcion = razonSocial;
                    }
                    else{
                        descripcion =  razonSocial+" / "+nombreCompleto;
                    }
                }
                map.put(descripcion, rs.getInt("id"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ProductoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return map;
    }
    
}
