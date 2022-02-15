/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package almacenes.vistas;

import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.Producto;
import dao.LugarDAO;
import dao.LugarDAOImpl;
import dao.ProductoDAO;
import dao.ProductoDAOImpl;
import dao.reportes.ReporteVentasDAO;
import dao.reportes.ReporteVentasDAOImpl;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jcapax
 */
public class FormStockProducto extends javax.swing.JFrame {

    private DatabaseUtils databaseUtils;
    private Connection connectionDB;
    DefaultTableModel dtm;
    byte idLugar;
    private ProductoDAO productoDAO;
    private LugarDAO lugarDAO;
    
    public FormStockProducto() {
        initComponents();
    }
        
    public FormStockProducto(Connection connectionDB) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.databaseUtils = new DatabaseUtils();
        this.connectionDB = connectionDB;        
        productoDAO = new ProductoDAOImpl(connectionDB);
        lugarDAO = new LugarDAOImpl(connectionDB);
        this.idLugar = 0;
        headerTabla();
        llenarComboLugar();
        llenarTablaStockProducto("");
        
        jlLugar.setVisible(false);
        jcLugar.setVisible(false);
        jtStockVencimiento.setVisible(false);
    }
    
    public void llenarComboLugar(){
        
        String sel = "Sel";
        jlIdLugar.setText("0");
        jcLugar.removeAllItems();
        jcLugar.addItem(sel);
        
        HashMap<String, Integer> map = lugarDAO.lugarClaveValor();

        for (String s : map.keySet()) {
            jcLugar.addItem(s.toString());
        }
        jlIdLugar.setVisible(false);
        llenarTablaStockProducto("");
    }
    
    private void seleccionarElementoLugar() {
        String sel = null;
        
        String comp = "Sel";
        
        HashMap<String, Integer> map = lugarDAO.lugarClaveValor();
            
        try {
            sel = jcLugar.getSelectedItem().toString();
            if(sel.equals(comp)){                
                idLugar = 0;
            }
            else{
                idLugar = Byte.valueOf(map.get(sel).toString());
                
            }
        } catch (Exception e) {
        }        
        llenarTablaStockProducto("");
        
    }
    
    public void headerTabla(){
        Font f = new Font("Times New Roman", Font.BOLD, 16);
        
        jtStockProducto.getTableHeader().setFont(f);
        jtStockProducto.getTableHeader().setBackground(Color.orange);
        
        jtStockVencimiento.getTableHeader().setFont(f);
        jtStockVencimiento.getTableHeader().setBackground(Color.orange);
    }

    public void llenarTablaStockProducto(String criterio){
        List<Producto> lProd = new ArrayList<>();

        lProd = productoDAO.getListaProductosVenta(criterio, idLugar);

        dtm = (DefaultTableModel) this.jtStockProducto.getModel();
        dtm.setRowCount(0);

        jtStockProducto.setModel(dtm);

        Object[] fila = new Object[19];

        lProd.forEach((producto)->{
            if(producto.getStock()!=0){
                fila[0] = producto.getDescripcion();
                fila[1] = producto.getNombreFamilia();
                fila[2] = producto.getNombreLaboratorio();
                fila[3] = producto.getNombreUnidadMedida();
                fila[4] = producto.getStock();
                dtm.addRow(fila);
            }            
        });
        
        jtStockProducto.setAutoscrolls(false);
        jtStockProducto.setModel(dtm);
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnSalir = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jtxtxCriterio = new javax.swing.JTextField();
        jlLugar = new javax.swing.JLabel();
        jcLugar = new javax.swing.JComboBox<String>();
        jlIdLugar = new javax.swing.JLabel();
        btnImprimir = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtStockVencimiento = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtStockProducto = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1102, 817));

        btnSalir.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/close_window.png"))); // NOI18N
        btnSalir.setText("Cerrar");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(153, 0, 51));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("STOCK DE PRODUCTOS");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 0, 51));
        jLabel2.setText("Criterio de Busqueda");

        jtxtxCriterio.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jtxtxCriterio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtxCriterioActionPerformed(evt);
            }
        });
        jtxtxCriterio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxtxCriterioKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtxtxCriterioKeyReleased(evt);
            }
        });

        jlLugar.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jlLugar.setForeground(new java.awt.Color(153, 0, 51));
        jlLugar.setText("Lugar");

        jcLugar.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jcLugar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jcLugar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcLugarActionPerformed(evt);
            }
        });

        btnImprimir.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/print.png"))); // NOI18N
        btnImprimir.setText("Imprimir");
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });

        jtStockVencimiento.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Fecha Vencimiento", "Cantidad"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jtStockVencimiento);

        jtStockProducto.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jtStockProducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Producto", "Laboratorio", "Familia", "Unidad", "Stock"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtStockProducto.setRowHeight(22);
        jtStockProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtStockProductoMouseClicked(evt);
            }
        });
        jtStockProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtStockProductoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtStockProductoKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jtStockProducto);
        if (jtStockProducto.getColumnModel().getColumnCount() > 0) {
            jtStockProducto.getColumnModel().getColumn(0).setMinWidth(500);
            jtStockProducto.getColumnModel().getColumn(0).setPreferredWidth(500);
            jtStockProducto.getColumnModel().getColumn(0).setMaxWidth(500);
            jtStockProducto.getColumnModel().getColumn(3).setMinWidth(100);
            jtStockProducto.getColumnModel().getColumn(3).setPreferredWidth(100);
            jtStockProducto.getColumnModel().getColumn(3).setMaxWidth(100);
            jtStockProducto.getColumnModel().getColumn(4).setMinWidth(85);
            jtStockProducto.getColumnModel().getColumn(4).setPreferredWidth(85);
            jtStockProducto.getColumnModel().getColumn(4).setMaxWidth(85);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnSalir))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jtxtxCriterio, javax.swing.GroupLayout.PREFERRED_SIZE, 745, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnImprimir))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jlLugar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jcLugar, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane1)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(20, 20, 20)
                        .addComponent(jlIdLugar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlLugar)
                    .addComponent(jcLugar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtxCriterio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnImprimir))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jlIdLugar)
                        .addGap(481, 481, 481))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 475, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSalir))
                        .addGap(38, 38, 38))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void jtxtxCriterioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtxCriterioKeyPressed

    }//GEN-LAST:event_jtxtxCriterioKeyPressed

    private void jtxtxCriterioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtxCriterioKeyReleased
        String criterio = "";
        if (jtxtxCriterio.getText().length() != 0) {

            criterio = jtxtxCriterio.getText();
        } else {
            criterio = "";
        }
        llenarTablaStockProducto(criterio);

    }//GEN-LAST:event_jtxtxCriterioKeyReleased

    private void jcLugarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcLugarActionPerformed
        seleccionarElementoLugar();
    }//GEN-LAST:event_jcLugarActionPerformed

    private void jtxtxCriterioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtxCriterioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtxCriterioActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        imprimir(Integer.valueOf(jlIdLugar.getText()));
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void jtStockProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtStockProductoMouseClicked
        seleccionarProducto();
    }//GEN-LAST:event_jtStockProductoMouseClicked

    private void jtStockProductoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtStockProductoKeyPressed
        /*
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            //jtxtCantidad.requestFocus();
            FormAuxiliarTemp formAuxiliarTemp = new FormAuxiliarTemp(connectionTemp,
                idProducto, idUnidadMedida,
                descripcionGeneralProducto);
            formAuxiliarTemp.setVisible(true);
        }
        */
    }//GEN-LAST:event_jtStockProductoKeyPressed

    private void jtStockProductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtStockProductoKeyReleased
        seleccionarProducto();
    }//GEN-LAST:event_jtStockProductoKeyReleased

    public void imprimir(int idLugar){
        ReporteVentasDAO reporte = new ReporteVentasDAOImpl(connectionDB, "0");
        reporte.stockProductosLugar(idLugar);
    };
    
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
            java.util.logging.Logger.getLogger(FormStockProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormStockProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormStockProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormStockProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormStockProducto().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JComboBox<String> jcLugar;
    private javax.swing.JLabel jlIdLugar;
    private javax.swing.JLabel jlLugar;
    private javax.swing.JTable jtStockProducto;
    private javax.swing.JTable jtStockVencimiento;
    private javax.swing.JTextField jtxtxCriterio;
    // End of variables declaration//GEN-END:variables

    private void seleccionarProducto() {
        
        int fila = jtStockProducto.getSelectedRow();
        
        int idProducto = (int) jtStockProducto.getValueAt(fila, 4);
        byte idUnidadMedida = (byte) jtStockProducto.getValueAt(fila, 5);
        
        //llenarStockVencimiento(idProducto, idUnidadMedida);
        
    }

    private void llenarStockVencimiento(int idProducto, byte idUnidadMedida) {
        /*
        ArrayList<StockVencimiento> list = new ArrayList<>();
        
        list = productoDAOImpl.getListStockVencimiento(idLugar, idProducto, idUnidadMedida);
        
        dtm = (DefaultTableModel) this.jtStockVencimiento.getModel();
        dtm.setRowCount(0);

        jtStockVencimiento.setModel(dtm);

        Object[] fila = new Object[2];

        for (int i = 0; i < list.size(); i++) {
            fila[0] = list.get(i).getFechaVencimiento();
            fila[1] = list.get(i).getCantidad();
            
            dtm.addRow(fila);
        }
        

        TableColumnModel columnModel = jtStockVencimiento.getColumnModel();
        
        TableColumn cantidadStock = columnModel.getColumn(1);
        
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(jLabel1.RIGHT);
        
        cantidadStock.setCellRenderer(renderer);
        
        jtStockVencimiento.setModel(dtm);
*/
    }
}
