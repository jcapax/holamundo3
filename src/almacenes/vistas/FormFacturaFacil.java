/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package almacenes.vistas;

import CodigoControl.ControlCode;
import almacenes.conectorDB.DataBaseSqlite;
import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.DetalleFacturaFacil;
import almacenes.model.FacturaVenta;
import com.mxrck.autocompleter.TextAutoCompleter;
import dao.FacturaDAO;
import dao.FacturaDAOImpl;
import dao.FacturaFacilDAO;
import dao.FacturaFacilDAOImpl;
import dao.FacturaVentaDAO;
import dao.FacturaVentaDAOImpl;
import dao.SucursalDAO;
import dao.SucursalDAOImpl;
import dao.TemporalDAOImpl;
import dao.UtilsDAO;
import dao.UtilsDAOImpl;
import dao.reportes.ReporteFacturacionDAOImpl;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author jcapax
 */
public class FormFacturaFacil extends javax.swing.JFrame {

    /**
     * Creates new form FormRubro
     */
    private DatabaseUtils databaseUtils;
    private Connection connectionDB, connectionTemp;
    DefaultTableModel dtm;
    
    private byte idLugar;
    private String usuario;
    
    private TextAutoCompleter ac;
    
    private ArrayList<DetalleFacturaFacil> listaDetalleFF;
    
    private FacturaFacilDAO facilDAO;
    private TemporalDAOImpl tempDAOImpl;
    
    public FormFacturaFacil(Connection connectionDB, byte idLugar, boolean config, String usuario) {
        initComponents();
        this.setLocationRelativeTo(null);
        headerTabla();
        
        this.databaseUtils = new DatabaseUtils();
        this.connectionDB = connectionDB;
        this.usuario = usuario;
        
        this.idLugar = idLugar;
        
        abrirConexionTemp();
        listaDetalleFF = new ArrayList<DetalleFacturaFacil>();
        facilDAO = new FacturaFacilDAOImpl(connectionDB);
        
        //generarAutocompletado();
        llenarCombo();        
        //ac = new TextAutoCompleter(jtxtDetalle);
        
        llenarComboSucursal();
        AutoCompleteDecorator.decorate(jcomboDetalle);
        limpiarComponentes();
        jbFacturar.setEnabled(false);
        
        
        
        jlNroSucursal.setVisible(false);        
    }
    
    public void llenarCombo(){
        List<String> list = new ArrayList<>();
        
        list = facilDAO.getListaProductosAutocompletado();
        String aux = null;
                
        for(int i=0; i<list.size(); i++){
            aux = list.get(i);
            jcomboDetalle.addItem(aux);
        }  
    }
    
    public void abrirConexionTemp() {
        DataBaseSqlite sqLite = new DataBaseSqlite();
        connectionTemp = sqLite.conexion();

        System.err.println(connectionTemp);
        
        tempDAOImpl = new TemporalDAOImpl(connectionTemp);
        tempDAOImpl.vaciarDetalleFacturaFacilTemp();
    }
     
    public FormFacturaFacil() {
        initComponents();
    }
    
    public void headerTabla(){
        Font f = new Font("Times New Roman", Font.BOLD, 24);
        
        jtDetalleFacturaFacil.getTableHeader().setFont(f);
        jtDetalleFacturaFacil.getTableHeader().setBackground(Color.orange);
    }
    
    public void generarAutocompletado(){
        /*
        List<String> list = new ArrayList<>();
        
        list = facilDAO.getListaProductosAutocompletado();
        String aux = null;
        ac = new TextAutoCompleter(jcomboDetalle);
        ac.removeAllItems();
                
        for(int i=0; i<list.size(); i++){
            aux = list.get(i);
            ac.addItem(aux);
        } 
        */
    }
    
    public void limpiarComponentes(){
        jtxtCantidad.setText("");
        jcomboDetalle.setSelectedIndex(-1);
        jtxtValorTotal.setText("");
        jtxtValorUnitario.setText("");
        jcomboDetalle.setFocusable(true);
        jtxtNit.setText("");
        jtxtRazonSocial.setText("");
    }
    
    public void llenarTablaDetalleFacturaFacil(){
        ArrayList<DetalleFacturaFacil> lista = new ArrayList<>();
        DetalleFacturaFacil facil = new DetalleFacturaFacil();
        
        dtm = (DefaultTableModel) this.jtDetalleFacturaFacil.getModel();
        dtm.setRowCount(0);
        
        jtDetalleFacturaFacil.setModel(dtm);
        
        lista = tempDAOImpl.getListaDetalleFacturaFacilTemporal();
        
        Object[] fila = new Object[5];
        
        for(int i=0; i< lista.size(); i++){
            fila[0] = lista.get(i).getId();
            fila[1] = lista.get(i).getDetalle();
            fila[2] = lista.get(i).getCantidad();
            fila[3] = lista.get(i).getValorUnitario();
            fila[4] = lista.get(i).getValorTotal();            
            
            dtm.addRow(fila);
        }
        
        jtDetalleFacturaFacil.setModel(dtm);
        
        totalTemporal();
    }
    
    public void agregarDetalleFacturaFacil(){
        DetalleFacturaFacil facil = new DetalleFacturaFacil();
        
        facil.setId(1);
        facil.setDetalle(jcomboDetalle.getSelectedItem().toString().trim());
        facil.setCantidad(Double.valueOf(jtxtCantidad.getText().toString()));
        facil.setValorUnitario(Double.valueOf(jtxtValorUnitario.getText().toString()));
        facil.setValorTotal(Double.valueOf(jtxtValorTotal.getText().toString()));
                
        
        TemporalDAOImpl temp = new TemporalDAOImpl(connectionTemp);
        temp.insertarDetalleFacturaFacilTemp(facil);
        
        llenarTablaDetalleFacturaFacil();              
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
        jtDetalleFacturaFacil = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jbAgregarDetalle = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jtxtValorUnitario = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jtxtValorTotal = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jtxtCantidad = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jcomboDetalle = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jbFacturar = new javax.swing.JButton();
        jlnit = new javax.swing.JLabel();
        jtxtNit = new javax.swing.JTextField();
        jlRazonSocial = new javax.swing.JLabel();
        jtxtRazonSocial = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jcSucursal = new javax.swing.JComboBox<String>();
        jlNroSucursal = new javax.swing.JLabel();
        jtxttotalTemp = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jtDetalleFacturaFacil.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jtDetalleFacturaFacil.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Detalle", "Cantidad", "P/Unit", "P/Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtDetalleFacturaFacil.setRowHeight(24);
        jtDetalleFacturaFacil.setRowMargin(2);
        jtDetalleFacturaFacil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtDetalleFacturaFacilKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtDetalleFacturaFacilKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jtDetalleFacturaFacil);
        if (jtDetalleFacturaFacil.getColumnModel().getColumnCount() > 0) {
            jtDetalleFacturaFacil.getColumnModel().getColumn(0).setMinWidth(0);
            jtDetalleFacturaFacil.getColumnModel().getColumn(0).setPreferredWidth(0);
            jtDetalleFacturaFacil.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        jbAgregarDetalle.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jbAgregarDetalle.setText("Agregar");
        jbAgregarDetalle.setFocusable(false);
        jbAgregarDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAgregarDetalleActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(153, 0, 51));
        jLabel4.setText("P/Unit");

        jtxtValorUnitario.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jtxtValorUnitario.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtxtValorUnitario.setNextFocusableComponent(jtxtValorTotal);
        jtxtValorUnitario.setOpaque(false);
        jtxtValorUnitario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtxtValorUnitarioKeyReleased(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(153, 0, 51));
        jLabel3.setText("Total");

        jtxtValorTotal.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jtxtValorTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtxtValorTotal.setName(""); // NOI18N
        jtxtValorTotal.setNextFocusableComponent(jtDetalleFacturaFacil);
        jtxtValorTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtValorTotalActionPerformed(evt);
            }
        });
        jtxtValorTotal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxtValorTotalKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtxtValorTotalKeyReleased(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(153, 0, 51));
        jLabel6.setText("Cantidad");

        jtxtCantidad.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jtxtCantidad.setNextFocusableComponent(jtxtValorUnitario);
        jtxtCantidad.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtCantidadFocusLost(evt);
            }
        });
        jtxtCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtCantidadActionPerformed(evt);
            }
        });
        jtxtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtxtCantidadKeyReleased(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(153, 0, 51));
        jLabel1.setText("Detalle");

        jcomboDetalle.setEditable(true);
        jcomboDetalle.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jcomboDetalle.setNextFocusableComponent(jtxtCantidad);
        jcomboDetalle.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jcomboDetalleKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 351, Short.MAX_VALUE))
                    .addComponent(jcomboDetalle, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtxtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel6)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(jLabel4))
                            .addComponent(jtxtValorUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jtxtValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)))
                    .addComponent(jbAgregarDetalle, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jtxtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jtxtValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jtxtValorUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jcomboDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addComponent(jbAgregarDetalle)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jbFacturar.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jbFacturar.setText("Facturar");
        jbFacturar.setFocusable(false);
        jbFacturar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbFacturarActionPerformed(evt);
            }
        });
        jbFacturar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jbFacturarKeyPressed(evt);
            }
        });

        jlnit.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlnit.setForeground(new java.awt.Color(153, 0, 51));
        jlnit.setText("CI / NIT");

        jtxtNit.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jtxtNit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxtNitKeyPressed(evt);
            }
        });

        jlRazonSocial.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlRazonSocial.setForeground(new java.awt.Color(153, 0, 51));
        jlRazonSocial.setText("Razon Social");

        jtxtRazonSocial.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jlnit)
                        .addGap(0, 111, Short.MAX_VALUE))
                    .addComponent(jtxtNit))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlRazonSocial)
                    .addComponent(jtxtRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jbFacturar)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlnit)
                    .addComponent(jlRazonSocial))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtNit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbFacturar)))
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 0, 51));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("FACTURA");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(153, 0, 51));
        jLabel5.setText("Nro Sucursal");

        jcSucursal.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jcSucursal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jcSucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcSucursalActionPerformed(evt);
            }
        });

        jlNroSucursal.setText("0");

        jtxttotalTemp.setEditable(false);
        jtxttotalTemp.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jtxttotalTemp.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtxttotalTemp.setFocusable(false);
        jtxttotalTemp.setName(""); // NOI18N
        jtxttotalTemp.setNextFocusableComponent(jtDetalleFacturaFacil);
        jtxttotalTemp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxttotalTempActionPerformed(evt);
            }
        });
        jtxttotalTemp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxttotalTempKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtxttotalTempKeyReleased(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(153, 0, 51));
        jLabel7.setText("Total");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jcSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(23, 23, 23)
                        .addComponent(jlNroSucursal)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtxttotalTemp, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jlNroSucursal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jtxttotalTemp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel7))
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jtxtValorTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtValorTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtValorTotalActionPerformed

    private void jtxtValorTotalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtValorTotalKeyPressed
        //agregarDetalleFacturaFacil();
    }//GEN-LAST:event_jtxtValorTotalKeyPressed

    private void jtxtValorTotalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtValorTotalKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            jcomboDetalle.requestFocus();
            agregarDetalleFacturaFacil();
            limpiarComponentes();
        }else{         
            if((isNumeric(jtxtCantidad.getText())&&(isNumeric(jtxtValorTotal.getText())))){
                Double cantidad = Double.valueOf(jtxtCantidad.getText());
                Double valorTotal = Double.valueOf(jtxtValorTotal.getText());
                Double valorUnitario = valorTotal / cantidad * 1.0;
                DecimalFormat df = new DecimalFormat("###.##");
                
                jtxtValorUnitario.setText(String.valueOf(df.format(valorUnitario)));         
            }
        }
        
         if(evt.getKeyCode() == KeyEvent.VK_TAB){
             jtDetalleFacturaFacil.requestFocus();
             jtDetalleFacturaFacil. editCellAt(0, 0);
         }
        
    }//GEN-LAST:event_jtxtValorTotalKeyReleased

    private void jtxtCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtCantidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtCantidadActionPerformed

    private void jtxtNitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtNitKeyPressed
        if ((evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER)&&(jtxtNit.getText().length()>0)) {
            if (jtxtNit.getText().equals("0")) {
                jtxtRazonSocial.setText("SIN NOMBRE");
            } else {
                FacturaVentaDAOImpl fac = new FacturaVentaDAOImpl(connectionDB);
                jtxtRazonSocial.setText(fac.getRazonSocialFactura(jtxtNit.getText()));
                jtxtRazonSocial.requestFocus();
            }
        }
    }//GEN-LAST:event_jtxtNitKeyPressed

    private void jbAgregarDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAgregarDetalleActionPerformed
        agregarDetalleFacturaFacil();
        limpiarComponentes();
    }//GEN-LAST:event_jbAgregarDetalleActionPerformed

    private void jbFacturarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbFacturarActionPerformed
        boolean shift = false;
        boolean aux = true;
        if((evt.getModifiers() & InputEvent.SHIFT_MASK)!=0){
            shift = true;
        }
        
        if((jtxtNit.getText().trim().length() == 0) && aux){
            aux = false;
            shift = false;
            JOptionPane.showMessageDialog(null, "Registrar NIT o CI!!!");
            jtxtNit.requestFocus();
        }
        
        if((jtxtRazonSocial.getText().trim().length() == 0)&& aux){
            shift = false;
            JOptionPane.showMessageDialog(null, "Registrar Razon Social!!!");
            jtxtRazonSocial.requestFocus();
        }
        
        if(shift){            
            SucursalDAO suc = new SucursalDAOImpl(connectionDB);
            byte idSucursal = Byte.valueOf(jlNroSucursal.getText().trim());                    

            FacturaVentaDAO facturaVentaDAO = new FacturaVentaDAOImpl(connectionDB);                    
            
            FacturaDAO fac = new FacturaDAOImpl(connectionDB);
            int nroIdFactura = 0;
            try {
                nroIdFactura = registrarFactura(idSucursal);
                facilDAO.insertarDetalleFacturaFacil(tempDAOImpl.getListaDetalleFacturaFacilTemporal(), nroIdFactura);

                ReporteFacturacionDAOImpl repFactura = new ReporteFacturacionDAOImpl(connectionDB, "XXX");

                //repFactura.VistaPreviaFacturaVenta(nroIdFactura, facDaoImpl.getCadenaCodigoQr(idTransaccion), fact.getImporteTotal());
                repFactura.VistaPreviaFacturaFacil(nroIdFactura, 
                facturaVentaDAO.getCadenaCodigoQrFacturaFacil(nroIdFactura), 
                tempDAOImpl.totalTemporal());

                tempDAOImpl.vaciarDetalleFacturaFacilTemp();
                llenarTablaDetalleFacturaFacil();  
                jcSucursal.setRequestFocusEnabled(true);
                
            } catch (ParseException ex) {
                Logger.getLogger(FormFacturaFacil.class.getName()).log(Level.SEVERE, null, ex);
            }
            limpiarComponentes();
        }
    }//GEN-LAST:event_jbFacturarActionPerformed

    private void jtxtCantidadKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtCantidadKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            
            jtxtValorUnitario.requestFocus();
            
           /* 
            boolean aux = false;
        
            aux = isNumeric(jtxtCantidad.getText());
            if((!aux)&&(jtxtCantidad.getText().length()>0)){
                JOptionPane.showMessageDialog(this, "Registro inválido!!!");
                jtxtCantidad.requestFocus();
            }else{            
                jtxtValorUnitario.requestFocus();
            }
            */
        }
        if((evt.getKeyCode() == KeyEvent.VK_SHIFT)&&(evt.getKeyCode() == KeyEvent.VK_ENTER)){
            jtDetalleFacturaFacil.requestFocus();
        }
    }//GEN-LAST:event_jtxtCantidadKeyReleased

    private void jtxtValorUnitarioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtValorUnitarioKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            jtxtValorTotal.requestFocus();
        }else{
            if((isNumeric(jtxtCantidad.getText())&&(isNumeric(jtxtValorUnitario.getText())))){
                Double cantidad = Double.valueOf(jtxtCantidad.getText());
                Double valorUnitario = Double.valueOf(jtxtValorUnitario.getText());
                Double valorTotal = cantidad * valorUnitario * 1.0;
                DecimalFormat df = new DecimalFormat("###.##");
                
                jtxtValorTotal.setText(String.valueOf(df.format(valorTotal)));
            }
        }
    }//GEN-LAST:event_jtxtValorUnitarioKeyReleased

    private void jtxtCantidadFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtCantidadFocusLost
        
    }//GEN-LAST:event_jtxtCantidadFocusLost

    private void jtDetalleFacturaFacilKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtDetalleFacturaFacilKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            jtxtNit.requestFocus();
        }
    }//GEN-LAST:event_jtDetalleFacturaFacilKeyReleased

    private void jtDetalleFacturaFacilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtDetalleFacturaFacilKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_DELETE){
            if(tempDAOImpl.getListaDetalleFacturaFacilTemporal().size()>0){
                int filSel = jtDetalleFacturaFacil.getSelectedRow();            
                int id = (int) jtDetalleFacturaFacil.getValueAt(filSel, 0);

                tempDAOImpl.eliminarProductoDetalleFacturaFacil(id);
                llenarTablaDetalleFacturaFacil();
                jcomboDetalle.requestFocus();
            }
        }
    }//GEN-LAST:event_jtDetalleFacturaFacilKeyPressed

    private void jbFacturarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jbFacturarKeyPressed
        
    }//GEN-LAST:event_jbFacturarKeyPressed

    private void jcSucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcSucursalActionPerformed
        seleccionarElementoSucursal();
    }//GEN-LAST:event_jcSucursalActionPerformed

    private void jtxttotalTempActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxttotalTempActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxttotalTempActionPerformed

    private void jtxttotalTempKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxttotalTempKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxttotalTempKeyPressed

    private void jtxttotalTempKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxttotalTempKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxttotalTempKeyReleased

    private void jcomboDetalleKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jcomboDetalleKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            boolean aux = false;
        
            aux = isNumeric(jtxtCantidad.getText());
            if((!aux)&&(jtxtCantidad.getText().length()>0)){
                JOptionPane.showMessageDialog(this, "Registro inválido!!!");
                jtxtCantidad.requestFocus();
            }else{            
                jtxtCantidad.requestFocus();            
            }
        }
        if((evt.getKeyCode() == KeyEvent.VK_SHIFT)&&(evt.getKeyCode() == KeyEvent.VK_ENTER)){
            jtDetalleFacturaFacil.requestFocus();
        }
    }//GEN-LAST:event_jcomboDetalleKeyReleased

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
            java.util.logging.Logger.getLogger(FormFacturaFacil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormFacturaFacil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormFacturaFacil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormFacturaFacil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormFacturaFacil().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbAgregarDetalle;
    private javax.swing.JButton jbFacturar;
    private javax.swing.JComboBox<String> jcSucursal;
    private javax.swing.JComboBox jcomboDetalle;
    private javax.swing.JLabel jlNroSucursal;
    private javax.swing.JLabel jlRazonSocial;
    private javax.swing.JLabel jlnit;
    private javax.swing.JTable jtDetalleFacturaFacil;
    private javax.swing.JTextField jtxtCantidad;
    private javax.swing.JTextField jtxtNit;
    private javax.swing.JTextField jtxtRazonSocial;
    private javax.swing.JTextField jtxtValorTotal;
    private javax.swing.JTextField jtxtValorUnitario;
    private javax.swing.JTextField jtxttotalTemp;
    // End of variables declaration//GEN-END:variables

    private int registrarFactura(byte idSucursal) throws ParseException {
        FacturaVentaDAOImpl facDaoImpl = new FacturaVentaDAOImpl(connectionDB);
        UtilsDAO utilsDAO = new UtilsDAOImpl(connectionDB);
        
        java.sql.Date fechaServidor = utilsDAO.getFechaActual();
        
        ControlCode controlCode = new ControlCode();
        SucursalDAO sucursalDAO = new SucursalDAOImpl(connectionDB);

        String nit = jtxtNit.getText().trim();
        String razonSocial = jtxtRazonSocial.getText().trim().toUpperCase();

        String codigoControl = "";
        int correlativoSucursal = 1;

        int especificacion = 1;
        String estado = "V";
        
        String nroAutorizacion = facDaoImpl.getNroAutorizacion(idSucursal);
        
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(fechaServidor);
        
        int day  = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH)+1;
        int year   = calendar.get(Calendar.YEAR);
        
        String anno = String.valueOf(year);
        String mes = String.valueOf(month);
        if(mes.length() == 1){
            mes = '0'+mes;
        }
        String dia = String.valueOf(day);        
        if(dia.length() == 1){
            dia = '0'+dia;
        }
        
        String fechaCadena = anno+'-'+mes+'-'+dia;  
        
        java.sql.Date fechaLimiteEmision = facDaoImpl.getFechaLimiteEmision(nroAutorizacion);
        int idDosificacion = facDaoImpl.getIdDosificacion(idSucursal);
        int nroFactura = facDaoImpl.getNewNroFactura(nroAutorizacion);
        String llaveDosf = facDaoImpl.getLlaveDosificacion(nroAutorizacion);
        
        double importeTotal = tempDAOImpl.totalTemporal();
        double importeExportaciones = 0;
        double importeIce = 0;
        double importeRebajas = 0;
        double importeSubTotal = importeTotal - importeExportaciones - importeIce;
        double importeVentasTasaCero = 0;
        double importeBaseDebitoFiscal = importeSubTotal;
        double debitoFiscal = importeBaseDebitoFiscal * 0.13;

        String auxFecha = anno+mes+dia;

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
        fact.setFechaFactura(fechaServidor);
        fact.setFechaLimiteEmision(fechaLimiteEmision);
        fact.setIdDosificacion(idDosificacion);
        fact.setIdSucursal(idSucursal);
        fact.setIdTransaccion(0);
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
        
        System.out.println("id dosificacion: "+fact.getIdDosificacion());

        FacturaVentaDAOImpl factDaoImpl = new FacturaVentaDAOImpl(connectionDB);
        factDaoImpl.insertarFacturaVenta(fact);

//        ReporteFacturacionDAOImpl repFactura = new ReporteFacturacionDAOImpl(connectionDB, estado);

//        repFactura.VistaPreviaFacturaFacil(, facDaoImpl.getCadenaCodigoQr(idTransaccion), fact.getImporteTotal());
        return facilDAO.getIdFacturaUltima();        
    }
    
    public static boolean isNumeric(String str) { 
        try {  
          Double.parseDouble(str);  
          return true;
        } catch(NumberFormatException e){  
          return false;  
        }  
    }
    
    public void llenarComboSucursal(){        
        String sel = "Sel";
        
        jcSucursal.removeAllItems();
        jcSucursal.addItem(sel);
        
        SucursalDAOImpl sucursalDAOImpl = new SucursalDAOImpl(connectionDB);
        
        HashMap<String, Integer> map = sucursalDAOImpl.sucursalClaveValor();

        for (String s : map.keySet()) {
            jcSucursal.addItem(s.toString());
        }   
    }
    
    public void totalTemporal(){
        Double totalTemp = tempDAOImpl.totalTemporal();
        jtxttotalTemp.setText(String.valueOf(totalTemp));
    }
    
    private void seleccionarElementoSucursal() {
        String sel = null;
        
        String comp = "Sel";
        
        SucursalDAOImpl sucursalDAOImpl = new SucursalDAOImpl(connectionDB);
        
        HashMap<String, Integer> map = sucursalDAOImpl.sucursalClaveValor();
            
        try {
            sel = jcSucursal.getSelectedItem().toString();

//            System.out.println("elemento seleccionado "+ sel);

            if(sel.equals(comp)){
                jlNroSucursal.setText("0");
                jbFacturar.setEnabled(false);
            }
            else{
                jlNroSucursal.setText(map.get(sel).toString());
                jbFacturar.setEnabled(true);
            }
        } catch (Exception e) {
        }
    }
}
