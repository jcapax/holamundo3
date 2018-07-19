/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package almacenes.vistas;

import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.Configuracion;
import almacenes.model.FacturaVenta;
import dao.FacturaDAOImpl;
import dao.SistemaDAO;
import dao.SistemaDAOImpl;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 *
 * @author jcapax
 */
public class FormLibroVentas extends javax.swing.JFrame {

    private DatabaseUtils databaseUtils;
    private static Connection connectionDB;

    private static SistemaDAO sistemaDAO;

    DefaultTableModel dtm;

    public FormLibroVentas() {
        initComponents();
    }

    public void headerTabla() {
        Font f = new Font("Times New Roman", Font.BOLD, 13);

        jtFactura.getTableHeader().setFont(f);
        jtFactura.getTableHeader().setBackground(Color.orange);
    }

    public FormLibroVentas(Connection connectionDB) {
        initComponents();
        this.setLocationRelativeTo(null);

        this.databaseUtils = new DatabaseUtils();
        this.connectionDB = connectionDB;

        sistemaDAO = new SistemaDAOImpl(this.connectionDB);

        headerTabla();

        iniciarComponentes();

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
        jLabel2 = new javax.swing.JLabel();
        jcAnno = new javax.swing.JComboBox<>();
        jcMes = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtFactura = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jtxtSumImporteTotal = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jtxtSumImporteBaseDebitoFiscal = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jtxtSumDebitoFiscal = new javax.swing.JTextField();
        jbBuscar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jlTituloFormulario = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setForeground(new java.awt.Color(153, 0, 51));
        jLabel1.setText("Mes");

        jLabel2.setForeground(new java.awt.Color(153, 0, 51));
        jLabel2.setText("Año");

        jcAnno.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jcMes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jtFactura.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nro", "Fecha", "Nro Fac.", "Nro Aut.", "Estado", "NIT / CI", "Razon Social", "Imp. Total", "ICE", "Export.", "Tasa Cero", "Sub Total", "Rebaja", "Imp. Base", "Deb. Fiscal", "Cod. Control"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, true, false, true, false, true, false, false, false, true, false, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jtFactura);
        if (jtFactura.getColumnModel().getColumnCount() > 0) {
            jtFactura.getColumnModel().getColumn(0).setResizable(false);
            jtFactura.getColumnModel().getColumn(0).setPreferredWidth(20);
            jtFactura.getColumnModel().getColumn(2).setResizable(false);
            jtFactura.getColumnModel().getColumn(2).setPreferredWidth(30);
            jtFactura.getColumnModel().getColumn(4).setResizable(false);
            jtFactura.getColumnModel().getColumn(4).setPreferredWidth(20);
            jtFactura.getColumnModel().getColumn(6).setResizable(false);
            jtFactura.getColumnModel().getColumn(6).setPreferredWidth(200);
            jtFactura.getColumnModel().getColumn(7).setResizable(false);
            jtFactura.getColumnModel().getColumn(8).setMinWidth(0);
            jtFactura.getColumnModel().getColumn(8).setPreferredWidth(0);
            jtFactura.getColumnModel().getColumn(8).setMaxWidth(0);
            jtFactura.getColumnModel().getColumn(9).setMinWidth(0);
            jtFactura.getColumnModel().getColumn(9).setPreferredWidth(0);
            jtFactura.getColumnModel().getColumn(9).setMaxWidth(0);
            jtFactura.getColumnModel().getColumn(10).setMinWidth(0);
            jtFactura.getColumnModel().getColumn(10).setPreferredWidth(0);
            jtFactura.getColumnModel().getColumn(10).setMaxWidth(0);
            jtFactura.getColumnModel().getColumn(12).setMinWidth(0);
            jtFactura.getColumnModel().getColumn(12).setPreferredWidth(0);
            jtFactura.getColumnModel().getColumn(12).setMaxWidth(0);
        }

        jLabel3.setForeground(new java.awt.Color(153, 0, 51));
        jLabel3.setText("Imp. Total");

        jtxtSumImporteTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel4.setForeground(new java.awt.Color(153, 0, 51));
        jLabel4.setText("Imp. Base");

        jtxtSumImporteBaseDebitoFiscal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel5.setForeground(new java.awt.Color(153, 0, 51));
        jLabel5.setText("Deb. Fiscal");

        jtxtSumDebitoFiscal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jtxtSumImporteTotal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(112, 112, 112)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jtxtSumImporteBaseDebitoFiscal, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtxtSumDebitoFiscal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(5, 5, 5)))
                .addGap(113, 113, 113))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 462, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jtxtSumDebitoFiscal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5)
                                .addComponent(jLabel4))))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jtxtSumImporteBaseDebitoFiscal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jtxtSumImporteTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        jbBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Search-icon.png"))); // NOI18N
        jbBuscar.setText("Buscar");
        jbBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbBuscarActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/print.png"))); // NOI18N
        jButton2.setText("Imprimir");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jlTituloFormulario.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        jlTituloFormulario.setForeground(new java.awt.Color(153, 0, 51));
        jlTituloFormulario.setText("Libro de Ventas");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/excel_logo.png"))); // NOI18N
        jButton1.setText("Exportar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/close_window.png"))); // NOI18N
        btnSalir.setText("Cerrar");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcMes, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcAnno, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(107, 107, 107)
                        .addComponent(jbBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSalir)
                        .addGap(0, 239, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jlTituloFormulario)
                .addGap(444, 444, 444))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jlTituloFormulario)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jcAnno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbBuscar)
                    .addComponent(jButton2)
                    .addComponent(jButton1)
                    .addComponent(btnSalir))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbBuscarActionPerformed
        vaciarTotales();
        llenarTablaFactura();
    }//GEN-LAST:event_jbBuscarActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        FacturaDAOImpl rub = new FacturaDAOImpl(connectionDB);
        DecimalFormat df = new DecimalFormat("###,##0.00");

        ArrayList<FacturaVenta> r = new ArrayList<FacturaVenta>();

        byte mes = 0;
        int anno = 0;

        mes = (byte) (jcMes.getSelectedIndex() + 1);
        anno = Integer.parseInt(jcAnno.getSelectedItem().toString());

        r = rub.getListaFacturasLibroVenta(mes, anno);

        exportarExcel(r, anno, mes);
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(FormLibroVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormLibroVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormLibroVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormLibroVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormLibroVentas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbBuscar;
    private javax.swing.JComboBox<String> jcAnno;
    private javax.swing.JComboBox<String> jcMes;
    private javax.swing.JLabel jlTituloFormulario;
    private javax.swing.JTable jtFactura;
    private javax.swing.JTextField jtxtSumDebitoFiscal;
    private javax.swing.JTextField jtxtSumImporteBaseDebitoFiscal;
    private javax.swing.JTextField jtxtSumImporteTotal;
    // End of variables declaration//GEN-END:variables

    private void iniciarComponentes() {

        llenarMeses();
        llenarAnnos();
        vaciarTotales();
    }

    public void vaciarTotales() {
        jtxtSumDebitoFiscal.setText("");
        jtxtSumImporteBaseDebitoFiscal.setText("");
        jtxtSumImporteTotal.setText("");

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

        FacturaDAOImpl fact = new FacturaDAOImpl(connectionDB);
        ArrayList<Integer> lanno = fact.getListaAnnosFacturacion();
        for (byte i = 0; i < lanno.size(); i++) {
            jcAnno.addItem(lanno.get(i).toString());
        }

    }

    public void llenarTablaFactura() {
        double importeTotal = 0, baseDebitoFiscal = 0, debitoFiscal = 0;

        FacturaDAOImpl rub = new FacturaDAOImpl(connectionDB);
        DecimalFormat df = new DecimalFormat("###,##0.00");

        ArrayList<FacturaVenta> r = new ArrayList<FacturaVenta>();

        byte mes = 0;
        int anno = 0;

        mes = (byte) (jcMes.getSelectedIndex() + 1);
        anno = Integer.parseInt(jcAnno.getSelectedItem().toString());

        r = rub.getListaFacturasLibroVenta(mes, anno);

        dtm = (DefaultTableModel) this.jtFactura.getModel();
        dtm.setRowCount(0);

        jtFactura.setModel(dtm);

        Object[] fila = new Object[16];

        for (int i = 0; i < r.size(); i++) {
            fila[0] = i + 1;
            fila[1] = r.get(i).getFechaFactura();
            fila[2] = r.get(i).getNroFactura();
            fila[3] = r.get(i).getNroAutorizacion();
            fila[4] = r.get(i).getEstado();
            fila[5] = r.get(i).getNit();
            fila[6] = r.get(i).getRazonSocial();
            fila[7] = df.format(r.get(i).getImporteTotal());
            importeTotal = importeTotal + r.get(i).getImporteTotal();
            fila[8] = r.get(i).getImporteIce();
            fila[9] = r.get(i).getImporteExportaciones();
            fila[10] = r.get(i).getImporteVentasTasaCero();
            fila[11] = df.format(r.get(i).getImporteSubtotal());
            fila[12] = r.get(i).getImporteRebajas();
            fila[13] = df.format(r.get(i).getImporteBaseDebitoFiscal());
            baseDebitoFiscal = baseDebitoFiscal + r.get(i).getImporteBaseDebitoFiscal();
            fila[14] = df.format(r.get(i).getDebitoFiscal());
            debitoFiscal = debitoFiscal + r.get(i).getDebitoFiscal();
            fila[15] = r.get(i).getCodigoControl();

            dtm.addRow(fila);
        }

        jtxtSumImporteTotal.setText(String.valueOf(df.format(importeTotal)));
        jtxtSumImporteBaseDebitoFiscal.setText(String.valueOf(df.format(baseDebitoFiscal)));
        jtxtSumDebitoFiscal.setText(String.valueOf(df.format(debitoFiscal)));

        TableColumnModel columnModel = jtFactura.getColumnModel();

        TableColumn colImpTot = columnModel.getColumn(7);
        TableColumn colSubTot = columnModel.getColumn(11);
        TableColumn colBase = columnModel.getColumn(13);
        TableColumn colDebFis = columnModel.getColumn(14);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(jLabel1.RIGHT);

        colImpTot.setCellRenderer(renderer);
        colSubTot.setCellRenderer(renderer);
        colBase.setCellRenderer(renderer);
        colDebFis.setCellRenderer(renderer);

        jtFactura.setModel(dtm);
    }

    public static void exportarExcel(ArrayList<FacturaVenta> fact, int anno, int mes) {

        Configuracion configuracion = new Configuracion();

        try {
            configuracion = sistemaDAO.getGestionConfiguraciones();

            String ruta = configuracion.getRutaExcel() + "/";

            WritableWorkbook libro1 = null;
            try {

                String nombreArchivo = String.valueOf(anno) + "_" + String.valueOf(mes) + ".xls";

                System.out.println(ruta + nombreArchivo);

                File file = new File(ruta + nombreArchivo);

                libro1 = Workbook.createWorkbook(file);

                WritableSheet hoja1 = libro1.createSheet("hoja XX", 0);

                Label labelEspecificacion = new Label(0, 0, "especificacion");
                Label labelNro = new Label(1, 0, "nro");
                Label labelFechaFactura = new Label(2, 0, "Fecha Fact.");
                Label labelNroFactura = new Label(3, 0, "Nro Fact.");
                Label labelNroAutorizacion = new Label(4, 0, "Nro Autori.");
                Label labelEstado = new Label(5, 0, "Estado");
                Label labelNit = new Label(6, 0, "Nit");
                Label labelRazonSocial = new Label(7, 0, "Razon Social");
                Label labelImporteTotal = new Label(8, 0, "Importe Total");
                Label labelImporteIce = new Label(9, 0, "Importe Ice");
                Label labelImporteExportaciones = new Label(10, 0, "Importe Exportaciones");
                Label labelImportetasaCero = new Label(11, 0, "Importe Tasa Cero");
                Label labelImporteSubTotal = new Label(12, 0, "Importe Sub Total");
                Label labelImporteRebajas = new Label(13, 0, "Importe Rebajas");
                Label labelImporteBase = new Label(14, 0, "Importe Base");
                Label labelDebitoFiscal = new Label(15, 0, "Debito Fiscal");
                Label labelCodigoControl = new Label(16, 0, "Codigo Control");

                hoja1.addCell(labelEspecificacion);
                hoja1.addCell(labelNro);
                hoja1.addCell(labelFechaFactura);
                hoja1.addCell(labelNroFactura);
                hoja1.addCell(labelNroAutorizacion);
                hoja1.addCell(labelEstado);
                hoja1.addCell(labelNit);
                hoja1.addCell(labelRazonSocial);
                hoja1.addCell(labelImporteTotal);
                hoja1.addCell(labelImporteIce);
                hoja1.addCell(labelImporteExportaciones);
                hoja1.addCell(labelImportetasaCero);
                hoja1.addCell(labelImporteSubTotal);
                hoja1.addCell(labelImporteRebajas);
                hoja1.addCell(labelImporteBase);
                hoja1.addCell(labelDebitoFiscal);
                hoja1.addCell(labelCodigoControl);

                Number especificacion = null;
                Number nro = null;
                Label fechaFactura = null;
                Number nroFactura = null;
                Label nroAutorizacion = null;
                Label estado = null;
                Label nit = null;
                Label razonSocial = null;
                Number importeTotal = null;
                Number importeIce = null;
                Number importeExportaciones = null;
                Number importeTasaCero = null;
                Number importeSubTotal = null;
                Number importeRebajas = null;
                Number importeBase = null;
                Number importeDebitoFiscal = null;
                Label codigoControl = null;

                Integer getNroFactura = 0;
                String getNroAutorizacion = "";

                int i = 0;
//                        Number nit = null;
                for (int x = 0; x < fact.size(); x++) {

                    i = x + 1;

                    especificacion = new Number(0, i, 3);
                    nro = new Number(1, i, i);
                    fechaFactura = new Label(2, i, fact.get(x).getFechaFactura().toString());

                    getNroFactura = fact.get(x).getNroFactura();
                    nroFactura = new Number(3, i, getNroFactura);

                    getNroAutorizacion = fact.get(x).getNroAutorizacion();
                    nroAutorizacion = new Label(4, i, getNroAutorizacion);

                    estado = new Label(5, i, fact.get(x).getEstado());
                    nit = new Label(6, i, fact.get(x).getNit());
                    razonSocial = new Label(7, i, fact.get(x).getRazonSocial());
                    importeTotal = new Number(8, i, fact.get(x).getImporteTotal());
                    importeIce = new Number(9, i, fact.get(x).getImporteIce());
                    importeExportaciones = new Number(10, i, fact.get(x).getImporteExportaciones());
                    importeTasaCero = new Number(11, i, fact.get(x).getImporteVentasTasaCero());
                    importeSubTotal = new Number(12, i, fact.get(x).getImporteSubtotal());
                    importeRebajas = new Number(13, i, fact.get(x).getImporteRebajas());
                    importeBase = new Number(14, i, fact.get(x).getImporteBaseDebitoFiscal());
                    importeDebitoFiscal = new Number(15, i, fact.get(x).getDebitoFiscal());
                    codigoControl = new Label(16, i, fact.get(x).getCodigoControl());

                    hoja1.addCell(especificacion);
                    hoja1.addCell(nro);
                    hoja1.addCell(fechaFactura);
                    hoja1.addCell(nroFactura);
                    hoja1.addCell(nroAutorizacion);
                    hoja1.addCell(estado);
                    hoja1.addCell(nit);
                    hoja1.addCell(razonSocial);
                    hoja1.addCell(importeTotal);
                    hoja1.addCell(importeIce);
                    hoja1.addCell(importeExportaciones);
                    hoja1.addCell(importeTasaCero);
                    hoja1.addCell(importeSubTotal);
                    hoja1.addCell(importeRebajas);
                    hoja1.addCell(importeBase);
                    hoja1.addCell(importeDebitoFiscal);
                    hoja1.addCell(codigoControl);

                }
                libro1.write();
            } catch (WriteException | IOException ex) {
                Logger.getLogger(FormLibroVentas.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (libro1 != null) {
                try {
                    libro1.close();
                } catch (WriteException | IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                // TODO Auto-generated catch block

            }
        } catch (SQLException ex) {
            Logger.getLogger(FormLibroVentas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
