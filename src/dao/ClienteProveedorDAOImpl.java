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
        String sql = "SELECT * FROM clienteProveedor where tipo = ?";
        
        ArrayList<ClienteProveedor> cliPro = new ArrayList<ClienteProveedor>();
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ps.setString(1, tipo);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                ClienteProveedor cp = new ClienteProveedor();
                
                cp.setCedulaIdentidad(rs.getString("cedulaIdentidad"));
                cp.setDireccion(rs.getString("direccion"));
                cp.setEstado(rs.getString("estado"));
                cp.setId(rs.getInt("id"));
                cp.setNit(rs.getString("nit"));
                cp.setNombreCompleto(rs.getString("nombreCompleto"));
                cp.setOtrosDatos(rs.getString("otrosDatos"));
                cp.setRazonSocial(rs.getString("razonSocial"));
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
            String sql = "insert into clienteProveedor("
                    + "tipo, cedulaIdentidad, nombreCompleto, razonSocial, "
                    + "nit, direccion, telefonos, otrosDatos, estado) "
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
        String sql = "delete from clienteProveedor where id = ?";
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
        String sql = "update clienteProveedor "
                + "set cedulaIdentidad = ?, nombreCompleto = ?, "
                + "razonSocial = ?, nit = ?, direccion = ?, "
                + "telefonos = ?, otrosDatos = ?, estado = ? "
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
    public TreeMap<String, Integer> clienteProveedorClaveValor(String tipo) {
        TreeMap<String, Integer> map = new TreeMap<String, Integer>();
        String sql = null;
        
         sql = "SELECT * FROM clienteProveedor WHERE tipo = '"+tipo+"' ORDER BY razonSocial, nombreCompleto";
        
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ResultSet rs  = ps.executeQuery();
            
            String nombreCompleto = null;
            String razonSocial = null;
            String descripcion = null;
            
            while(rs.next()){
                ClienteProveedor cp = new ClienteProveedor(); 
                nombreCompleto = rs.getString("nombreCompleto");
                razonSocial = rs.getString("razonSocial");
                
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
            
        } catch (Exception e) {
        }
        
        return map;
    }
    
}
