/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package almacenes.vistas.configuracion;

import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.Dosificacion;
import dao.DosificacionDAOImpl;
import dao.SucursalDAOImpl;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jcapax
 */
public class FormDosificacion extends javax.swing.JFrame {

    private DatabaseUtils databaseUtils;
    private Connection connectionDB;
    DefaultTableModel dtm;
    
    /**
     * Creates new form FormDosificacion
     */
    public FormDosificacion() {
        initComponents();
    }
    
    public FormDosificacion(Connection connectionDB) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.connectionDB = connectionDB;
        
        headerTabla();
        llenarTablaDosificacion();
        llenarComboSucursal();
        deshabilitarComponentes();
        jlNroSucursal.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jlTituloFormulario = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jtxtLlaveDosificacion = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jtxtNroAutorizacion = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jtxtNroInicioFactura = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jdateFechaLimiteEmision = new com.toedter.calendar.JDateChooser();
        jdateFechaDosificacion = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        jtxtPieFactura = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jchEstado = new javax.swing.JCheckBox();
        jcSucursal = new javax.swing.JComboBox<String>();
        jlNroSucursal = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jbNuevo = new javax.swing.JButton();
        jbGuardar = new javax.swing.JButton();
        jbCancelar = new javax.swing.JButton();
        jbSalir = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtDosificacion = new javax.swing.JTable();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jlTituloFormulario.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        jlTituloFormulario.setForeground(new java.awt.Color(153, 0, 51));
        jlTituloFormulario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlTituloFormulario.setText("Dosificaciones");

        jLabel3.setForeground(new java.awt.Color(153, 0, 51));
        jLabel3.setText("Llave");

        jtxtLlaveDosificacion.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtxtLlaveDosificacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtLlaveDosificacionActionPerformed(evt);
            }
        });

        jLabel4.setForeground(new java.awt.Color(153, 0, 51));
        jLabel4.setText("Fecha Dos.");

        jLabel5.setForeground(new java.awt.Color(153, 0, 51));
        jLabel5.setText("Nro Sucursal");

        jLabel6.setForeground(new java.awt.Color(153, 0, 51));
        jLabel6.setText("Nro Autorizacion");

        jtxtNroAutorizacion.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel7.setForeground(new java.awt.Color(153, 0, 51));
        jLabel7.setText("Nro Inicio Factura");

        jtxtNroInicioFactura.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel8.setForeground(new java.awt.Color(153, 0, 51));
        jLabel8.setText("Fecha Limite Emision");

        jdateFechaLimiteEmision.setDateFormatString("dd/MM/yyyy");

        jdateFechaDosificacion.setDateFormatString("dd/MM/yyyy");

        jLabel9.setForeground(new java.awt.Color(153, 0, 51));
        jLabel9.setText("Pie Factura");

        jtxtPieFactura.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        jLabel10.setForeground(new java.awt.Color(153, 0, 51));
        jLabel10.setText("Estado");

        jcSucursal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jcSucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcSucursalActionPerformed(evt);
            }
        });

        jlNroSucursal.setText("0");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jtxtLlaveDosificacion, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jdateFechaDosificacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel4))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jcSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel5)
                                                .addGap(23, 23, 23)
                                                .addComponent(jlNroSucursal)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jtxtNroAutorizacion, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel6))
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(32, 32, 32)
                                                .addComponent(jtxtNroInicioFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)))
                                    .addComponent(jtxtPieFactura))
                                .addComponent(jLabel9)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jdateFechaLimiteEmision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel10)
                                .addComponent(jchEstado)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtxtLlaveDosificacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(jLabel5)
                        .addComponent(jlNroSucursal))
                    .addComponent(jLabel8)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(jLabel6)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jdateFechaLimiteEmision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtNroInicioFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtNroAutorizacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jdateFechaDosificacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtxtPieFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jchEstado))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jbNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/folder-add-icon.png"))); // NOI18N
        jbNuevo.setText("Nuevo");
        jbNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbNuevoActionPerformed(evt);
            }
        });

        jbGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Save-icon.png"))); // NOI18N
        jbGuardar.setText("Guardar");
        jbGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbGuardarActionPerformed(evt);
            }
        });

        jbCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/exit.png"))); // NOI18N
        jbCancelar.setText("Cancelar");
        jbCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCancelarActionPerformed(evt);
            }
        });

        jbSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/close_window.png"))); // NOI18N
        jbSalir.setText("Salir");
        jbSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jbNuevo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbGuardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbCancelar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbSalir)
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jbGuardar)
                        .addComponent(jbSalir)
                        .addComponent(jbCancelar))
                    .addComponent(jbNuevo))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jtDosificacion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "", "Llave", "Fecha Dos.", "Sucursal", "Nro Aut.", "Nro Inicio", "Fecha Limite Emi.", "Pie Factura", "Estado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jtDosificacion);
        if (jtDosificacion.getColumnModel().getColumnCount() > 0) {
            jtDosificacion.getColumnModel().getColumn(0).setMinWidth(0);
            jtDosificacion.getColumnModel().getColumn(0).setPreferredWidth(0);
            jtDosificacion.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlTituloFormulario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(154, 154, 154)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jlTituloFormulario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jtxtLlaveDosificacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtLlaveDosificacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtLlaveDosificacionActionPerformed

    private void jbSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSalirActionPerformed
        dispose();
    }//GEN-LAST:event_jbSalirActionPerformed

    private void jbCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCancelarActionPerformed
        byte x = 4;
        botones(x);
    }//GEN-LAST:event_jbCancelarActionPerformed

    private void jbGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbGuardarActionPerformed
        byte x = 4;
        botones(x);
        guardarDosificacion();
//        jlEdicion.setText("0");

    }//GEN-LAST:event_jbGuardarActionPerformed

    private void jbNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbNuevoActionPerformed
        byte x = 1;
        botones(x);
    }//GEN-LAST:event_jbNuevoActionPerformed

    private void jcSucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcSucursalActionPerformed
        seleccionarElementoSucursal();
    }//GEN-LAST:event_jcSucursalActionPerformed

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
            java.util.logging.Logger.getLogger(FormDosificacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormDosificacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormDosificacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormDosificacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormDosificacion().setVisible(true);
            }
        });
    }
    
    public void headerTabla(){
        Font f = new Font("Times New Roman", Font.BOLD, 13);
        
        jtDosificacion.getTableHeader().setFont(f);
        jtDosificacion.getTableHeader().setBackground(Color.orange);
    }
    
    public void llenarComboSucursal(){
        
        String sel = "Sel";
        
        jcSucursal.removeAllItems();
        jcSucursal.addItem(sel);
        
        SucursalDAOImpl sucursalDAOImpl = new SucursalDAOImpl(connectionDB);
        
            HashMap<String, Integer> map = sucursalDAOImpl.sucursalClaveValor();
            
            for (String s : map.keySet()) {
                jcSucursal.addItem(s.toString());
            }
    }
    
    public void llenarTablaDosificacion(){
        DosificacionDAOImpl dos = new DosificacionDAOImpl(connectionDB);
        
        ArrayList<Dosificacion> d = new ArrayList<Dosificacion>();
        
        d = dos.getListDosificacion();
        
        dtm = (DefaultTableModel) this.jtDosificacion.getModel();
        dtm.setRowCount(0);
        
        jtDosificacion.setModel(dtm);
        
        Object[] fila = new Object[9];
        
//        System.out.println("nro de registros en pila: " + r.size());
        
        for(int i=0; i< d.size(); i++){
            fila[0] = d.get(i).getId();
            fila[1] = d.get(i).getLlaveDosificacion();
            fila[2] = d.get(i).getFecha();
            fila[3] = d.get(i).getNombreSucursal();
            fila[4] = d.get(i).getNroAutorizacion();
            fila[5] = d.get(i).getNroInicioFactura();
            fila[6] = d.get(i).getFechaLimiteEmision();
            fila[7] = d.get(i).getPieFactura();
            boolean aux = (d.get(i).getEstado() == 1)?true:false;
            fila[8] = aux;
            
            dtm.addRow(fila);
        }
        
        jtDosificacion.setModel(dtm);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JButton jbGuardar;
    private javax.swing.JButton jbNuevo;
    private javax.swing.JButton jbSalir;
    private javax.swing.JComboBox<String> jcSucursal;
    private javax.swing.JCheckBox jchEstado;
    private com.toedter.calendar.JDateChooser jdateFechaDosificacion;
    private com.toedter.calendar.JDateChooser jdateFechaLimiteEmision;
    private javax.swing.JLabel jlNroSucursal;
    private javax.swing.JLabel jlTituloFormulario;
    private javax.swing.JTable jtDosificacion;
    private javax.swing.JTextField jtxtLlaveDosificacion;
    private javax.swing.JTextField jtxtNroAutorizacion;
    private javax.swing.JTextField jtxtNroInicioFactura;
    private javax.swing.JTextField jtxtPieFactura;
    // End of variables declaration//GEN-END:variables

    public void botones(byte x){
        switch(x){
            case 1: //nuevo
                jbNuevo.setEnabled(false);
                jbGuardar.setEnabled(true);
                jbCancelar.setEnabled(true);
                limpiar();
                habilitarComponentes();
                break;
            case 2: //guardar
                jbNuevo.setEnabled(true);
                jbGuardar.setEnabled(false);
                jbCancelar.setEnabled(false);
                deshabilitarComponentes();
                break;
            case 3: // editar
                jbNuevo.setEnabled(false);
                jbGuardar.setEnabled(true);
                jbCancelar.setEnabled(true);
                habilitarComponentes();
                break;
            case 4: // cancelar
                jbNuevo.setEnabled(true);
                jbGuardar.setEnabled(false);
                jbCancelar.setEnabled(false);
                deshabilitarComponentes();
                break;
        }
    }

    public void limpiar(){
        jtxtLlaveDosificacion.setText("");
//        jdateFechaDosificacion;
//        jdateFechaLimiteEmision;
        llenarComboSucursal();
        jtxtNroAutorizacion.setText("");
        jtxtNroInicioFactura.setText("");
        jchEstado.setSelected(false);
        jtxtPieFactura.setText("");
    }
    
    
    public void habilitarComponentes(){
        boolean aux = true;

        //jPanelComponentes.setEnabled(aux);
        jtxtLlaveDosificacion.setEnabled(aux);
        jdateFechaDosificacion.setEnabled(aux);
        jdateFechaLimiteEmision.setEnabled(aux);
        jtxtNroAutorizacion.setEnabled(aux);
        jtxtNroInicioFactura.setEnabled(aux);
        jcSucursal.setEnabled(aux);
        jchEstado.setEnabled(aux);
        jtxtPieFactura.setEnabled(aux);
    }

    public void deshabilitarComponentes(){
        boolean aux = false;

        //jPanelComponentes.setEnabled(aux);
        jtxtLlaveDosificacion.setEnabled(aux);
        jdateFechaDosificacion.setEnabled(aux);
        jdateFechaLimiteEmision.setEnabled(aux);
        jtxtNroAutorizacion.setEnabled(aux);
        jtxtNroInicioFactura.setEnabled(aux);
        jcSucursal.setEnabled(aux);
        jchEstado.setEnabled(aux);
        jtxtPieFactura.setEnabled(aux);
    }

    private void guardarDosificacion() {
        
        Date fechaDosificacion = new Date(jdateFechaDosificacion.getDate().getTime());
        Date fechaLimiteEmision = new Date(jdateFechaLimiteEmision.getDate().getTime());
        
        String llavedosificacion = jtxtLlaveDosificacion.getText();
        int idSucursal = Integer.valueOf(jlNroSucursal.getText());
        String nroAutorizacion = jtxtNroAutorizacion.getText();
        int nroFactura = Integer.valueOf(jtxtNroInicioFactura.getText());
        String pieFactura = jtxtPieFactura.getText();
        int estado = 0;
        if(jchEstado.isSelected()){
            estado = 1;
        }
        Dosificacion d = new Dosificacion();
        d.setEstado(estado);
        d.setFecha(fechaDosificacion);
        d.setFechaLimiteEmision(fechaLimiteEmision);
        d.setIdSucursal(idSucursal);
        d.setLlaveDosificacion(llavedosificacion);
        d.setNroAutorizacion(nroAutorizacion);
        d.setNroInicioFactura(nroFactura);
        d.setPieFactura(pieFactura);
        
        DosificacionDAOImpl dosificacionDAO = new DosificacionDAOImpl(connectionDB);
        dosificacionDAO.bajaDosificacionSucursal(idSucursal);
        dosificacionDAO.insertarDosificacion(d);
        
        llenarTablaDosificacion();
        
    }
    
    private void seleccionarElementoSucursal() {
        String sel = null;
        
        String comp = "Sel";
        
        SucursalDAOImpl sucursalDAOImpl = new SucursalDAOImpl(connectionDB);
        
        HashMap<String, Integer> map = sucursalDAOImpl.sucursalClaveValor();
            
        try {
            sel = jcSucursal.getSelectedItem().toString();

//            System.out.println("elemento seleccionado "+ sel);

            if(sel.equals(comp)){
                jlNroSucursal.setText("0");
            }
            else{
                jlNroSucursal.setText(map.get(sel).toString());
            }
        } catch (Exception e) {
        }
        
    }
}