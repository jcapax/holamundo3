/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package almacenes.vistas;

import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.Caja;
import almacenes.model.DetalleTransaccion;
import almacenes.model.IngresoEgreso;
import almacenes.model.Transaccion;
import dao.ArqueoDAOImpl;
import dao.CajaDAOImpl;
import dao.DetalleTransaccionDAOImpl;
import dao.IngresoEgresoDAOImpl;
import dao.TransaccionDAOImpl;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jcapax
 */
public class FormIngresosEgresos extends javax.swing.JFrame {

    private DatabaseUtils databaseUtils;
    private Connection connectionDB;
    DefaultTableModel dtm;
    private String tipoCuenta;
    private byte idLugar;
    private byte idTerminal = 1;
    private String usuario;
    private int idTipoTransaccion;
    
    
    public FormIngresosEgresos() {
        initComponents();
    }
    
    public void headerTabla(){
        Font f = new Font("Times New Roman", Font.BOLD, 13);
        
        jtCuentas.getTableHeader().setFont(f);
        jtCuentas.getTableHeader().setBackground(Color.orange);
    }
    
    public FormIngresosEgresos(Connection connectionDB, String tipoCuenta, 
                byte idLugar, String usuario, int idTipoTransaccion) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.databaseUtils = new DatabaseUtils();
        this.connectionDB = connectionDB;
        this.tipoCuenta = tipoCuenta;
        this.idLugar = idLugar;
        this.usuario = usuario;
        this.idTipoTransaccion = idTipoTransaccion;
        
        headerTabla();
        
        if(idTipoTransaccion == 4){
            jlTitulo.setText("REGISTRO EGRESOS");
        }
        else{
            jlTitulo.setText("REGISTRO INGRESOS");
        }
        
        llenarTablaCuentas();
        validarCaja();
    }
    
    public void registrarDetalleTransaccion(int idTransaccion){
        
        DetalleTransaccionDAOImpl detTranDAOImpl = new DetalleTransaccionDAOImpl(connectionDB);
        
        int idProducto = 0;
        int idUnidadMedida = 0;
        double cantidad = 1;
        double valorUnitario = 0;
        double valorTotal = 0;
        String tipoValor = "N";
        
        ArrayList<DetalleTransaccion> detTrans = new ArrayList<DetalleTransaccion>();

        idProducto = (int) jtCuentas.getValueAt(jtCuentas.getSelectedRow(), 0);
        valorUnitario = Double.valueOf(jtxtImporte.getText());
        valorTotal = valorUnitario * cantidad;

        DetalleTransaccion dt = new DetalleTransaccion();

        dt.setIdTransaccion(idTransaccion);
        dt.setIdProducto(idProducto);
        dt.setIdUnidadMedida(idUnidadMedida);
        dt.setCantidad(cantidad);
        dt.setValorUnitario(valorUnitario);
        dt.setValorTotal(valorTotal);
        dt.setTipoValor(tipoValor);

        detTrans.add(dt);

        detTranDAOImpl.insertarDetalleTransaccion(detTrans);
        
    }

    public int resgistrarTransaccion(){
        TransaccionDAOImpl transDaoImpl = new TransaccionDAOImpl(connectionDB);
        
        int nroTipoTransaccion = 0;
        int tipoMovimineto = transDaoImpl.getTipoMovimiento(idTipoTransaccion);
        int idTransaccion = 0;
        String estado = "A";
        String descripcion = "";
//        String usuario = "SYS";
        java.util.Date hoy = new java.util.Date();
        java.sql.Date fecha = new java.sql.Date(hoy.getTime());
        
        nroTipoTransaccion = transDaoImpl.getNroTipoTransaccion(idTipoTransaccion);
        
        descripcion = jtxtDescripcion.getText().toUpperCase();
        
        Transaccion trans = new Transaccion(fecha, idTipoTransaccion, nroTipoTransaccion, 
                                idLugar, idTerminal, tipoMovimineto, estado, usuario, descripcion);
        
        idTransaccion = transDaoImpl.insertarTransaccion(trans);
        
        if(idTransaccion!=0){
//            System.out.println("transaccion registrada nro: "+idTransaccion);
        }
        else{
//            System.out.println("error en el registro");
        }
        
        return idTransaccion;
    }

    public void llenarTablaCuentas(){
        IngresoEgresoDAOImpl rub = new IngresoEgresoDAOImpl(connectionDB);
        
        ArrayList<IngresoEgreso> r = new ArrayList<>();
        
        r = rub.getListaCuentasIngresoEgreso(tipoCuenta);
        
        dtm = (DefaultTableModel) this.jtCuentas.getModel();
        dtm.setRowCount(0);
        
        jtCuentas.setModel(dtm);
        
        Object[] fila = new Object[2];
        
//        System.out.println("nro de registros en pila: " + r.size());
        
        for(int i=0; i< r.size(); i++){
            fila[0] = r.get(i).getIdProducto();
            fila[1] = r.get(i).getDescripcion();
            
            dtm.addRow(fila);
        }
        
        jtCuentas.setModel(dtm);
    }

    public void registrarCaja(int idTransaccion){
        String estado = "A";
//        String usuario = "SYS";
        Date fecha = null;
        int nroCobro = 0, nroPago = 0;
        double importe = 0;
        
        TransaccionDAOImpl trans = new TransaccionDAOImpl(connectionDB);
        fecha = trans.getFechaTransaccion(idTransaccion);
        importe = trans.getValorTotalTransaccion(idTransaccion);
        
        CajaDAOImpl cajaDaoImpl = new CajaDAOImpl(connectionDB);
        
        
        Caja caja = new Caja();
        caja.setEstado(estado);
        caja.setFecha(fecha);
        caja.setIdTransaccion(idTransaccion);
        caja.setImporte(importe);
        caja.setNroCobro(nroCobro);
        caja.setNroPago(nroPago);
        caja.setUsuario(usuario);
        
        cajaDaoImpl.insertarCaja(caja);
                
    }

        public void registrarIngresoEgreso(){
        
        int idTransaccion = 0;
        
        idTransaccion = resgistrarTransaccion();
        registrarDetalleTransaccion(idTransaccion);
        
        registrarCaja(idTransaccion);
        
        JOptionPane.showMessageDialog(this, "Registro realizado con exito");
        
        jtxtImporte.setText("");
        jtxtDescripcion.setText("");
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
        jtCuentas = new javax.swing.JTable();
        jtxtImporte = new javax.swing.JTextField();
        jbGuardar = new javax.swing.JButton();
        jlTitulo = new javax.swing.JLabel();
        btnSalir = new javax.swing.JButton();
        jtxtNombreCuenta = new javax.swing.JTextField();
        jbNuevaCuenta = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jtxtDescripcion = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jtCuentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "idProducto", "Cuenta"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jtCuentas);
        if (jtCuentas.getColumnModel().getColumnCount() > 0) {
            jtCuentas.getColumnModel().getColumn(0).setMinWidth(0);
            jtCuentas.getColumnModel().getColumn(0).setPreferredWidth(0);
            jtCuentas.getColumnModel().getColumn(0).setMaxWidth(0);
            jtCuentas.getColumnModel().getColumn(1).setMinWidth(250);
            jtCuentas.getColumnModel().getColumn(1).setPreferredWidth(250);
            jtCuentas.getColumnModel().getColumn(1).setMaxWidth(250);
        }

        jtxtImporte.setDragEnabled(false);

        jbGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/restore_db.png"))); // NOI18N
        jbGuardar.setText("Registrar");
        jbGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbGuardarActionPerformed(evt);
            }
        });

        jlTitulo.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jlTitulo.setForeground(new java.awt.Color(153, 0, 51));
        jlTitulo.setText("titulo");

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/close_window.png"))); // NOI18N
        btnSalir.setText("Cerrar");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        jbNuevaCuenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Save-icon.png"))); // NOI18N
        jbNuevaCuenta.setText("Guardar");
        jbNuevaCuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbNuevaCuentaActionPerformed(evt);
            }
        });

        jLabel1.setForeground(new java.awt.Color(153, 0, 51));
        jLabel1.setText("Nueva Cuenta");

        jLabel2.setForeground(new java.awt.Color(153, 0, 51));
        jLabel2.setText("Importe");

        jLabel3.setForeground(new java.awt.Color(153, 0, 51));
        jLabel3.setText("Descripcion");

        jtxtDescripcion.setDragEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jbGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jtxtImporte))
                                    .addComponent(jLabel3)
                                    .addComponent(jtxtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(15, 15, 15))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jbNuevaCuenta)
                            .addComponent(jLabel1)
                            .addComponent(jtxtNombreCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jlTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtxtImporte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jbGuardar)
                                .addGap(121, 249, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnSalir))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtxtNombreCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbNuevaCuenta)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbGuardarActionPerformed
        registrarIngresoEgreso();
    }//GEN-LAST:event_jbGuardarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void jbNuevaCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbNuevaCuentaActionPerformed
        registrarNuevaCuenta();
    }//GEN-LAST:event_jbNuevaCuentaActionPerformed

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
            java.util.logging.Logger.getLogger(FormIngresosEgresos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormIngresosEgresos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormIngresosEgresos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormIngresosEgresos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormIngresosEgresos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbGuardar;
    private javax.swing.JButton jbNuevaCuenta;
    private javax.swing.JLabel jlTitulo;
    private javax.swing.JTable jtCuentas;
    private javax.swing.JTextField jtxtDescripcion;
    private javax.swing.JTextField jtxtImporte;
    private javax.swing.JTextField jtxtNombreCuenta;
    // End of variables declaration//GEN-END:variables

    private void registrarNuevaCuenta() {
        String nombreCuenta= null;
        String tipoCuenta = "I";
        
        if(idTipoTransaccion==4){
            tipoCuenta = "E";
        }
        
        if(jtxtNombreCuenta.getText().length() != 0){
            nombreCuenta = jtxtNombreCuenta.getText().toUpperCase();
            IngresoEgresoDAOImpl ie = new IngresoEgresoDAOImpl(connectionDB);
            ie.registrarNuevaCuenta(nombreCuenta, tipoCuenta);
            jtxtNombreCuenta.setText("");
            llenarTablaCuentas();
        }
        
    }

    private void validarCaja() {
        ArqueoDAOImpl arq = new ArqueoDAOImpl(connectionDB);
        
        int idArqueo = arq.getIdArqueo(idLugar, idTerminal, usuario);
        
        if(!arq.getEstadoCaja(idArqueo).equals("A")){
            JOptionPane.showMessageDialog(this, "No es posible realizar transacciones hasta que registre Caja Inicial");
            jbGuardar.setEnabled(false);
            jbNuevaCuenta.setEnabled(false);
        }
        else{
            jbGuardar.setEnabled(true);
            jbNuevaCuenta.setEnabled(true);
        }
    }
}
