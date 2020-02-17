/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package almacenes.vistas;

import dao.LugarDAO;
import dao.LugarDAOImpl;
import dao.reportes.ReporteVentasDAO;
import dao.reportes.ReporteVentasDAOImpl;
import java.sql.Connection;
import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;
import javax.swing.JOptionPane;

/**
 *
 * @author georgeguitar
 */
public class ElegirFechasImprimirReportes extends javax.swing.JDialog {

    private static Connection connectionDB;
    private static boolean modal;
    private static String idUsuario;
    private static int modoOperacion;
    private static java.awt.Frame parent;

    private static final int REPORTE_UNO = 1;
    private static final int REPORTE_DOS = 2;
    private static final int REPORTE_TRES = 3;
    private static final int REPORTE_CUATRO = 4;

    /**
     *
     * Creates new form ImprimirContrato
     */
    public ElegirFechasImprimirReportes(java.awt.Frame _parent, boolean _modal, Connection _connectionDB, String _idUsuario, int _modoOperacion) {
        super(_parent, _modal);
        this.parent = _parent;
        initComponents();
        llenarComboLugar();
        this.connectionDB = _connectionDB;
        this.idUsuario = _idUsuario;
        this.modal = _modal;
        this.modoOperacion = _modoOperacion;
        switch (this.modoOperacion) {
            case REPORTE_UNO:
                this.setTitle("Reporte Uno");
                break;
            case REPORTE_DOS:
                this.setTitle("Reporte Dos");
                break;
            case REPORTE_TRES:
                this.setTitle("Reporte Tres");
                break;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        fechaInicio = new com.toedter.calendar.JDateChooser();
        fechaFinal = new com.toedter.calendar.JDateChooser();
        jlIdLugar = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jcLugar = new javax.swing.JComboBox<String>();
        jPanel2 = new javax.swing.JPanel();
        cerrarBT = new javax.swing.JButton();
        imprimirReporteBT = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Fechas");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Fechas de Creación del Credito para el Reporte"));

        jLabel1.setForeground(new java.awt.Color(153, 0, 51));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Fecha de Inicio:");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabel2.setForeground(new java.awt.Color(153, 0, 51));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Fecha Final:");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        fechaInicio.setDateFormatString("dd/MM/yyyy");

        fechaFinal.setDateFormatString("dd/MM/yyyy");

        jlIdLugar.setText("idLugar");

        jLabel3.setForeground(new java.awt.Color(153, 0, 51));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Lugar:");

        jcLugar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jcLugar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcLugarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jlIdLugar)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jcLugar, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fechaInicio, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                    .addComponent(fechaFinal, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlIdLugar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jcLugar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(fechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(fechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cerrarBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/close_window.png"))); // NOI18N
        cerrarBT.setText("Cerrar");
        cerrarBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cerrarBTActionPerformed(evt);
            }
        });

        imprimirReporteBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/print.png"))); // NOI18N
        imprimirReporteBT.setText("Reporte");
        imprimirReporteBT.setPreferredSize(new java.awt.Dimension(189, 40));
        imprimirReporteBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imprimirReporteBTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(imprimirReporteBT, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addComponent(cerrarBT, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(imprimirReporteBT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(cerrarBT, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cerrarBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cerrarBTActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_cerrarBTActionPerformed

    private void imprimirReporteBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imprimirReporteBTActionPerformed
        String errores = validarCampos();
        if (errores.isEmpty()) {
            ReporteVentasDAO reporteVentas = new ReporteVentasDAOImpl(this.connectionDB, this.idUsuario);
            byte idLugar = Byte.valueOf(jlIdLugar.getText());
            switch (this.modoOperacion) {
                case REPORTE_UNO:
                    reporteVentas.vistaPreviaReporte(idLugar, new Date(fechaInicio.getDate().getTime()), new Date(fechaFinal.getDate().getTime()));
                    break;
                case REPORTE_DOS:
//                    vistaPreviaEntregas
                    reporteVentas.vistaPreviaEntregas(idLugar, new Date(fechaInicio.getDate().getTime()), new Date(fechaFinal.getDate().getTime()));

                    break;
                case REPORTE_TRES:

                    break;
                case REPORTE_CUATRO:
                    reporteVentas.vistaPreviaMovimientoCaja(idLugar, new Date(fechaInicio.getDate().getTime()), new Date(fechaFinal.getDate().getTime()));
                    break;
            }
        } else {
            JOptionPane.showMessageDialog(null, errores, "¡Atención!",
                    JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_imprimirReporteBTActionPerformed

    private void jcLugarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcLugarActionPerformed
        seleccionarElementoLugar();
    }//GEN-LAST:event_jcLugarActionPerformed

    private void seleccionarElementoLugar() {
        jlIdLugar.setVisible(false);
        String sel = null;
        
        String comp = "Sel";
        
        LugarDAO lugarDAOImpl = new LugarDAOImpl(connectionDB);
        
        HashMap<String, Integer> map = lugarDAOImpl.lugarClaveValor();
            
        try {
            sel = jcLugar.getSelectedItem().toString();

//            System.out.println("elemento seleccionado "+ sel);

            if(sel.equals(comp)){
                jlIdLugar.setText("0");
            }
            else{
                jlIdLugar.setText(map.get(sel).toString());
            }
        } catch (Exception e) {
        }
        
    }
    
    public void llenarComboLugar(){
        
        String sel = "Sel";
        
        jcLugar.removeAllItems();
        jcLugar.addItem(sel);
        
        LugarDAO lugarDAO = new LugarDAOImpl(connectionDB);
        
        HashMap<String, Integer> map = lugarDAO.lugarClaveValor();

        for (String s : map.keySet()) {
            jcLugar.addItem(s.toString());
        }
        jlIdLugar.setVisible(false);
    }
    
    private String validarCampos() {
        String errores = "";

        Calendar calendar = Calendar.getInstance();
        calendar = fechaInicio.getCalendar();
        try {
            new Date(calendar.getTime().getTime());
        } catch (Exception ex) {
            errores = errores + "- ¡Problemas con La Fecha de Inicio, puede ser que esté vacio o no sea una fecha valida!."
                    + "\n\n";
        }

        calendar = fechaFinal.getCalendar();
        try {
            new Date(calendar.getTime().getTime());
        } catch (Exception ex) {
            errores = errores + "- ¡Problemas con La Fecha Final, puede ser que esté vacio o no sea una fecha valida!."
                    + "\n\n";
        }

        return errores;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ElegirFechasImprimirReportes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ElegirFechasImprimirReportes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ElegirFechasImprimirReportes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ElegirFechasImprimirReportes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ElegirFechasImprimirReportes dialog = new ElegirFechasImprimirReportes(parent, modal, connectionDB, idUsuario, modoOperacion);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cerrarBT;
    private com.toedter.calendar.JDateChooser fechaFinal;
    private com.toedter.calendar.JDateChooser fechaInicio;
    private javax.swing.JButton imprimirReporteBT;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JComboBox<String> jcLugar;
    private javax.swing.JLabel jlIdLugar;
    // End of variables declaration//GEN-END:variables
}
