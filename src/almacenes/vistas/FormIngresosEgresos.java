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
import almacenes.model.ListaIngresosEgresos;
import almacenes.model.Transaccion;
import dao.ArqueoDAO;
import dao.ArqueoDAOImpl;
import dao.CajaDAO;
import dao.CajaDAOImpl;
import dao.DetalleTransaccionDAO;
import dao.DetalleTransaccionDAOImpl;
import dao.IngresoEgresoDAO;
import dao.IngresoEgresoDAOImpl;
import dao.TransaccionDAO;
import dao.TransaccionDAOImpl;
import dao.reportes.ReporteIngresosEgresosDAO;
import dao.reportes.ReportesIngresosEgresosDAOImpl;
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
    private byte idTerminal;
    private String usuario;
    private int idTipoTransaccion;
    private IngresoEgresoDAO ingresoEgresoDAO;
    private DetalleTransaccionDAO detalleTransaccionDAO;
    private TransaccionDAO transaccionDAO;
    private CajaDAO cajaDAO;
    private ArqueoDAO arqueoDAO;
    
    
    public FormIngresosEgresos() {
        initComponents();
    }
    
    public void headerTabla(){
        Font f = new Font("Times New Roman", Font.BOLD, 13);
        
        jtCuentas.getTableHeader().setFont(f);
        jtCuentas.getTableHeader().setBackground(Color.orange);
        
        jtListaIngresosEgresos.getTableHeader().setFont(f);
        jtListaIngresosEgresos.getTableHeader().setBackground(Color.orange);
    }
    
    public FormIngresosEgresos(Connection connectionDB, String tipoCuenta, 
                byte idLugar, String usuario, int idTipoTransaccion, byte idTerminal) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.databaseUtils = new DatabaseUtils();
        this.connectionDB = connectionDB;
        this.tipoCuenta = tipoCuenta;
        this.idLugar = idLugar;
        this.usuario = usuario;
        this.idTipoTransaccion = idTipoTransaccion;
        this.idTerminal = idTerminal;
        
        headerTabla();
        
        if(idTipoTransaccion == 4){
            jlTitulo.setText("REGISTRO EGRESOS");
        }
        else{
            jlTitulo.setText("REGISTRO INGRESOS");
        }
        
        ingresoEgresoDAO = new IngresoEgresoDAOImpl(connectionDB);
        transaccionDAO = new TransaccionDAOImpl(connectionDB);
        detalleTransaccionDAO = new DetalleTransaccionDAOImpl(connectionDB);
        cajaDAO = new CajaDAOImpl(connectionDB);
        arqueoDAO = new ArqueoDAOImpl(connectionDB);
        
        llenarTablaCuentas();
        validarCaja();
    }
    
    public void registrarDetalleTransaccion(int idTransaccion){
        
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

        detalleTransaccionDAO.insertarDetalleTransaccion(detTrans);
        
    }

    public int resgistrarTransaccion(){
        
        int nroTipoTransaccion = 0;
        int tipoMovimineto = transaccionDAO.getTipoMovimiento(idTipoTransaccion);
        int idTransaccion = 0;
        String estado = "A";
        String descripcion = "";
//        String usuario = "SYS";
        java.util.Date hoy = new java.util.Date();
        java.sql.Date fecha = new java.sql.Date(hoy.getTime());
        
        nroTipoTransaccion = transaccionDAO.getNroTipoTransaccion(idTipoTransaccion);
        
        descripcion = jtxtDescripcion.getText().toUpperCase();
        
        Transaccion trans = new Transaccion(fecha, idTipoTransaccion, nroTipoTransaccion, 
                                idLugar, idTerminal, tipoMovimineto, estado, usuario, descripcion);
        
        idTransaccion = transaccionDAO.insertarTransaccion(trans);
        
        return idTransaccion;
    }

    public void llenarTablaCuentas(){

        ArrayList<IngresoEgreso> r = new ArrayList<>();
        
        r = ingresoEgresoDAO.getListaCuentasIngresoEgreso(tipoCuenta);
        
        dtm = (DefaultTableModel) this.jtCuentas.getModel();
        dtm.setRowCount(0);
        
        jtCuentas.setModel(dtm);
        
        Object[] fila = new Object[2];
        
        for(int i=0; i< r.size(); i++){
            fila[0] = r.get(i).getIdProducto();
            fila[1] = r.get(i).getDescripcion();
            
            dtm.addRow(fila);
        }
        
        jtCuentas.setModel(dtm);
    }
    
    public void seleccionarCuenta(){
        String cuenta;
        int fila = jtCuentas.getSelectedRow();
        
        cuenta = jtCuentas.getValueAt(fila, 1).toString();
        jtxtCuenta.setText(cuenta);
        
    }
    
    public int getIdTransaccionSeleccion(){
        int idTransaccion = 0;
        int fila = jtListaIngresosEgresos.getSelectedRow();
        
        idTransaccion =Integer.parseInt(jtListaIngresosEgresos.getValueAt(fila, 0).toString());
        
        return idTransaccion;
    }

    public void llenarIngresosEgresos(){

        ArrayList<ListaIngresosEgresos> listaIE = new ArrayList<>();
        
        listaIE = ingresoEgresoDAO.getListIngresosEgresosFechas(
                idTipoTransaccion, 
                new Date(jDateFechaInicio.getDate().getTime()), 
                new Date(jDateFechaFin.getDate().getTime()));
        
        dtm = (DefaultTableModel) this.jtListaIngresosEgresos.getModel();
        dtm.setRowCount(0);
        
        jtListaIngresosEgresos.setModel(dtm);
        
        Object[] fila = new Object[5];
        
        for(ListaIngresosEgresos lie : listaIE){
            fila[0] = lie.getId();
            fila[1] = lie.getFecha();
            fila[2] = lie.getCuenta();
            fila[3] = lie.getDescripcionIngresoEgreso();
            fila[4] = lie.getValorTotal();
            
            dtm.addRow(fila);
        }
        
        jtListaIngresosEgresos.setModel(dtm);
    }

    public void registrarCaja(int idTransaccion){
        String estado = "A";

        Date fecha = null;
        int nroCobro = 0, nroPago = 0;
        double importe = 0;
        

        fecha = transaccionDAO.getFechaTransaccion(idTransaccion);
        importe = transaccionDAO.getValorTotalTransaccion(idTransaccion);
        
        Caja caja = new Caja();
        caja.setEstado(estado);
        caja.setFecha(fecha);
        caja.setIdTransaccion(idTransaccion);
        caja.setImporte(importe);
        caja.setNroCobro(nroCobro);
        caja.setNroPago(nroPago);
        caja.setUsuario(usuario);
        
        cajaDAO.insertarCaja(caja);
                
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
        jbBuscar = new javax.swing.JButton();
        jDateFechaInicio = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jtxtCuenta = new javax.swing.JTextField();
        jDateFechaFin = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtListaIngresosEgresos = new javax.swing.JTable();
        jbEliminar = new javax.swing.JButton();
        jbImprimir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jtCuentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "idProducto", "Cuenta"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtCuentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtCuentasMouseClicked(evt);
            }
        });
        jtCuentas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtCuentasKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtCuentasKeyReleased(evt);
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

        jbGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/restore_db.png"))); // NOI18N
        jbGuardar.setText("Registrar");
        jbGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbGuardarActionPerformed(evt);
            }
        });

        jlTitulo.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        jlTitulo.setForeground(new java.awt.Color(153, 0, 51));
        jlTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
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

        jbBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Search-icon.png"))); // NOI18N
        jbBuscar.setText("Buscar");
        jbBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbBuscarActionPerformed(evt);
            }
        });

        jDateFechaInicio.setDateFormatString("dd/MM/yyyy");

        jLabel4.setForeground(new java.awt.Color(153, 0, 51));
        jLabel4.setText("Cuenta");

        jtxtCuenta.setEditable(false);

        jDateFechaFin.setDateFormatString("dd/MM/yyyy");

        jLabel5.setForeground(new java.awt.Color(153, 0, 51));
        jLabel5.setText("Entre");

        jLabel6.setForeground(new java.awt.Color(153, 0, 51));
        jLabel6.setText("Y");

        jtListaIngresosEgresos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Fecha", "Cuenta", "Descripcion", "Valor Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jtListaIngresosEgresos);
        if (jtListaIngresosEgresos.getColumnModel().getColumnCount() > 0) {
            jtListaIngresosEgresos.getColumnModel().getColumn(0).setMinWidth(0);
            jtListaIngresosEgresos.getColumnModel().getColumn(0).setPreferredWidth(0);
            jtListaIngresosEgresos.getColumnModel().getColumn(0).setMaxWidth(0);
            jtListaIngresosEgresos.getColumnModel().getColumn(1).setMinWidth(85);
            jtListaIngresosEgresos.getColumnModel().getColumn(1).setPreferredWidth(85);
            jtListaIngresosEgresos.getColumnModel().getColumn(1).setMaxWidth(85);
            jtListaIngresosEgresos.getColumnModel().getColumn(2).setMinWidth(170);
            jtListaIngresosEgresos.getColumnModel().getColumn(2).setPreferredWidth(170);
            jtListaIngresosEgresos.getColumnModel().getColumn(2).setMaxWidth(170);
            jtListaIngresosEgresos.getColumnModel().getColumn(4).setMinWidth(100);
            jtListaIngresosEgresos.getColumnModel().getColumn(4).setPreferredWidth(100);
            jtListaIngresosEgresos.getColumnModel().getColumn(4).setMaxWidth(100);
        }

        jbEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/trash_icon.png"))); // NOI18N
        jbEliminar.setText("Eliminar");
        jbEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbEliminarActionPerformed(evt);
            }
        });

        jbImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/print.png"))); // NOI18N
        jbImprimir.setText("Imprimir");
        jbImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbImprimirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(jbNuevaCuenta))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jbEliminar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jtxtCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jtxtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jtxtImporte, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jbGuardar))))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtxtNombreCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jDateFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jDateFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jbBuscar)
                                .addGap(18, 18, 18)
                                .addComponent(jbImprimir)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jDateFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jbBuscar)
                        .addComponent(jbImprimir))
                    .addComponent(jDateFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtxtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxtImporte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbGuardar)
                            .addComponent(jtxtCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtxtNombreCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbNuevaCuenta)
                    .addComponent(btnSalir)
                    .addComponent(jbEliminar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbGuardarActionPerformed
        jDateFechaInicio.setDate(new java.util.Date());
        jDateFechaFin.setDate(new java.util.Date());        
        registrarIngresoEgreso();
        llenarIngresosEgresos();
        jtxtCuenta.setText("");
    }//GEN-LAST:event_jbGuardarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void jbNuevaCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbNuevaCuentaActionPerformed
        registrarNuevaCuenta();
    }//GEN-LAST:event_jbNuevaCuentaActionPerformed

    private void jbBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbBuscarActionPerformed
        llenarIngresosEgresos();        
    }//GEN-LAST:event_jbBuscarActionPerformed

    private void jbEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbEliminarActionPerformed
        int idTransaccion = getIdTransaccionSeleccion();
        if(ingresoEgresoDAO.isIngresoEgresoAbierto(idTransaccion)){
            ingresoEgresoDAO.eliminarIngresoEgreso(idTransaccion);
            llenarIngresosEgresos();
        }else{
            JOptionPane.showMessageDialog(this, "El registro ya ha sido confirmado, no podrá ser eliminado!!!");
        }
    }//GEN-LAST:event_jbEliminarActionPerformed

    private void jtCuentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtCuentasMouseClicked
        seleccionarCuenta();
    }//GEN-LAST:event_jtCuentasMouseClicked

    private void jtCuentasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtCuentasKeyPressed
        seleccionarCuenta();
    }//GEN-LAST:event_jtCuentasKeyPressed

    private void jtCuentasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtCuentasKeyReleased
        seleccionarCuenta();
    }//GEN-LAST:event_jtCuentasKeyReleased

    private void jbImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbImprimirActionPerformed
        IngresoEgresoDAO ieDAO = new IngresoEgresoDAOImpl(connectionDB);
        ieDAO.createTemporalIngresosEgresos();
        
        String fechaInicio = String.valueOf(new Date(jDateFechaInicio.getDate().getTime()));
        String fechaFinal = String.valueOf(new Date(jDateFechaFin.getDate().getTime()));
        ieDAO.insertarIngresosEgresos(idTipoTransaccion, 
                fechaInicio, fechaFinal, usuario);
        ReporteIngresosEgresosDAO reporte = new ReportesIngresosEgresosDAOImpl(connectionDB, usuario);
        reporte.vistaPreviaReporte(fechaInicio, fechaFinal);
    }//GEN-LAST:event_jbImprimirActionPerformed

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
    private com.toedter.calendar.JDateChooser jDateFechaFin;
    private com.toedter.calendar.JDateChooser jDateFechaInicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbBuscar;
    private javax.swing.JButton jbEliminar;
    private javax.swing.JButton jbGuardar;
    private javax.swing.JButton jbImprimir;
    private javax.swing.JButton jbNuevaCuenta;
    private javax.swing.JLabel jlTitulo;
    private javax.swing.JTable jtCuentas;
    private javax.swing.JTable jtListaIngresosEgresos;
    private javax.swing.JTextField jtxtCuenta;
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
            ingresoEgresoDAO.registrarNuevaCuenta(nombreCuenta, tipoCuenta);
            jtxtNombreCuenta.setText("");
            llenarTablaCuentas();
        }
        
    }

    private void validarCaja() {
        
        int idArqueo = arqueoDAO.getIdArqueo(idLugar, idTerminal, usuario);
        
        if(!arqueoDAO.getEstadoCaja(idArqueo).equals("A")){
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
