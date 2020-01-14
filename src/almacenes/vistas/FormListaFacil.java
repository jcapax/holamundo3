/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package almacenes.vistas;

import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.FacturaFacil;
import dao.FacturaFacilDAO;
import dao.FacturaFacilDAOImpl;
import dao.FacturaVentaDAO;
import dao.FacturaVentaDAOImpl;
import dao.reportes.ReporteFacturacionDAOImpl;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author juanito
 */
public class FormListaFacil extends javax.swing.JFrame {

    private DatabaseUtils databaseUtils;
    private Connection connectionDB;
    DefaultTableModel dtm;
    FacturaFacilDAO ff;
    
    public FormListaFacil() {
        initComponents();
    }
    
    public FormListaFacil(Connection connectionDB) {
        initComponents();
        this.setLocationRelativeTo(null);

        this.databaseUtils = new DatabaseUtils();
        this.connectionDB = connectionDB;
        
        headerTabla();

        iniciarComponentes();
        llenarAnnos();
        llenarMeses();

    }
    
    public void iniciarComponentes(){
        ff = new FacturaFacilDAOImpl(connectionDB);
    }
    
    public void headerTabla(){
        Font f = new Font("Times New Roman", Font.BOLD, 13);
        
        jtListaFacturaFacil.getTableHeader().setFont(f);
        jtListaFacturaFacil.getTableHeader().setBackground(Color.orange);
    }
    
    private void llenarMeses() {
        jcMes.removeAllItems();

        ArrayList<String> lmeses = new ArrayList<String>();

        lmeses.add("ENERO");
        lmeses.add("FEBRERO");
        lmeses.add("MARZO");
        lmeses.add("ABRIL");
        lmeses.add("MAYO");
        lmeses.add("JUNIO");
        lmeses.add("JULIO");
        lmeses.add("AGOSTO");
        lmeses.add("SEPTIEMBRE");
        lmeses.add("OCTUBRE");
        lmeses.add("NOVIEMBRE");
        lmeses.add("DICIEMBRE");

        for (byte i = 0; i < lmeses.size(); i++) {
            jcMes.addItem(lmeses.get(i).toString());
        }
    }
    
    private void llenarAnnos() {
        jcAnno.removeAllItems();

        ArrayList<Integer> lanno = ff.getListaAnnosFacturaFacil();
        for (byte i = 0; i < lanno.size(); i++) {
            jcAnno.addItem(lanno.get(i).toString());
        }
    }
    
    public void anularFactura(){
        int filSel = jtListaFacturaFacil.getSelectedRow();
        int id = (int) jtListaFacturaFacil.getValueAt(filSel, 0);
        
        if(ff.isFacturaAbierta(id)){
            ff.anularFacturaFacil(id);
            ff.anularDetalleFacturaFacil(id);
            llenarListaFacturas();
            JOptionPane.showMessageDialog(null, "Anulacion Correcta", 
                "Mensaje",
                JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(null, "No es posible anular, la factura se encuentra en un periodo declarado", 
                "Mensaje",
                JOptionPane.ERROR_MESSAGE);
        }       
    }
    
    private void llenarListaFacturas(){
        double importeTotal = 0;

        DecimalFormat df = new DecimalFormat("###,##0.00");

        ArrayList<FacturaFacil> lista = new ArrayList<FacturaFacil>();

        byte mes = 0;
        int anno = 0;

        mes = (byte) (jcMes.getSelectedIndex() + 1);
        anno = Integer.parseInt(jcAnno.getSelectedItem().toString());

        lista = ff.getlistaFacturaFacil(anno, mes);

        dtm = (DefaultTableModel) this.jtListaFacturaFacil.getModel();
        dtm.setRowCount(0);

        jtListaFacturaFacil.setModel(dtm);

        Object[] fila = new Object[9];

        for (int i = 0; i < lista.size(); i++) {
            fila[0] = lista.get(i).getId();
            fila[1] = lista.get(i).getNombreSucursal();
            fila[2] = lista.get(i).getFechaFactura();
            fila[3] = lista.get(i).getNroFactura();
            fila[4] = lista.get(i).getNroAutorizacion();
            fila[5] = lista.get(i).getEstado();
            fila[6] = lista.get(i).getNit();
            fila[7] = lista.get(i).getRazonSocial();
            fila[8] = lista.get(i).getImporteTotal();
            
            dtm.addRow(fila);
        }
        

//        TableColumnModel columnModel = jtArqueos.getColumnModel();
        
//        TableColumn colImpApertura = columnModel.getColumn(3);
//        TableColumn colImpCierre = columnModel.getColumn(4);
//        TableColumn colImpTotal = columnModel.getColumn(5);
        
//        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
//        renderer.setHorizontalAlignment(jLabel1.RIGHT);
        
        
//        jtArqueos.setModel(dtm);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jcMes = new javax.swing.JComboBox<String>();
        jcAnno = new javax.swing.JComboBox<String>();
        jLabel2 = new javax.swing.JLabel();
        jlTituloFormulario = new javax.swing.JLabel();
        jbBuscar = new javax.swing.JButton();
        bImprimir = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtListaFacturaFacil = new javax.swing.JTable();
        btnAnularFacturaFacil = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setForeground(new java.awt.Color(153, 0, 51));
        jLabel1.setText("Mes");

        jcMes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jcAnno.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setForeground(new java.awt.Color(153, 0, 51));
        jLabel2.setText("Año");

        jlTituloFormulario.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        jlTituloFormulario.setForeground(new java.awt.Color(153, 0, 51));
        jlTituloFormulario.setText("Lista Facturas");

        jbBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Search-icon.png"))); // NOI18N
        jbBuscar.setText("Buscar");
        jbBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbBuscarActionPerformed(evt);
            }
        });

        bImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/print.png"))); // NOI18N
        bImprimir.setText("Imprimir");
        bImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bImprimirActionPerformed(evt);
            }
        });

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/close_window.png"))); // NOI18N
        btnSalir.setText("Cerrar");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        jtListaFacturaFacil.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Sucursal", "Fecha", "nroFactura", "nroAutorizacion", "Est.", "Nit", "Razon Social", "Importe Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtListaFacturaFacil.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtListaFacturaFacilMouseClicked(evt);
            }
        });
        jtListaFacturaFacil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtListaFacturaFacilKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jtListaFacturaFacil);
        if (jtListaFacturaFacil.getColumnModel().getColumnCount() > 0) {
            jtListaFacturaFacil.getColumnModel().getColumn(0).setMinWidth(0);
            jtListaFacturaFacil.getColumnModel().getColumn(0).setPreferredWidth(0);
            jtListaFacturaFacil.getColumnModel().getColumn(0).setMaxWidth(0);
            jtListaFacturaFacil.getColumnModel().getColumn(2).setMinWidth(65);
            jtListaFacturaFacil.getColumnModel().getColumn(2).setPreferredWidth(65);
            jtListaFacturaFacil.getColumnModel().getColumn(2).setMaxWidth(65);
            jtListaFacturaFacil.getColumnModel().getColumn(3).setMinWidth(100);
            jtListaFacturaFacil.getColumnModel().getColumn(3).setPreferredWidth(100);
            jtListaFacturaFacil.getColumnModel().getColumn(3).setMaxWidth(100);
            jtListaFacturaFacil.getColumnModel().getColumn(5).setMinWidth(45);
            jtListaFacturaFacil.getColumnModel().getColumn(5).setPreferredWidth(45);
            jtListaFacturaFacil.getColumnModel().getColumn(5).setMaxWidth(45);
            jtListaFacturaFacil.getColumnModel().getColumn(7).setMinWidth(311);
            jtListaFacturaFacil.getColumnModel().getColumn(7).setPreferredWidth(311);
            jtListaFacturaFacil.getColumnModel().getColumn(7).setMaxWidth(311);
        }

        btnAnularFacturaFacil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/exit.png"))); // NOI18N
        btnAnularFacturaFacil.setText("Anular");
        btnAnularFacturaFacil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnularFacturaFacilActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jlTituloFormulario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcMes, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcAnno, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jbBuscar)
                        .addGap(18, 18, 18)
                        .addComponent(bImprimir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 421, Short.MAX_VALUE)
                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAnularFacturaFacil)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jlTituloFormulario)
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jcAnno, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcMes, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbBuscar)
                    .addComponent(bImprimir)
                    .addComponent(btnSalir))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAnularFacturaFacil)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbBuscarActionPerformed
//        vaciarTotales();
        llenarListaFacturas();
    }//GEN-LAST:event_jbBuscarActionPerformed

    private void bImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bImprimirActionPerformed
        int filSel = jtListaFacturaFacil.getSelectedRow();
        int id = (int) jtListaFacturaFacil.getValueAt(filSel, 0);
        double importeTotal = (double) jtListaFacturaFacil.getValueAt(filSel, 8);
        
        FacturaVentaDAO fv = new FacturaVentaDAOImpl(connectionDB);
        
        ReporteFacturacionDAOImpl repFactura = new ReporteFacturacionDAOImpl(connectionDB, "XXX");

                //repFactura.VistaPreviaFacturaVenta(nroIdFactura, facDaoImpl.getCadenaCodigoQr(idTransaccion), fact.getImporteTotal());
                repFactura.VistaPreviaFacturaFacilCopia(id, 
                fv.getCadenaCodigoQrFacturaFacil(id), 
                importeTotal);
    }//GEN-LAST:event_bImprimirActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void jtListaFacturaFacilMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtListaFacturaFacilMouseClicked
//        seleccionarArqueo();
    }//GEN-LAST:event_jtListaFacturaFacilMouseClicked

    private void jtListaFacturaFacilKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtListaFacturaFacilKeyReleased
//        seleccionarArqueo();
    }//GEN-LAST:event_jtListaFacturaFacilKeyReleased

    private void btnAnularFacturaFacilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnularFacturaFacilActionPerformed
        int resp=JOptionPane.showConfirmDialog(null,"Desea anular la factura seleccionada?");
      
        if (JOptionPane.OK_OPTION == resp){
           anularFactura();           
        }
        else{            
        }
    }//GEN-LAST:event_btnAnularFacturaFacilActionPerformed

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
            java.util.logging.Logger.getLogger(FormListaFacil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormListaFacil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormListaFacil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormListaFacil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormListaFacil().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bImprimir;
    private javax.swing.JButton btnAnularFacturaFacil;
    private javax.swing.JButton btnEliminar1;
    private javax.swing.JButton btnEliminar2;
    private javax.swing.JButton btnEliminar3;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbBuscar;
    private javax.swing.JComboBox<String> jcAnno;
    private javax.swing.JComboBox<String> jcMes;
    private javax.swing.JLabel jlTituloFormulario;
    private javax.swing.JTable jtListaFacturaFacil;
    // End of variables declaration//GEN-END:variables
}
