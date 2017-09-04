/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.model.Configuracion;
import almacenes.model.Permiso;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author georgeguitar
 */
public interface SistemaDAO {
    public List<Permiso> getPermisosUsuario(String idUsuario);
    public Configuracion getGestionConfiguraciones() throws SQLException;
    public void gestionConfiguracionesDB(Configuracion configuracion) throws SQLException;
}
