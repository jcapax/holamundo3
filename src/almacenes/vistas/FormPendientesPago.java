/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package almacenes.vistas;

import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.Caja;
import almacenes.model.DetalleTransaccion;
import almacenes.model.PendientePago;
import dao.CajaDAOImpl;
import dao.CreditoDAO;
import dao.CreditoDAOImpl;
import dao.DetalleTransaccionDAOImpl;
import dao.TransaccionDAOImpl;
import dao.reportes.ReporteCreditoDAO;
import dao.reportes.ReporteCreditoDAOImpl;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author jcarlos.porcel
 */
public class FormPendientesPago extends javax.swing.JFrame {

    private DatabaseUtils databaseUtils;
    private Connection connectionDB;
    DefaultTableModel dtm;
    int idTipoTransaccion;
    String usuario;
    private DecimalFormat df;
    
    public FormPendientesPago(Connection connectionDB, int idTipoTransaccion, String usuario) {
        initComponents();
        this.setLocationRelativeTo(null);
        df = new DecimalFormat("###,##0.00");
        this.databaseUtils = new DatabaseUtils();
        this.connectionDB = connectionDB;
        this.idTipoTransaccion = idTipoTransaccion;
        this.usuario = usuario;
        headerTabla();        
        iniciarComponentes();
        llenarTablaPendientesPago();
    }
    
    public FormPendientesPago() {
        initComponents();
    }
    
    public void headerTabla(){
        Font f = new Font("Times New Roman", Font.BOLD, 13);
        
        jtPendientePago.getTableHeader().setFont(f);
        jtPendientePago.getTableHeader().setBackground(Color.orange);
        
        jtHistorialCaja.getTableHeader().setFont(f);
        jtHistorialCaja.getTableHeader().setBackground(Color.orange);
        
        jtDetalleTransaccion.getTableHeader().setFont(f);
        jtDetalleTransaccion.getTableHeader().setBackground(Color.orange);
    }
    
    public void llenarTablaPendientesPago(){
        CreditoDAO rub = new CreditoDAOImpl(connectionDB);
        
        ArrayList<PendientePago> r = new ArrayList<PendientePago>();
        
        r = rub.getListaPendientesPago(idTipoTransaccion);
        
        dtm = (DefaultTableModel) this.jtPendientePago.getModel();
        dtm.setRowCount(0);
        
        jtPendientePago.setModel(dtm);
        
        Object[] fila = new Object[11];
        
        for(int i=0; i< r.size(); i++){
            
            fila[0] = r.get(i).getIdTransaccion();
            fila[1] = r.get(i).getFecha();
            fila[2] = r.get(i).getIdTipoTransaccion();
            fila[3] = r.get(i).getNroTipoTransaccion();
            fila[4] = df.format(r.get(i).getImporteCaja());
            fila[5] = df.format(r.get(i).getValorTotal());
            fila[6] = df.format(r.get(i).getDiferencia());
            fila[7] = r.get(i).getNombreCompleto();
            fila[8] = r.get(i).getRazonSocial();
            fila[9] = r.get(i).getTelefonos();
            fila[10] = r.get(i).getDetalle();           
            
            dtm.addRow(fila);
        }
        
        TableColumnModel columnModel = jtPendientePago.getColumnModel();
        
        TableColumn importeCaja = columnModel.getColumn(4);
        TableColumn valorTotal = columnModel.getColumn(5);
        TableColumn diferencia = columnModel.getColumn(6);
        
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(jLabel1.RIGHT);
        
        importeCaja.setCellRenderer(renderer);
        valorTotal.setCellRenderer(renderer);
        diferencia.setCellRenderer(renderer);
        
        jtPendientePago.setModel(dtm);
    }
    
    public void llenarHistorialCaja(int idTransaccion){
        double total = 0;
        double saldo = 0;
        CreditoDAO rub = new CreditoDAOImpl(connectionDB);
        
        ArrayList<Caja> r = new ArrayList<Caja>();
        
        r = rub.getHistorialPagos(idTransaccion);
        
        dtm = (DefaultTableModel) this.jtHistorialCaja.getModel();
        dtm.setRowCount(0);
        
        jtHistorialCaja.setModel(dtm);
        
        Object[] fila = new Object[2];
        for(int i=0; i<r.size(); i++) {
            total = total + r.get(i).getImporte();
            
            fila[0] = r.get(i).getFecha();
            fila[1] = r.get(i).getImporte();
            
            dtm.addRow(fila);
        }        
        TableColumnModel columnModel = jtHistorialCaja.getColumnModel();
        
        TableColumn importeCaja = columnModel.getColumn(1);
        
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(jLabel1.RIGHT);
        
        importeCaja.setCellRenderer(renderer);
        
        jtHistorialCaja.setModel(dtm);
        jtxtImporteTotalEnCaja.setText(String.valueOf(df.format(total))); 
        jtxtSaldo.setText(df.format(rub.getSaldoTransaccion(idTransaccion)).toString());
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
        
        jtxtTotalTransaccion.setText(df.format(importeTotal).toString());

        TableColumnModel columnModel = jtDetalleTransaccion.getColumnModel();
        
        TableColumn colImpTotal = columnModel.getColumn(3);
        
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(jLabel1.RIGHT);
        
        colImpTotal.setCellRenderer(renderer);
        
        jtDetalleTransaccion.setModel(dtm);
    }
    
    public int getIdTransaccionSeleccion(){
        int idTransaccion = 0;
        int fila = jtPendientePago.getSelectedRow();
        
        idTransaccion =Integer.parseInt(jtPendientePago.getValueAt(fila, 0).toString());
        
        return idTransaccion;
    }
    
    public void registrarCaja(int idTransaccion) {
        String estado = "A";
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");        
        Date fecha = new Date(Calendar.getInstance().getTime().getTime());
        
        int nroCobro = 0, nroPago = 0;
        double importe = 0;

        TransaccionDAOImpl trans = new TransaccionDAOImpl(connectionDB);
        //fecha = trans.getFechaTransaccion(idTransaccion);
        importe = Double.parseDouble(jtxtImporte.getText());

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
        
        llenarTablaPendientesPago();
        llenarHistorialCaja(0);
        llenarDetalleTransaccion(0);
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
        jtPendientePago = new javax.swing.JTable();
        jlTituloFormulario = new javax.swing.JLabel();
        jlTituloFormulario1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtHistorialCaja = new javax.swing.JTable();
        jpanelPago = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jtxtImporte = new javax.swing.JTextField();
        jbPagar = new javax.swing.JButton();
        Total1 = new javax.swing.JLabel();
        jtxtSaldo = new javax.swing.JTextField();
        Total = new javax.swing.JLabel();
        jtxtImporteTotalEnCaja = new javax.swing.JTextField();
        jbSalir = new javax.swing.JButton();
        jlTituloFormulario2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtDetalleTransaccion = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jtxtTotalTransaccion = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setFont(new java.awt.Font("Agency FB", 1, 12)); // NOI18N

        jtPendientePago.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "idTransaccion", "Fecha", "idTipoTransaccion", "nroTipoTransaccion", "Caja", "Total", "Saldo", "Nombre Completo", "Razon Social", "Teléfonos", "Detalle"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, true, true, true, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtPendientePago.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtPendientePagoMouseClicked(evt);
            }
        });
        jtPendientePago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtPendientePagoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtPendientePagoKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jtPendientePago);
        if (jtPendientePago.getColumnModel().getColumnCount() > 0) {
            jtPendientePago.getColumnModel().getColumn(0).setMinWidth(0);
            jtPendientePago.getColumnModel().getColumn(0).setPreferredWidth(0);
            jtPendientePago.getColumnModel().getColumn(0).setMaxWidth(0);
            jtPendientePago.getColumnModel().getColumn(1).setMinWidth(75);
            jtPendientePago.getColumnModel().getColumn(1).setPreferredWidth(75);
            jtPendientePago.getColumnModel().getColumn(1).setMaxWidth(75);
            jtPendientePago.getColumnModel().getColumn(2).setMinWidth(0);
            jtPendientePago.getColumnModel().getColumn(2).setPreferredWidth(0);
            jtPendientePago.getColumnModel().getColumn(2).setMaxWidth(0);
            jtPendientePago.getColumnModel().getColumn(3).setMinWidth(0);
            jtPendientePago.getColumnModel().getColumn(3).setPreferredWidth(0);
            jtPendientePago.getColumnModel().getColumn(3).setMaxWidth(0);
            jtPendientePago.getColumnModel().getColumn(4).setMinWidth(70);
            jtPendientePago.getColumnModel().getColumn(4).setPreferredWidth(70);
            jtPendientePago.getColumnModel().getColumn(4).setMaxWidth(70);
            jtPendientePago.getColumnModel().getColumn(5).setMinWidth(70);
            jtPendientePago.getColumnModel().getColumn(5).setPreferredWidth(70);
            jtPendientePago.getColumnModel().getColumn(5).setMaxWidth(70);
            jtPendientePago.getColumnModel().getColumn(6).setMinWidth(70);
            jtPendientePago.getColumnModel().getColumn(6).setPreferredWidth(70);
            jtPendientePago.getColumnModel().getColumn(6).setMaxWidth(70);
            jtPendientePago.getColumnModel().getColumn(9).setMinWidth(100);
            jtPendientePago.getColumnModel().getColumn(9).setPreferredWidth(100);
            jtPendientePago.getColumnModel().getColumn(9).setMaxWidth(100);
        }

        jlTituloFormulario.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        jlTituloFormulario.setForeground(new java.awt.Color(153, 0, 51));
        jlTituloFormulario.setText("titulo");

        jlTituloFormulario1.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        jlTituloFormulario1.setForeground(new java.awt.Color(153, 0, 51));
        jlTituloFormulario1.setText("Historial Caja");

        jtHistorialCaja.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null}
            },
            new String [] {
                "Fecha", "Importe"
            }
        ));
        jScrollPane2.setViewportView(jtHistorialCaja);

        jpanelPago.setForeground(new java.awt.Color(255, 255, 204));
        jpanelPago.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(153, 0, 51));
        jLabel1.setText("Importe Pago");

        jtxtImporte.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxtImporte.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jbPagar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/carrar_caja.png"))); // NOI18N
        jbPagar.setText("Pagar");
        jbPagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbPagarActionPerformed(evt);
            }
        });

        Total1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Total1.setForeground(new java.awt.Color(153, 0, 51));
        Total1.setText("Saldo");

        jtxtSaldo.setEditable(false);
        jtxtSaldo.setBackground(new java.awt.Color(255, 255, 51));

        javax.swing.GroupLayout jpanelPagoLayout = new javax.swing.GroupLayout(jpanelPago);
        jpanelPago.setLayout(jpanelPagoLayout);
        jpanelPagoLayout.setHorizontalGroup(
            jpanelPagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelPagoLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jpanelPagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtxtSaldo, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Total1)
                    .addGroup(jpanelPagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jtxtImporte, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbPagar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jpanelPagoLayout.setVerticalGroup(
            jpanelPagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelPagoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Total1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtxtSaldo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jtxtImporte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbPagar)
                .addContainerGap())
        );

        Total.setForeground(new java.awt.Color(153, 0, 51));
        Total.setText("Total Cancelado");

        jtxtImporteTotalEnCaja.setEditable(false);

        jbSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/close_window.png"))); // NOI18N
        jbSalir.setText("Salir");
        jbSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSalirActionPerformed(evt);
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
        ));
        jScrollPane3.setViewportView(jtDetalleTransaccion);
        if (jtDetalleTransaccion.getColumnModel().getColumnCount() > 0) {
            jtDetalleTransaccion.getColumnModel().getColumn(1).setMinWidth(50);
            jtDetalleTransaccion.getColumnModel().getColumn(1).setPreferredWidth(50);
            jtDetalleTransaccion.getColumnModel().getColumn(1).setMaxWidth(50);
            jtDetalleTransaccion.getColumnModel().getColumn(2).setMinWidth(65);
            jtDetalleTransaccion.getColumnModel().getColumn(2).setPreferredWidth(65);
            jtDetalleTransaccion.getColumnModel().getColumn(2).setMaxWidth(65);
            jtDetalleTransaccion.getColumnModel().getColumn(3).setMinWidth(85);
            jtDetalleTransaccion.getColumnModel().getColumn(3).setPreferredWidth(85);
            jtDetalleTransaccion.getColumnModel().getColumn(3).setMaxWidth(85);
        }

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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlTituloFormulario2)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtxtTotalTransaccion, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jlTituloFormulario1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addComponent(Total)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jtxtImporteTotalEnCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addComponent(jpanelPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(381, 381, 381)
                        .addComponent(jlTituloFormulario))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1046, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlTituloFormulario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlTituloFormulario1)
                    .addComponent(jlTituloFormulario2))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jpanelPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtxtTotalTransaccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(Total)
                            .addComponent(jtxtImporteTotalEnCaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbSalir)
                        .addGap(22, 22, 22))))
        );

        jpanelPago.getAccessibleContext().setAccessibleName("");
        jpanelPago.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jtPendientePagoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtPendientePagoMouseClicked
        int idTransaccion = getIdTransaccionSeleccion();
        llenarHistorialCaja(idTransaccion);
        llenarDetalleTransaccion(idTransaccion);
    }//GEN-LAST:event_jtPendientePagoMouseClicked

    private void jtPendientePagoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtPendientePagoKeyReleased
        int idTransaccion = getIdTransaccionSeleccion();
        llenarHistorialCaja(idTransaccion);
        llenarDetalleTransaccion(idTransaccion);
    }//GEN-LAST:event_jtPendientePagoKeyReleased

    private void jbPagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbPagarActionPerformed
        boolean aux = true;
        
        if (jtxtImporte.getText().length() == 0) {
            JOptionPane.showMessageDialog(this, "Registrar el importe!!!");
            jtxtImporte.setText("");
            jtxtImporte.requestFocus();
            aux = false;
            return;
        }

        try {
            Double x = Double.parseDouble(jtxtImporte.getText().replace(",", ""));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "El importe registrado no es valido!!!");
            jtxtImporte.setText("");
            jtxtImporte.requestFocus();
            aux = false;
            return;
        }

        if (jtxtImporte.getText().equals("0")) {
            JOptionPane.showMessageDialog(this, "El importe registrado no es valido!!!");
            jtxtImporte.setText("");
            jtxtImporte.requestFocus();
            aux = false;
            return;
        }

        if (Double.parseDouble((jtxtImporte.getText().replace(",", "")))<0.0) {
            JOptionPane.showMessageDialog(this, "El importe registrado no es valido!!!");
            jtxtImporte.setText("");
            jtxtImporte.requestFocus();
            aux = false;
            return;
        }
        
        if (Double.parseDouble(jtxtImporte.getText()) > Double.parseDouble(jtxtSaldo.getText().replace(",", ""))) {
            JOptionPane.showMessageDialog(this, "El Importe Registrado no debe ser mayor al Saldo!!!");
            jtxtImporte.setText("");
            jtxtImporte.requestFocus();
            aux = false;
            return;
        }

        if(aux){
            int idTransaccion = getIdTransaccionSeleccion();
            registrarCaja(idTransaccion);
            ReporteCreditoDAO rep = new ReporteCreditoDAOImpl(connectionDB, usuario);
            rep.vistaPreviaPagoCredito(idTransaccion);
        }
        
        jtxtImporte.setText("");
    }//GEN-LAST:event_jbPagarActionPerformed

    private void jtPendientePagoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtPendientePagoKeyPressed
        int idTransaccion = getIdTransaccionSeleccion();
        llenarHistorialCaja(idTransaccion);
        llenarDetalleTransaccion(idTransaccion);
    }//GEN-LAST:event_jtPendientePagoKeyPressed

    private void jbSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSalirActionPerformed
        dispose();
    }//GEN-LAST:event_jbSalirActionPerformed

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
            java.util.logging.Logger.getLogger(FormPendientesPago.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormPendientesPago.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormPendientesPago.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormPendientesPago.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormPendientesPago().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Total;
    private javax.swing.JLabel Total1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton jbPagar;
    private javax.swing.JButton jbSalir;
    private javax.swing.JLabel jlTituloFormulario;
    private javax.swing.JLabel jlTituloFormulario1;
    private javax.swing.JLabel jlTituloFormulario2;
    private javax.swing.JPanel jpanelPago;
    private javax.swing.JTable jtDetalleTransaccion;
    private javax.swing.JTable jtHistorialCaja;
    private javax.swing.JTable jtPendientePago;
    private javax.swing.JTextField jtxtImporte;
    private javax.swing.JTextField jtxtImporteTotalEnCaja;
    private javax.swing.JTextField jtxtSaldo;
    private javax.swing.JTextField jtxtTotalTransaccion;
    // End of variables declaration//GEN-END:variables

    private void iniciarComponentes() {
        if(idTipoTransaccion == 3){
            jlTituloFormulario.setText("PENDIENTES DE COBRO");
        }
        jpanelPago.setBackground(Color.LIGHT_GRAY);
        jtxtImporteTotalEnCaja.setHorizontalAlignment(JTextField.RIGHT);
        jtxtSaldo.setHorizontalAlignment(JTextField.RIGHT);
    }
}
