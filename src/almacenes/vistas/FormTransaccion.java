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
import almacenes.model.Producto;
import almacenes.model.Transaccion;
import almacenes.model.Vencimiento;
import dao.ArqueoDAO;
import dao.ArqueoDAOImpl;
import dao.CajaDAO;
import dao.CajaDAOImpl;
import dao.ClienteProveedorDAO;
import dao.ClienteProveedorDAOImpl;
import dao.CreditoDAO;
import dao.CreditoDAOImpl;
import dao.DetalleTransaccionDAO;
import dao.DetalleTransaccionDAOImpl;
import dao.ProductoDAO;
import dao.TemporalDAOImpl;
import dao.ProductoDAOImpl;
import dao.TemporalDAO;
import dao.TransaccionDAO;
import dao.TransaccionDAOImpl;
import dao.VencimientoDAO;
import dao.VencimientoDAOImpl;
import dao.reportes.ReporteComprasDAO;
import dao.reportes.ReporteComprasDAOImpl;
import dao.reportes.ReporteVentasDAO;
import dao.reportes.ReporteVentasDAOImpl;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
public class FormTransaccion extends javax.swing.JFrame {

    private DatabaseUtils databaseUtils;
    private Connection connectionDB, connectionTemp;
    private DefaultTableModel dtm;
    private int idTipoTransaccion; // tipo de proceso q se ejecutara
    private int idTipoTransaccionEntrega;  // tipo de entrega q se ejecutara
    private byte idLugar;
    private byte idTerminal;
    private int idClienteProveedor;
    private int idProducto;
    private int idUnidadMedida;
    private Double valorUnitarioProducto;
    private String descripcionGeneralProducto, usuario;
    private DecimalFormat df;
    private VencimientoDAO vencimientoDAO;
    private ClienteProveedorDAO clienteProveedorDAO;   
    private DetalleTransaccionDAO detalleTransaccionDAO;  
    private ReporteComprasDAO reporteComprasDAO;
    private ReporteVentasDAO reporteVentasDAO;
    private TransaccionDAO transaccionDAO;
    private ProductoDAO productoDAO;
    private TemporalDAO temporalDAO;
    private ArqueoDAO arqueoDAO;
    private CajaDAO cajaDAO;

//    DefaultTableModel dtm;
    public FormTransaccion(Connection connectionDB,
            int idTipoTransaccion, int idTipoTransaccionEntrega,
            String usuario, byte idLugar, byte idTerminal) {

        initComponents();

        this.setLocationRelativeTo(null);

        df = new DecimalFormat("###,###.00");

        this.databaseUtils = new DatabaseUtils();
        this.connectionDB = connectionDB;
        this.idTipoTransaccion = idTipoTransaccion;
        this.idTipoTransaccionEntrega = idTipoTransaccionEntrega;
        this.usuario = usuario;
        this.idLugar = idLugar;
        this.idTerminal = idTerminal;
        this.descripcionGeneralProducto = "";        
        
        productoDAO = new ProductoDAOImpl(connectionDB);
        clienteProveedorDAO = new ClienteProveedorDAOImpl(connectionDB);
        arqueoDAO = new ArqueoDAOImpl(connectionDB);
        transaccionDAO = new TransaccionDAOImpl(connectionDB);
        detalleTransaccionDAO = new DetalleTransaccionDAOImpl(connectionDB);
        cajaDAO = new CajaDAOImpl(connectionDB);
        reporteComprasDAO = new ReporteComprasDAOImpl(connectionDB, usuario);    
        reporteVentasDAO = new ReporteVentasDAOImpl(connectionDB, usuario);
        vencimientoDAO = new VencimientoDAOImpl(connectionDB);

        headerTabla();

        iniciarComponentes();
        abrirConexionTemp();
        createEnterKeybindings(jtProductos);
    }

    public void headerTabla() {
        Font f = new Font("Times New Roman", Font.BOLD, 15);

        jtProductos.getTableHeader().setFont(f);
        jtProductos.getTableHeader().setBackground(Color.orange);

        jtTemporal.getTableHeader().setFont(f);
        jtTemporal.getTableHeader().setBackground(Color.orange);
    }

    public void iniciarComponentes() {

        llenarTablaProductos("");        
        idClienteProveedor = 0;

        switch (idTipoTransaccion) {
            case 1:  //compras
                jbTransaccion.setVisible(true);
                jlTituloFormulario.setText("COMPRAS");
                llenarClienteProveedor("P");

                break;
            case 2:
                jbTransaccion.setVisible(true);
                jlTituloFormulario.setText("VENTAS");
                llenarClienteProveedor("C");
                break;
            case 6:  //stock
                jbTransaccion.setVisible(true);
                jlTituloFormulario.setText("AJUSTE STOCK");
                llenarClienteProveedor("");
                break;    
            default:
                System.err.println("nada");
        }

        int idArqueo = arqueoDAO.getIdArqueo(idLugar, idTerminal, usuario);
        
        if (!arqueoDAO.getEstadoCaja(idArqueo).equals("A")) {
            JOptionPane.showMessageDialog(this, "No es posible realizar transacciones hasta que registre Caja Inicial");
            jbTransaccion.setEnabled(false);
        } else {
            jbTransaccion.setEnabled(true);
        }
    }

//    public void registrarCompra(int idTipoTransaccion, int idTipoTransaccionEntrega){
    public void registrarCompraVenta() {

        int idTransaccion = 0;
        int idEntregaTransaccion = 0;        

//        int idTipoTransaccion = 1; // compra
        idTransaccion = resgistrarTransaccion(idTipoTransaccion);
        registrarDetalleTransaccion(idTransaccion);

        //int idTipoTransaccionEntrega = 7; // recepcion - material
        idEntregaTransaccion = resgistrarTransaccion(idTipoTransaccionEntrega);
        registrarDetalleTransaccion(idEntregaTransaccion);        
        registrarEntregaTransaccion(idEntregaTransaccion, idTransaccion);        
        //registrarProductosVencimiento(idEntregaTransaccion);
        registrarCaja(idTransaccion);
        limpiar();
        vaciarProductosTemporales();    
        
        if(idTipoTransaccion == 1){
            reporteComprasDAO.vistaPreviaCompras(idEntregaTransaccion);
        }else{
            reporteVentasDAO.vistaPreviaReciboVenta(idTransaccion);
        }
        
        JOptionPane.showMessageDialog(this, "Registro generado con éxito");
        
        llenarTablaProductos(jtxtxCriterio.getText().toString());
    }

    public boolean validarRegistros() {
        boolean aux = true;
        if (jtTemporal.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No existen productos para ejectuar la transaccion!!!");
            aux = false;
        }
        
        return aux;
    }

    public void registrarAjusteStock() {

        int idTransaccion = 0;
        int idEntregaTransaccion = 0;

//        int idTipoTransaccion = 6; // compra
        idTransaccion = resgistrarTransaccion(idTipoTransaccion);
        registrarDetalleTransaccion(idTransaccion);

        //int idTipoTransaccionEntrega = 7; // recepcion - material
        idEntregaTransaccion = resgistrarTransaccion(idTipoTransaccionEntrega);

        registrarDetalleTransaccion(idEntregaTransaccion);

        registrarEntregaTransaccion(idEntregaTransaccion, idTransaccion);
        
        //registrarProductosVencimiento(idEntregaTransaccion);

//        registrarCaja(idTransaccion);

        limpiar();

        vaciarProductosTemporales();
        
        reporteComprasDAO.vistaPreviaAjusteStock(idEntregaTransaccion);
        
//        JOptionPane.showMessageDialog(this, "Ajuste Stock registrado con exito");
        
    }

    public void insertarCredito(int idTransaccion, int idClienteProveedor, String detalle){
        CreditoDAO cred = new CreditoDAOImpl(connectionDB);
        cred.insertarCredito(idTransaccion, idClienteProveedor, detalle);
        
    }
    
    public void abrirConexionTemp() {
        DataBaseSqlite sqLite = new DataBaseSqlite();
        connectionTemp = sqLite.conexion();
        temporalDAO = new TemporalDAOImpl(connectionTemp);
        temporalDAO.vaciarProductoTemp();
    }

    public void calcularImportTotalTemp() {
        double importeTotal = 0;        
        importeTotal = temporalDAO.totalProductosTemp();
        jtxtTotalTransaccion.setText(String.valueOf(df.format(importeTotal)));
    }

    public void eliminarProductoTemporal() {
        int filSel = jtTemporal.getSelectedRow();
        int idProducto = (int) jtTemporal.getValueAt(filSel, 0);
        int idUnidadMedida = (int) jtTemporal.getValueAt(filSel, 1);

        temporalDAO.eliminarProdcutoTemp(idProducto, idUnidadMedida);

        llenarTablaTemporal();
    }

    public void llenarTablaTemporal() {

        ArrayList<Temporal> t = new ArrayList<Temporal>();

        t = temporalDAO.getListaTemporal();

        dtm = (DefaultTableModel) this.jtTemporal.getModel();
        dtm.setRowCount(0);

        jtTemporal.setModel(dtm);

        Object[] fila = new Object[8];

        for (int i = 0; i < t.size(); i++) {
            fila[0] = t.get(i).getIdProducto();
            fila[1] = t.get(i).getIdUnidadMedida();
            fila[2] = t.get(i).getNombreProducto();            
            fila[3] = t.get(i).getCantidad();
//            fila[5] = df.format(t.get(i).getValorUnitario());
//            fila[6] = df.format(t.get(i).getValorTotal());
            fila[4] = t.get(i).getValorUnitario();
            fila[5] = t.get(i).getValorTotal();


            dtm.addRow(fila);
        }
        
        TableColumnModel columnModel = jtTemporal.getColumnModel();

        TableColumn colCantidad = columnModel.getColumn(3);
        TableColumn colPrecioUnitario = columnModel.getColumn(4);
        TableColumn colPrecioTotal = columnModel.getColumn(5);
        

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(jLabel1.RIGHT);

        colCantidad.setCellRenderer(renderer);
        colPrecioUnitario.setCellRenderer(renderer);
        colPrecioTotal.setCellRenderer(renderer);

        jtTemporal.setModel(dtm);

        calcularImportTotalTemp();
    }

    public void seleccionarProducto() {
        
        int filSel = jtProductos.getSelectedRow();

        idProducto = (int) jtProductos.getValueAt(filSel, 7);
        idUnidadMedida = (int) jtProductos.getValueAt(filSel, 8);
        descripcionGeneralProducto = jtProductos.getValueAt(filSel, 1).toString()+" "+
                jtProductos.getValueAt(filSel, 2).toString()+" "+
                jtProductos.getValueAt(filSel, 3).toString()+" "+
                jtProductos.getValueAt(filSel, 4).toString();
        String nombreProducto = jtProductos.getValueAt(filSel, 1).toString();
        valorUnitarioProducto = (double) jtProductos.getValueAt(filSel, 5);

        jlDescripcionProducto.setText(descripcionGeneralProducto);

        /*
        if(vencimientoDAO.isVencimiento(idProducto)){
            jlFechaVencimiento.setVisible(true);
            jtxtFechaVencimiento.setVisible(true);
        }else{
            jlFechaVencimiento.setVisible(false);
            jtxtFechaVencimiento.setVisible(false);
        }
        */

    }

    public void llenarTablaProductos(String criterio) {

        List<Producto> lProd = new ArrayList<>();

        lProd = productoDAO.getListaProductosVenta(criterio, idLugar);

        dtm = (DefaultTableModel) this.jtProductos.getModel();
        dtm.setRowCount(0);

        jtProductos.setModel(dtm);

        Object[] fila = new Object[19];

//        System.out.println("nro de registros en pila: " + lProd.size());
        for (int i = 0; i < lProd.size(); i++) {
            fila[0] = lProd.get(i).getIdUnidadProducto();
            fila[1] = lProd.get(i).getDescripcion();
            fila[2] = lProd.get(i).getNombreLaboratorio();
            fila[3] = lProd.get(i).getNombreFamilia();
            fila[4] = lProd.get(i).getNombreUnidadMedida();
            fila[5] = lProd.get(i).getPrecioVenta();
            fila[6] = lProd.get(i).getStock();
            fila[7] = lProd.get(i).getId();
            fila[8] = lProd.get(i).getIdUnidadMedida();
            dtm.addRow(fila);
        }

        jtProductos.setAutoscrolls(false);
        jtProductos.setModel(dtm);
        
    }

    public void registrarCaja(int idTransaccion) {
        String estado = "A";
//        String usuario = "SYS";
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

    public void registrarDetalleTransaccion(int idTransaccion) {

        double cantidad = 0;
        double valorUnitario = 0;
        double valorTotal = 0;
        String tipoValor = "N";

        ArrayList<DetalleTransaccion> detTrans = new ArrayList<DetalleTransaccion>();
        
        temporalDAO.getListaTemporal().forEach((t)->{ 
            DetalleTransaccion dt = new DetalleTransaccion();
            
            dt.setIdTransaccion(idTransaccion);
            dt.setIdProducto(t.getIdProducto());
            dt.setIdUnidadMedida(t.getIdUnidadMedida());
            dt.setCantidad(t.getCantidad());
            dt.setValorUnitario(t.getValorUnitario());
            dt.setValorTotal(t.getValorTotal());
            dt.setTipoValor(tipoValor);

            detTrans.add(dt);            
        });
/*
        for (int fila = 0; fila < jtTemporal.getRowCount(); fila++) {
            idProducto = Integer.valueOf(jtTemporal.getValueAt(fila, 0).toString());
            idUnidadMedida = Integer.valueOf(jtTemporal.getValueAt(fila, 1).toString());
            cantidad = Double.valueOf(jtTemporal.getValueAt(fila, 4).toString());
            valorUnitario = Double.valueOf(jtTemporal.getValueAt(fila, 5).toString());
            valorTotal = Double.valueOf(jtTemporal.getValueAt(fila, 6).toString());

            DetalleTransaccion dt = new DetalleTransaccion();

            dt.setIdTransaccion(idTransaccion);
            dt.setIdProducto(idProducto);
            dt.setIdUnidadMedida(idUnidadMedida);
            dt.setCantidad(cantidad);
            dt.setValorUnitario(valorUnitario);
            dt.setValorTotal(valorTotal);
            dt.setTipoValor(tipoValor);

            detTrans.add(dt);

        }
*/
        detalleTransaccionDAO.insertarDetalleTransaccion(detTrans);

    }

    public void limpiar() {
        dtm = (DefaultTableModel) this.jtTemporal.getModel();
        dtm.setRowCount(0);
        jtTemporal.setModel(dtm);

        jtxtRazonSocial.setText("");

        jtxtTotalTransaccion.setText("");
        
    }

    public int resgistrarTransaccion(int idTipoTransaccion) {

        int nroTipoTransaccion = 0;
        int tipoMovimineto = transaccionDAO.getTipoMovimiento(idTipoTransaccion);
//        int idTerminal = 1;
        int idTransaccion = 0;
        String estado = "A";
        String descripcion = "";
//        String usuario = "SYS";
        java.util.Date hoy = new java.util.Date();
        java.sql.Date fecha = new java.sql.Date(hoy.getTime());

        nroTipoTransaccion = transaccionDAO.getNroTipoTransaccion(idTipoTransaccion);

        descripcion = jtxtRazonSocial.getText().toUpperCase();

        Transaccion trans = new Transaccion(fecha, idTipoTransaccion, nroTipoTransaccion,
                idLugar, idTerminal, tipoMovimineto, estado, usuario, descripcion, idClienteProveedor);

        idTransaccion = transaccionDAO.insertarTransaccion(trans);

        if (idTransaccion != 0) {
//            System.out.println("transaccion registrada nro: " + idTransaccion);
        } else {
//            System.out.println("error en el registro");
        }

        return idTransaccion;
    }

    public void registrarEntregaTransaccion(int idEntregaTransaccion, int idTransaccion) {        
        transaccionDAO.insertarEntregaTransaccion(idTransaccion, idEntregaTransaccion);
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
        jlDescripcionProducto = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jlRazonSocial = new javax.swing.JLabel();
        jtxtRazonSocial = new javax.swing.JTextField();
        jbTransaccion = new javax.swing.JButton();
        jcClienteProveedor = new javax.swing.JComboBox<>();
        jlClienteProveedor = new javax.swing.JLabel();
        jlTituloFormulario = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        jtProductos.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jtProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "idUnidadProducto", "Producto", "Laboratorio", "Familia", "Unidad", "P/Venta", "Stock", "idProducto", "idUnidadMedida"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

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
            jtProductos.getColumnModel().getColumn(1).setMinWidth(250);
            jtProductos.getColumnModel().getColumn(1).setPreferredWidth(250);
            jtProductos.getColumnModel().getColumn(1).setMaxWidth(250);
            jtProductos.getColumnModel().getColumn(2).setMinWidth(100);
            jtProductos.getColumnModel().getColumn(2).setPreferredWidth(100);
            jtProductos.getColumnModel().getColumn(2).setMaxWidth(100);
            jtProductos.getColumnModel().getColumn(3).setMinWidth(120);
            jtProductos.getColumnModel().getColumn(3).setPreferredWidth(120);
            jtProductos.getColumnModel().getColumn(3).setMaxWidth(120);
            jtProductos.getColumnModel().getColumn(4).setMinWidth(70);
            jtProductos.getColumnModel().getColumn(4).setPreferredWidth(70);
            jtProductos.getColumnModel().getColumn(4).setMaxWidth(70);
            jtProductos.getColumnModel().getColumn(5).setMinWidth(80);
            jtProductos.getColumnModel().getColumn(5).setPreferredWidth(80);
            jtProductos.getColumnModel().getColumn(5).setMaxWidth(80);
            jtProductos.getColumnModel().getColumn(6).setMinWidth(70);
            jtProductos.getColumnModel().getColumn(6).setPreferredWidth(70);
            jtProductos.getColumnModel().getColumn(6).setMaxWidth(70);
            jtProductos.getColumnModel().getColumn(7).setMinWidth(0);
            jtProductos.getColumnModel().getColumn(7).setPreferredWidth(0);
            jtProductos.getColumnModel().getColumn(7).setMaxWidth(0);
            jtProductos.getColumnModel().getColumn(8).setMinWidth(0);
            jtProductos.getColumnModel().getColumn(8).setPreferredWidth(0);
            jtProductos.getColumnModel().getColumn(8).setMaxWidth(0);
        }

        jToggleButton3.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jToggleButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Clear-icon.png"))); // NOI18N
        jToggleButton3.setText("Limpiar");
        jToggleButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton3ActionPerformed(evt);
            }
        });

        jbSalir.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jbSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/close_window.png"))); // NOI18N
        jbSalir.setText("Salir");
        jbSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSalirActionPerformed(evt);
            }
        });

        jtxtxCriterio.setFont(new java.awt.Font("Noto Sans", 0, 15)); // NOI18N
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

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 0, 51));
        jLabel2.setText("Criterio de Busqueda");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jbSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(478, 478, 478)
                        .addComponent(jToggleButton3))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 696, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtxCriterio, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jtxtxCriterio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jToggleButton3)
                    .addComponent(jbSalir))
                .addContainerGap())
        );

        jtTemporal.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jtTemporal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "idProducto", "idUnidadMedida", "Producto", "Cantidad", "P/Unit", "P/Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtTemporal.setRowHeight(22);
        jScrollPane2.setViewportView(jtTemporal);
        if (jtTemporal.getColumnModel().getColumnCount() > 0) {
            jtTemporal.getColumnModel().getColumn(0).setMinWidth(0);
            jtTemporal.getColumnModel().getColumn(0).setPreferredWidth(0);
            jtTemporal.getColumnModel().getColumn(0).setMaxWidth(0);
            jtTemporal.getColumnModel().getColumn(1).setMinWidth(0);
            jtTemporal.getColumnModel().getColumn(1).setPreferredWidth(0);
            jtTemporal.getColumnModel().getColumn(1).setMaxWidth(0);
            jtTemporal.getColumnModel().getColumn(3).setMinWidth(55);
            jtTemporal.getColumnModel().getColumn(3).setPreferredWidth(55);
            jtTemporal.getColumnModel().getColumn(3).setMaxWidth(55);
            jtTemporal.getColumnModel().getColumn(4).setMinWidth(65);
            jtTemporal.getColumnModel().getColumn(4).setPreferredWidth(65);
            jtTemporal.getColumnModel().getColumn(4).setMaxWidth(65);
            jtTemporal.getColumnModel().getColumn(5).setMinWidth(80);
            jtTemporal.getColumnModel().getColumn(5).setPreferredWidth(80);
            jtTemporal.getColumnModel().getColumn(5).setMaxWidth(80);
        }

        jtxtEliminar.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jtxtEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/trash_icon.png"))); // NOI18N
        jtxtEliminar.setText("Eliminar");
        jtxtEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtEliminarActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(153, 0, 51));
        jLabel8.setText("Total");

        jtxtTotalTransaccion.setEditable(false);
        jtxtTotalTransaccion.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jtxtTotalTransaccion.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtxtTotalTransaccion.setEnabled(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(153, 0, 51));
        jLabel1.setText("Producto");

        jlDescripcionProducto.setFont(new java.awt.Font("Noto Sans", 0, 15)); // NOI18N
        jlDescripcionProducto.setText("...");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jlDescripcionProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 665, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 34, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlDescripcionProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 617, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jtxtEliminar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8)
                        .addGap(28, 28, 28)
                        .addComponent(jtxtTotalTransaccion, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(103, 103, 103))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtEliminar)
                    .addComponent(jtxtTotalTransaccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jlRazonSocial.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jlRazonSocial.setForeground(new java.awt.Color(153, 0, 51));
        jlRazonSocial.setText("Detalle");

        jtxtRazonSocial.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jtxtRazonSocial.setNextFocusableComponent(jbTransaccion);

        jbTransaccion.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jbTransaccion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Configurar.png"))); // NOI18N
        jbTransaccion.setText("Transaccion");
        jbTransaccion.setAlignmentY(0.7F);
        jbTransaccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbTransaccionActionPerformed(evt);
            }
        });

        jcClienteProveedor.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jcClienteProveedor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jcClienteProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcClienteProveedorActionPerformed(evt);
            }
        });

        jlClienteProveedor.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jlClienteProveedor.setForeground(new java.awt.Color(153, 0, 51));
        jlClienteProveedor.setText("Proveedor");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlClienteProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                            .addComponent(jcClienteProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbTransaccion))
                        .addComponent(jtxtRazonSocial, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 608, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jlRazonSocial)
                .addGap(8, 8, 8)
                .addComponent(jtxtRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlClienteProveedor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcClienteProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbTransaccion))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jlTituloFormulario.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
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
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jlidClienteProveedor)
                        .addGap(190, 190, 190))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlTituloFormulario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jlidClienteProveedor))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSalirActionPerformed
        dispose();
    }//GEN-LAST:event_jbSalirActionPerformed

    private void jToggleButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton3ActionPerformed
        limpiar();
    }//GEN-LAST:event_jToggleButton3ActionPerformed

    private void jtProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtProductosMouseClicked
        seleccionarProducto();
    }//GEN-LAST:event_jtProductosMouseClicked

    private void jtProductosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtProductosKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            //jtxtCantidad.requestFocus();
            FormAuxiliarTemp formAuxiliarTemp = new FormAuxiliarTemp(connectionTemp, 
                    idProducto, idUnidadMedida,                                         
                    valorUnitarioProducto,
                    idTipoTransaccion,
                    descripcionGeneralProducto);
            formAuxiliarTemp.setVisible(true);                     
        }
    }//GEN-LAST:event_jtProductosKeyPressed

    private void createEnterKeybindings(JTable table) {
        table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
        table.getActionMap().put("Enter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //jtxtCantidad.requestFocus();
            }
        });
    }
    

    private void jtxtEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtEliminarActionPerformed
        eliminarProductoTemporal();
    }//GEN-LAST:event_jtxtEliminarActionPerformed

    private void jbTransaccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbTransaccionActionPerformed
        jbTransaccion.setEnabled(false);
        switch (idTipoTransaccion) {
            case 1:
                if (!validarRegistros()) {
                    return;
                }
                registrarCompraVenta();
                break;
            case 2:
                if (!validarRegistros()) {
                    return;
                }
                registrarCompraVenta();
                break;
            case 6:
                if (!validarRegistros()) {
                    return;
                }
                registrarAjusteStock();
                break;
        }
        jbTransaccion.setEnabled(true);
    }//GEN-LAST:event_jbTransaccionActionPerformed

    private void jtProductosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtProductosKeyReleased
        seleccionarProducto();

    }//GEN-LAST:event_jtProductosKeyReleased

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

    }//GEN-LAST:event_jtxtxCriterioKeyPressed

    private void jcClienteProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcClienteProveedorActionPerformed
        seleccionarClienteProveedor();
    }//GEN-LAST:event_jcClienteProveedorActionPerformed

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        llenarTablaTemporal();       
    }//GEN-LAST:event_formWindowGainedFocus


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToggleButton jToggleButton3;
    private javax.swing.JToggleButton jbSalir;
    private javax.swing.JButton jbTransaccion;
    private javax.swing.JComboBox<String> jcClienteProveedor;
    private javax.swing.JLabel jlClienteProveedor;
    private javax.swing.JLabel jlDescripcionProducto;
    private javax.swing.JLabel jlRazonSocial;
    private javax.swing.JLabel jlTituloFormulario;
    private javax.swing.JLabel jlidClienteProveedor;
    private javax.swing.JTable jtProductos;
    private javax.swing.JTable jtTemporal;
    private javax.swing.JButton jtxtEliminar;
    private javax.swing.JTextField jtxtRazonSocial;
    private javax.swing.JTextField jtxtTotalTransaccion;
    private javax.swing.JTextField jtxtxCriterio;
    // End of variables declaration//GEN-END:variables

    private void vaciarProductosTemporales() {        
        temporalDAO.vaciarProductoTemp();
    }

    private void registrarProductosVencimiento(int idEntregaTransaccion) {
        int idProducto = 0;
        int idUnidadMedida = 0;
        double cantidad = 0;
        String fechaVencimiento = "";
        
        List<Vencimiento> listaVencimiento = new ArrayList<>();

        for (int fila = 0; fila < jtTemporal.getRowCount(); fila++) {
            
            idProducto = Integer.valueOf(jtTemporal.getValueAt(fila, 0).toString());
            
            if(vencimientoDAO.isVencimiento(idProducto)){
                idUnidadMedida = Integer.valueOf(jtTemporal.getValueAt(fila, 1).toString());
                cantidad = Double.valueOf(jtTemporal.getValueAt(fila, 4).toString());
                fechaVencimiento = jtTemporal.getValueAt(fila, 7).toString();

                Vencimiento v = new Vencimiento();
                
                v.setId_transaccion(idEntregaTransaccion);
                v.setId_producto(idProducto);
                v.setId_unidad_medida(idUnidadMedida);
                v.setCantidad(cantidad);
                v.setFecha_vencimiento(Date.valueOf(fechaVencimiento));
                
                listaVencimiento.add(v);
            }
        }
        
        System.out.println("registrarProductosVencimiento(): "+ listaVencimiento.size());
        
        //vencimientoDAO.insertarVencimiento(listaVencimiento);
    }
    
    public void llenarClienteProveedor(String tipo) {
        String sel = "Sel";

        jcClienteProveedor.removeAllItems();
        jcClienteProveedor.addItem(sel);

        HashMap<String, Integer> map = clienteProveedorDAO.clienteProveedorClaveValor(tipo);

        for (String s : map.keySet()) {
            jcClienteProveedor.addItem(s.toString());
        }
    }
    
    private void seleccionarClienteProveedor() {

        String sel = null;
        String tipo = "P";
        String comp = "Sel";
        
        HashMap<String, Integer> map = clienteProveedorDAO.clienteProveedorClaveValor(tipo);

        try {
            sel = jcClienteProveedor.getSelectedItem().toString();

            if (sel.equals(comp)) {
                idClienteProveedor = 0;
            } else {
                idClienteProveedor =  Integer.valueOf(map.get(sel).toString());
            }
        } catch (Exception e) {
        }
    }
}
