/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package almacenes.conectorDB;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jcapax
 */
public class DataBaseSqlite {
    Connection connect;
    String url = new File ("bd/jventas.db").getAbsolutePath ();
    
    public Connection conexion(){
        try {
            connect = DriverManager.getConnection("jdbc:sqlite:"+url);
            if (connect!=null) {
//                System.out.println("Conectado a sqlite");
            }
         }catch (SQLException ex) {
//            System.err.println("No se ha podido conectar a la base de datos\n"+ex.getMessage());
         }
        return connect;
    }
    public void close(){
           try {
               connect.close();
           } catch (SQLException ex) {
               Logger.getLogger(DataBaseSqlite.class.getName()).log(Level.SEVERE, null, ex);
           }
    }
    
    
}
