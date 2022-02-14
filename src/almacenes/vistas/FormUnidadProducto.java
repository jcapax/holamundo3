/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package almacenes.vistas;

import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.Producto;
import almacenes.model.UnidadProducto;
import dao.UnidadMedidaDAO;
import dao.UnidadMedidaDAOImpl;
import dao.UnidadProductoDAO;
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
    String nombreProducto, principioActivo, indicaciones, laboratorio, familia;
    String usuario;
    private byte idLugar;
    private int idProducto;
    private UnidadProductoDAO unidadProductoDAO;
    private UnidadMedidaDAO unidadMedidaDAO;    
    
    public FormUnidadProducto() {
        initComponents();

    }

    public FormUnidadProducto(Connection connectionDB, Producto producto, String usuario, byte idLugar) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.databaseUtils = new DatabaseUtils();
        this.connectionDB = connectionDB;
        
        unidadProductoDAO = new UnidadProductoDAOImlp(connectionDB);
        unidadMedidaDAO = new UnidadMedidaDAOImpl(connectionDB);
        
        this.idProducto = producto.getId();        
        this.usuario = usuario;
        this.idLugar = idLugar;
        
        headerTabla();
        
        llenarTablaUnidadProduco(producto.getId());
        llenarComboUnidadMedida();
//        llenarComboUnidadMedidaPrincipal();
        
        jlNombreProducto2.setText(producto.getDescripcion());
        jlPrincipioActivo.setText(producto.getPrincipioActivo());
        jlIndicaciones.setText(producto.getIndicaciones());
        jlLaboratorio.setText(producto.getNombreLaboratorio());
        jlFamilia.setText(producto.getNombreFamilia());

        byte x = 2;
        botones(x);
        
        jlIdProducto.setVisible(false);
        jlIdUnidadMedida.setVisible(false);
        jlIdUnidadMedidaPrincipal.setVisible(false);
        jlEdicion.setVisible(false);
        deshabilitarComponentes();
        jbEditar.setEnabled(false);
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
//        jtxtCantidad.setEnabled(aux);
//        jtxtDescuento.setEnabled(aux);
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
        //jtxtCantidad.setEnabled(aux);
        //jtxtDescuento.setEnabled(aux);
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

        HashMap<String, Integer> map = unidadMedidaDAO.unidadMedidaClaveValor();

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
//        jtxtCantidad.setText("");
//        jtxtDescuento.setText("");
    }

    public void llenarTablaUnidadProduco(int idProducto) {
        
        ArrayList<UnidadProducto> up = new ArrayList<UnidadProducto>();
        up = unidadProductoDAO.getListaUnidadProducto(idProducto);

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
            fila[9] = up.get(i).getCantidad();
            fila[10] = up.get(i).getDescuento();

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

        jtxtStockMinimo.setText(jtUnidadProducto.getValueAt(fila, 4).toString());
        jtxtPrecioVenta.setText(jtUnidadProducto.getValueAt(fila, 5).toString());
        jtxtPrecioCompra.setText(jtUnidadProducto.getValueAt(fila, 6).toString());
        //jtxtCantidad.setText(jtUnidadProducto.getValueAt(fila, 9).toString());
        //jtxtDescuento.setText(jtUnidadProducto.getValueAt(fila, 10).toString());
    }

    public void guardarUnidadProdcto() {

        int idUnidadMedida = Integer.valueOf(jlIdUnidadMedida.getText().trim());
//        int idUnidadMedidaPrincipal = Integer.valueOf(jlIdUnidadMedidaPrincipal.getText());
        int idUnidadMedidaPrincipal = 0;
        
        double stockMinimo;
        if(jtxtStockMinimo.getText().trim().equals("")){
            stockMinimo = 0;
        }else{
            stockMinimo = Double.valueOf(jtxtStockMinimo.getText());
        }
        
        double precioVenta;
        if(jtxtPrecioVenta.getText().trim().equals("")){
            precioVenta = 0;
        }else{
            precioVenta = Double.valueOf(jtxtPrecioVenta.getText());
        }
//        double precioVentaRebaja = Double.valueOf(jtxtPrecioVentaRebaja.getText());
//        double precioVentaAumento = Double.valueOf(jtxtPrecioVentaAumento.getText());
        double precioVentaRebaja = 0;
        double precioVentaAumento = 0;
        
        double precioCompra;
        if(jtxtPrecioCompra.getText().trim().equals("")){
            precioCompra = 0;
        }else{
            precioCompra = Double.valueOf(jtxtPrecioCompra.getText());
        }        
/*                
        int garantiaMeses;
        if(jtxtGarantiaMses.getText().trim().equals("")){
           garantiaMeses = 0;
        }else{
           garantiaMeses = Integer.valueOf(jtxtGarantiaMses.getText());
        }
        
        String codigoAdjunto = jtxtCodigoAdjunto.getText().trim();
        codigoAdjunto = codigoAdjunto.replace("'", "-");
  */      
        int cantidad = 0;
        /*if(jtxtCantidad.getText().trim().equals("")){
           cantidad = 0;
        }else{
           cantidad = Integer.valueOf(jtxtCantidad.getText().toString().trim());
        }*/
        
        int descuento = 0;
        /*if(jtxtCantidad.getText().trim().equals("")){
           descuento = 0;
        }else{
           descuento = Integer.valueOf(jtxtDescuento.getText().toString().trim());
        }*/
        
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
//        unidadProducto.setGarantiaMeses(garantiaMeses);
        unidadProducto.setUsuario(usuario);
//        unidadProducto.setCodigoAdjunto(codigoAdjunto);
        unidadProducto.setCantidad(cantidad);
        unidadProducto.setDescuento(descuento);
        unidadProducto.setIdLugar(idLugar);

        
        String aux = "0";
        
        if (jlEdicion.getText().trim().equals(aux)) {    
            unidadProductoDAO.insertarUnidadProducto(unidadProducto);
        } else {
            int idUnidadProducto = 0;
            
            idUnidadProducto = Integer.valueOf(jlIdUnidadProducto.getText().trim());
            unidadProducto.setId(idUnidadProducto);
            
            unidadProductoDAO.editarUnidadProducto(unidadProducto);
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
        jlPrincipioActivo = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jcUnidadMedida = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jtxtStockMinimo = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jtxtPrecioVenta = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jtxtPrecioCompra = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jbNuevo = new javax.swing.JButton();
        jbEditar = new javax.swing.JButton();
        jbGuardar = new javax.swing.JButton();
        jbCancelar = new javax.swing.JButton();
        jbEliminar = new javax.swing.JButton();
        jbSalir = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jlIndicaciones = new javax.swing.JLabel();
        jlNombreProducto2 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jlLaboratorio = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jlFamilia = new javax.swing.JLabel();

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

        jlPrincipioActivo.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jlPrincipioActivo.setForeground(new java.awt.Color(0, 0, 102));
        jlPrincipioActivo.setText("Principio Activo");

        jLabel1.setForeground(new java.awt.Color(153, 0, 51));
        jLabel1.setText("Unidad Medida");

        jcUnidadMedida.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jcUnidadMedida, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jtxtStockMinimo, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jtxtPrecioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jtxtPrecioCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel7))
                .addGap(267, 267, 267))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtxtPrecioCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtPrecioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtStockMinimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcUnidadMedida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(35, Short.MAX_VALUE))
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
                .addGap(34, 34, 34)
                .addComponent(jbNuevo)
                .addGap(18, 18, 18)
                .addComponent(jbGuardar)
                .addGap(18, 18, 18)
                .addComponent(jbEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jbEliminar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(jbSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        jLabel10.setForeground(new java.awt.Color(153, 0, 51));
        jLabel10.setText("Nombre prod.");

        jLabel11.setForeground(new java.awt.Color(153, 0, 51));
        jLabel11.setText("Principio Activo");

        jLabel12.setForeground(new java.awt.Color(153, 0, 51));
        jLabel12.setText("Indicaciones");

        jlIndicaciones.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jlIndicaciones.setForeground(new java.awt.Color(0, 0, 102));
        jlIndicaciones.setText("Indicaciones");

        jlNombreProducto2.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jlNombreProducto2.setForeground(new java.awt.Color(0, 0, 102));
        jlNombreProducto2.setText("jlNombreProducto");

        jLabel13.setForeground(new java.awt.Color(153, 0, 51));
        jLabel13.setText("Laboratorio");

        jlLaboratorio.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jlLaboratorio.setForeground(new java.awt.Color(0, 0, 102));
        jlLaboratorio.setText("Laboratorio");

        jLabel14.setForeground(new java.awt.Color(153, 0, 51));
        jLabel14.setText("Familia");

        jlFamilia.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jlFamilia.setForeground(new java.awt.Color(0, 0, 102));
        jlFamilia.setText("Laboratorio");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jlTituloFormulario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlIndicaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jlPrincipioActivo, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel14))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jlNombreProducto2, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(234, 234, 234)
                                        .addComponent(jLabel13)))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jlLaboratorio, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jlFamilia, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 18, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jlIdProducto)
                        .addGap(677, 677, 677)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlIdUnidadMedida)
                            .addComponent(jlIdUnidadMedidaPrincipal))
                        .addGap(239, 239, 239)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlIdUnidadProducto, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jlEdicion, javax.swing.GroupLayout.Alignment.TRAILING)))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(147, 147, 147)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jlTituloFormulario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlIdProducto)
                .addGap(0, 0, 0)
                .addComponent(jlIdUnidadMedidaPrincipal)
                .addGap(7, 7, 7)
                .addComponent(jlIdUnidadMedida)
                .addGap(5, 5, 5)
                .addComponent(jlIdUnidadProducto)
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jlNombreProducto2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jlLaboratorio, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlEdicion)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jlPrincipioActivo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel14)
                        .addComponent(jlFamilia, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jlIndicaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
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
        jlEdicion.setText("0");
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
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
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
    private javax.swing.JLabel jlFamilia;
    private javax.swing.JLabel jlIdProducto;
    private javax.swing.JLabel jlIdUnidadMedida;
    private javax.swing.JLabel jlIdUnidadMedidaPrincipal;
    private javax.swing.JLabel jlIdUnidadProducto;
    private javax.swing.JLabel jlIndicaciones;
    private javax.swing.JLabel jlLaboratorio;
    private javax.swing.JLabel jlNombreProducto2;
    private javax.swing.JLabel jlPrincipioActivo;
    private javax.swing.JLabel jlTituloFormulario;
    private javax.swing.JTable jtUnidadProducto;
    private javax.swing.JTextField jtxtPrecioCompra;
    private javax.swing.JTextField jtxtPrecioVenta;
    private javax.swing.JTextField jtxtStockMinimo;
    // End of variables declaration//GEN-END:variables

    private void seleccionarUnidadMedida() {
        //jcUnidadMedida.setVisible(false);

        String sel = null;

        String comp = "Sel";
        
        HashMap<String, Integer> map = unidadMedidaDAO.unidadMedidaClaveValor();

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
        
        unidadProductoDAO.eliminarUnidadProducto(idUnidadProducto);
        
        llenarTablaUnidadProduco(Integer.parseInt(jlIdProducto.getText()));
    }
}
