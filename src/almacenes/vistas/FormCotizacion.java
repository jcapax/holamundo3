/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package almacenes.vistas;

import almacenes.conectorDB.DataBaseSqlite;
import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.Caja;
import almacenes.model.DetalleTransaccion;
import almacenes.model.Temporal;
import almacenes.model.ListaProductos;
import almacenes.model.Transaccion;
import almacenes.model.UnidadProducto;
import dao.ArqueoDAOImpl;
import dao.CajaDAOImpl;
import dao.ClienteProveedorDAO;
import dao.ClienteProveedorDAOImpl;
import dao.ConfiguracionGeneralDAO;
import dao.ConfiguracionGeneralDAOImpl;
import dao.CreditoDAO;
import dao.CreditoDAOImpl;
import dao.DetalleTransaccionDAOImpl;
import dao.FacturaDAO;
import dao.FacturaDAOImpl;
import dao.FacturaVentaDAOImpl;
import dao.TemporalDAOImpl;
import dao.ProductoDAOImpl;
import dao.SucursalDAO;
import dao.SucursalDAOImpl;
import dao.TemporalDAO;
import dao.TransaccionDAOImpl;
import dao.UnidadMedidaDAO;
import dao.UnidadMedidaDAOImpl;
import dao.UnidadProductoDAO;
import dao.UnidadProductoDAOImlp;
import dao.VencimientoDAO;
import dao.VencimientoDAOImpl;
import dao.reportes.ReporteCreditoDAO;
import dao.reportes.ReporteCreditoDAOImpl;
import dao.reportes.ReporteFacturacionDAOImpl;
import dao.reportes.ReporteVentasDAO;
import dao.reportes.ReporteVentasDAOImpl;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TreeMap;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author jcapax
 */
public class FormCotizacion extends javax.swing.JFrame {

    private DatabaseUtils databaseUtils;
    private Connection connectionDB, connectionTemp;
    private DefaultTableModel dtm;
    private int idTipoTransaccion; // tipo de proceso q se ejecutara
    private byte idLugar;
    private byte idTerminal;
    private String usuario;
    private DecimalFormat df;
    private VencimientoDAO vencimientoDAO;
    private ConfiguracionGeneralDAO configuracionGeneralDAO;

//    DefaultTableModel dtm;
    public FormCotizacion(Connection connectionDB,
            int idTipoTransaccion,
            String usuario, byte idLugar, byte idTerminal) {

        initComponents();

        this.setLocationRelativeTo(null);

        df = new DecimalFormat("###,###.00");

        this.databaseUtils = new DatabaseUtils();
        this.connectionDB = connectionDB;
        this.idTipoTransaccion = idTipoTransaccion;        
        this.usuario = usuario;
        this.idLugar = idLugar;
        this.idTerminal = idTerminal;

        headerTabla();

        iniciarComponentes();

        abrirConexionTemp();

        createEnterKeybindings(jtProductos);
    }

    public void headerTabla() {
        Font f = new Font("Times New Roman", Font.BOLD, 13);

        jtProductos.getTableHeader().setFont(f);
        jtProductos.getTableHeader().setBackground(Color.orange);

        jtTemporal.getTableHeader().setFont(f);
        jtTemporal.getTableHeader().setBackground(Color.orange);
    }

    public void iniciarComponentes() {
        
        ArqueoDAOImpl arq = new ArqueoDAOImpl(connectionDB);
        configuracionGeneralDAO = new ConfiguracionGeneralDAOImpl(connectionDB);

        llenarTablaProductos("");
        jtxtDetalle.setText("");
        
        jlIdProducto.setVisible(false);
        jlIdUnidadMedida.setVisible(false);
        jlidClienteProveedor.setText("0");
        jlidClienteProveedor.setVisible(false);

        switch (idTipoTransaccion) {
            case 10:
                jbTransaccion.setVisible(true);
                jlTituloFormulario.setText("COTIZACION");

                jtxtValorUnitario.setEnabled(false);
                jtxtValorUnitario.setEditable(false);

                jlDetalle.setVisible(true);
                jtxtDetalle.setVisible(true);

                break;
            default:
                System.err.println("nada");
        }

        jbTransaccion.setEnabled(true);
        
    }

    public boolean validarRegistros() {
        boolean aux = true;
        if (jtTemporal.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No existen productos para ejectuar la transaccion!!!");
            aux = false;
        }        
        
        return aux;
    }

    public void registrarCotizacion() {

        int idTransaccion = 0;        
        int idClienteProveedor = Integer.parseInt(jlidClienteProveedor.getText());
        
        idTransaccion = resgistrarTransaccion(idTipoTransaccion);        
        registrarDetalleTransaccion(idTransaccion);
        
        ReporteCreditoDAO rep = new ReporteCreditoDAOImpl(connectionDB, usuario);
        
        rep.vistaPreviaCotizacion(idTransaccion);
        
        limpiar();

        vaciarProductosTemporales();
        iniciarComponentes();
        
    }

    public void abrirConexionTemp() {
        DataBaseSqlite sqLite = new DataBaseSqlite();
        connectionTemp = sqLite.conexion();

        TemporalDAOImpl tempDAOImpl = new TemporalDAOImpl(connectionTemp);
        tempDAOImpl.vaciarProductoTemp();

    }

    public void calcularImportTotalTemp() {
        double importeTotal = 0;
        TemporalDAOImpl tempDAO = new TemporalDAOImpl(connectionTemp);
        importeTotal = tempDAO.totalProductosTemp();
        jtxtTotalTransaccion.setText(String.valueOf(df.format(importeTotal)));
    }

    public void eliminarProductoTemporal() {
        TemporalDAOImpl tempDAO = new TemporalDAOImpl(connectionTemp);

        int filSel = jtTemporal.getSelectedRow();
        int idProducto = (int) jtTemporal.getValueAt(filSel, 0);
        int idUnidadMedida = (int) jtTemporal.getValueAt(filSel, 1);

        tempDAO.eliminarProdcutoTemp(idProducto, idUnidadMedida);

        llenarTablaTemporal();
    }

    public void llenarTablaTemporal() {

        TemporalDAOImpl tempDAO = new TemporalDAOImpl(connectionTemp);

        ArrayList<Temporal> t = new ArrayList<>();

        t = tempDAO.getListaTemporal();

        dtm = (DefaultTableModel) this.jtTemporal.getModel();
        dtm.setRowCount(0);

        jtTemporal.setModel(dtm);

        Object[] fila = new Object[9];

        for (int i = 0; i < t.size(); i++) {
            fila[0] = t.get(i).getIdProducto();
            fila[1] = t.get(i).getIdUnidadMedida();
            fila[2] = t.get(i).getNombreProducto();
            fila[3] = t.get(i).getSimbolo();
            fila[4] = t.get(i).getCantidad();
            fila[5] = t.get(i).getValorUnitario();
            fila[6] = t.get(i).getValorSubTotal();
            fila[7] = t.get(i).getDescuento();            
            fila[8] = t.get(i).getValorTotal();

            dtm.addRow(fila);
        }
        
        TableColumnModel columnModel = jtTemporal.getColumnModel();

        TableColumn colCantidad = columnModel.getColumn(4);
        TableColumn colPrecioUnitario = columnModel.getColumn(5);
        TableColumn colPrecioTotal = columnModel.getColumn(6);
        

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(jLabel1.RIGHT);

        colCantidad.setCellRenderer(renderer);
        colPrecioUnitario.setCellRenderer(renderer);
        colPrecioTotal.setCellRenderer(renderer);

        jtTemporal.setModel(dtm);

        calcularImportTotalTemp();
    }

    public void insertarTemp() {

        UnidadMedidaDAO unidadMedidaDAO = new UnidadMedidaDAOImpl(connectionDB);
        UnidadProductoDAO unidadProductoDAO = new UnidadProductoDAOImlp(connectionDB);
        
        Temporal temp = new Temporal();

        int idProducto = Integer.valueOf(jlIdProducto.getText());
        int idUnidadMedida = Integer.valueOf(jlIdUnidadMedida.getText());
        String nombreProducto = jtxtNombreProducto.getText();
        String simbolo = unidadMedidaDAO.getSimboloUnidadMedida(idUnidadMedida);
        double cantidad = Double.valueOf(jtxtCantidad.getText());
        double descuento = 0.0;
        double valorSubTotal = 0.0;
        if(jtxtDescuento.getText().trim().length()>0){
            descuento = Double.valueOf(jtxtDescuento.getText());
        }         
        
        double valorUnitario = 0.0;
        if(idTipoTransaccion == 2){
            valorUnitario = unidadProductoDAO.getValorUnitarioDescuento(idProducto, idUnidadMedida, cantidad);
        }
        if(valorUnitario == 0){
            valorUnitario = Double.valueOf(jtxtValorUnitario.getText());
        }
        
        valorSubTotal = cantidad * valorUnitario;
        
        byte descuentoProUnidadProducto;
        descuentoProUnidadProducto = configuracionGeneralDAO.getDescuentoPorUnidadProducto();
        if(descuentoProUnidadProducto == 1){
            descuento = descuento * cantidad;
        }
                
        double valorTotal = valorSubTotal - descuento;
        
        String tipoValor = "N";// normal

        temp.setCantidad(cantidad);
        temp.setIdProducto(idProducto);
        temp.setIdUnidadMedida(idUnidadMedida);
        temp.setNombreProducto(nombreProducto);
        temp.setSimbolo(simbolo);
        temp.setTipoValor(tipoValor);
        temp.setValorTotal(valorTotal);
        temp.setValorUnitario(valorUnitario);
        temp.setValorSubTotal(valorSubTotal);
        temp.setDescuento(descuento);

        TemporalDAO tempDAOImpl = new TemporalDAOImpl(connectionTemp);
        tempDAOImpl.insertarProductoTemp(temp);

        llenarTablaTemporal();
    }

    public void seleccionarProducto() {
        
        UnidadMedidaDAO unidadMedidaDAO = new UnidadMedidaDAOImpl(connectionDB);

        UnidadProductoDAOImlp up = new UnidadProductoDAOImlp(connectionDB);

        int filSel = jtProductos.getSelectedRow();

        int idProducto = (int) jtProductos.getValueAt(filSel, 0);
        int idUnidadMedida = (int) jtProductos.getValueAt(filSel, 1);
        String nombreProducto = jtProductos.getValueAt(filSel, 2).toString();
        Double valorUnitario = (double) jtProductos.getValueAt(filSel, 5);

        double stock = up.getStockProducto(idProducto, idUnidadMedida, idLugar);

        jtxtNombreProducto.setText(nombreProducto);
        jtxtValorUnitario.setText(valorUnitario.toString());
        jlIdProducto.setText(String.valueOf(idProducto));
        jlIdUnidadMedida.setText(String.valueOf(idUnidadMedida));
        
        jtxtUnidad.setText(unidadMedidaDAO.getSimboloUnidadMedida(idUnidadMedida));

        jlStockProducto.setText(String.valueOf(stock));

    }
    
    public void seleccionarProductoCodigoAdjunto() {
        
        UnidadMedidaDAO unidadMedidaDAO = new UnidadMedidaDAOImpl(connectionDB);

        UnidadProductoDAOImlp up = new UnidadProductoDAOImlp(connectionDB);

        String codigoAdjunto = jtxtxCriterio.getText().trim();
        codigoAdjunto = codigoAdjunto.replace("'", "-");
        UnidadProducto producto = new UnidadProducto();
        producto = up.getProductoCodigoBarras(codigoAdjunto);
        
        

        int idProducto = producto.getIdProdcuto();
        int idUnidadMedida = producto.getIdUnidadMedida();
        String nombreProducto = producto.getNombreProducto();
        Double valorUnitario = producto.getPrecioVenta();

        double stock = up.getStockProducto(idProducto, idUnidadMedida, idLugar);

        jtxtNombreProducto.setText(nombreProducto);
        jtxtValorUnitario.setText(valorUnitario.toString());
        jlIdProducto.setText(String.valueOf(idProducto));
        jlIdUnidadMedida.setText(String.valueOf(idUnidadMedida));
        
        jtxtUnidad.setText(unidadMedidaDAO.getSimboloUnidadMedida(idUnidadMedida));

        jlStockProducto.setText(String.valueOf(stock));

    }

    public void llenarTablaProductos(String criterio) {
        ProductoDAOImpl prodDAOImpl = new ProductoDAOImpl(connectionDB);

        ArrayList<ListaProductos> lProd = new ArrayList<ListaProductos>();

        lProd = prodDAOImpl.getListaProductosVenta(criterio);

        dtm = (DefaultTableModel) this.jtProductos.getModel();
        dtm.setRowCount(0);

        jtProductos.setModel(dtm);

        Object[] fila = new Object[19];

        for (int i = 0; i < lProd.size(); i++) {
            fila[0] = lProd.get(i).getId();
            fila[1] = lProd.get(i).getIDUNIDADMEDIDA();
            fila[2] = lProd.get(i).getDescripcion();
            fila[3] = lProd.get(i).getNombreUnidadMedida();
            fila[4] = lProd.get(i).getMarca();
            fila[5] = lProd.get(i).getPRECIOVENTA();
            dtm.addRow(fila);
        }

        jtProductos.setAutoscrolls(false);
        jtProductos.setModel(dtm);
    }

    private void rebajarRegistros(){
        TemporalDAO temp = new TemporalDAOImpl(connectionTemp);
        temp.reducir10();
        llenarTablaTemporal();
    }

    public void registrarDetalleTransaccion(int idTransaccion) {

        DetalleTransaccionDAOImpl detTranDAOImpl = new DetalleTransaccionDAOImpl(connectionDB);

        int idProducto = 0;
        int idUnidadMedida = 0;
        double cantidad = 0;
        double valorUnitario = 0;
        double valorSubTotal = 0.0;
        double descuento = 0.0;
        double valorTotal = 0;
        String tipoValor = "N";

        ArrayList<DetalleTransaccion> detTrans = new ArrayList<>();

        for (int fila = 0; fila < jtTemporal.getRowCount(); fila++) {
            idProducto = Integer.valueOf(jtTemporal.getValueAt(fila, 0).toString());
            idUnidadMedida = Integer.valueOf(jtTemporal.getValueAt(fila, 1).toString());
            cantidad = Double.valueOf(jtTemporal.getValueAt(fila, 4).toString());
            valorUnitario = Double.valueOf(jtTemporal.getValueAt(fila, 5).toString());
            valorSubTotal = Double.valueOf(jtTemporal.getValueAt(fila, 6).toString());
            descuento = Double.valueOf(jtTemporal.getValueAt(fila, 7).toString());
            valorTotal = Double.valueOf(jtTemporal.getValueAt(fila, 8).toString());

            DetalleTransaccion dt = new DetalleTransaccion();

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

    public void limpiar() {
        dtm = (DefaultTableModel) this.jtTemporal.getModel();
        dtm.setRowCount(0);
        jtTemporal.setModel(dtm);

//        jtxtNit.setText("");
//        jtxtRazonSocial.setText("");

        jtxtTotalTransaccion.setText("");
    }

    public int resgistrarTransaccion(int idTipoTransaccion) {

        TransaccionDAOImpl transDaoImpl = new TransaccionDAOImpl(connectionDB);

        int nroTipoTransaccion = 0;
        int tipoMovimineto = transDaoImpl.getTipoMovimiento(idTipoTransaccion);
//        int idTerminal = 1;
        int idTransaccion = 0;
        String estado = "A";
        String detalle = jtxtDetalle.getText().toUpperCase();
//        String usuario = "SYS";
        java.util.Date hoy = new java.util.Date();
        java.sql.Date fecha = new java.sql.Date(hoy.getTime());

        nroTipoTransaccion = transDaoImpl.getNroTipoTransaccion(idTipoTransaccion);

//        descripcion = jtxtRazonSocial.getText().toUpperCase();

        Transaccion trans = new Transaccion(fecha, idTipoTransaccion, nroTipoTransaccion,
                idLugar, idTerminal, tipoMovimineto, estado, usuario, detalle);

        idTransaccion = transDaoImpl.insertarTransaccion(trans);

        if (idTransaccion != 0) {
//            System.out.println("transaccion registrada nro: " + idTransaccion);
        } else {
//            System.out.println("error en el registro");
        }

        return idTransaccion;
    }

    public void registrarEntregaTransaccion(int idEntregaTransaccion, int idTransaccion) {
        TransaccionDAOImpl transDaoImpl = new TransaccionDAOImpl(connectionDB);
        transDaoImpl.insertarEntregaTransaccion(idTransaccion, idEntregaTransaccion);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jlidClienteProveedor = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtProductos = new javax.swing.JTable();
        jToggleButton3 = new javax.swing.JToggleButton();
        jbSalir = new javax.swing.JToggleButton();
        jtxtxCriterio = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtTemporal = new javax.swing.JTable();
        jtxtEliminar = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jtxtTotalTransaccion = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jtxtNombreProducto = new javax.swing.JTextField();
        jtxtUnidad = new javax.swing.JTextField();
        jlIdProducto = new javax.swing.JLabel();
        jlIdUnidadMedida = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jlStockProducto = new javax.swing.JLabel();
        jbAgregar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jtxtValorUnitario = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jtxtDescuento = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jtxtCantidad = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jbTransaccion = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jlDetalle = new javax.swing.JLabel();
        jtxtDetalle = new javax.swing.JTextField();
        jlTituloFormulario = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jtProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "idProducto", "idUnidadMedida", "Producto", "Simbolo", "Marca", "P/Venta"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtProductosMouseClicked(evt);
            }
        });
        jtProductos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtProductosKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtProductosKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jtProductos);
        if (jtProductos.getColumnModel().getColumnCount() > 0) {
            jtProductos.getColumnModel().getColumn(0).setMinWidth(0);
            jtProductos.getColumnModel().getColumn(0).setPreferredWidth(0);
            jtProductos.getColumnModel().getColumn(0).setMaxWidth(0);
            jtProductos.getColumnModel().getColumn(1).setMinWidth(0);
            jtProductos.getColumnModel().getColumn(1).setPreferredWidth(0);
            jtProductos.getColumnModel().getColumn(1).setMaxWidth(0);
            jtProductos.getColumnModel().getColumn(2).setMinWidth(200);
            jtProductos.getColumnModel().getColumn(2).setPreferredWidth(200);
            jtProductos.getColumnModel().getColumn(2).setMaxWidth(200);
            jtProductos.getColumnModel().getColumn(3).setMinWidth(50);
            jtProductos.getColumnModel().getColumn(3).setPreferredWidth(50);
            jtProductos.getColumnModel().getColumn(3).setMaxWidth(50);
            jtProductos.getColumnModel().getColumn(4).setMinWidth(60);
            jtProductos.getColumnModel().getColumn(4).setPreferredWidth(60);
            jtProductos.getColumnModel().getColumn(4).setMaxWidth(60);
            jtProductos.getColumnModel().getColumn(5).setMinWidth(60);
            jtProductos.getColumnModel().getColumn(5).setPreferredWidth(60);
            jtProductos.getColumnModel().getColumn(5).setMaxWidth(60);
        }

        jToggleButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Clear-icon.png"))); // NOI18N
        jToggleButton3.setText("Limpiar");
        jToggleButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton3ActionPerformed(evt);
            }
        });

        jbSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/close_window.png"))); // NOI18N
        jbSalir.setText("Salir");
        jbSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSalirActionPerformed(evt);
            }
        });

        jtxtxCriterio.setNextFocusableComponent(jtProductos);
        jtxtxCriterio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxtxCriterioKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtxtxCriterioKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxtxCriterioKeyTyped(evt);
            }
        });

        jLabel2.setForeground(new java.awt.Color(153, 0, 51));
        jLabel2.setText("Criterio de Busqueda");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jtxtxCriterio)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jbSalir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jToggleButton3))
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jtxtxCriterio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jToggleButton3)
                    .addComponent(jbSalir))
                .addGap(0, 0, 0))
        );

        jtTemporal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "idProducto", "idUnidadMedida", "Descripcion", "Simbolo", "Cantidad", "P/Unit", "Sub Total", "Desc.", "P/Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jtTemporal);
        if (jtTemporal.getColumnModel().getColumnCount() > 0) {
            jtTemporal.getColumnModel().getColumn(0).setMinWidth(0);
            jtTemporal.getColumnModel().getColumn(0).setPreferredWidth(0);
            jtTemporal.getColumnModel().getColumn(0).setMaxWidth(0);
            jtTemporal.getColumnModel().getColumn(1).setMinWidth(0);
            jtTemporal.getColumnModel().getColumn(1).setPreferredWidth(0);
            jtTemporal.getColumnModel().getColumn(1).setMaxWidth(0);
            jtTemporal.getColumnModel().getColumn(3).setMinWidth(80);
            jtTemporal.getColumnModel().getColumn(3).setPreferredWidth(80);
            jtTemporal.getColumnModel().getColumn(3).setMaxWidth(80);
            jtTemporal.getColumnModel().getColumn(4).setMinWidth(60);
            jtTemporal.getColumnModel().getColumn(4).setPreferredWidth(60);
            jtTemporal.getColumnModel().getColumn(4).setMaxWidth(60);
            jtTemporal.getColumnModel().getColumn(5).setMinWidth(80);
            jtTemporal.getColumnModel().getColumn(5).setPreferredWidth(80);
            jtTemporal.getColumnModel().getColumn(5).setMaxWidth(80);
            jtTemporal.getColumnModel().getColumn(6).setMinWidth(80);
            jtTemporal.getColumnModel().getColumn(6).setPreferredWidth(80);
            jtTemporal.getColumnModel().getColumn(6).setMaxWidth(80);
            jtTemporal.getColumnModel().getColumn(7).setMinWidth(60);
            jtTemporal.getColumnModel().getColumn(7).setPreferredWidth(60);
            jtTemporal.getColumnModel().getColumn(7).setMaxWidth(60);
            jtTemporal.getColumnModel().getColumn(8).setMinWidth(100);
            jtTemporal.getColumnModel().getColumn(8).setPreferredWidth(100);
            jtTemporal.getColumnModel().getColumn(8).setMaxWidth(100);
        }

        jtxtEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/trash_icon.png"))); // NOI18N
        jtxtEliminar.setText("Eliminar");
        jtxtEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtEliminarActionPerformed(evt);
            }
        });

        jLabel8.setForeground(new java.awt.Color(153, 0, 51));
        jLabel8.setText("Total");

        jtxtTotalTransaccion.setEditable(false);
        jtxtTotalTransaccion.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtxtTotalTransaccion.setEnabled(false);

        jLabel1.setForeground(new java.awt.Color(153, 0, 51));
        jLabel1.setText("Producto");

        jLabel6.setForeground(new java.awt.Color(153, 0, 51));
        jLabel6.setText("Unidad");

        jtxtNombreProducto.setEditable(false);
        jtxtNombreProducto.setFocusable(false);
        jtxtNombreProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtNombreProductoActionPerformed(evt);
            }
        });

        jtxtUnidad.setEditable(false);
        jtxtUnidad.setFocusable(false);
        jtxtUnidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtUnidadActionPerformed(evt);
            }
        });

        jlIdProducto.setText("...");
        jlIdProducto.setAlignmentY(0.7F);

        jlIdUnidadMedida.setText("...");
        jlIdUnidadMedida.setAlignmentY(0.7F);

        jLabel5.setForeground(new java.awt.Color(153, 0, 51));
        jLabel5.setText("Stock Almacen");
        jLabel5.setAlignmentY(0.7F);

        jlStockProducto.setText("...");
        jlStockProducto.setAlignmentY(0.7F);

        jbAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/plus_button.png"))); // NOI18N
        jbAgregar.setText("Agregar");
        jbAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAgregarActionPerformed(evt);
            }
        });

        jLabel4.setForeground(new java.awt.Color(153, 0, 51));
        jLabel4.setText("P/Unit");

        jtxtValorUnitario.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtxtValorUnitario.setFocusable(false);
        jtxtValorUnitario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxtValorUnitarioKeyPressed(evt);
            }
        });

        jLabel7.setForeground(new java.awt.Color(153, 0, 51));
        jLabel7.setText("Descuento");

        jtxtDescuento.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtxtDescuento.setNextFocusableComponent(jtxtCantidad);
        jtxtDescuento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtDescuentoActionPerformed(evt);
            }
        });
        jtxtDescuento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxtDescuentoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtxtDescuentoKeyReleased(evt);
            }
        });

        jLabel3.setForeground(new java.awt.Color(153, 0, 51));
        jLabel3.setText("Cantidad");

        jtxtCantidad.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtxtCantidad.setNextFocusableComponent(jtxtCantidad);
        jtxtCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtCantidadActionPerformed(evt);
            }
        });
        jtxtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxtCantidadKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtxtCantidadKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jlIdProducto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jlIdUnidadMedida)
                        .addGap(64, 64, 64)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(jlStockProducto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbAgregar))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jtxtNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabel6))
                            .addComponent(jtxtUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jtxtValorUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7)
                            .addComponent(jtxtDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jtxtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(jLabel1))
                    .addComponent(jLabel4)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtxtDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtValorUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jlIdProducto)
                        .addComponent(jlIdUnidadMedida)
                        .addComponent(jLabel5)
                        .addComponent(jlStockProducto))
                    .addComponent(jbAgregar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jtxtEliminar)
                                .addGap(459, 459, 459)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jtxtTotalTransaccion, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 713, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtEliminar)
                    .addComponent(jtxtTotalTransaccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jbTransaccion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Configurar.png"))); // NOI18N
        jbTransaccion.setText("Transaccion");
        jbTransaccion.setAlignmentY(0.7F);
        jbTransaccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbTransaccionActionPerformed(evt);
            }
        });

        jlDetalle.setForeground(new java.awt.Color(153, 0, 51));
        jlDetalle.setText("Detalle");

        jtxtDetalle.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxtDetalleKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jlDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 637, Short.MAX_VALUE))
                    .addComponent(jtxtDetalle))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jlDetalle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtxtDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbTransaccion)
                .addGap(39, 39, 39))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbTransaccion)
                .addContainerGap())
        );

        jlTituloFormulario.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        jlTituloFormulario.setForeground(new java.awt.Color(153, 0, 51));
        jlTituloFormulario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlTituloFormulario.setText("titulo");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(jlTituloFormulario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jlidClienteProveedor)
                        .addGap(190, 190, 190))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlTituloFormulario)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jlidClienteProveedor)
                .addGap(26, 26, 26))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSalirActionPerformed
        dispose();
    }//GEN-LAST:event_jbSalirActionPerformed

    private void jtxtNombreProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtNombreProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtNombreProductoActionPerformed

    private void jToggleButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton3ActionPerformed
//        TemporalDAOImpl tempDAOImpl = new TemporalDAOImpl(connectionTemp);  
//        tempDAOImpl.vaciarProductoTemp();
        limpiar();
    }//GEN-LAST:event_jToggleButton3ActionPerformed

    private void jtxtCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtCantidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtCantidadActionPerformed

    private void jtProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtProductosMouseClicked
        seleccionarProducto();
    }//GEN-LAST:event_jtProductosMouseClicked

    private void jtProductosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtProductosKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_TAB) {
            jtxtCantidad.requestFocus();
        }
    }//GEN-LAST:event_jtProductosKeyPressed

    private void createEnterKeybindings(JTable table) {
        table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
        table.getActionMap().put("Enter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                jtxtCantidad.requestFocus();
            }
        });
    }
    
    public void agregar(){
        boolean aux = true;
        if (jtxtCantidad.getText().length() == 0) {
            JOptionPane.showMessageDialog(this, "Registrar la cantidad!!!");
            jtxtCantidad.setText("");
            jtxtCantidad.requestFocus();
            aux = false;
            return;
        }

        try {
            Double x = Double.parseDouble(jtxtCantidad.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "La cantidad registrada no es valida!!!");
            jtxtCantidad.setText("");
            jtxtCantidad.requestFocus();
            aux = false;
            return;
        }

        if (jtxtCantidad.getText().equals("0")) {
            JOptionPane.showMessageDialog(this, "La cantidad registrada no es valida!!!");
            jtxtCantidad.setText("");
            jtxtCantidad.requestFocus();
            aux = false;
            return;
        }

        try {
            Integer y = Integer.parseInt(jlIdProducto.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "No se ha seleccionado ningun producto!!!");
            jtxtCantidad.requestFocus();
            aux = false;
            return;
        }
        
        /*

        if ((idTipoTransaccion == 2)||(idTipoTransaccion == 3)) { // solo para ventas y creditos
            ProductoDAOImpl prod = new ProductoDAOImpl(connectionDB);
            byte controlStock = prod.getControlStock(Integer.parseInt(jlIdProducto.getText()));

            if (controlStock == 1) {
                double stock = Double.valueOf(jlStockProducto.getText());
                if (Double.parseDouble(jtxtCantidad.getText()) > stock) {
                    JOptionPane.showMessageDialog(this, "No existe la suficiente cantidad del producto seleccionado en Almacen!!!");
                    aux = false;
                    jtxtCantidad.requestFocus();
                    return;
                }
            }
        }
                
        */

        if (aux) {
            insertarTemp();
        }
        jtxtNombreProducto.setText("");
        jtxtCantidad.setText("");
        jtxtValorUnitario.setText("");
        jtxtDescuento.setText("");
        jlStockProducto.setText("...");
    }
    
    public void registrarCaja(int idTransaccion) {
        String estado = "A";
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");        
        Date fecha = new Date(Calendar.getInstance().getTime().getTime());
        
        int nroCobro = 0, nroPago = 0;
        double importe = 0;

        TransaccionDAOImpl trans = new TransaccionDAOImpl(connectionDB);
        //fecha = trans.getFechaTransaccion(idTransaccion);

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
        
        if(jtxtDetalle.getText().length()>0){
            cajaDaoImpl.registrarCajaDetalle(cajaDaoImpl.getIdCaja(), jtxtDetalle.getText().trim());
        }
    }

    private void jtxtEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtEliminarActionPerformed
        eliminarProductoTemporal();
    }//GEN-LAST:event_jtxtEliminarActionPerformed

    private void jbTransaccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbTransaccionActionPerformed
    
        SucursalDAO suc = new SucursalDAOImpl(connectionDB);
        byte idSucursal = suc.getIdSucursal(idLugar);
        
        jbTransaccion.setEnabled(false);
        
        registrarCotizacion();
        
        jbTransaccion.setEnabled(true);
    }//GEN-LAST:event_jbTransaccionActionPerformed

    
    private void jtProductosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtProductosKeyReleased
        seleccionarProducto();

    }//GEN-LAST:event_jtProductosKeyReleased

    private void jtxtDetalleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtDetalleKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtDetalleKeyPressed

    private void jtxtUnidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtUnidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtUnidadActionPerformed

    private void jtxtxCriterioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtxCriterioKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtxCriterioKeyTyped

    private void jtxtxCriterioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtxCriterioKeyReleased
        String criterio = "";
        if (jtxtxCriterio.getText().length() != 0) {

            criterio = jtxtxCriterio.getText();            
        } else {
            criterio = "";
        }
        llenarTablaProductos(criterio);
    }//GEN-LAST:event_jtxtxCriterioKeyReleased

    private void jtxtxCriterioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtxCriterioKeyPressed
        boolean aux = false;
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            aux = true;
            seleccionarProductoCodigoAdjunto();  
            evt.setKeyCode(KeyEvent.VK_ESCAPE);
            
        }
        if(aux){           
           jtxtCantidad.requestFocus();
           jtxtxCriterio.setText("");
           llenarTablaProductos("");
        }
    }//GEN-LAST:event_jtxtxCriterioKeyPressed

    private void jtxtCantidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtCantidadKeyPressed
        
    }//GEN-LAST:event_jtxtCantidadKeyPressed

    private void jtxtCantidadKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtCantidadKeyReleased
        if((evt.getKeyCode() == KeyEvent.VK_ENTER)&&(jtxtCantidad.getText().length()>0)){
            agregar();
            jtxtxCriterio.requestFocus();
            jtxtCantidad.setText("");
        }
    }//GEN-LAST:event_jtxtCantidadKeyReleased

    private void jbAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAgregarActionPerformed
        agregar();
    }//GEN-LAST:event_jbAgregarActionPerformed

    private void jtxtValorUnitarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtValorUnitarioKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            jtxtCantidad.requestFocus();
        }
    }//GEN-LAST:event_jtxtValorUnitarioKeyPressed

    private void jtxtDescuentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtDescuentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtDescuentoActionPerformed

    private void jtxtDescuentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtDescuentoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtDescuentoKeyPressed

    private void jtxtDescuentoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtDescuentoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtDescuentoKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToggleButton jToggleButton3;
    private javax.swing.JButton jbAgregar;
    private javax.swing.JToggleButton jbSalir;
    private javax.swing.JButton jbTransaccion;
    private javax.swing.JLabel jlDetalle;
    private javax.swing.JLabel jlIdProducto;
    private javax.swing.JLabel jlIdUnidadMedida;
    private javax.swing.JLabel jlStockProducto;
    private javax.swing.JLabel jlTituloFormulario;
    private javax.swing.JLabel jlidClienteProveedor;
    private javax.swing.JTable jtProductos;
    private javax.swing.JTable jtTemporal;
    private javax.swing.JTextField jtxtCantidad;
    private javax.swing.JTextField jtxtDescuento;
    private javax.swing.JTextField jtxtDetalle;
    private javax.swing.JButton jtxtEliminar;
    private javax.swing.JTextField jtxtNombreProducto;
    private javax.swing.JTextField jtxtTotalTransaccion;
    private javax.swing.JTextField jtxtUnidad;
    private javax.swing.JTextField jtxtValorUnitario;
    private javax.swing.JTextField jtxtxCriterio;
    // End of variables declaration//GEN-END:variables

    private void vaciarProductosTemporales() {
        TemporalDAOImpl tempDAOImpl = new TemporalDAOImpl(connectionTemp);
        tempDAOImpl.vaciarProductoTemp();
    }

    private void vistaPreviaReciboVenta(int idTransaccion) {
        ReporteVentasDAO reporteVentasDAO = new ReporteVentasDAOImpl(connectionDB, usuario);
        reporteVentasDAO.vistaPreviaReciboVenta(idTransaccion);
        
    }
}
