/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package almacenes.vistas;

import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.AnularTransaccion;
import almacenes.model.DetalleTransaccion;
import dao.AnularTransaccionDAO;
import dao.AnularTransaccionDAOImpl;
import dao.DetalleTransaccionDAOImpl;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author jcapax
 */
public class FormAnularTransaccion extends javax.swing.JFrame {

    private DatabaseUtils databaseUtils;
    private Connection connectionDB;
    
    DefaultTableModel dtm;
    String usuario;
    
    public FormAnularTransaccion(Connection connectionDB, String usuario) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.databaseUtils = new DatabaseUtils();
        this.connectionDB = connectionDB;
        this.usuario = usuario;
                
        headerTabla();
        llenarTablaAnularTransaccion();
    }

    public FormAnularTransaccion() {
        initComponents();
    }
    
    public void headerTabla(){
        Font f = new Font("Times New Roman", Font.BOLD, 13);
        
        jtAnularComprobante.getTableHeader().setFont(f);
        jtAnularComprobante.getTableHeader().setBackground(Color.orange);
        
    }

    public void anularTransaccion() {
        AnularTransaccionDAOImpl anuTrans = new AnularTransaccionDAOImpl(connectionDB);

        int idTransaccion = 0;
        int idEntregaTransaccion = 0;

        int filSel = jtAnularComprobante.getSelectedRow();

        if (filSel != -1) {

            idEntregaTransaccion = (int) jtAnularComprobante.getValueAt(filSel, 0);
            idTransaccion = (int) jtAnularComprobante.getValueAt(filSel, 1);

            anuTrans.anularCaja(idTransaccion);
            anuTrans.anularFactura(idTransaccion);
            anuTrans.anularTrans(idTransaccion, idEntregaTransaccion);
            
            JOptionPane.showMessageDialog(this, "Transaccion Anulada");
            
            llenarTablaAnularTransaccion();
        }

    }

    public void llenarTablaAnularTransaccion() {
        AnularTransaccionDAO rub = new AnularTransaccionDAOImpl(connectionDB);

        ArrayList<AnularTransaccion> r = new ArrayList<AnularTransaccion>();

        byte idTipoTransaccion = 2;
        r = rub.getListaTransaccionesAnular(idTipoTransaccion, usuario);

        dtm = (DefaultTableModel) this.jtAnularComprobante.getModel();
        dtm.setRowCount(0);

        jtAnularComprobante.setModel(dtm);

        Object[] fila = new Object[11];

        for (int i = 0; i < r.size(); i++) {
            fila[0] = r.get(i).getIdEntregaTransaccion();
            fila[1] = r.get(i).getIdTransaccion();
            fila[2] = r.get(i).getNombreTransaccion();
            fila[3] = r.get(i).getNombreLugar();
            fila[4] = r.get(i).getNombreTerminal();
            fila[5] = r.get(i).getUsuario();
            fila[6] = r.get(i).getFecha();
            fila[7] = r.get(i).getValorTotal();
            fila[8] = r.get(i).getNroFactura();
            fila[9] = r.get(i).getNit();
            fila[10] = r.get(i).getRazonSocial();
           
            dtm.addRow(fila);
        }

        jtAnularComprobante.setModel(dtm);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnEliminar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtAnularComprobante = new javax.swing.JTable();
        btnEliminar1 = new javax.swing.JButton();
        jlTituloFormulario = new javax.swing.JLabel();
        jbSalir = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtDetalleTransaccion = new javax.swing.JTable();
        jlTituloFormulario1 = new javax.swing.JLabel();

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/trash_icon.png"))); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Anular Transacciones");
        setLocation(new java.awt.Point(100, 100));
        setResizable(false);

        jtAnularComprobante.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "idTransaccionEntrega", "idEntrega", "Tipo", "Lugar", "Terminal", "Usuario", "Fecha", "ValorTotal", "nroFactura", "nit", "razon social"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, java.lang.Integer.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, true, true, true, true, true, true, true, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtAnularComprobante.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtAnularComprobanteMouseClicked(evt);
            }
        });
        jtAnularComprobante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtAnularComprobanteKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jtAnularComprobante);
        if (jtAnularComprobante.getColumnModel().getColumnCount() > 0) {
            jtAnularComprobante.getColumnModel().getColumn(0).setMinWidth(0);
            jtAnularComprobante.getColumnModel().getColumn(0).setPreferredWidth(0);
            jtAnularComprobante.getColumnModel().getColumn(0).setMaxWidth(0);
            jtAnularComprobante.getColumnModel().getColumn(1).setMinWidth(0);
            jtAnularComprobante.getColumnModel().getColumn(1).setPreferredWidth(0);
            jtAnularComprobante.getColumnModel().getColumn(1).setMaxWidth(0);
            jtAnularComprobante.getColumnModel().getColumn(6).setMinWidth(100);
            jtAnularComprobante.getColumnModel().getColumn(6).setPreferredWidth(100);
            jtAnularComprobante.getColumnModel().getColumn(6).setMaxWidth(100);
            jtAnularComprobante.getColumnModel().getColumn(7).setMinWidth(100);
            jtAnularComprobante.getColumnModel().getColumn(7).setPreferredWidth(100);
            jtAnularComprobante.getColumnModel().getColumn(7).setMaxWidth(100);
            jtAnularComprobante.getColumnModel().getColumn(8).setMinWidth(100);
            jtAnularComprobante.getColumnModel().getColumn(8).setPreferredWidth(100);
            jtAnularComprobante.getColumnModel().getColumn(8).setMaxWidth(100);
            jtAnularComprobante.getColumnModel().getColumn(9).setMinWidth(100);
            jtAnularComprobante.getColumnModel().getColumn(9).setPreferredWidth(100);
            jtAnularComprobante.getColumnModel().getColumn(9).setMaxWidth(100);
            jtAnularComprobante.getColumnModel().getColumn(10).setMinWidth(250);
            jtAnularComprobante.getColumnModel().getColumn(10).setPreferredWidth(250);
            jtAnularComprobante.getColumnModel().getColumn(10).setMaxWidth(250);
        }

        btnEliminar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/exit.png"))); // NOI18N
        btnEliminar1.setText("Anular");
        btnEliminar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminar1ActionPerformed(evt);
            }
        });

        jlTituloFormulario.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        jlTituloFormulario.setForeground(new java.awt.Color(153, 0, 51));
        jlTituloFormulario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlTituloFormulario.setText("Detalle");

        jbSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/close_window.png"))); // NOI18N
        jbSalir.setText("Salir");
        jbSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSalirActionPerformed(evt);
            }
        });

        jtDetalleTransaccion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Producto", "Simbolo", "Cantidad", "Importe Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jtDetalleTransaccion);

        jlTituloFormulario1.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        jlTituloFormulario1.setForeground(new java.awt.Color(153, 0, 51));
        jlTituloFormulario1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlTituloFormulario1.setText("Anular Transacciones");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnEliminar1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 966, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jlTituloFormulario)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jlTituloFormulario1, javax.swing.GroupLayout.DEFAULT_SIZE, 966, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jlTituloFormulario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnEliminar1)
                        .addComponent(jbSalir))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(26, 26, 26)
                    .addComponent(jlTituloFormulario1)
                    .addContainerGap(453, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed

    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnEliminar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminar1ActionPerformed
        anularTransaccion();
    }//GEN-LAST:event_btnEliminar1ActionPerformed

    private void jbSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSalirActionPerformed
        dispose();
    }//GEN-LAST:event_jbSalirActionPerformed

    private void jtAnularComprobanteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtAnularComprobanteKeyReleased
        seleccionarDetalletransaccion();
    }//GEN-LAST:event_jtAnularComprobanteKeyReleased

    private void jtAnularComprobanteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtAnularComprobanteMouseClicked
        seleccionarDetalletransaccion();
    }//GEN-LAST:event_jtAnularComprobanteMouseClicked

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
            java.util.logging.Logger.getLogger(FormAnularTransaccion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormAnularTransaccion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormAnularTransaccion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormAnularTransaccion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormAnularTransaccion().setVisible(true);
            }
        });
    }
    
    public void seleccionarDetalletransaccion(){
        int fila = jtAnularComprobante.getSelectedRow();
        int idtransaccion = (int) jtAnularComprobante.getValueAt(fila, 0);
        
        llenarDetalleTransaccion(idtransaccion);
    }
    
    public void llenarDetalleTransaccion(int idTransaccion){
        double importeTotal = 0;

        DetalleTransaccionDAOImpl rub = new DetalleTransaccionDAOImpl(connectionDB);
        DecimalFormat df = new DecimalFormat("###,##0.00");

        ArrayList<DetalleTransaccion> r = new ArrayList<DetalleTransaccion>();

        r = rub.getDetalleTransaccion(idTransaccion);

        dtm = (DefaultTableModel) this.jtDetalleTransaccion.getModel();
        dtm.setRowCount(0);

        jtDetalleTransaccion.setModel(dtm);

        Object[] fila = new Object[4];

        for (int i = 0; i < r.size(); i++) {
            fila[0] = r.get(i).getNombreProducto();
            fila[1] = r.get(i).getSimbolo();
            fila[2] = r.get(i).getCantidad();
            fila[3] = df.format(r.get(i).getValorTotal());
            importeTotal = importeTotal + r.get(i).getValorTotal();
            
            dtm.addRow(fila);
        }
        
        TableColumnModel columnModel = jtDetalleTransaccion.getColumnModel();
        
        TableColumn colImpTotal = columnModel.getColumn(3);
        
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        
        colImpTotal.setCellRenderer(renderer);
        
        jtDetalleTransaccion.setModel(dtm);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnEliminar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton jbSalir;
    private javax.swing.JLabel jlTituloFormulario;
    private javax.swing.JLabel jlTituloFormulario1;
    private javax.swing.JTable jtAnularComprobante;
    private javax.swing.JTable jtDetalleTransaccion;
    // End of variables declaration//GEN-END:variables
}
