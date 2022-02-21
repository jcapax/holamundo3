/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package almacenes.vistas;

import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.ListaTransaccion;
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
import java.util.ArrayList;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author juanito
 */
public class FormListaTransacciones extends javax.swing.JFrame {

    /**
     * Creates new form FormListaTransacciones
     */
    private DatabaseUtils databaseUtils;
    private Connection connectionDB;
    
    private String usuario;
    DefaultTableModel dtm;
    
    TransaccionDAO transaccionDAO;

    public FormListaTransacciones(Connection connectionDB, String usuario) {
        initComponents();
        
        this.connectionDB = connectionDB;
        this.usuario = usuario;
        this.setLocationRelativeTo(null);
        
        transaccionDAO = new TransaccionDAOImpl(connectionDB);
        
        headerTabla();

    }
    
    public void headerTabla(){
        Font f = new Font("Times New Roman", Font.BOLD, 18);
        
        jtListaTransacciones.getTableHeader().setFont(f);
        jtListaTransacciones.getTableHeader().setBackground(Color.orange);
    }
    
    public FormListaTransacciones() {
        initComponents();
    }

    public void llenarTablaTransacciones(){
        double importeTotal = 0;
        
        ArrayList<ListaTransaccion> lista = new ArrayList<>();

        lista = transaccionDAO.getlistaTransacciones(new Date(fecha.getDate().getTime()), usuario);

        dtm = (DefaultTableModel) this.jtListaTransacciones.getModel();
        dtm.setRowCount(0);

        jtListaTransacciones.setModel(dtm);

        Object[] fila = new Object[8];

        for (int i = 0; i < lista.size(); i++) {
            fila[0] = lista.get(i).getId();
            fila[1] = lista.get(i).getNroTransaccion();
            fila[2] = lista.get(i).getFecha();            
            fila[3] = lista.get(i).getDescripcion();
            fila[4] = lista.get(i).getNombre_completo();
            fila[5] = lista.get(i).getDetalle();
            fila[6] = lista.get(i).getIdTipoTransaccion();
            fila[7] = lista.get(i).getValorTotal();            
            
            dtm.addRow(fila);
        }
        
        TableColumnModel columnModel = jtListaTransacciones.getColumnModel();
        
        TableColumn colImpCaja = columnModel.getColumn(6);
        
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
        fecha = new com.toedter.calendar.JDateChooser();
        jlTituloFormulario = new javax.swing.JLabel();
        btnSalir = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jbBuscar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jtListaTransacciones.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jtListaTransacciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Nro Trans", "Fecha", "Tipo", "Cliente/Proveedor", "Detalle", "idTipoTransaccion", "Valor Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtListaTransacciones.setRowHeight(20);
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
            jtListaTransacciones.getColumnModel().getColumn(3).setMinWidth(150);
            jtListaTransacciones.getColumnModel().getColumn(3).setPreferredWidth(150);
            jtListaTransacciones.getColumnModel().getColumn(3).setMaxWidth(150);
            jtListaTransacciones.getColumnModel().getColumn(4).setMinWidth(250);
            jtListaTransacciones.getColumnModel().getColumn(4).setPreferredWidth(250);
            jtListaTransacciones.getColumnModel().getColumn(4).setMaxWidth(250);
            jtListaTransacciones.getColumnModel().getColumn(6).setMinWidth(0);
            jtListaTransacciones.getColumnModel().getColumn(6).setPreferredWidth(0);
            jtListaTransacciones.getColumnModel().getColumn(6).setMaxWidth(0);
            jtListaTransacciones.getColumnModel().getColumn(7).setMinWidth(100);
            jtListaTransacciones.getColumnModel().getColumn(7).setPreferredWidth(100);
            jtListaTransacciones.getColumnModel().getColumn(7).setMaxWidth(100);
        }

        fecha.setDateFormatString("dd/MM/yyyy");
        fecha.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jlTituloFormulario.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        jlTituloFormulario.setForeground(new java.awt.Color(153, 0, 51));
        jlTituloFormulario.setText("Lista Transacciones");

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/close_window.png"))); // NOI18N
        btnSalir.setText("Cerrar");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/print.png"))); // NOI18N
        jButton3.setText("Imprimir");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jbBuscar.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jbBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Search-icon.png"))); // NOI18N
        jbBuscar.setText("Buscar");
        jbBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jlTituloFormulario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSalir))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jbBuscar)
                        .addGap(0, 1062, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jlTituloFormulario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbBuscar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 577, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(btnSalir))
                .addGap(8, 8, 8))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try{
            ReporteCreditoDAO reporteCreditoDAO;
            
            int filSel = jtListaTransacciones.getSelectedRow();            
            int id = (int) jtListaTransacciones.getValueAt(filSel, 0);
            int idTipoTransaccion = (int) jtListaTransacciones.getValueAt(filSel, 5);
            switch(idTipoTransaccion){
                case 1: // compra
                    ReporteComprasDAO reporte = new ReporteComprasDAOImpl(connectionDB, usuario);
                    reporte.vistaPreviaCompras(id);
                    break;
                case 2: // venta
                    vistaPreviaReciboVenta(id);
                    break;
                case 3: // pedido
                    ReporteCreditoDAO rep = new ReporteCreditoDAOImpl(connectionDB, usuario);
                    if(transaccionDAO.isCreditoEntrega(id)){
                        rep.vistaPreviaEntregaPendiente(id);
                    }else{                        
                        rep.vistaPreviaCredito(id);
                    }
                    break;
                case 6: // ajuste
                    ReporteComprasDAO reporteComprasDAO = new ReporteComprasDAOImpl(connectionDB, usuario);
                    reporteComprasDAO.vistaPreviaAjusteStock(id);
                    break;
                case 8: // ajuste
                    TransaccionDAO transaccionDAO = new TransaccionDAOImpl(connectionDB);
                    int idTransaccionInicial = transaccionDAO.getIdTransaccionOriginalDeEntregaPendiente(id);
                    transaccionDAO.crearTemporalEntrega();                    
                    transaccionDAO.insertarEntregaTemporal(idTransaccionInicial, id);
                    
                    reporteCreditoDAO = new ReporteCreditoDAOImpl(connectionDB, usuario);
//                    reporteCreditoDAO.vistaPreviaEntregaProductosCredito(idTransaccionInicial, id );                    
                    reporteCreditoDAO.vistaPreviaEntregaProductosCredito();
                    transaccionDAO.eliminarDatosTemporalEntrega();
                    break;
                case 10: // cotizacion
                    reporteCreditoDAO = new ReporteCreditoDAOImpl(connectionDB, usuario);//                  
                    reporteCreditoDAO.vistaPreviaCotizacion(id);                    
                    break;
            }
            
        }catch(Exception e){
            
        }        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jbBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbBuscarActionPerformed
        llenarTablaTransacciones();
        new Date(fecha.getDate().getTime());
    }//GEN-LAST:event_jbBuscarActionPerformed

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
            java.util.logging.Logger.getLogger(FormListaTransacciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormListaTransacciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormListaTransacciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormListaTransacciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormListaTransacciones().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSalir;
    private com.toedter.calendar.JDateChooser fecha;
    private javax.swing.JButton jButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbBuscar;
    private javax.swing.JLabel jlTituloFormulario;
    private javax.swing.JTable jtListaTransacciones;
    // End of variables declaration//GEN-END:variables
}
