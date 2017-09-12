/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package almacenes.vistas;

import CodigoControl.ControlCode;
import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.DetalleTransaccion;
import almacenes.model.FacturaVenta;
import almacenes.model.PendientePago;
import dao.DetalleTransaccionDAOImpl;
import dao.FacturaDAO;
import dao.FacturaDAOImpl;
import dao.FacturaVentaDAOImpl;
import dao.TransaccionDAOImpl;
import dao.reportes.ReporteFacturacionDAOImpl;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author jcapax
 */
public class FormFacturaCredito extends javax.swing.JFrame {

    /**
     * Creates new form FormFacturaCredito
     */
    private DatabaseUtils databaseUtils;
    private Connection connectionDB;
    DefaultTableModel dtm;
    private DecimalFormat df;

    public FormFacturaCredito(Connection connectionDB) {
        initComponents();
        this.databaseUtils = new DatabaseUtils();
        this.connectionDB = connectionDB;
        headerTabla();
        df = new DecimalFormat("###,##0.00");
        llenarTablaFacturaPendiente();
    }

    public FormFacturaCredito() {
        initComponents();
    }

    public void headerTabla() {
        Font f = new Font("Times New Roman", Font.BOLD, 13);

        jtListaCreditoPorFacturar.getTableHeader().setFont(f);
        jtListaCreditoPorFacturar.getTableHeader().setBackground(Color.orange);

        jtDetalleTransaccion.getTableHeader().setFont(f);
        jtDetalleTransaccion.getTableHeader().setBackground(Color.orange);
    }

    public void llenarTablaFacturaPendiente() {
        FacturaDAO rub = new FacturaDAOImpl(connectionDB);

        ArrayList<PendientePago> r = new ArrayList<PendientePago>();

        r = rub.getListaCreditoPorFacturar();

        dtm = (DefaultTableModel) this.jtListaCreditoPorFacturar.getModel();
        dtm.setRowCount(0);

        jtListaCreditoPorFacturar.setModel(dtm);

        Object[] fila = new Object[6];

        for (int i = 0; i < r.size(); i++) {

            fila[0] = r.get(i).getIdTransaccion();
            fila[1] = r.get(i).getFecha();
            fila[2] = df.format(r.get(i).getValorTotal());
            fila[3] = r.get(i).getDetalle();
            fila[4] = r.get(i).getNombreCompleto();
            fila[5] = r.get(i).getRazonSocial();

            dtm.addRow(fila);
        }

        TableColumnModel columnModel = jtListaCreditoPorFacturar.getColumnModel();

        TableColumn valorTotal = columnModel.getColumn(2);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(jlRazonSocial.RIGHT);

        valorTotal.setCellRenderer(renderer);

        jtListaCreditoPorFacturar.setModel(dtm);
    }

    public void llenarDetalleTransaccion(int idTransaccion) {
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

        jtxtTotalTransaccion.setText(df.format(importeTotal).toString());

        TableColumnModel columnModel = jtDetalleTransaccion.getColumnModel();

        TableColumn colImpTotal = columnModel.getColumn(3);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(jlRazonSocial.RIGHT);

        colImpTotal.setCellRenderer(renderer);

        jtDetalleTransaccion.setModel(dtm);
    }

    public int getIdTransaccionSeleccion() {
        int idTransaccion = 0;
        int fila = jtListaCreditoPorFacturar.getSelectedRow();

        idTransaccion = Integer.parseInt(jtListaCreditoPorFacturar.getValueAt(fila, 0).toString());

        return idTransaccion;
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
        jtListaCreditoPorFacturar = new javax.swing.JTable();
        jpanelCompraVenta = new javax.swing.JPanel();
        jlnit = new javax.swing.JLabel();
        jtxtNit = new javax.swing.JTextField();
        jlRazonSocial = new javax.swing.JLabel();
        jtxtRazonSocial = new javax.swing.JTextField();
        jbTransaccion = new javax.swing.JButton();
        jbSalir = new javax.swing.JToggleButton();
        jlTituloFormulario = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtDetalleTransaccion = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jtxtTotalTransaccion = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jtListaCreditoPorFacturar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "IdTransaccion", "Fecha", "Valor Total", "Detalle", "Nombre Completo", "Razon Social"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtListaCreditoPorFacturar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtListaCreditoPorFacturarMouseClicked(evt);
            }
        });
        jtListaCreditoPorFacturar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtListaCreditoPorFacturarKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtListaCreditoPorFacturarKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jtListaCreditoPorFacturar);
        if (jtListaCreditoPorFacturar.getColumnModel().getColumnCount() > 0) {
            jtListaCreditoPorFacturar.getColumnModel().getColumn(0).setMinWidth(0);
            jtListaCreditoPorFacturar.getColumnModel().getColumn(0).setPreferredWidth(0);
            jtListaCreditoPorFacturar.getColumnModel().getColumn(0).setMaxWidth(0);
            jtListaCreditoPorFacturar.getColumnModel().getColumn(1).setMinWidth(85);
            jtListaCreditoPorFacturar.getColumnModel().getColumn(1).setPreferredWidth(85);
            jtListaCreditoPorFacturar.getColumnModel().getColumn(1).setMaxWidth(85);
            jtListaCreditoPorFacturar.getColumnModel().getColumn(2).setMinWidth(85);
            jtListaCreditoPorFacturar.getColumnModel().getColumn(2).setPreferredWidth(85);
            jtListaCreditoPorFacturar.getColumnModel().getColumn(2).setMaxWidth(85);
        }

        jlnit.setForeground(new java.awt.Color(153, 0, 51));
        jlnit.setText("CI / NIT");

        jtxtNit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxtNitKeyPressed(evt);
            }
        });

        jlRazonSocial.setForeground(new java.awt.Color(153, 0, 51));
        jlRazonSocial.setText("Razon Social");

        javax.swing.GroupLayout jpanelCompraVentaLayout = new javax.swing.GroupLayout(jpanelCompraVenta);
        jpanelCompraVenta.setLayout(jpanelCompraVentaLayout);
        jpanelCompraVentaLayout.setHorizontalGroup(
            jpanelCompraVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelCompraVentaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpanelCompraVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlnit)
                    .addComponent(jtxtNit, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpanelCompraVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlRazonSocial)
                    .addComponent(jtxtRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jpanelCompraVentaLayout.setVerticalGroup(
            jpanelCompraVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelCompraVentaLayout.createSequentialGroup()
                .addGroup(jpanelCompraVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlnit)
                    .addComponent(jlRazonSocial))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpanelCompraVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtNit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jbTransaccion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Configurar.png"))); // NOI18N
        jbTransaccion.setText("Transaccion");
        jbTransaccion.setAlignmentY(0.7F);
        jbTransaccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbTransaccionActionPerformed(evt);
            }
        });

        jbSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/close_window.png"))); // NOI18N
        jbSalir.setText("Salir");
        jbSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSalirActionPerformed(evt);
            }
        });

        jlTituloFormulario.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        jlTituloFormulario.setForeground(new java.awt.Color(153, 0, 51));
        jlTituloFormulario.setText("Factura por Credito");

        jtDetalleTransaccion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Producto", "Simbolo", "Cantidad", "Importe Total"
            }
        ));
        jScrollPane3.setViewportView(jtDetalleTransaccion);

        jLabel7.setForeground(new java.awt.Color(153, 0, 51));
        jLabel7.setText("Total Movimiento");

        jtxtTotalTransaccion.setEditable(false);
        jtxtTotalTransaccion.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jpanelCompraVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jbTransaccion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 672, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jtxtTotalTransaccion, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jlTituloFormulario)
                .addGap(428, 428, 428))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlTituloFormulario)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtxtTotalTransaccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jpanelCompraVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jbTransaccion)
                            .addComponent(jbSalir))
                        .addGap(27, 27, 27))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jtxtNitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtNitKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            if (jtxtNit.getText().equals("0")) {
                jtxtRazonSocial.setText("SIN NOMBRE");
            } else {
                FacturaVentaDAOImpl fac = new FacturaVentaDAOImpl(connectionDB);
                jtxtRazonSocial.setText(fac.getRazonSocialFactura(jtxtNit.getText()));
                jtxtRazonSocial.requestFocus();
            }
        }
    }//GEN-LAST:event_jtxtNitKeyPressed

    public void registrarFactura(int idTransaccion) {
        TransaccionDAOImpl tran = new TransaccionDAOImpl(connectionDB);
        FacturaVentaDAOImpl facDaoImpl = new FacturaVentaDAOImpl(connectionDB);
        ControlCode controlCode = new ControlCode();

        String nit = jtxtNit.getText().trim();
        String razonSocial = jtxtRazonSocial.getText().trim().toUpperCase();

        int idSucursal = 1;
        String codigoControl = "";
        int correlativoSucursal = 1;

        int especificacion = 1;
        String estado = "V";
        String nroAutorizacion = facDaoImpl.getNroAutorizacion(idSucursal);
        //Date fechaFactura = tran.getFechaTransaccion(idTransaccion);
        //java.util.Date fechaFactura1 = new Date(fechaFactura.getTime());
        java.sql.Date fechaFactura1 = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        Date fechaLimiteEmision = facDaoImpl.getFechaLimiteEmision(nroAutorizacion);
        int idDosificacion = 1;
        int nroFactura = facDaoImpl.getNewNroFactura(nroAutorizacion);
        String llaveDosf = facDaoImpl.getLlaveDosificacion(nroAutorizacion);
        double importeTotal = tran.getValorTotalTransaccion(idTransaccion);
        double importeExportaciones = 0;
        double importeIce = 0;
        double importeRebajas = 0;
        double importeSubTotal = importeTotal - importeExportaciones - importeIce;
        double importeVentasTasaCero = 0;
        double importeBaseDebitoFiscal = importeSubTotal;
        double debitoFiscal = importeBaseDebitoFiscal * 0.13;

        Format fd = new SimpleDateFormat("yyyyMMdd");

        String auxFecha = fd.format(fechaFactura1).trim();

        codigoControl = controlCode.generate(nroAutorizacion,
                String.valueOf(nroFactura).trim(),
                nit.trim(),
                auxFecha.trim(),
                String.valueOf(importeTotal).trim(),
                llaveDosf.trim());

        FacturaVenta fact = new FacturaVenta();

        fact.setCodigoControl(codigoControl);
        fact.setCorrelativoSucursal(correlativoSucursal);
        fact.setDebitoFiscal(debitoFiscal);
        fact.setEspecificacion(especificacion);
        fact.setEstado(estado);
        fact.setFechaFactura(fechaFactura1);
        fact.setFechaLimiteEmision(fechaLimiteEmision);
        fact.setIdDosificacion(idDosificacion);
        fact.setIdSucursal(idSucursal);
        fact.setIdTransaccion(idTransaccion);
        fact.setImporteBaseDebitoFiscal(importeBaseDebitoFiscal);
        fact.setImporteExportaciones(importeExportaciones);
        fact.setImporteIce(importeIce);
        fact.setImporteRebajas(importeRebajas);
        fact.setImporteSubtotal(importeSubTotal);
        fact.setImporteTotal(importeTotal);
        fact.setImporteVentasTasaCero(importeVentasTasaCero);
        fact.setNit(nit);
        fact.setNroAutorizacion(nroAutorizacion);
        fact.setNroFactura(nroFactura);
        fact.setRazonSocial(razonSocial);

        FacturaVentaDAOImpl factDaoImpl = new FacturaVentaDAOImpl(connectionDB);
        factDaoImpl.insertarFacturaVenta(fact);

        ReporteFacturacionDAOImpl repFactura = new ReporteFacturacionDAOImpl(connectionDB, estado);

        repFactura.VistaPreviaFacturaVenta(idTransaccion, facDaoImpl.getCadenaCodigoQr(idTransaccion), fact.getImporteTotal());
    }

    public boolean validarRegistros() {
        boolean aux = true;

        if (jtxtNit.getText().length() == 0) {
            JOptionPane.showMessageDialog(this, "El Nit o CI del cliente no pueden estar vacio!!!");
            aux = false;
        }
        if (jtxtRazonSocial.getText().length() == 0) {
            JOptionPane.showMessageDialog(this, "Favor registrar Razon Social del cliente!!!");
            aux = false;
        }
        try {
            Long x = Long.parseLong(jtxtNit.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Nit o Ci no valido!!!");
            aux = false;
        }

        return aux;
    }

    private void jbTransaccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbTransaccionActionPerformed
        if(validarRegistros()){
            registrarFactura(getIdTransaccionSeleccion());
            llenarTablaFacturaPendiente();
            llenarDetalleTransaccion(0);
        }
    }//GEN-LAST:event_jbTransaccionActionPerformed

    private void jbSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSalirActionPerformed
        dispose();
    }//GEN-LAST:event_jbSalirActionPerformed

    private void jtListaCreditoPorFacturarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtListaCreditoPorFacturarMouseClicked
        int idTransaccion = getIdTransaccionSeleccion();
        llenarDetalleTransaccion(idTransaccion);
    }//GEN-LAST:event_jtListaCreditoPorFacturarMouseClicked

    private void jtListaCreditoPorFacturarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtListaCreditoPorFacturarKeyPressed
        int idTransaccion = getIdTransaccionSeleccion();
        llenarDetalleTransaccion(idTransaccion);
    }//GEN-LAST:event_jtListaCreditoPorFacturarKeyPressed

    private void jtListaCreditoPorFacturarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtListaCreditoPorFacturarKeyReleased
        int idTransaccion = getIdTransaccionSeleccion();
        llenarDetalleTransaccion(idTransaccion);
    }//GEN-LAST:event_jtListaCreditoPorFacturarKeyReleased

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
            java.util.logging.Logger.getLogger(FormFacturaCredito.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormFacturaCredito.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormFacturaCredito.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormFacturaCredito.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormFacturaCredito().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JToggleButton jbSalir;
    private javax.swing.JButton jbTransaccion;
    private javax.swing.JLabel jlRazonSocial;
    private javax.swing.JLabel jlTituloFormulario;
    private javax.swing.JLabel jlnit;
    private javax.swing.JPanel jpanelCompraVenta;
    private javax.swing.JTable jtDetalleTransaccion;
    private javax.swing.JTable jtListaCreditoPorFacturar;
    private javax.swing.JTextField jtxtNit;
    private javax.swing.JTextField jtxtRazonSocial;
    private javax.swing.JTextField jtxtTotalTransaccion;
    // End of variables declaration//GEN-END:variables
}
