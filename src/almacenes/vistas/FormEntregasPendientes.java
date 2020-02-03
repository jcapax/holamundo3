/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package almacenes.vistas;

import almacenes.conectorDB.DataBaseSqlite;
import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.DetalleTransaccion;
import almacenes.model.EntregaPendiente;
import almacenes.model.Temporal;
import almacenes.model.Transaccion;
import dao.DetalleTransaccionDAOImpl;
import dao.EntregasDAO;
import dao.EntregasDAOImpl;
import dao.TemporalDAO;
import dao.TemporalDAOImpl;
import dao.TransaccionDAO;
import dao.TransaccionDAOImpl;
import dao.reportes.ReporteCreditoDAO;
import dao.reportes.ReporteCreditoDAOImpl;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jcarlos.porcel
 */
public final class FormEntregasPendientes extends javax.swing.JFrame {

    private DatabaseUtils databaseUtils;
    private Connection connectionDB, connectionTemp;
    private DefaultTableModel dtm;    
    private int idTipoTransaccionEntrega;  // tipo de entrega q se ejecutara
    private byte idLugar;
    private byte idTerminal;
    private String usuario;
    private DecimalFormat df;
    
    TransaccionDAO transaccionDAO;
    EntregasDAO entregasDAO;
    ReporteCreditoDAO reporteCreditoDAO;
    TemporalDAO temporalDAO;
    Temporal productoTemporal;

    public FormEntregasPendientes(Connection connectionDB, 
            int idTipoTransaccionEntrega, byte idLugar, 
            byte idTerminal, String usuario) {        
        initComponents();
        
        this.setLocationRelativeTo(null);
        
        this.connectionDB = connectionDB;        
        this.idTipoTransaccionEntrega = idTipoTransaccionEntrega;
        this.idLugar = idLugar;
        this.idTerminal = idTerminal;
        this.usuario = usuario;
        
        headerTabla();
        abrirConexionTemp();
        iniciarVariables();
        llenarPendientesEntrega();        
    }
    
    public void abrirConexionTemp() {
        DataBaseSqlite sqLite = new DataBaseSqlite();
        connectionTemp = sqLite.conexion();
    }
    public void headerTabla() {
        Font f = new Font("Times New Roman", Font.BOLD, 14);

        jtPendientes.getTableHeader().setFont(f);
        jtPendientes.getTableHeader().setBackground(Color.orange);
        
        jtProductosPendientes.getTableHeader().setFont(f);
        jtProductosPendientes.getTableHeader().setBackground(Color.orange);
        
        jtPorEntregar.getTableHeader().setFont(f);
        jtPorEntregar.getTableHeader().setBackground(Color.orange);
    }
    
    private void iniciarVariables(){
        transaccionDAO = new TransaccionDAOImpl(connectionDB);
        entregasDAO = new EntregasDAOImpl(connectionDB); 
        temporalDAO = new TemporalDAOImpl(connectionTemp);
        
        temporalDAO.emptyEntregaTemporal();        
    }
    
    public int resgistrarTransaccion(int idTipoTransaccion) {
        TransaccionDAOImpl transDaoImpl = new TransaccionDAOImpl(connectionDB);

        int nroTipoTransaccion = 0;
        int tipoMovimineto = transDaoImpl.getTipoMovimiento(idTipoTransaccion);

        int idTransaccion = 0;
        String estado = "A";
        String descripcion = "Entrega de Productos";

        java.util.Date hoy = new java.util.Date();
        java.sql.Date fecha = new java.sql.Date(hoy.getTime());

        nroTipoTransaccion = transDaoImpl.getNroTipoTransaccion(idTipoTransaccion);

        Transaccion trans = new Transaccion(fecha, idTipoTransaccion, nroTipoTransaccion,
                idLugar, idTerminal, tipoMovimineto, estado, usuario, descripcion);

        idTransaccion = transDaoImpl.insertarTransaccion(trans);

        return idTransaccion;
    }
    
    public void registrarDetalleTransaccion(int idTransaccion) {

        DetalleTransaccionDAOImpl detTranDAOImpl = new DetalleTransaccionDAOImpl(connectionDB);
        ArrayList<DetalleTransaccion> detTrans = new ArrayList<DetalleTransaccion>();

        int idProducto = 0;
        int idUnidadMedida = 0;
        double cantidad = 0;
        double valorUnitario = 0;
        double valorSubTotal = 0.0;
        double descuento = 0.0;
        double valorTotal = 0;
        String tipoValor = "N";
        
        for(Temporal lista : temporalDAO.getListEntregaTemporal()){
            DetalleTransaccion dt = new DetalleTransaccion();
            
            idProducto = lista.getIdProducto();
            idUnidadMedida = lista.getIdUnidadMedida();
            cantidad = lista.getCantidad();
            
            dt.setIdTransaccion(idTransaccion);
            dt.setIdProducto(idProducto);
            dt.setIdUnidadMedida(idUnidadMedida);
            dt.setCantidad(cantidad);
            dt.setValorUnitario(valorUnitario);
            dt.setValorSubTotal(valorSubTotal);
            dt.setDescuento(descuento);
            dt.setValorTotal(valorTotal);
            dt.setTipoValor(tipoValor);

            detTrans.add(dt);
        }
        detTranDAOImpl.insertarDetalleTransaccion(detTrans);
    }
    
    public void registrarEntregaTransaccion(int idEntregaTransaccion, int idTransaccion) {
        TransaccionDAOImpl transDaoImpl = new TransaccionDAOImpl(connectionDB);
        transDaoImpl.insertarEntregaTransaccion(idTransaccion, idEntregaTransaccion);
    }
    
    public void llenarPendientesEntrega(){
        
        ArrayList<EntregaPendiente> lista = new ArrayList<>();
        
        lista = entregasDAO.getListaEntregasPendientes();
        
        dtm = (DefaultTableModel) this.jtPendientes.getModel();
        dtm.setRowCount(0);
        
        jtPendientes.setModel(dtm);
        
        Object[] fila = new Object[6];
        
        for (EntregaPendiente lista1 : lista) {
            fila[0] = lista1.getIdTransaccionCredito();
            fila[1] = lista1.getNroTipoTransaccion();
            fila[2] = lista1.getFecha();
            fila[3] = lista1.getNombreCompleto();
            fila[4] = lista1.getDireccion();
            fila[5] = lista1.getTelefonos();
            dtm.addRow(fila);
        }
        jtPendientes.setModel(dtm);
    }
    
    public void llenarProductosPendientes(int idTransaccion){
        ArrayList<EntregaPendiente> lista = new ArrayList<>();
        
        lista = entregasDAO.getProductosPendientes(idTransaccion);
        
        dtm = (DefaultTableModel) this.jtProductosPendientes.getModel();
        dtm.setRowCount(0);
        
        jtProductosPendientes.setModel(dtm);
        
        Object[] fila = new Object[8];
        
        for (EntregaPendiente lista1 : lista) {
            fila[0] = lista1.getIdTransaccionCredito();
            fila[1] = lista1.getIdProducto();
            fila[2] = lista1.getIdUnidadMedida();
            fila[3] = lista1.getNombreProducto();
            fila[4] = lista1.getNombreUnidadMedida();
            fila[5] = lista1.getCantidadCredido();
            fila[6] = lista1.getCantidadEntrega();
            fila[7] = lista1.getDiferencia();
            dtm.addRow(fila);
        }        
        jtProductosPendientes.setModel(dtm);
    }
    
    public void llenarProductosPorEntregar(){
        dtm = (DefaultTableModel) this.jtPorEntregar.getModel();
        dtm.setRowCount(0);
        
        jtPorEntregar.setModel(dtm);
        
        Object[] fila = new Object[3];
        
        for (Temporal lista : temporalDAO.getListEntregaTemporal()) {
            fila[0] = lista.getNombreProducto();
            fila[1] = lista.getSimbolo();
            fila[2] = lista.getCantidad();
            dtm.addRow(fila);
        }        
        jtPorEntregar.setModel(dtm);
    }
    
    public void seleccionarPendiente(int idTransaccion){        
        llenarProductosPendientes(idTransaccion); 
        temporalDAO.emptyEntregaTemporal();
    }
    
    private void seleccionarProductoPendiente(){
        
    }
    
    public FormEntregasPendientes() {
        initComponents();
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
        jtPendientes = new javax.swing.JTable();
        jlTituloFormulario = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtPorEntregar = new javax.swing.JTable();
        jlTituloFormulario1 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jbPorEntregar = new javax.swing.JButton();
        txtCantidad = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtProductosPendientes = new javax.swing.JTable();
        jlTituloFormulario2 = new javax.swing.JLabel();
        jbTransaccion = new javax.swing.JButton();
        jbSalir = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jtPendientes.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jtPendientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "idTransaccion", "Nro Trans.", "Fecha", "Nombre Cliente", "Direccion", "Telefonos"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtPendientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtPendientesMouseClicked(evt);
            }
        });
        jtPendientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtPendientesKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jtPendientes);
        if (jtPendientes.getColumnModel().getColumnCount() > 0) {
            jtPendientes.getColumnModel().getColumn(0).setMinWidth(0);
            jtPendientes.getColumnModel().getColumn(0).setPreferredWidth(0);
            jtPendientes.getColumnModel().getColumn(0).setMaxWidth(0);
            jtPendientes.getColumnModel().getColumn(1).setMinWidth(75);
            jtPendientes.getColumnModel().getColumn(1).setPreferredWidth(75);
            jtPendientes.getColumnModel().getColumn(1).setMaxWidth(75);
            jtPendientes.getColumnModel().getColumn(2).setMinWidth(80);
            jtPendientes.getColumnModel().getColumn(2).setPreferredWidth(80);
            jtPendientes.getColumnModel().getColumn(2).setMaxWidth(80);
            jtPendientes.getColumnModel().getColumn(5).setMinWidth(150);
            jtPendientes.getColumnModel().getColumn(5).setPreferredWidth(150);
            jtPendientes.getColumnModel().getColumn(5).setMaxWidth(150);
        }

        jlTituloFormulario.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        jlTituloFormulario.setForeground(new java.awt.Color(153, 0, 51));
        jlTituloFormulario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlTituloFormulario.setText("Productos Pendientes");

        jtPorEntregar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jtPorEntregar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre Producto", "Unidad Medida", "A Entregar"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtPorEntregar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtPorEntregarKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jtPorEntregar);
        if (jtPorEntregar.getColumnModel().getColumnCount() > 0) {
            jtPorEntregar.getColumnModel().getColumn(2).setMinWidth(80);
            jtPorEntregar.getColumnModel().getColumn(2).setPreferredWidth(80);
            jtPorEntregar.getColumnModel().getColumn(2).setMaxWidth(80);
        }

        jlTituloFormulario1.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        jlTituloFormulario1.setForeground(new java.awt.Color(153, 0, 51));
        jlTituloFormulario1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlTituloFormulario1.setText("Entregas Pendientes");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(153, 0, 51));
        jLabel1.setText("A Entregar");

        jbPorEntregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/flecha.png"))); // NOI18N
        jbPorEntregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbPorEntregarActionPerformed(evt);
            }
        });

        txtCantidad.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        txtCantidad.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jtProductosPendientes.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jtProductosPendientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "idTransaccion", "idProducto", "idUnidadMedida", "Nombre Producto", "Unidad Medida", "Cantidad", "Entregado", "Diferencia"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtProductosPendientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtProductosPendientesKeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(jtProductosPendientes);
        if (jtProductosPendientes.getColumnModel().getColumnCount() > 0) {
            jtProductosPendientes.getColumnModel().getColumn(0).setMinWidth(0);
            jtProductosPendientes.getColumnModel().getColumn(0).setPreferredWidth(0);
            jtProductosPendientes.getColumnModel().getColumn(0).setMaxWidth(0);
            jtProductosPendientes.getColumnModel().getColumn(1).setMinWidth(0);
            jtProductosPendientes.getColumnModel().getColumn(1).setPreferredWidth(0);
            jtProductosPendientes.getColumnModel().getColumn(1).setMaxWidth(0);
            jtProductosPendientes.getColumnModel().getColumn(2).setMinWidth(0);
            jtProductosPendientes.getColumnModel().getColumn(2).setPreferredWidth(0);
            jtProductosPendientes.getColumnModel().getColumn(2).setMaxWidth(0);
            jtProductosPendientes.getColumnModel().getColumn(4).setMinWidth(100);
            jtProductosPendientes.getColumnModel().getColumn(4).setPreferredWidth(100);
            jtProductosPendientes.getColumnModel().getColumn(4).setMaxWidth(100);
            jtProductosPendientes.getColumnModel().getColumn(5).setMinWidth(75);
            jtProductosPendientes.getColumnModel().getColumn(5).setPreferredWidth(75);
            jtProductosPendientes.getColumnModel().getColumn(5).setMaxWidth(75);
            jtProductosPendientes.getColumnModel().getColumn(6).setMinWidth(75);
            jtProductosPendientes.getColumnModel().getColumn(6).setPreferredWidth(75);
            jtProductosPendientes.getColumnModel().getColumn(6).setMaxWidth(75);
            jtProductosPendientes.getColumnModel().getColumn(7).setMinWidth(75);
            jtProductosPendientes.getColumnModel().getColumn(7).setPreferredWidth(75);
            jtProductosPendientes.getColumnModel().getColumn(7).setMaxWidth(75);
        }

        jlTituloFormulario2.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        jlTituloFormulario2.setForeground(new java.awt.Color(153, 0, 51));
        jlTituloFormulario2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlTituloFormulario2.setText("Por Entregar");

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jlTituloFormulario, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jlTituloFormulario2, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 1203, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(81, 81, 81)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtCantidad)
                            .addComponent(jbPorEntregar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jbTransaccion)
                                .addGap(236, 236, 236)
                                .addComponent(jbSalir))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(28, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(49, 49, 49)
                    .addComponent(jlTituloFormulario1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(30, 30, 30)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jlTituloFormulario)
                    .addComponent(jlTituloFormulario2))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbPorEntregar))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 34, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbSalir)
                    .addComponent(jbTransaccion))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(34, 34, 34)
                    .addComponent(jlTituloFormulario1)
                    .addContainerGap(606, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jtPendientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtPendientesMouseClicked
        int filSel = jtPendientes.getSelectedRow();
        int idTransaccion = (int) jtPendientes.getValueAt(filSel, 0);
        seleccionarPendiente(idTransaccion);
    }//GEN-LAST:event_jtPendientesMouseClicked

    private void jtPendientesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtPendientesKeyPressed
        int filSel = jtPendientes.getSelectedRow();
        int idTransaccion = (int) jtPendientes.getValueAt(filSel, 0);
        seleccionarPendiente(idTransaccion);
    }//GEN-LAST:event_jtPendientesKeyPressed

    private void jbTransaccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbTransaccionActionPerformed
        jbTransaccion.setEnabled(false);
        
        if(temporalDAO.getNroTemporalEntrega() > 0){
        
            int idTransaccion = resgistrarTransaccion(idTipoTransaccionEntrega);
            registrarDetalleTransaccion(idTransaccion);
            registrarEntregaTransaccion(idTransaccion, productoTemporal.getIdTransaccion());

            temporalDAO.emptyEntregaTemporal();
            llenarPendientesEntrega();
            llenarProductosPorEntregar();
            llenarProductosPendientes(0);

            transaccionDAO.crearTemporalEntrega();                    
            transaccionDAO.insertarEntregaTemporal(productoTemporal.getIdTransaccion(), idTransaccion);

            ReporteCreditoDAO reporteCreditoDAO = new ReporteCreditoDAOImpl(connectionDB, usuario);    
            reporteCreditoDAO.vistaPreviaEntregaProductosCredito(productoTemporal.getIdTransaccion(), idTransaccion);
            transaccionDAO.eliminarDatosTemporalEntrega();
        }else{
            JOptionPane.showMessageDialog( null, "Debe registrar algun producto para la entrega!!!" , "Error", JOptionPane.ERROR_MESSAGE);
        }
        jbTransaccion.setEnabled(true);
        
    }//GEN-LAST:event_jbTransaccionActionPerformed

    private void jbSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSalirActionPerformed
        dispose();
    }//GEN-LAST:event_jbSalirActionPerformed

    private void jtProductosPendientesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtProductosPendientesKeyPressed
        seleccionarProductoPendiente();
    }//GEN-LAST:event_jtProductosPendientesKeyPressed

    private void jbPorEntregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbPorEntregarActionPerformed
    
        int filSel = jtProductosPendientes.getSelectedRow();
        int idTransaccion = (int) jtProductosPendientes.getValueAt(filSel, 0);
        int idProducto =  (int) jtProductosPendientes.getValueAt(filSel, 1);
        int idUnidadMedida =  (int) jtProductosPendientes.getValueAt(filSel, 2);
        String nombreProducto = (String) jtProductosPendientes.getValueAt(filSel, 3);
        String nombreUnidadMedida = (String) jtProductosPendientes.getValueAt(filSel, 4);
        double diferencia =  (double) jtProductosPendientes.getValueAt(filSel, 7);
        double cantidad = Double.valueOf(txtCantidad.getText().toString());
        
        productoTemporal = new Temporal();
        
        productoTemporal.setIdTransaccion(idTransaccion);
        productoTemporal.setIdProducto(idProducto);
        productoTemporal.setIdUnidadMedida(idUnidadMedida);
        productoTemporal.setNombreProducto(nombreProducto);
        productoTemporal.setSimbolo(nombreUnidadMedida);
        if(cantidad > diferencia){
            productoTemporal.setCantidad(diferencia);
        }else{
            productoTemporal.setCantidad(cantidad);
        }
        
        temporalDAO.saveEntregaTemporal(productoTemporal);
        llenarProductosPorEntregar();
//        llenarProductosPendientes(0);
        
        txtCantidad.setText("");
    }//GEN-LAST:event_jbPorEntregarActionPerformed

    private void jtPorEntregarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtPorEntregarKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_DELETE){
            if(temporalDAO.getNroTemporalEntrega()>0){
                int filSel = jtPorEntregar.getSelectedRow();            
                String nombreProdcuto = (String) jtPorEntregar.getValueAt(filSel, 0);
                String nombreUnidadMedida = (String) jtPorEntregar.getValueAt(filSel, 1);

                temporalDAO.deleteProductoEntregaTemporal(nombreProdcuto, nombreUnidadMedida);
                llenarProductosPorEntregar();
            }
        }
    }//GEN-LAST:event_jtPorEntregarKeyPressed

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
            java.util.logging.Logger.getLogger(FormEntregasPendientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormEntregasPendientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormEntregasPendientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormEntregasPendientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormEntregasPendientes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton jbPorEntregar;
    private javax.swing.JToggleButton jbSalir;
    private javax.swing.JButton jbTransaccion;
    private javax.swing.JLabel jlTituloFormulario;
    private javax.swing.JLabel jlTituloFormulario1;
    private javax.swing.JLabel jlTituloFormulario2;
    private javax.swing.JTable jtPendientes;
    private javax.swing.JTable jtPorEntregar;
    private javax.swing.JTable jtProductosPendientes;
    private javax.swing.JTextField txtCantidad;
    // End of variables declaration//GEN-END:variables
}
