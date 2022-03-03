/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package almacenes.vistas;

import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.Producto;
import dao.LaboratorioDAO;
import dao.LaboratorioDAOImpl;

import dao.FamiliaDAO;
import dao.FamiliaDAOImpl;
import dao.ProductoDAO;
import dao.ProductoDAOImpl;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;


/**
 *
 * @author jcapax
 */
public class FormProducto extends javax.swing.JFrame {

    DefaultTableModel dtm;
    private DatabaseUtils databaseUtils;
    private Connection connectionDB;
    private ProductoDAO productoDAO;
    private String usuario;
    private LaboratorioDAO laboratorioDAO;
    private FamiliaDAO familiaDAO;
    private int idProducto;
    
    private byte idLugar;    
    
    public FormProducto() {
        initComponents();
    }
    
    public FormProducto(Connection connectionDB, String usuario, byte idLugar) {
        initComponents();
        this.setLocationRelativeTo(null);
        ljEditar.setVisible(false);        
        jbGuardar.setEnabled(false);
        
        this.databaseUtils = new DatabaseUtils();
        this.connectionDB = connectionDB;
        this.usuario = usuario;
        this.idLugar = idLugar;
        
        productoDAO = new ProductoDAOImpl(connectionDB);
        laboratorioDAO = new LaboratorioDAOImpl(connectionDB);
        familiaDAO = new FamiliaDAOImpl(connectionDB);
        
        this.idProducto = 0;
        
        headerTabla();
        
        llenarComboLaboratorio();
        llenarComboFamilia();        
        llenarTablaProductos();
        deshabilitarComponentes();
        
        jchCaducidad.setVisible(false);
    }
    
    public void habilitarComponentes(){
        boolean aux = true;
        
        //jPanelComponentes.setEnabled(aux);
        jcLaboratorio.setEnabled(aux);
        jcFamilia.setEnabled(aux);        
        jtxtNombreProducto.setEnabled(aux);
        jtxtPrincipioActivo.setEnabled(aux);
        jtxtIndicaciones.setEnabled(aux);
        jchControlStock.setEnabled(aux);
        jchCaducidad.setEnabled(aux);
        jchEstado.setEnabled(aux);
    }
    
    public void deshabilitarComponentes(){
        boolean aux = false;
        
        //jPanelComponentes.setEnabled(aux);
        jcLaboratorio.setEnabled(aux);
        jcFamilia.setEnabled(aux);
        jtxtNombreProducto.setEnabled(aux);
        jtxtPrincipioActivo.setEnabled(aux);
        jtxtIndicaciones.setEnabled(aux);
        jchControlStock.setEnabled(aux);
        jchCaducidad.setEnabled(aux);
        jchEstado.setEnabled(aux);
    }
    
    public void headerTabla(){
        Font f = new Font("Times New Roman", Font.BOLD, 18);
        
        jtProductos.getTableHeader().setFont(f);
        jtProductos.getTableHeader().setBackground(Color.orange);
    }
     
    public void llenarTablaProductos(){
        
        DecimalFormat df = new DecimalFormat("###,##0.00");
        
        List<Producto> lProd = new ArrayList<Producto>();
        
        lProd = productoDAO.getListaProductos();
        //lProd = productoDAO.getListaProductosVenta("", idLugar);
        
        dtm = (DefaultTableModel) this.jtProductos.getModel();
        dtm.setRowCount(0);
        
        jtProductos.setModel(dtm);
        
        Object[] fila = new Object[14];

        boolean aux = true;
        
        for(int i=0; i< lProd.size(); i++){
            fila[0]  = lProd.get(i).getId();
            fila[1]  = lProd.get(i).getDescripcion();
            fila[2]  = lProd.get(i).getIdLaboratorio();
            fila[3]  = lProd.get(i).getNombreLaboratorio();
            fila[4]  = lProd.get(i).getIdFamilia();
            fila[5]  = lProd.get(i).getNombreFamilia();
            fila[6]  = lProd.get(i).getPrincipioActivo();
            fila[7]  = lProd.get(i).getIndicaciones();
            aux = (lProd.get(i).getEstado().equals("V"))?true:false;
            fila[8]  = aux;
            aux = (lProd.get(i).getControlStock()==1)?true:false;
            fila[9]  = aux;
            aux = (lProd.get(i).getCaducidad()==1)?true:false;
            fila[10]  = aux;
            fila[11] = lProd.get(i).getSimbolo();
            fila[12] = lProd.get(i).getPrecioVenta();
            fila[13] = lProd.get(i).getPrecioCompra();
             
            dtm.addRow(fila);
        }
        
        TableColumnModel columnModel = jtProductos.getColumnModel();

        TableColumn colPVenta = columnModel.getColumn(12);
        TableColumn colPCompra = columnModel.getColumn(13);
        

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(jLabel1.RIGHT);

        colPVenta.setCellRenderer(renderer);
        colPCompra.setCellRenderer(renderer);
        
        jtProductos.setModel(dtm);
        
    }
    
    public void guardarProducto(){
        
        int idLaboratorio = Integer.valueOf(jlIdLaboratorio.getText());        
        int idFamilia = Integer.valueOf(jlIdFamilia.getText());
        
        String estado;
        int controlStock;
        int caducidad;
        
        String descripcion = jtxtNombreProducto.getText().toUpperCase();
        String principioActivo = jtxtPrincipioActivo.getText().toUpperCase();
        String indicaciones = jtxtIndicaciones.getText().toUpperCase();
        
        if (jchEstado.isSelected()) {
            estado = "V";
        } else {
            estado = "A";
        }
        
        if(jchControlStock.isSelected()){
            controlStock = 1;
        }else{
            controlStock = 0;
        }
        
        if(jchCaducidad.isSelected()){
            caducidad = 1;
        }else{
            caducidad = 0;
        }
        
        if(ljEditar.getText().equals("1")){
            Producto producto = new Producto();
            
            producto.setControlStock(controlStock);
            producto.setCaducidad(caducidad);
            producto.setDescripcion(descripcion.toUpperCase());
            producto.setEstado(estado);
            producto.setId(idProducto);
            producto.setIdLaboratorio(idLaboratorio);
            producto.setIdFamilia(idFamilia);
            producto.setPrincipioActivo(principioActivo);
            producto.setIndicaciones(indicaciones);
            producto.setUsuario(usuario);
            
            productoDAO.editarProducto(producto);
        }
        else{
            Producto producto = new Producto(idLaboratorio, idFamilia, "", 
                    descripcion, principioActivo, indicaciones, 
                    "", estado, controlStock, 
                    1, usuario);
        
            productoDAO.insertarProducto(producto);
        }
        
        llenarTablaProductos();
        
    }
    
    public void llenarComboLaboratorio(){
        
        String sel = "Sel";
        
        jcLaboratorio.removeAllItems();
        jcLaboratorio.addItem(sel);
        
        HashMap<String, Integer> map = laboratorioDAO.laboratorioClaveValor();

        for (String s : map.keySet()) {
            jcLaboratorio.addItem(s.toString());
        }
    }
    
    public void llenarComboFamilia(){
        
        String sel = "Sel";
        
        jcFamilia.removeAllItems();
        jcFamilia.addItem(sel);
        
        HashMap<String, Integer> map = familiaDAO.familiaClaveValor();

        for (String s : map.keySet()) {
            jcFamilia.addItem(s.toString());
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jlIdLaboratorio = new javax.swing.JLabel();
        jlIdFamilia = new javax.swing.JLabel();
        jlIdRubroProducto = new javax.swing.JLabel();
        ljEditar = new javax.swing.JLabel();
        jlTituloFormulario = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jbNuevo = new javax.swing.JButton();
        jbGuardar = new javax.swing.JButton();
        jbEditar = new javax.swing.JButton();
        jbCancelar = new javax.swing.JButton();
        jbUnidadProducto = new javax.swing.JButton();
        jbSalir = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jtxtCriterio = new javax.swing.JTextField();
        jPanelComponentes = new javax.swing.JPanel();
        jtxtNombreProducto = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jchEstado = new javax.swing.JCheckBox();
        jchControlStock = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jcLaboratorio = new javax.swing.JComboBox<String>();
        jLabel2 = new javax.swing.JLabel();
        jcFamilia = new javax.swing.JComboBox<String>();
        jchCaducidad = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        jtxtPrincipioActivo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jtxtIndicaciones = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtProductos = new javax.swing.JTable();

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

        jlTituloFormulario.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        jlTituloFormulario.setForeground(new java.awt.Color(153, 0, 51));
        jlTituloFormulario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlTituloFormulario.setText("Registro de Productos");

        jbNuevo.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jbNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/folder-add-icon.png"))); // NOI18N
        jbNuevo.setText("Nuevo");
        jbNuevo.setPreferredSize(new java.awt.Dimension(150, 40));
        jbNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbNuevoActionPerformed(evt);
            }
        });

        jbGuardar.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jbGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Save-icon.png"))); // NOI18N
        jbGuardar.setText("Guardar");
        jbGuardar.setPreferredSize(new java.awt.Dimension(150, 40));
        jbGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbGuardarActionPerformed(evt);
            }
        });

        jbEditar.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jbEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/editar.png"))); // NOI18N
        jbEditar.setText("Editar");
        jbEditar.setPreferredSize(new java.awt.Dimension(150, 40));
        jbEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbEditarActionPerformed(evt);
            }
        });

        jbCancelar.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jbCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/exit.png"))); // NOI18N
        jbCancelar.setText("Cancelar");
        jbCancelar.setPreferredSize(new java.awt.Dimension(150, 40));
        jbCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCancelarActionPerformed(evt);
            }
        });

        jbUnidadProducto.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jbUnidadProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/unidadProducto.png"))); // NOI18N
        jbUnidadProducto.setText("Unidad Medida");
        jbUnidadProducto.setPreferredSize(new java.awt.Dimension(150, 40));
        jbUnidadProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbUnidadProductoActionPerformed(evt);
            }
        });

        jbSalir.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jbSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/close_window.png"))); // NOI18N
        jbSalir.setText("Salir");
        jbSalir.setPreferredSize(new java.awt.Dimension(150, 40));
        jbSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSalirActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(153, 0, 51));
        jLabel5.setText("Criterio");

        jtxtCriterio.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtxtCriterio, javax.swing.GroupLayout.PREFERRED_SIZE, 491, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jbNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(64, 64, 64)
                        .addComponent(jbGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(76, 76, 76)
                        .addComponent(jbEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(69, 69, 69)
                        .addComponent(jbCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(67, 67, 67)
                        .addComponent(jbUnidadProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60)
                        .addComponent(jbSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(136, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbUnidadProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbSalir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtCriterio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jtxtNombreProducto.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(153, 0, 51));
        jLabel4.setText("Nombre prod.");

        jchEstado.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jchEstado.setForeground(new java.awt.Color(153, 0, 51));
        jchEstado.setText("Estado");
        jchEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchEstadoActionPerformed(evt);
            }
        });

        jchControlStock.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jchControlStock.setForeground(new java.awt.Color(153, 0, 51));
        jchControlStock.setText("Control Stock");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(153, 0, 51));
        jLabel1.setText("Laboratorio");

        jcLaboratorio.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jcLaboratorio.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jcLaboratorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcLaboratorioActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 0, 51));
        jLabel2.setText("Familia");

        jcFamilia.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jcFamilia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jcFamilia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcFamiliaActionPerformed(evt);
            }
        });

        jchCaducidad.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jchCaducidad.setForeground(new java.awt.Color(153, 0, 51));
        jchCaducidad.setText("Caducidad");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(153, 0, 51));
        jLabel6.setText("Principio Activo");

        jtxtPrincipioActivo.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(153, 0, 51));
        jLabel7.setText("Indicaciones");

        jtxtIndicaciones.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N

        javax.swing.GroupLayout jPanelComponentesLayout = new javax.swing.GroupLayout(jPanelComponentes);
        jPanelComponentes.setLayout(jPanelComponentesLayout);
        jPanelComponentesLayout.setHorizontalGroup(
            jPanelComponentesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelComponentesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelComponentesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelComponentesLayout.createSequentialGroup()
                        .addGroup(jPanelComponentesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4))
                        .addGap(66, 66, 66)
                        .addGroup(jPanelComponentesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtxtNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 809, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelComponentesLayout.createSequentialGroup()
                                .addComponent(jcLaboratorio, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jcFamilia, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jtxtPrincipioActivo, javax.swing.GroupLayout.PREFERRED_SIZE, 809, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxtIndicaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 809, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelComponentesLayout.createSequentialGroup()
                                .addComponent(jchEstado)
                                .addGap(41, 41, 41)
                                .addComponent(jchControlStock)
                                .addGap(34, 34, 34)
                                .addComponent(jchCaducidad))))
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelComponentesLayout.setVerticalGroup(
            jPanelComponentesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelComponentesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelComponentesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jcLaboratorio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jcFamilia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelComponentesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanelComponentesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtPrincipioActivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanelComponentesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtIndicaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addGroup(jPanelComponentesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jchEstado)
                    .addComponent(jchControlStock)
                    .addComponent(jchCaducidad)))
        );

        jtProductos.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jtProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Descripcion", "idLaboratorio", "Laboratorio", "idFamilia", "Familia", "PrincipioActivo", "Indicaciones", "Estado", "Stock", "Caduca", "U. Medida", "P. Venta", "P. Compra"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, true, true, true, true, true, true, true, false, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtProductos.setRowHeight(22);
        jtProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtProductosMouseClicked(evt);
            }
        });
        jtProductos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtProductosKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jtProductos);
        if (jtProductos.getColumnModel().getColumnCount() > 0) {
            jtProductos.getColumnModel().getColumn(0).setMinWidth(0);
            jtProductos.getColumnModel().getColumn(0).setPreferredWidth(0);
            jtProductos.getColumnModel().getColumn(0).setMaxWidth(0);
            jtProductos.getColumnModel().getColumn(1).setMinWidth(300);
            jtProductos.getColumnModel().getColumn(1).setPreferredWidth(300);
            jtProductos.getColumnModel().getColumn(1).setMaxWidth(300);
            jtProductos.getColumnModel().getColumn(2).setMinWidth(0);
            jtProductos.getColumnModel().getColumn(2).setPreferredWidth(0);
            jtProductos.getColumnModel().getColumn(2).setMaxWidth(0);
            jtProductos.getColumnModel().getColumn(3).setMinWidth(100);
            jtProductos.getColumnModel().getColumn(3).setPreferredWidth(100);
            jtProductos.getColumnModel().getColumn(3).setMaxWidth(100);
            jtProductos.getColumnModel().getColumn(4).setMinWidth(0);
            jtProductos.getColumnModel().getColumn(4).setPreferredWidth(0);
            jtProductos.getColumnModel().getColumn(4).setMaxWidth(0);
            jtProductos.getColumnModel().getColumn(5).setMinWidth(150);
            jtProductos.getColumnModel().getColumn(5).setPreferredWidth(150);
            jtProductos.getColumnModel().getColumn(5).setMaxWidth(150);
            jtProductos.getColumnModel().getColumn(6).setMinWidth(200);
            jtProductos.getColumnModel().getColumn(6).setPreferredWidth(200);
            jtProductos.getColumnModel().getColumn(6).setMaxWidth(200);
            jtProductos.getColumnModel().getColumn(7).setMinWidth(200);
            jtProductos.getColumnModel().getColumn(7).setPreferredWidth(200);
            jtProductos.getColumnModel().getColumn(7).setMaxWidth(200);
            jtProductos.getColumnModel().getColumn(8).setMinWidth(50);
            jtProductos.getColumnModel().getColumn(8).setPreferredWidth(50);
            jtProductos.getColumnModel().getColumn(8).setMaxWidth(50);
            jtProductos.getColumnModel().getColumn(9).setMinWidth(50);
            jtProductos.getColumnModel().getColumn(9).setPreferredWidth(50);
            jtProductos.getColumnModel().getColumn(9).setMaxWidth(50);
            jtProductos.getColumnModel().getColumn(10).setMinWidth(0);
            jtProductos.getColumnModel().getColumn(10).setPreferredWidth(0);
            jtProductos.getColumnModel().getColumn(10).setMaxWidth(0);
            jtProductos.getColumnModel().getColumn(11).setMinWidth(80);
            jtProductos.getColumnModel().getColumn(11).setPreferredWidth(80);
            jtProductos.getColumnModel().getColumn(11).setMaxWidth(80);
            jtProductos.getColumnModel().getColumn(12).setMinWidth(90);
            jtProductos.getColumnModel().getColumn(12).setPreferredWidth(90);
            jtProductos.getColumnModel().getColumn(12).setMaxWidth(90);
            jtProductos.getColumnModel().getColumn(13).setMinWidth(90);
            jtProductos.getColumnModel().getColumn(13).setPreferredWidth(90);
            jtProductos.getColumnModel().getColumn(13).setMaxWidth(90);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jlTituloFormulario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 172, Short.MAX_VALUE)
                        .addComponent(ljEditar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelComponentes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(204, 204, 204))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(325, 325, 325)
                                        .addComponent(jlIdLaboratorio))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(587, 587, 587)
                                        .addComponent(jlIdFamilia)
                                        .addGap(269, 269, 269)
                                        .addComponent(jlIdRubroProducto)))
                                .addGap(0, 503, Short.MAX_VALUE))
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jlTituloFormulario)
                .addGap(3, 3, 3)
                .addComponent(jlIdLaboratorio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlIdFamilia)
                    .addComponent(jlIdRubroProducto))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(97, 97, 97)
                        .addComponent(ljEditar))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelComponentes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(37, 37, 37)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jchEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchEstadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jchEstadoActionPerformed

    private void jcLaboratorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcLaboratorioActionPerformed
        seleccionarElementoLaboratorio();        
    }//GEN-LAST:event_jcLaboratorioActionPerformed

    private void jcFamiliaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcFamiliaActionPerformed
        seleccionarElementoFamilia();
    }//GEN-LAST:event_jcFamiliaActionPerformed

    private void jbGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbGuardarActionPerformed
        byte x = 2;
        botones(x);
        guardarProducto();
        ljEditar.setText("0");
    }//GEN-LAST:event_jbGuardarActionPerformed

    private void jbUnidadProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbUnidadProductoActionPerformed
        int filSel = jtProductos.getSelectedRow();
        
        int idProducto = (int) jtProductos.getValueAt(filSel, 0);
        
        FormUnidadProducto fUnidadProd = new FormUnidadProducto(connectionDB, 
                getProducto(idProducto), 
                usuario, 
                idLugar);
        fUnidadProd.setVisible(true);
    }//GEN-LAST:event_jbUnidadProductoActionPerformed

    private void jtProductosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtProductosKeyReleased
        seleccionarProducto();
    }//GEN-LAST:event_jtProductosKeyReleased

    private void jtProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtProductosMouseClicked
        seleccionarProducto();
    }//GEN-LAST:event_jtProductosMouseClicked

    private void jbEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbEditarActionPerformed
        byte x = 3;
        botones(x);
        ljEditar.setText("1");       
    }//GEN-LAST:event_jbEditarActionPerformed

    private void jbNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbNuevoActionPerformed
        byte x = 1;
        botones(x);
        ljEditar.setText("0");
    }//GEN-LAST:event_jbNuevoActionPerformed

    private void jbCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCancelarActionPerformed
        byte x = 4;
        botones(x);
        ljEditar.setText("0");
    }//GEN-LAST:event_jbCancelarActionPerformed

    private void jbSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSalirActionPerformed
        dispose();
    }//GEN-LAST:event_jbSalirActionPerformed

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        
    }//GEN-LAST:event_formFocusGained

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        llenarTablaProductos();
    }//GEN-LAST:event_formWindowGainedFocus

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
            java.util.logging.Logger.getLogger(FormProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormProducto().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelComponentes;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JButton jbEditar;
    private javax.swing.JButton jbGuardar;
    private javax.swing.JButton jbNuevo;
    private javax.swing.JButton jbSalir;
    private javax.swing.JButton jbUnidadProducto;
    private javax.swing.JComboBox<String> jcFamilia;
    private javax.swing.JComboBox<String> jcLaboratorio;
    private javax.swing.JCheckBox jchCaducidad;
    private javax.swing.JCheckBox jchControlStock;
    private javax.swing.JCheckBox jchEstado;
    private javax.swing.JLabel jlIdFamilia;
    private javax.swing.JLabel jlIdLaboratorio;
    private javax.swing.JLabel jlIdRubroProducto;
    private javax.swing.JLabel jlTituloFormulario;
    private javax.swing.JTable jtProductos;
    private javax.swing.JTextField jtxtCriterio;
    private javax.swing.JTextField jtxtIndicaciones;
    private javax.swing.JTextField jtxtNombreProducto;
    private javax.swing.JTextField jtxtPrincipioActivo;
    private javax.swing.JLabel ljEditar;
    // End of variables declaration//GEN-END:variables

    private void seleccionarElementoLaboratorio() {
        
        jlIdLaboratorio.setVisible(false);
        String sel = null;        
        String comp = "Sel";
        HashMap<String, Integer> map = laboratorioDAO.laboratorioClaveValor();
            
        try {
            sel = jcLaboratorio.getSelectedItem().toString();

//            System.out.println("elemento seleccionado "+ sel);

            if(sel.equals(comp)){
                jlIdLaboratorio.setText("0");
            }
            else{
                jlIdLaboratorio.setText(map.get(sel).toString());
            }
        } catch (Exception e) {
        }

        
    }

    private void seleccionarElementoFamilia() {
        jlIdFamilia.setVisible(false);
        String sel = null;        
        String comp = "Sel";
        HashMap<String, Integer> map = familiaDAO.familiaClaveValor();
            
        try {
            sel = jcFamilia.getSelectedItem().toString();

            if(sel.equals(comp)){
                jlIdFamilia.setText("0");
            }
            else{
                jlIdFamilia.setText(map.get(sel).toString());
            }
        } catch (Exception e) {
        }
    }
    
    private Producto getProducto(int id){        
        return productoDAO.getProductoById(id);
    }
    
    private void seleccionarProducto() {
        int fila = jtProductos.getSelectedRow();
        idProducto = Integer.valueOf(jtProductos.getValueAt(fila, 0).toString());                
        Producto p = new Producto();        
        p = getProducto(idProducto);

        jcLaboratorio.setSelectedItem(p.getNombreLaboratorio());
        
        jcFamilia.setSelectedItem(p.getNombreFamilia());
        
        jtxtNombreProducto.setText(p.getDescripcion());
        jtxtPrincipioActivo.setText(p.getPrincipioActivo());
        jtxtIndicaciones.setText(p.getIndicaciones());
        
        jchEstado.setSelected(Boolean.parseBoolean(jtProductos.getValueAt(fila, 8).toString()));
        
        boolean aux = false;
        aux = (Boolean.parseBoolean(jtProductos.getValueAt(fila, 9).toString()))?true:false;        
        jchControlStock.setSelected(aux);        
        aux = (Boolean.parseBoolean(jtProductos.getValueAt(fila, 10).toString()))?true:false;        
        jchCaducidad.setSelected(aux);        
    }
    
    public void botones(byte x){
        switch(x){
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

    private void limpiar() {
        llenarComboLaboratorio();
        llenarComboFamilia();
        jtxtNombreProducto.setText("");
        jchControlStock.setSelected(false);
        jchEstado.setSelected(false);
        jchCaducidad.setSelected(false);
    }
}
