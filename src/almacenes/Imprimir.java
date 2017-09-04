/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package almacenes;

import com.jgoodies.common.base.SystemUtils;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;


/**
 *
 * @author georgeguitar
 */
public class Imprimir {
    private Connection connectionDB;
    private String idUsuario;
    
    private static String DECIMALES = "%.2f";  // Dos decimales
    private static String RUTA_REPORTES = "reportes";
    
    public Imprimir(Connection _connectionDB, String _idUsuario) {
        this.connectionDB = _connectionDB;
        this.idUsuario = _idUsuario;
    }

    private JasperPrint prepararReporte(String tituloReporte, String nombreReporte, Map<String, Object> parametros) {
        JasperPrint print = null;
        try {
            String rutaReporte = "";
            String rutaLogo = "";
            
            if (SystemUtils.IS_OS_WINDOWS) {
                rutaReporte = RUTA_REPORTES + "\\" + nombreReporte;
                if (parametros.get("nombreLogo") != null) {
                    rutaLogo = RUTA_REPORTES + "\\" + parametros.get("nombreLogo").toString();
                }
            } else if (SystemUtils.IS_OS_LINUX || SystemUtils.IS_OS_MAC) {
                rutaReporte = RUTA_REPORTES + "/" + nombreReporte;
                if (parametros.get("nombreLogo") != null) {
                    rutaLogo = RUTA_REPORTES + "/" + parametros.get("nombreLogo").toString();
                }
            }

            JasperReport report = JasperCompileManager.compileReport(rutaReporte);
            
            Map parameters = new HashMap();
            parameters = parametros;
            if (!rutaLogo.isEmpty()) {
                parameters.put("logo", rutaLogo);
            }

            print = JasperFillManager.fillReport(report, parameters, connectionDB);  
        } catch (Exception ex) {    
            JOptionPane.showMessageDialog( null, ex.getMessage(), "ERROR DE IMPRESION", JOptionPane.ERROR_MESSAGE);
            System.out.println(ex);
            Logger.getLogger(Imprimir.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return print;
    }

    public void vistaPreviaReporte(String tituloReporte, String nombreReporte, Map<String, Object> parametros) {
        JasperPrint print = prepararReporte(tituloReporte, nombreReporte, parametros);
        if (print != null && print.getPages().size() > 0) {
            JasperViewer jviewer = new JasperViewer(print,false);
            jviewer.setTitle(tituloReporte);
            jviewer.setVisible(true);
        } else {
            JOptionPane.showMessageDialog( null, "¡No existe páginas para imprimir!", "Adventencia", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void impresionDirecta(String tituloReporte, String nombreReporte, Map<String, Object> parametros, Boolean conConfirmacionDeImpresion) {
        try {
            JasperPrint print = prepararReporte(tituloReporte, nombreReporte, parametros);
            if (print != null && print.getPages().size() > 0) {
                JasperPrintManager.printReport(print, conConfirmacionDeImpresion);
            } else {
                JOptionPane.showMessageDialog( null, "¡No existe páginas para imprimir!", "Adventencia", JOptionPane.ERROR_MESSAGE);
            }
        } catch (JRException ex) {
            JOptionPane.showMessageDialog( null, ex.getMessage(), "ERROR DE IMPRESION", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(Imprimir.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void impresionEnPDF(String tituloReporte, String nombreReporte, Map<String, Object> parametros, String rutaNombreExportarPDF) {
        try {
            JasperPrint print = prepararReporte(tituloReporte, nombreReporte, parametros);
            if (print != null && print.getPages().size() > 0) {
                JasperExportManager.exportReportToPdfFile(print, rutaNombreExportarPDF);
            } else {
                JOptionPane.showMessageDialog( null, "¡No existe páginas para imprimir!", "Adventencia", JOptionPane.ERROR_MESSAGE);
            }
        } catch (JRException ex) {
            JOptionPane.showMessageDialog( null, ex.getMessage(), "ERROR DE IMPRESION", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(Imprimir.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
