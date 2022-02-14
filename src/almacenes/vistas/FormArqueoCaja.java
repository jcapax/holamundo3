/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package almacenes.vistas;

import almacenes.conectorDB.DatabaseUtils;
import dao.ArqueoDAO;
import dao.ArqueoDAOImpl;
import dao.reportes.ReporteVentasDAO;
import dao.reportes.ReporteVentasDAOImpl;
import java.sql.Connection;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author jcapax
 */
public class FormArqueoCaja extends javax.swing.JFrame {
    
    private DatabaseUtils databaseUtils;
    private Connection connectionDB;
    
    private byte idLugar;
    private byte idTerminal;
    private String usuario;
    private ArqueoDAO arqueoDAO;

    
    public FormArqueoCaja(Connection connectionDB, String usuario, byte idLugar, byte idTerminal) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.databaseUtils = new DatabaseUtils();
        this.connectionDB = connectionDB;
        this.usuario = usuario;
        this.idLugar = idLugar;
        this.idTerminal = idTerminal;
        arqueoDAO = new ArqueoDAOImpl(connectionDB);
        
        cajaInicial();
        importeArqueo();
    }

    /**
     * Creates new form FormArqueoCaja
     */
    public FormArqueoCaja() {
        initComponents();
    }
    
    public void cajaInicial(){
        int idArqueo = arqueoDAO.getIdArqueo(idLugar, idTerminal, usuario);
        
        double cajaIni = arqueoDAO.getCajaInicial(idArqueo);
        double impXuser = 0;
        
        double xArqueo = 0;
        
        jtxtCajaInicial.setText(String.valueOf(cajaIni));
        jtxtCajaInicial.setEnabled(false);
        
        if(cajaIni == 0){
            btnGuardar.setEnabled(false);
            jtxtImporteCierre.setEnabled(false);
        }
        
        ArrayList<Integer> lTrans = new ArrayList<>(); 
        
        lTrans = arqueoDAO.getListaTransaccionArqueoPorUsuarioMaquina(idLugar, idTerminal, usuario);
        
//        impXuser = arq.getImportePorArqueoUsuarioMaquina(lTrans);
        impXuser = arqueoDAO.getImportePorArqueoUsuarioMaquina(idLugar, idTerminal, usuario);
        
        xArqueo = impXuser + cajaIni;
        
        jtxtImporteCierre.setText(String.valueOf(xArqueo));
        jtxtImporteCierre.setEnabled(false);
        
    }
    
    public void importeArqueo(){
        double importeA = 0;
                
        importeA = arqueoDAO.getImportePorArqueoUsuarioMaquina(idLugar, idTerminal, usuario);
        jlImporteArque.setText(String.valueOf(importeA));
        
        jlImporteArque.setVisible(false);
        
     
    }
    
    public void cerrarCaja(){
        int idArqueo = arqueoDAO.getIdArqueo(idLugar, idTerminal, usuario);
        
        ArrayList<Integer> lTrans = new ArrayList<>(); 
        lTrans = arqueoDAO.getListaTransaccionArqueoPorUsuarioMaquina(idLugar, idTerminal, usuario);
        
        //*********************************
        //CERRAMOS CAJA
        //*********************************
        arqueoDAO.cerrarCaja(lTrans, idArqueo);
        
        //*********************************
        //CERRAMOS TRANSACCIONES
        //*********************************
        arqueoDAO.cerrarTransacciones(lTrans);
        
        //*********************************
        //CERRAMOS CREDITO + ARQUEO
        //*********************************
        arqueoDAO.cerrarCreditoCaja(idLugar, idTerminal, usuario, idArqueo);
        
        double importeCierre = 0.0;
        importeCierre = Double.valueOf(jtxtImporteCierre.getText());
        
        arqueoDAO.cerrarArqueo(importeCierre, idArqueo);
                
        ReporteVentasDAOImpl reparq = new ReporteVentasDAOImpl(connectionDB, usuario);
        reparq.arqueo(idArqueo);       
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jtxtCajaInicial = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jtxtImporteCierre = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jlImporteArque = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jtxtCajaInicial.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(153, 0, 51));
        jLabel1.setText("Caja Inicial");

        btnGuardar.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Save-icon.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnSalir.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/close_window.png"))); // NOI18N
        btnSalir.setText("Cerrar");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        jtxtImporteCierre.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 0, 51));
        jLabel2.setText("Importe Cierre");

        jlImporteArque.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jlImporteArque.setText("importe arqueo");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(45, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jlImporteArque)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(9, 9, 9)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jtxtCajaInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel1)))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtxtImporteCierre, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtxtCajaInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtxtImporteCierre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalir)
                    .addComponent(btnGuardar))
                .addGap(52, 52, 52)
                .addComponent(jlImporteArque)
                .addContainerGap(52, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        btnGuardar.setEnabled(false);
        cerrarCaja();
        JOptionPane.showMessageDialog(this, "Caja Finalizada con exito!!!");
        dispose();
        btnGuardar.setEnabled(true);
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

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
            java.util.logging.Logger.getLogger(FormArqueoCaja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormArqueoCaja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormArqueoCaja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormArqueoCaja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormArqueoCaja().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jlImporteArque;
    private javax.swing.JTextField jtxtCajaInicial;
    private javax.swing.JTextField jtxtImporteCierre;
    // End of variables declaration//GEN-END:variables
}
