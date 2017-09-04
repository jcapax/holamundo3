/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.model.Configuracion;
import almacenes.model.Permiso;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author georgeguitar
 */
public class SistemaDAOImpl implements SistemaDAO {
    private final Connection connectionDB;
        
    public SistemaDAOImpl(Connection _connectionDB) {
        this.connectionDB = _connectionDB;
    }
    
    @Override
    public List<Permiso> getPermisosUsuario(String idUsuario) {
        List<Permiso> permisosUsuario = new ArrayList<>();

        String sql = "   select p.id_permiso, p.nombre_permiso\n" +
                    "    from roles_permisos as rp\n" +
                    "    join permisos as p on p.id_permiso = rp.id_permiso\n" +
                    "    join rol as r on r.id = rp.id_rol\n" +
                    "    join usuario as u on u.rol = r.id\n" +
                    "    where u.login = \"" + idUsuario + "\"";
        
        try {
            PreparedStatement pst = connectionDB.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                Permiso permiso = new Permiso();
                permiso.setIdPermiso(rs.getLong("id_permiso"));
                permiso.setNombrePermiso(rs.getString("nombre_permiso"));
                permisosUsuario.add(permiso);
            }            
        } catch (SQLException ex) {
            Logger.getLogger(RubroDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }        
        
        return permisosUsuario;
    }

    public Configuracion getGestionConfiguraciones() throws SQLException {
        Configuracion configuracion = new Configuracion();

        Statement stmt = connectionDB.createStatement();

        String query = "SELECT id_configuracion, " +
                            "ruta_visor_pdf, " +
                            "ruta_destino_archivos_pdf, " +
                            "solo_guadar_archivos_pdf, " +
                            "ruta_programas_pg " +
                            "FROM configuraciones;";
        
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next())
        {
           configuracion.setIdConfiguracion(rs.getInt("id_configuracion"));
           configuracion.setRutaVisorPdf(rs.getString("ruta_visor_pdf"));
           configuracion.setRutaDestinoArchivosPDF(rs.getString("ruta_destino_archivos_pdf"));
           configuracion.setSoloGuadarArchivosPDF(rs.getBoolean("solo_guadar_archivos_pdf"));
           configuracion.setRutaProgramasPG(rs.getString("ruta_programas_pg"));
        }
        rs.close();

        return configuracion;
    }
    
    public void gestionConfiguracionesDB(Configuracion _configuracion) throws SQLException {
        String sql = "";
        Configuracion configuracion = new Configuracion();
        configuracion = _configuracion;

        PreparedStatement ps;

        // Solo debe haber un registo.
        if (getGestionConfiguraciones().getIdConfiguracion() != null) {
            configuracion.setIdConfiguracion(getGestionConfiguraciones().getIdConfiguracion());
        }
        
        if (configuracion.getIdConfiguracion() != null && configuracion.getIdConfiguracion() > 0) {
            sql = "UPDATE configuraciones " + 
                    "SET ruta_visor_pdf = \"" + configuracion.getRutaVisorPdf() + "\", " + 
                        "ruta_destino_archivos_pdf = \""+ configuracion.getRutaDestinoArchivosPDF() + "\", " + 
                        "solo_guadar_archivos_pdf = "+ configuracion.getSoloGuadarArchivosPDF() + ", " +
                        "ruta_programas_pg = \"" + configuracion.getRutaProgramasPG() + "\" " + 
                    "WHERE id_configuracion = " + configuracion.getIdConfiguracion();
            ps = connectionDB.prepareStatement(sql);
            ps.executeUpdate();
        } else {
            sql = "INSERT INTO configuraciones " +
                    "(ruta_visor_pdf, " +
                    "ruta_destino_archivos_pdf, " +
                    "solo_guadar_archivos_pdf, " +
                    "ruta_programas_pg) " +
                    "VALUES (\"" + configuracion.getRutaVisorPdf() + "\", " +
                            "\"" + configuracion.getRutaDestinoArchivosPDF() + "\", " +
                            configuracion.getSoloGuadarArchivosPDF() + ", " +
                            "\"" + configuracion.getRutaProgramasPG() + "\")";
            ps = connectionDB.prepareStatement(sql);
            ps.execute();
        }

    }
}
