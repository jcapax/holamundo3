/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.model.Usuario;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author georgeguitar
 */
public interface UsuariosDAO {
    public List<Usuario> listar();
    public ArrayList<Usuario> getlistaUsuarios();
    public String verificarUsuario(String nombreUsuario, String password);
    public void insertarUsuario(Usuario usuario);
    public void eliminarUsuario(String usuario);
    public boolean validarEliminacionUsuario(String login);
    public void editarUsuario(Usuario usuario);
    public boolean verificarPassEdicionUsuario(String login, String pass);
    public boolean verificarPass1Pass2(char[] pass1, char[] pass2);
    public int getRolUsuario(String login);
}
