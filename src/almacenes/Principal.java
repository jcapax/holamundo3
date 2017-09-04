/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package almacenes;

import almacenes.conectorDB.DatabaseUtils;
import almacenes.vistas.FormLogin;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author georgeguitar
 */
public class Principal {
    private static DatabaseUtils databaseUtils;
    private static Connection connectionDB;
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            databaseUtils = new DatabaseUtils();
            connectionDB = databaseUtils.createConnection(almacenes.conectorDB.DatosConexion.DEFAULT_DRIVER,
                    almacenes.conectorDB.DatosConexion.DEFAULT_URL +
                    almacenes.conectorDB.DatosConexion.DB_HOST + "/" +
                    almacenes.conectorDB.DatosConexion.DB_NAME, 
                    almacenes.conectorDB.DatosConexion.DEFAULT_USERNAME, 
                    almacenes.conectorDB.DatosConexion.DEFAULT_PASSWORD);
            
            //Asigna el tema de Windows si está disponible: en GNU y MAC no estará disponible.
            for (UIManager.LookAndFeelInfo info: UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            
            new FormLogin(connectionDB).setVisible(true);   
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
