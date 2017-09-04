/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author georgeguitar
 */
public class UsuariosDAOImpl implements UsuariosDAO {
        private DatabaseUtils databaseUtils;
        private Connection connectionDB;
        
        
        public UsuariosDAOImpl(Connection _connectionDB) {
            this.databaseUtils = new DatabaseUtils();
            this.connectionDB = _connectionDB;
        
	}
    
    	@Override
	public List<Usuario> listar() {
            ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();
            
            List<Map<String, Object>> respuesta = new ArrayList<Map<String, Object>>();
            try {
                respuesta = this.databaseUtils.consulta(connectionDB, "SELECT * FROM usuario");
          
                for (Map<String, Object> resp : respuesta) {
                    Usuario usuario = new Usuario();
                    usuario.setLogin(resp.get("login").toString());
                    usuario.setNombres(resp.get("NOMBRES").toString());
                    usuario.setApellidos(resp.get("APELLIDOS").toString());
                    usuario.setDireccion(resp.get("DIRECCION").toString());
                    usuario.setTelefono(resp.get("TELEFONO").toString());
                    //usuario.setTelCelular(resp.get("CELULAR").toString());                    
                    usuario.setContrasenia(resp.get("PASS").toString());
                    usuario.setRol(resp.get("ROL").toString());
                    //usuario.setFechaHoraRegistro(resp.get("FECHAHORAREGISTRO").toString());
                    //usuario.setEstado(resp.get("ESTADO").toString());
                    usuario.setUsuarioRegistro(resp.get("USUARIOREGISTRO").toString());

                    listaUsuarios.add(usuario);
                }
            } catch (SQLException ex) {
                Logger.getLogger(UsuariosDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
		
            return listaUsuarios;
	}
        
    @Override
    public String verificarUsuario(String nombreUsuario, String password) {
        String idUsuario = "";

        String sql = "SELECT login FROM usuario WHERE login = \"" +
                nombreUsuario + "\" and pass = md5(?)";
        
        try {
            PreparedStatement pst = connectionDB.prepareStatement(sql);
            pst.setString(1, password);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){              
                idUsuario = rs.getString("login");
            }            
        } catch (SQLException ex) {
            Logger.getLogger(RubroDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }        
        
        return idUsuario;
    }

    @Override
    public void insertarUsuario(Usuario usuario) {
        String sql = "insert into usuario(login, pass, rol, estado) "
                + "values(?, md5(?), ?, ?)";
        
            try {
                PreparedStatement ps = connectionDB.prepareStatement(sql);
                ps.setString(1, usuario.getLogin());                
                ps.setString(2, usuario.getContrasenia());
                ps.setString(3, usuario.getRol());
                ps.setString(4, usuario.getEstado());
                ps.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(UsuariosDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    }

    @Override
    public ArrayList<Usuario> getlistaUsuarios() {
        ArrayList<Usuario> listaUsuario = new ArrayList<Usuario>();
        
        String sql = "select * from usuario";
        
            try {
                PreparedStatement ps = connectionDB.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    Usuario us = new Usuario();
                    us.setLogin(rs.getString("login"));                    
                    us.setRol(rs.getString("rol"));
                    us.setEstado(rs.getString("estado"));
                    
                    listaUsuario.add(us);
                }
            } catch (SQLException ex) {
                Logger.getLogger(UsuariosDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        return listaUsuario;
    }

    @Override
    public boolean verificarPassEdicionUsuario(String login, String pass) {
        boolean aux = true;
        
        String sql = "select login from usuario where login = ? and pass = md5(?)";
        
        PreparedStatement ps;
            try {
                ps = connectionDB.prepareStatement(sql);
                ps.setString(1, login);
                ps.setString(2, pass);
                ResultSet rs = ps.executeQuery();
                int x = 0;
                while(rs.next()){
                    x++;
                }
                if(x==0){
                    aux = false;
                }
            } catch (SQLException ex) {
                Logger.getLogger(UsuariosDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        return aux;
    }

    @Override
    public void editarUsuario(Usuario usuario) {
        String sql = "update usuario set pass = md5(?), estado = ?, rol = ? "
                + "where login = ?";
        
        PreparedStatement ps;
            try {
                ps = connectionDB.prepareCall(sql);
                ps.setString(1, usuario.getContrasenia());
                ps.setString(2, usuario.getEstado());
                ps.setString(3, usuario.getRol());
                ps.setString(4, usuario.getLogin());
                ps.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(UsuariosDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    }

    @Override
    public boolean verificarPass1Pass2(char[] pass1, char[] pass2) {
       boolean aux = true;
       
       String p1 = new String(pass1);
       String p2 = new String(pass2);
       
       if(!p1.equals(p2)){
           aux = false;
       }
       
       return aux;
    }

    @Override
    public void eliminarUsuario(String usuario) {
        String sql = "delete from usuario where login = ?";
            try {
                PreparedStatement ps = connectionDB.prepareStatement(sql);
                ps.setString(1, usuario);
                ps.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(UsuariosDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    @Override
    public boolean validarEliminacionUsuario(String login) {
        boolean aux = false;
        
        String sql = "SELECT usuario from transaccion where usuario = '"+login+"' LIMIT 1";
            try {
                PreparedStatement ps = connectionDB.prepareStatement(sql);
                //ps.setString(1, usuario);
                ResultSet rs = ps.executeQuery();
                int i = 0;
                while(rs.next()){
                    i++;
                }
                aux = (i==0)?true:false;                
            } catch (SQLException ex) {
                Logger.getLogger(UsuariosDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        return aux;
    }

    @Override
    public int getRolUsuario(String login) {
        int rol = 0;
        String sql = "select rol from usuario where login = ?";
        PreparedStatement ps;
            try {
                ps = connectionDB.prepareStatement(sql);
                ps.setString(1, login);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    rol = rs.getInt("rol");
                }
            } catch (SQLException ex) {
                Logger.getLogger(UsuariosDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        return rol;
    }
}
