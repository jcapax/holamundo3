/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package almacenes.vistas;

import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.UnidadProducto;
import dao.UnidadMedidaDAOImpl;
import dao.UnidadProductoDAOImlp;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jcapax
 */
public class FormUnidadProducto extends javax.swing.JFrame {

    private DatabaseUtils databaseUtils;
    private Connection connectionDB;
    DefaultTableModel dtm;
    String nombreProducto;
    String usuario;
    
    public FormUnidadProducto() {
        initComponents();

    }

    public FormUnidadProducto(Connection connectionDB, int idProducto, String nombreProducto, String usuario) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.databaseUtils = new DatabaseUtils();
        this.connectionDB = connectionDB;
        this.nombreProducto = nombreProducto;
        this.usuario = usuario;
        
        headerTabla();
        
        llenarTablaUnidadProduco(idProducto);
        llenarComboUnidadMedida();
//        llenarComboUnidadMedidaPrincipal();
        jlIdProducto.setText(String.valueOf(idProducto));
        jlNombreProducto.setText(nombreProducto);

        byte x = 2;
        botones(x);
        
        jlIdProducto.setVisible(false);
        jlIdUnidadMedida.setVisible(false);
        jlIdUnidadMedidaPrincipal.setVisible(false);
        jlEdicion.setVisible(false);
        deshabilitarComponentes();
    }
    
    public void habilitarComponentes(){
        boolean aux = true;
        jcUnidadMedida.setEnabled(aux);
//        jcUnidadMedidaPrincipal.setEnabled(aux);
        jtxtPrecioCompra.setEnabled(aux);
        jtxtPrecioVenta.setEnabled(aux);
//        jtxtPrecioVentaAumento.setEnabled(aux);
//        jtxtPrecioVentaRebaja.setEnabled(aux);
        jtxtStockMinimo.setEnabled(aux);  
        //jPanelComponentes.setEnabled(aux);
        jtxtGarantiaMses.setEnabled(aux);
        jtxtCodigoAdjunto.setEnabled(aux);
    }
    
    public void deshabilitarComponentes(){
        boolean aux = false;
        jcUnidadMedida.setEnabled(aux);
//        jcUnidadMedidaPrincipal.setEnabled(aux);
        jtxtPrecioCompra.setEnabled(aux);
        jtxtPrecioVenta.setEnabled(aux);
//        jtxtPrecioVentaAumento.setEnabled(aux);
//        jtxtPrecioVentaRebaja.setEnabled(aux);
        jtxtStockMinimo.setEnabled(aux); 
        //jPanelComponentes.setEnabled(aux);
        jtxtGarantiaMses.setEnabled(aux);
        jtxtCodigoAdjunto.setEnabled(aux);
    }
    
    public void headerTabla(){
        Font f = new Font("Times New Roman", Font.BOLD, 13);
        
        jtUnidadProducto.getTableHeader().setFont(f);
        jtUnidadProducto.getTableHeader().setBackground(Color.orange);
    }

    public void llenarComboUnidadMedida() {
        String sel = "Sel";

        jcUnidadMedida.removeAllItems();
        jcUnidadMedida.addItem(sel);

        UnidadMedidaDAOImpl unid = new UnidadMedidaDAOImpl(connectionDB);

        HashMap<String, Integer> map = unid.unidadMedidaClaveValor();

        for (String s : map.keySet()) {
            jcUnidadMedida.addItem(s.toString());
        }
    }

    public void botones(byte x) {
        switch (x) {
            case 1: //nuevo
                jbNuevo.setEnabled(false);
                jbGuardar.setEnabled(true);
                jbEditar.setEnabled(false);
                jbCancelar.setEnabled(true);
                limpiar();
                habilitarComponentes();
                break;
            case 2: //guardar
                jbNuevo.setEnabled(true);
                jbGuardar.setEnabled(false);
                jbEditar.setEnabled(true);
                jbCancelar.setEnabled(false);
                deshabilitarComponentes();
                break;
            case 3: // editar
                jbNuevo.setEnabled(false);
                jbGuardar.setEnabled(true);
                jbEditar.setEnabled(false);
                jbCancelar.setEnabled(true);
                habilitarComponentes();
                break;
            case 4: // cancelar
                jbNuevo.setEnabled(true);
                jbGuardar.setEnabled(false);
                jbEditar.setEnabled(true);
                jbCancelar.setEnabled(false);
                deshabilitarComponentes();
                break;
        }
    }

    public void limpiar() {
        llenarComboUnidadMedida();
//        llenarComboUnidadMedidaPrincipal();
        jtxtStockMinimo.setText("");
        jtxtPrecioVenta.setText("");
//        jtxtPrecioVentaRebaja.setText("");
//        jtxtPrecioVentaAumento.setText("");
        jtxtPrecioCompra.setText("");
        jtxtGarantiaMses.setText("");
        jtxtCodigoAdjunto.setText("");
    }

    public void llenarTablaUnidadProduco(int idProducto) {
        UnidadProductoDAOImlp uProd = new UnidadProductoDAOImlp(connectionDB);

        ArrayList<UnidadProducto> up = new ArrayList<UnidadProducto>();
        up = uProd.getListaUnidadProducto(idProducto);

        dtm = (DefaultTableModel) this.jtUnidadProducto.getModel();
        dtm.setRowCount(0);

        jtUnidadProducto.setModel(dtm);

        Object[] fila = new Object[12];
        for (int i = 0; i < up.size(); i++) {
            fila[0] = up.get(i).getId();
            fila[1] = up.get(i).getIdProdcuto();
            fila[2] = up.get(i).getIdUnidadMedida();
            fila[3] = up.get(i).getNombreUnidadMedida();
            fila[4] = up.get(i).getStockMinimo();
            fila[5] = up.get(i).getPrecioVenta();
            fila[6] = up.get(i).getPrecioCompra();
            fila[7] = up.get(i).getGarantiaMeses();
            fila[8] = up.get(i).getCodigoAdjunto();
            fila[9] = up.get(i).getCodigoAdjunto();
            fila[10] = up.get(i).getCantidad();
            fila[11] = up.get(i).getDescuento();

            dtm.addRow(fila);
        }

        jtUnidadProducto.setModel(dtm);
    }

    public void seleccionarUnidadProducto() {
    
        byte x=4;//cancelar
        botones(x);
        
        jlEdicion.setText("0");

        int fila = jtUnidadProducto.getSelectedRow();
        int idUnidadProducto = 0;
        idUnidadProducto = (int) jtUnidadProducto.getValueAt(fila, 0);
        jlIdUnidadProducto.setText(String.valueOf(idUnidadProducto));

        String unidadMedida = (String) jtUnidadProducto.getValueAt(fila, 3);
        jcUnidadMedida.setSelectedItem(unidadMedida);

//        String unidadPrincipal = (String) jtUnidadProducto.getValueAt(fila, 5);
//        jcUnidadMedidaPrincipal.setSelectedItem(unidadPrincipal);

        jtxtStockMinimo.setText(jtUnidadProducto.getValueAt(fila, 4).toString());
        jtxtPrecioVenta.setText(jtUnidadProducto.getValueAt(fila, 5).toString());
//        jtxtPrecioVentaRebaja.setText(jtUnidadProducto.getValueAt(fila, 8).toString());
//        jtxtPrecioVentaAumento.setText(jtUnidadProducto.getValueAt(fila, 9).toString());
        jtxtPrecioCompra.setText(jtUnidadProducto.getValueAt(fila, 6).toString());
        jtxtGarantiaMses.setText(jtUnidadProducto.getValueAt(fila, 7).toString());
        jtxtCodigoAdjunto.setText(jtUnidadProducto.getValueAt(fila, 8).toString());

    }

    public void guardarUnidadProdcto() {

        int idProducto = Integer.valueOf(jlIdProducto.getText());
        int idUnidadMedida = Integer.valueOf(jlIdUnidadMedida.getText().trim());
//        int idUnidadMedidaPrincipal = Integer.valueOf(jlIdUnidadMedidaPrincipal.getText());
        int idUnidadMedidaPrincipal = 0;
        double stockMinimo = Double.valueOf(jtxtStockMinimo.getText());
        double precioVenta = Double.valueOf(jtxtPrecioVenta.getText());
//        double precioVentaRebaja = Double.valueOf(jtxtPrecioVentaRebaja.getText());
//        double precioVentaAumento = Double.valueOf(jtxtPrecioVentaAumento.getText());
        double precioVentaRebaja = 0;
        double precioVentaAumento = 0;
        double precioCompra = Double.valueOf(jtxtPrecioCompra.getText());
        String codigoAdjunto = jtxtCodigoAdjunto.getText().trim();
        
        int garantiaMeses;
        if(jtxtGarantiaMses.getText().length()==0){
           garantiaMeses = 0;
        }else{
           garantiaMeses = Integer.valueOf(jtxtGarantiaMses.getText());
        }
         
        int actualizacion = 1;
        String usuario = "";
        
        UnidadProducto unidadProducto = new UnidadProducto();
        
        unidadProducto.setIdProdcuto(idProducto);
        unidadProducto.setIdUnidadMedida(idUnidadMedida);
        unidadProducto.setUnidadPrincipal(idUnidadMedidaPrincipal);
        unidadProducto.setStockMinimo(stockMinimo);
        unidadProducto.setPrecioVenta(precioVenta);
        unidadProducto.setPrecioVentaRebaja(precioVentaRebaja);
        unidadProducto.setPrecioVentaAumento(precioVentaAumento);
        unidadProducto.setPrecioCompra(precioCompra);
        unidadProducto.setGarantiaMeses(garantiaMeses);
        unidadProducto.setUsuario(usuario);
        unidadProducto.setCodigoAdjunto(codigoAdjunto);

        UnidadProductoDAOImlp uProdDAOImpl = new UnidadProductoDAOImlp(connectionDB);
        
        String aux = "0";
        
        if (jlEdicion.getText().toString().trim().equals(aux)) {    
            uProdDAOImpl.insertarUnidadProducto(unidadProducto);
        } else {
            int idUnidadProducto = 0;
            
            idUnidadProducto = Integer.valueOf(jlIdUnidadProducto.getText().toString().trim());
            unidadProducto.setId(idUnidadProducto);
            
            uProdDAOImpl.editarUnidadProducto(unidadProducto);
        }
        llenarTablaUnidadProduco(idProducto);

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
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtUnidadProducto = new javax.swing.JTable();
        jlIdProducto = new javax.swing.JLabel();
        jlIdUnidadMedida = new javax.swing.JLabel();
        jlIdUnidadMedidaPrincipal = new javax.swing.JLabel();
        jlTituloFormulario = new javax.swing.JLabel();
        jlIdUnidadProducto = new javax.swing.JLabel();
        jlEdicion = new javax.swing.JLabel();
        jlNombreProducto = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jcUnidadMedida = new javax.swing.JComboBox<String>();
        jLabel3 = new javax.swing.JLabel();
        jtxtStockMinimo = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jtxtPrecioVenta = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jtxtPrecioCompra = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jtxtGarantiaMses = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jtxtCodigoAdjunto = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jtxtCantidad = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jtxtDescuento = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jbNuevo = new javax.swing.JButton();
        jbEditar = new javax.swing.JButton();
        jbGuardar = new javax.swing.JButton();
        jbCancelar = new javax.swing.JButton();
        jbEliminar = new javax.swing.JButton();
        jbSalir = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });

        jtUnidadProducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "idUnidadProdcutol", "idProducto", "idUnidadMedida", "U. Medida", "Stock Min.", "Venta", "Compra", "Garantia Meses", "Codigo Adjunto", "Cantidad", "Descuento"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true, true, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtUnidadProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtUnidadProductoMouseClicked(evt);
            }
        });
        jtUnidadProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtUnidadProductoKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(jtUnidadProducto);
        if (jtUnidadProducto.getColumnModel().getColumnCount() > 0) {
            jtUnidadProducto.getColumnModel().getColumn(0).setMinWidth(0);
            jtUnidadProducto.getColumnModel().getColumn(0).setPreferredWidth(0);
            jtUnidadProducto.getColumnModel().getColumn(0).setMaxWidth(0);
            jtUnidadProducto.getColumnModel().getColumn(1).setMinWidth(0);
            jtUnidadProducto.getColumnModel().getColumn(1).setPreferredWidth(0);
            jtUnidadProducto.getColumnModel().getColumn(1).setMaxWidth(0);
            jtUnidadProducto.getColumnModel().getColumn(2).setMinWidth(0);
            jtUnidadProducto.getColumnModel().getColumn(2).setPreferredWidth(0);
            jtUnidadProducto.getColumnModel().getColumn(2).setMaxWidth(0);
        }

        jlTituloFormulario.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        jlTituloFormulario.setForeground(new java.awt.Color(153, 0, 51));
        jlTituloFormulario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlTituloFormulario.setText("Unidad Medida Producto");
        jlTituloFormulario.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jlNombreProducto.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jlNombreProducto.setForeground(new java.awt.Color(0, 0, 102));
        jlNombreProducto.setText("jlNombreRpoducto");

        jLabel1.setForeground(new java.awt.Color(153, 0, 51));
        jLabel1.setText("Unidad Medida");

        jcUnidadMedida.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jcUnidadMedida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcUnidadMedidaActionPerformed(evt);
            }
        });

        jLabel3.setForeground(new java.awt.Color(153, 0, 51));
        jLabel3.setText("Stock Minimo");

        jtxtStockMinimo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtxtStockMinimo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtStockMinimoActionPerformed(evt);
            }
        });

        jLabel4.setForeground(new java.awt.Color(153, 0, 51));
        jLabel4.setText("Precio Venta");

        jtxtPrecioVenta.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel7.setForeground(new java.awt.Color(153, 0, 51));
        jLabel7.setText("Precio Compra");

        jtxtPrecioCompra.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel8.setForeground(new java.awt.Color(153, 0, 51));
        jLabel8.setText("Garantia Meses");

        jtxtGarantiaMses.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel9.setForeground(new java.awt.Color(153, 0, 51));
        jLabel9.setText("Codigo Adjunto");

        jtxtCodigoAdjunto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel5.setForeground(new java.awt.Color(153, 0, 51));
        jLabel5.setText("Cantidad");

        jtxtCantidad.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtxtCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtCantidadActionPerformed(evt);
            }
        });

        jLabel6.setForeground(new java.awt.Color(153, 0, 51));
        jLabel6.setText("Descuento %");

        jtxtDescuento.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtxtDescuento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtDescuentoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jcUnidadMedida, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jtxtStockMinimo, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jtxtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jtxtDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jtxtPrecioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(jtxtPrecioCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(jtxtGarantiaMses, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jtxtCodigoAdjunto, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE))
                        .addGap(31, 31, 31))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxtCodigoAdjunto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3)
                                .addComponent(jLabel1))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jtxtStockMinimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jcUnidadMedida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(jLabel4)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtxtPrecioCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jtxtPrecioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel8)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jtxtGarantiaMses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxtDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jbNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/folder-add-icon.png"))); // NOI18N
        jbNuevo.setText("Nuevo");
        jbNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbNuevoActionPerformed(evt);
            }
        });

        jbEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/editar.png"))); // NOI18N
        jbEditar.setText("Editar");
        jbEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbEditarActionPerformed(evt);
            }
        });

        jbGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Save-icon.png"))); // NOI18N
        jbGuardar.setText("Guardar");
        jbGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbGuardarActionPerformed(evt);
            }
        });

        jbCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/exit.png"))); // NOI18N
        jbCancelar.setText("Cancelar");
        jbCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCancelarActionPerformed(evt);
            }
        });

        jbEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/trash_icon.png"))); // NOI18N
        jbEliminar.setText("Eliminar");
        jbEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbEliminarActionPerformed(evt);
            }
        });

        jbSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/close_window.png"))); // NOI18N
        jbSalir.setText("Salir");
        jbSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 24, Short.MAX_VALUE)
                .addComponent(jbNuevo)
                .addGap(18, 18, 18)
                .addComponent(jbGuardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbEliminar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbSalir))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbGuardar)
                    .addComponent(jbEliminar)
                    .addComponent(jbSalir)
                    .addComponent(jbNuevo)
                    .addComponent(jbEditar)
                    .addComponent(jbCancelar))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlIdProducto)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(677, 677, 677)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlIdUnidadMedida)
                            .addComponent(jlIdUnidadMedidaPrincipal)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane2)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(86, 86, 86)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 45, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlIdUnidadProducto, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jlEdicion, javax.swing.GroupLayout.Alignment.TRAILING)))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jlNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jlTituloFormulario, javax.swing.GroupLayout.PREFERRED_SIZE, 689, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jlTituloFormulario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlIdProducto)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(jlIdUnidadMedida)
                        .addGap(5, 5, 5)
                        .addComponent(jlIdUnidadProducto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jlEdicion))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlIdUnidadMedidaPrincipal))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSalirActionPerformed
        dispose();
    }//GEN-LAST:event_jbSalirActionPerformed

    private void jtxtStockMinimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtStockMinimoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtStockMinimoActionPerformed

    private void jbGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbGuardarActionPerformed
        byte x = 4;
        botones(x);
        guardarUnidadProdcto();
        jlEdicion.setText("0");
        
    }//GEN-LAST:event_jbGuardarActionPerformed

    private void jcUnidadMedidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcUnidadMedidaActionPerformed
        seleccionarUnidadMedida();
    }//GEN-LAST:event_jcUnidadMedidaActionPerformed

    private void jbEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbEliminarActionPerformed
        eliminarUnidadProducto();
    }//GEN-LAST:event_jbEliminarActionPerformed

    private void jbNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbNuevoActionPerformed
        byte x = 1;
        botones(x);
        jlEdicion.setText("0");
    }//GEN-LAST:event_jbNuevoActionPerformed

    private void jbEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbEditarActionPerformed
        byte x = 3;
        botones(x);
        jlEdicion.setText("1");
    }//GEN-LAST:event_jbEditarActionPerformed

    private void jbCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCancelarActionPerformed
        byte x = 4;
        botones(x);
        jlEdicion.setText("0");
    }//GEN-LAST:event_jbCancelarActionPerformed

    private void jtUnidadProductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtUnidadProductoKeyReleased
        seleccionarUnidadProducto();
    }//GEN-LAST:event_jtUnidadProductoKeyReleased

    private void jtUnidadProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtUnidadProductoMouseClicked
        seleccionarUnidadProducto();
    }//GEN-LAST:event_jtUnidadProductoMouseClicked

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
         
    }//GEN-LAST:event_formFocusGained

    private void jtxtCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtCantidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtCantidadActionPerformed

    private void jtxtDescuentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtDescuentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtDescuentoActionPerformed

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
            java.util.logging.Logger.getLogger(FormUnidadProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormUnidadProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormUnidadProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormUnidadProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormUnidadProducto().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JButton jbEditar;
    private javax.swing.JButton jbEliminar;
    private javax.swing.JButton jbGuardar;
    private javax.swing.JButton jbNuevo;
    private javax.swing.JButton jbSalir;
    private javax.swing.JComboBox<String> jcUnidadMedida;
    private javax.swing.JLabel jlEdicion;
    private javax.swing.JLabel jlIdProducto;
    private javax.swing.JLabel jlIdUnidadMedida;
    private javax.swing.JLabel jlIdUnidadMedidaPrincipal;
    private javax.swing.JLabel jlIdUnidadProducto;
    private javax.swing.JLabel jlNombreProducto;
    private javax.swing.JLabel jlTituloFormulario;
    private javax.swing.JTable jtUnidadProducto;
    private javax.swing.JTextField jtxtCantidad;
    private javax.swing.JTextField jtxtCodigoAdjunto;
    private javax.swing.JTextField jtxtDescuento;
    private javax.swing.JTextField jtxtGarantiaMses;
    private javax.swing.JTextField jtxtPrecioCompra;
    private javax.swing.JTextField jtxtPrecioVenta;
    private javax.swing.JTextField jtxtStockMinimo;
    // End of variables declaration//GEN-END:variables

    private void seleccionarUnidadMedida() {
        //jcUnidadMedida.setVisible(false);

        String sel = null;

        String comp = "Sel";

        UnidadMedidaDAOImpl unid = new UnidadMedidaDAOImpl(connectionDB);

        HashMap<String, Integer> map = unid.unidadMedidaClaveValor();

        try {
            sel = jcUnidadMedida.getSelectedItem().toString();

            if (sel.equals(comp)) {
                jlIdUnidadMedida.setText("0");
            } else {
                jlIdUnidadMedida.setText(map.get(sel).toString());
            }
        } catch (Exception e) {
        }
    }

//    private void seleccionarUnidadMedidaPrincipal() {
//        //jcUnidadMedida.setVisible(false);
//
//        String sel = null;
//
//        String comp = "Sel";
//
//        UnidadMedidaDAOImpl unid = new UnidadMedidaDAOImpl(connectionDB);
//
//        HashMap<String, Integer> map = unid.unidadMedidaClaveValor();
//
//        try {
//            sel = jcUnidadMedidaPrincipal.getSelectedItem().toString();
//
//            if (sel.equals(comp)) {
//                jlIdUnidadMedidaPrincipal.setText("0");
//            } else {
//                jlIdUnidadMedidaPrincipal.setText(map.get(sel).toString());
//            }
//        } catch (Exception e) {
//        }
//    }

    private void eliminarUnidadProducto() {
        int filSel = jtUnidadProducto.getSelectedRow();

        int idUnidadProducto = (int) jtUnidadProducto.getValueAt(filSel, 0);
        
        UnidadProductoDAOImlp uProd = new UnidadProductoDAOImlp(connectionDB);
        uProd.eliminarUnidadProducto(idUnidadProducto);
        
        llenarTablaUnidadProduco(Integer.parseInt(jlIdProducto.getText()));
    }
}
