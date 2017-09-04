/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package almacenes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author georgeguitar
 */
public class Utiles {
    public String normalizePathForGNU(String path) {
       return path.replaceAll("\\", "/");
    }
    
    public String normalizePathForWindows(String path) {
       return path.replaceAll("/", "\\");
    }
    
    public Boolean fechaEsFinDeSemana(Date fecha) {
        Boolean respuesta = false;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
          respuesta = true;
        }
        if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
          respuesta = true;
        }
        return respuesta;
    }
    
    public void ejecutarPrograma(String nombrePrograma) {
        try
        {            
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(nombrePrograma);
//            int exitVal = proc.exitValue();
            proc.waitFor();
//            System.out.println("Process exitValue: " + exitVal);
        } catch (Throwable t)
          {
            t.printStackTrace();
          }
    }
    

 /**
  * Ejecuta mysqldump
  *  
  * @param dbHost 
  * @param dbPort 
  * @param dbName 
  * @param dbUser 
  * @param dbPassword 
  * @param dumpFile 
  */ 
 public String execMariaDump(String rutaMariaDUMP, String dbHost, String dbPort, String dbName, String dbUser, String dbPassword, String dumpFile) throws InterruptedException { 
    //mysqldump --host=localhost --port=3306 --user=root --password=root --result-file=archivoBK.backup pacheco_sales_2
    String mensajes = ""; 
     ProcessBuilder parametrosPg_Dump = new ProcessBuilder(rutaMariaDUMP + "/mysqldump", 
       "--host=" + dbHost, // database host 
       "--port=" + dbPort, // database port 
       "--user=" + dbUser, // database user 
       "--password=" + dbPassword,
       "--result-file=" + dumpFile, // export file 
       dbName // database name (must be last parameter) 
     ); 
    mensajes = executarProgramaConsola(parametrosPg_Dump, null);
    
    return mensajes;
 } 
 
 /**
  * pg_restore
  * @param rutaOrigen
  * @param dbHost 
  * @param dbPort 
  * @param dbName 
  * @param dbUser 
  * @param dbPassword 
  * @param dumpFile 
  */ 
 public String execPgRestore(String rutaOrigen, String dbHost, String dbPort, String dbName, String dbUser, String dbPassword, String dumpFile) throws InterruptedException {
    String mensajes = "";

    ProcessBuilder parametrosDropDB = new ProcessBuilder(rutaOrigen + "/dropdb",
       "-h", dbHost, // database host 
       "-p", dbPort, // database port 
       "-w", // do not prompt for password        
       "-U", dbUser, // database user 
        dbName
    );
    mensajes = executarProgramaConsola(parametrosDropDB, dbPassword);

    if (mensajes.isEmpty()) {
        ProcessBuilder parametrosCreateDB = new ProcessBuilder(rutaOrigen + "/createdb",
           "-h", dbHost, // database host 
           "-p", dbPort, // database port 
           "-w", // do not prompt for password 
           "-U", dbUser, // database user 
           "-E", "UTF8", // database user 
            dbName
        );
        mensajes = mensajes + executarProgramaConsola(parametrosCreateDB, dbPassword);
    
        if (mensajes.isEmpty()) {
            ProcessBuilder parametrosPgRestore = new ProcessBuilder(rutaOrigen + "/pg_restore",
               "-h", dbHost, // database host 
               "-p", dbPort, // database port 
               "-w", // do not prompt for password 
               "-U", dbUser, // database user 
               "-d", dbName, // database name
                dumpFile
            ); 
            mensajes = mensajes + executarProgramaConsola(parametrosPgRestore, dbPassword);
        }
    }
    
    return mensajes;
 }  
 
 public String executarProgramaConsola(ProcessBuilder parametros, String dbPassword) {
    String command = "";
    String mensajesRespuesta = ""; 
    try { 
        for (String p : parametros.command()) 
           command = command + p + " ";

        parametros.redirectErrorStream(true); 
        if (dbPassword != null) {
           // only for postgres pg_dump
           parametros.environment().put("PGPASSWORD", dbPassword);          
        }
        Process p = parametros.start(); 
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream())); 
        ArrayList<String> consoleMessages = new ArrayList<String>(); 
        String consoleMessage = null; 
        while ((consoleMessage = stdInput.readLine()) != null) { 
            consoleMessages.add(consoleMessage);
        }
        int exitCode = p.waitFor(); 
        if (exitCode != 0) {
         for (int i = 0; i < consoleMessages.size(); i++) 
          mensajesRespuesta += consoleMessages.get(i) + "\n"; 
          System.out.println("Comando dump falló: " + command + " ; exit code = " + exitCode + "\n" + mensajesRespuesta);
        } 
    } catch (Exception e) {
        System.out.println(e);
        mensajesRespuesta = e.toString();
    }
    
    return mensajesRespuesta;
 }  
    
    /**
     * Función que elimina acentos y caracteres especiales de
     * una cadena de texto. Nombre original: remove1
     * http://www.v3rgu1.com/blog/231/2010/programacion/eliminar-acentos-y-caracteres-especiales-en-java/
     * @param input
     * @return cadena de texto limpia de acentos y caracteres especiales.
     */
    public static String removeCaracteresEspeciales(String input) {
        // Cadena de caracteres original a sustituir.
        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
        // Cadena de caracteres ASCII que reemplazarán los originales.
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
        String output = input;
        for (int i=0; i<original.length(); i++) {
            // Reemplazamos los caracteres especiales.
            output = output.replace(original.charAt(i), ascii.charAt(i));
        }//for i
        return output;
    }
    
    public int convertirMesesEnDias(Date fecha, Short numCoutas) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTimeInMillis(fecha.getTime());
        calendar.add(Calendar.MONTH, numCoutas);
        long fechaFutura = calendar.getTimeInMillis();

        int numberOfDays =(int)( (fechaFutura - fecha.getTime()) / (1000* 3600 *24));
        
        return numberOfDays;
    }
    
    //Funciona pasando el objeto tabla ya que en Java si son objetos se pasa la dirección de
    //memoria, por lo que cualquier cambio hecho en este procedimiento, afectará el objeto
    //original. No es una forma bonita de programar pero por falta de tiempo se hará de esta forma.
    public void eliminarFilaDeTabla(JTable tabla, int columnaEliminar) {
        if ((Boolean)tabla.getValueAt(tabla.getSelectedRow(), columnaEliminar)) {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog (null, "¿Confirma que desea eliminar esta fila?","¡Atención!",dialogButton);
            if(dialogResult == JOptionPane.YES_OPTION) {
                DefaultTableModel model = (DefaultTableModel) tabla.getModel();
                int[] rows = tabla.getSelectedRows();
                for(int i = 0; i < rows.length; i++) {
                        model.removeRow(rows[i]-i);
                }
            }
        } 
    }
}
