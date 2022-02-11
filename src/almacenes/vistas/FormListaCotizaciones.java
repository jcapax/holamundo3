/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package almacenes.vistas;

import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.DetalleTransaccion;
import almacenes.model.ListaTransaccion;
import dao.DetalleTransaccionDAOImpl;
import dao.FacturaDAO;
import dao.FacturaDAOImpl;
import dao.SucursalDAO;
import dao.SucursalDAOImpl;
import dao.TransaccionDAO;
import dao.TransaccionDAOImpl;
import dao.reportes.ReporteComprasDAO;
import dao.reportes.ReporteComprasDAOImpl;
import dao.reportes.ReporteCreditoDAO;
import dao.reportes.ReporteCreditoDAOImpl;
import dao.reportes.ReporteVentasDAO;
import dao.reportes.ReporteVentasDAOImpl;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author juanito
 */
public class FormListaCotizaciones extends javax.swing.JFrame {

    /**
     * Creates new form FormListaTransacciones
     */
    private DatabaseUtils databaseUtils;
    private Connection connectionDB;
    
    private String usuario;
    private byte idLugar;
    private byte idTerminal;
    DefaultTableModel dtm;
    
    TransaccionDAO transaccionDAO;

    public FormListaCotizaciones(Connection connectionDB, String usuario, byte idLugar, byte idTerminal) {
        initComponents();
        
        this.connectionDB = connectionDB;
        this.usuario = usuario;
        this.idLugar = idLugar;
        this.idTerminal = idTerminal;
        
        this.setLocationRelativeTo(null);
        
        headerTabla();
        
        llenarTablaCotizaciones();
    }
    
    public void headerTabla(){
        Font f = new Font("Times New Roman", Font.BOLD, 14);
        
        jtListaTransacciones.getTableHeader().setFont(f);
        jtListaTransacciones.getTableHeader().setBackground(Color.orange);
        
        jtDetalleTransaccion.getTableHeader().setFont(f);
        jtDetalleTransaccion.getTableHeader().setBackground(Color.orange);
        
    }
    
    public FormListaCotizaciones() {
        initComponents();
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
        renderer.setHorizontalAlignment(jlTituloFormulario.RIGHT);
        
        colImpTotal.setCellRenderer(renderer);
        
        jtDetalleTransaccion.setModel(dtm);
    }

    public void llenarTablaCotizaciones(){
        double importeTotal = 0;
        
        transaccionDAO = new TransaccionDAOImpl(connectionDB);
        
        ArrayList<ListaTransaccion> l = new ArrayList<>();

        l = transaccionDAO.getlistaCotizacionesPendientes(idLugar);

        dtm = (DefaultTableModel) this.jtListaTransacciones.getModel();
        dtm.setRowCount(0);

        jtListaTransacciones.setModel(dtm);

        Object[] fila = new Object[6];

        for (int i = 0; i < l.size(); i++) {
            fila[0] = l.get(i).getId();
            fila[1] = l.get(i).getNroTransaccion();
            fila[2] = l.get(i).getFecha();            
            fila[3] = l.get(i).getDetalle();
            fila[4] = l.get(i).getValorTotal();
            fila[5] = l.get(i).getUsuario();
            
            dtm.addRow(fila);
        }
        
        TableColumnModel columnModel = jtListaTransacciones.getColumnModel();
        
        TableColumn colImpCaja = columnModel.getColumn(4);
        
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(jlTituloFormulario.RIGHT);
        
        colImpCaja.setCellRenderer(renderer);
        
        jtListaTransacciones.setModel(dtm);
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
        jtListaTransacciones = new javax.swing.JTable();
        jlTituloFormulario = new javax.swing.JLabel();
        btnSalir = new javax.swing.JButton();
        jbVenta = new javax.swing.JButton();
        jlTituloFormulario2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtDetalleTransaccion = new javax.swing.JTable();
        jbPedido = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        jtListaTransacciones.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        jtListaTransacciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Nro Trans", "Fecha", "Descripcion", "Valor Total", "Usuario"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtListaTransacciones.setRowHeight(20);
        jtListaTransacciones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtListaTransaccionesMouseClicked(evt);
            }
        });
        jtListaTransacciones.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtListaTransaccionesKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtListaTransaccionesKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jtListaTransacciones);
        if (jtListaTransacciones.getColumnModel().getColumnCount() > 0) {
            jtListaTransacciones.getColumnModel().getColumn(0).setMinWidth(0);
            jtListaTransacciones.getColumnModel().getColumn(0).setPreferredWidth(0);
            jtListaTransacciones.getColumnModel().getColumn(0).setMaxWidth(0);
            jtListaTransacciones.getColumnModel().getColumn(1).setMinWidth(75);
            jtListaTransacciones.getColumnModel().getColumn(1).setPreferredWidth(75);
            jtListaTransacciones.getColumnModel().getColumn(1).setMaxWidth(75);
            jtListaTransacciones.getColumnModel().getColumn(2).setMinWidth(100);
            jtListaTransacciones.getColumnModel().getColumn(2).setPreferredWidth(100);
            jtListaTransacciones.getColumnModel().getColumn(2).setMaxWidth(100);
            jtListaTransacciones.getColumnModel().getColumn(4).setMinWidth(100);
            jtListaTransacciones.getColumnModel().getColumn(4).setPreferredWidth(100);
            jtListaTransacciones.getColumnModel().getColumn(4).setMaxWidth(100);
            jtListaTransacciones.getColumnModel().getColumn(5).setMinWidth(120);
            jtListaTransacciones.getColumnModel().getColumn(5).setPreferredWidth(120);
            jtListaTransacciones.getColumnModel().getColumn(5).setMaxWidth(120);
        }

        jlTituloFormulario.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        jlTituloFormulario.setForeground(new java.awt.Color(153, 0, 51));
        jlTituloFormulario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlTituloFormulario.setText("Lista Cotizaciones Pendientes");

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/close_window.png"))); // NOI18N
        btnSalir.setText("Cerrar");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        jbVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/venta.png"))); // NOI18N
        jbVenta.setText("Venta");
        jbVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbVentaActionPerformed(evt);
            }
        });

        jlTituloFormulario2.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        jlTituloFormulario2.setForeground(new java.awt.Color(153, 0, 51));
        jlTituloFormulario2.setText("Detalle Transaccion");

        jtDetalleTransaccion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Producto", "Simbolo", "Cantidad", "Importe Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jtDetalleTransaccion);
        if (jtDetalleTransaccion.getColumnModel().getColumnCount() > 0) {
            jtDetalleTransaccion.getColumnModel().getColumn(1).setMinWidth(80);
            jtDetalleTransaccion.getColumnModel().getColumn(1).setPreferredWidth(80);
            jtDetalleTransaccion.getColumnModel().getColumn(1).setMaxWidth(80);
            jtDetalleTransaccion.getColumnModel().getColumn(2).setMinWidth(80);
            jtDetalleTransaccion.getColumnModel().getColumn(2).setPreferredWidth(80);
            jtDetalleTransaccion.getColumnModel().getColumn(2).setMaxWidth(80);
            jtDetalleTransaccion.getColumnModel().getColumn(3).setMinWidth(90);
            jtDetalleTransaccion.getColumnModel().getColumn(3).setPreferredWidth(90);
            jtDetalleTransaccion.getColumnModel().getColumn(3).setMaxWidth(90);
        }

        jbPedido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Entregas-Credito.png"))); // NOI18N
        jbPedido.setText("Pedido");
        jbPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbPedidoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlTituloFormulario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jlTituloFormulario2)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jbVenta)
                                        .addGap(30, 30, 30)
                                        .addComponent(jbPedido)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnSalir))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 885, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jlTituloFormulario)
                .addGap(28, 28, 28)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jbVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSalir)
                        .addComponent(jbPedido))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jlTituloFormulario2)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    public int getIdTransaccionSeleccion(){
        int idTransaccion = 0;
        int fila = jtListaTransacciones.getSelectedRow();
        
        idTransaccion =Integer.parseInt(jtListaTransacciones.getValueAt(fila, 0).toString());
        
        return idTransaccion;
    }
    private void jbVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbVentaActionPerformed
        int idTipoTransaccion = 2;
        int idTipoTransaccionEntrega = 8;
        int idTransaccionCotizacion = getIdTransaccionSeleccion();
       
        SucursalDAO suc = new SucursalDAOImpl(connectionDB);
        byte idSucursal = suc.getIdSucursal(idLugar);
        
        FacturaDAO fac = new FacturaDAOImpl(connectionDB);
        
        if(!fac.getEstadoDosificacion(idSucursal)){
            JOptionPane.showMessageDialog( null, "¡No se cuenta con una dosificación válida, no podrá emitir facturas!!!" , "Error", JOptionPane.ERROR_MESSAGE);
        }else{
            if(!fac.getFechaLimiteEmisionVigente(idSucursal)){
               JOptionPane.showMessageDialog( null, "¡La fecha de emisión para las facturas ha vencido, no podrá emitir facturas!!!" , "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        
//        FormTransaccionBK formTrans = new FormTransaccionBK(connectionDB, 
//                                            idTipoTransaccion, idTipoTransaccionEntrega, 
 //                                           usuario, idLugar, idTerminal,
  //                                          idTransaccionCotizacion);
    //    formTrans.setVisible(true);
    }//GEN-LAST:event_jbVentaActionPerformed

    private void jtListaTransaccionesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtListaTransaccionesKeyPressed
        llenarDetalleTransaccion(getIdTransaccionSeleccion());
    }//GEN-LAST:event_jtListaTransaccionesKeyPressed

    private void jtListaTransaccionesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtListaTransaccionesKeyReleased
        llenarDetalleTransaccion(getIdTransaccionSeleccion());
    }//GEN-LAST:event_jtListaTransaccionesKeyReleased

    private void jtListaTransaccionesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtListaTransaccionesMouseClicked
        llenarDetalleTransaccion(getIdTransaccionSeleccion());
    }//GEN-LAST:event_jtListaTransaccionesMouseClicked

    private void jbPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbPedidoActionPerformed
        int idTipoTransaccion = 3; //pedidos
        int idTransaccionCotizacion = getIdTransaccionSeleccion();
        
        FormTransaccionPedidos credito = new FormTransaccionPedidos(connectionDB, 
                                            idTipoTransaccion, 
                                            usuario, idLugar, idTerminal, 
                                            idTransaccionCotizacion);
        credito.setVisible(true);
    }//GEN-LAST:event_jbPedidoActionPerformed

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_formFocusGained

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        llenarTablaCotizaciones();
    }//GEN-LAST:event_formWindowGainedFocus

    private void vistaPreviaReciboVenta(int idTransaccion) {
        ReporteVentasDAO reporteVentasDAO = new ReporteVentasDAOImpl(connectionDB, usuario);
        reporteVentasDAO.vistaPreviaReciboVenta(idTransaccion);
        
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
            java.util.logging.Logger.getLogger(FormListaCotizaciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormListaCotizaciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormListaCotizaciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormListaCotizaciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormListaCotizaciones().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSalir;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton jbPedido;
    private javax.swing.JButton jbVenta;
    private javax.swing.JLabel jlTituloFormulario;
    private javax.swing.JLabel jlTituloFormulario2;
    private javax.swing.JTable jtDetalleTransaccion;
    private javax.swing.JTable jtListaTransacciones;
    // End of variables declaration//GEN-END:variables
}
