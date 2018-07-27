/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package almacenes.vistas.configuracion;

import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.Sucursal;
import dao.LugarDAO;
import dao.LugarDAOImpl;
import dao.SucursalDAO;
import dao.SucursalDAOImpl;
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
public class FormSucursal extends javax.swing.JFrame {

    /**
     * Creates new form FormSucursal
     */
    private DatabaseUtils databaseUtils;
    private Connection connectionDB;
    DefaultTableModel dtm;
    private boolean editar = false;
    byte idSucursal = 0;

    public FormSucursal(Connection connectionDB) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.connectionDB = connectionDB;
        this.databaseUtils = new DatabaseUtils();
        
        headerTabla();
        llenarComboLugar();
        llenarTablaSucursal();
        iniciarBotones();
    }
    
    public void headerTabla(){
        Font f = new Font("Times New Roman", Font.BOLD, 13);
        
        jtSucursal.getTableHeader().setFont(f);
        jtSucursal.getTableHeader().setBackground(Color.orange);
    }
    
    public void llenarComboLugar(){
        
        String sel = "Sel";
        
        jcLugarSucursal.removeAllItems();
        jcLugarSucursal.addItem(sel);
        
        LugarDAO lugarDAOImpl = new LugarDAOImpl(connectionDB);
        
        HashMap<String, Integer> map = lugarDAOImpl.lugarClaveValor();

        for (String s : map.keySet()) {
            jcLugarSucursal.addItem(s.toString());
        }
        jlIdLugarSucursal.setVisible(false);
    }
    
    private void seleccionarElementoLugar() {
        jlIdLugarSucursal.setVisible(false);
        String sel = null;
        
        String comp = "Sel";
        
        LugarDAO lugarDAOImpl = new LugarDAOImpl(connectionDB);
        
        HashMap<String, Integer> map = lugarDAOImpl.lugarClaveValor();
            
        try {
            sel = jcLugarSucursal.getSelectedItem().toString();

//            System.out.println("elemento seleccionado "+ sel);

            if(sel.equals(comp)){
                jlIdLugarSucursal.setText("0");
            }
            else{
                jlIdLugarSucursal.setText(map.get(sel).toString());
            }
        } catch (Exception e) {
        }
        
    }
    
    public void habilitarComponentes(){
        boolean aux = true;
        jtxtActividadEconomica.setEnabled(aux);
        jtxtDireccionSucursal.setEnabled(aux);
        jcLugarSucursal.setEnabled(aux);
        jtxtNitSucursal.setEnabled(aux);
        jtxtNombreSucursal.setEnabled(aux);
        jtxtTipoProductos.setEnabled(aux);
        jtxtActividadSucursal.setEnabled(aux);
        jchEstado.setEnabled(aux);
        jPanelComponentes.setEnabled(aux);
    }
    
    public void dehabilitarComponentes(){
        boolean aux = false;
        jtxtActividadEconomica.setEnabled(aux);
        jtxtDireccionSucursal.setEnabled(aux);
        jcLugarSucursal.setEnabled(aux);
        jtxtNitSucursal.setEnabled(aux);
        jtxtNombreSucursal.setEnabled(aux);
        jtxtTipoProductos.setEnabled(aux);
        jtxtActividadSucursal.setEnabled(aux);
        jchEstado.setEnabled(aux);
        jPanelComponentes.setEnabled(aux);
    }
    
    public void iniciarBotones(){
        jbGuardar.setEnabled(false);
        jbCancelar.setEnabled(false);
        dehabilitarComponentes();
    }
    
    public void llenarTablaSucursal(){
        SucursalDAO suc = new SucursalDAOImpl(connectionDB);
        
        ArrayList<Sucursal> s = new ArrayList<Sucursal>();
        
        s = suc.getListSucursal();
        
        dtm = (DefaultTableModel) this.jtSucursal.getModel();
        dtm.setRowCount(0);
        
        jtSucursal.setModel(dtm);
        
        Object[] fila = new Object[8];
        
        for(int i=0; i< s.size(); i++){
            fila[0] = s.get(i).getId();
            fila[1] = s.get(i).getNitSucursal();
            fila[2] = s.get(i).getNombreSucursal();
            fila[3] = s.get(i).getDireccion();
            fila[4] = s.get(i).getTipoProductos();
            fila[5] = s.get(i).getNombreLugar();
            fila[6] = s.get(i).getActividadEconomica();
            fila[7] = s.get(i).getEstado();
            
            dtm.addRow(fila);
        }
        jtSucursal.setModel(dtm);
    }
    
    public void botones(byte x){
        switch(x){
            case 1: //nuevo
                jbNuevo.setEnabled(false);
                jbGuardar.setEnabled(true);
                jbEditar.setEnabled(false);
                jbCancelar.setEnabled(true);
                habilitarComponentes();
                limpiar();
                break;
            case 2: //guardar
                jbNuevo.setEnabled(true);
                jbGuardar.setEnabled(false);
                jbEditar.setEnabled(true);
                jbCancelar.setEnabled(false);
                dehabilitarComponentes();
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
                dehabilitarComponentes();
                break;
        }
    }
    
    private void limpiar() {
        jtxtActividadEconomica.setText("");
        jtxtDireccionSucursal.setText("");
        jtxtNitSucursal.setText("");
        jtxtTipoProductos.setText("");
        jchEstado.setSelected(false);
    }
    
    public FormSucursal() {
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

        jbGuardar = new javax.swing.JButton();
        jbNuevo = new javax.swing.JButton();
        jbEditar = new javax.swing.JButton();
        jbCancelar = new javax.swing.JButton();
        jbSalir = new javax.swing.JButton();
        jlTituloFormulario = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtSucursal = new javax.swing.JTable();
        jlIdLugarSucursal = new javax.swing.JLabel();
        jbEliminar = new javax.swing.JButton();
        jPanelComponentes = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jtxtActividadEconomica = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jtxtDireccionSucursal = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jcLugarSucursal = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jtxtNitSucursal = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jtxtNombreSucursal = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jtxtTipoProductos = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jtxtActividadSucursal = new javax.swing.JTextField();
        jchEstado = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jbGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Save-icon.png"))); // NOI18N
        jbGuardar.setText("Guardar");
        jbGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbGuardarActionPerformed(evt);
            }
        });

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

        jbCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/exit.png"))); // NOI18N
        jbCancelar.setText("Cancelar");
        jbCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCancelarActionPerformed(evt);
            }
        });

        jbSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/close_window.png"))); // NOI18N
        jbSalir.setText("Salir");
        jbSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSalirActionPerformed(evt);
            }
        });

        jlTituloFormulario.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        jlTituloFormulario.setForeground(new java.awt.Color(153, 0, 51));
        jlTituloFormulario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlTituloFormulario.setText("Sucursal");

        jtSucursal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "idSucursal", "NIT", "Nombre Sucursal", "Direccion", "Tipo Productos", "Lugar", "Actividad Economica", "Estado"
            }
        ));
        jtSucursal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtSucursalMouseClicked(evt);
            }
        });
        jtSucursal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtSucursalKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jtSucursal);
        if (jtSucursal.getColumnModel().getColumnCount() > 0) {
            jtSucursal.getColumnModel().getColumn(0).setMinWidth(0);
            jtSucursal.getColumnModel().getColumn(0).setPreferredWidth(0);
            jtSucursal.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        jbEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/trash_icon.png"))); // NOI18N
        jbEliminar.setText("Eliminar");
        jbEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbEliminarActionPerformed(evt);
            }
        });

        jLabel4.setForeground(new java.awt.Color(153, 0, 51));
        jLabel4.setText("Actividad Economica");

        jLabel5.setForeground(new java.awt.Color(153, 0, 51));
        jLabel5.setText("Direccion");

        jLabel1.setForeground(new java.awt.Color(153, 0, 51));
        jLabel1.setText("Lugar");

        jcLugarSucursal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jcLugarSucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcLugarSucursalActionPerformed(evt);
            }
        });

        jLabel6.setForeground(new java.awt.Color(153, 0, 51));
        jLabel6.setText("Nit");

        jLabel7.setForeground(new java.awt.Color(153, 0, 51));
        jLabel7.setText("Nombre Sucursal");

        jLabel8.setForeground(new java.awt.Color(153, 0, 51));
        jLabel8.setText("Tipo Productos");

        jLabel9.setForeground(new java.awt.Color(153, 0, 51));
        jLabel9.setText("Actividad Sucursal");

        jchEstado.setForeground(new java.awt.Color(153, 0, 51));
        jchEstado.setText("Estado");
        jchEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchEstadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelComponentesLayout = new javax.swing.GroupLayout(jPanelComponentes);
        jPanelComponentes.setLayout(jPanelComponentesLayout);
        jPanelComponentesLayout.setHorizontalGroup(
            jPanelComponentesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelComponentesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelComponentesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelComponentesLayout.createSequentialGroup()
                        .addGroup(jPanelComponentesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jtxtNitSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelComponentesLayout.createSequentialGroup()
                                .addGap(97, 97, 97)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jcLugarSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(27, 27, 27))
                    .addGroup(jPanelComponentesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanelComponentesLayout.createSequentialGroup()
                            .addComponent(jLabel6)
                            .addGap(503, 503, 503))
                        .addGroup(jPanelComponentesLayout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jtxtDireccionSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 491, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanelComponentesLayout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jtxtActividadEconomica, javax.swing.GroupLayout.PREFERRED_SIZE, 491, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanelComponentesLayout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jtxtNombreSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 491, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanelComponentesLayout.createSequentialGroup()
                            .addComponent(jLabel8)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanelComponentesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jchEstado)
                                .addComponent(jtxtTipoProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 491, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanelComponentesLayout.createSequentialGroup()
                            .addComponent(jLabel9)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jtxtActividadSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 491, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelComponentesLayout.setVerticalGroup(
            jPanelComponentesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelComponentesLayout.createSequentialGroup()
                .addGap(0, 15, Short.MAX_VALUE)
                .addGroup(jPanelComponentesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtActividadEconomica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelComponentesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtDireccionSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelComponentesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jcLugarSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanelComponentesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtNitSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelComponentesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtNombreSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelComponentesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtTipoProductos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(5, 5, 5)
                .addGroup(jPanelComponentesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtActividadSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jchEstado))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(89, 89, 89)
                        .addComponent(jbNuevo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbGuardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanelComponentes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(30, 30, 30)
                        .addComponent(jlIdLugarSucursal))
                    .addComponent(jlTituloFormulario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlTituloFormulario)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addComponent(jlIdLugarSucursal)
                        .addGap(213, 213, 213))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelComponentes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbEditar)
                    .addComponent(jbCancelar)
                    .addComponent(jbGuardar)
                    .addComponent(jbSalir)
                    .addComponent(jbNuevo)
                    .addComponent(jbEliminar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jchEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchEstadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jchEstadoActionPerformed

    private void jbGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbGuardarActionPerformed
        byte x = 2;
        botones(x);
        guardarSucursal();
    }//GEN-LAST:event_jbGuardarActionPerformed

    private void jbNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbNuevoActionPerformed
        byte x = 1;
        botones(x);
    }//GEN-LAST:event_jbNuevoActionPerformed

    private void jbEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbEditarActionPerformed
        byte x = 3;
        editar = true;
        botones(x);
    }//GEN-LAST:event_jbEditarActionPerformed

    private void jbCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCancelarActionPerformed
        byte x = 4;
        botones(x);
    }//GEN-LAST:event_jbCancelarActionPerformed

    private void jbSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSalirActionPerformed
        dispose();
    }//GEN-LAST:event_jbSalirActionPerformed

    private void jcLugarSucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcLugarSucursalActionPerformed
        seleccionarElementoLugar();
    }//GEN-LAST:event_jcLugarSucursalActionPerformed

    private void jbEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbEliminarActionPerformed
        int filSel = jtSucursal.getSelectedRow();

        int id = (int) jtSucursal.getValueAt(filSel, 0);

        SucursalDAO tdao = new SucursalDAOImpl(connectionDB);
        tdao.eliminarSucursal((byte)id);
        llenarTablaSucursal();
    }//GEN-LAST:event_jbEliminarActionPerformed

    private void jtSucursalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtSucursalKeyReleased
        selccionarSucursal();
    }//GEN-LAST:event_jtSucursalKeyReleased

    private void jtSucursalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtSucursalMouseClicked
        selccionarSucursal();
    }//GEN-LAST:event_jtSucursalMouseClicked

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
            java.util.logging.Logger.getLogger(FormSucursal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormSucursal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormSucursal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormSucursal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormSucursal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanelComponentes;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JButton jbEditar;
    private javax.swing.JButton jbEliminar;
    private javax.swing.JButton jbGuardar;
    private javax.swing.JButton jbNuevo;
    private javax.swing.JButton jbSalir;
    private javax.swing.JComboBox<String> jcLugarSucursal;
    private javax.swing.JCheckBox jchEstado;
    private javax.swing.JLabel jlIdLugarSucursal;
    private javax.swing.JLabel jlTituloFormulario;
    private javax.swing.JTable jtSucursal;
    private javax.swing.JTextField jtxtActividadEconomica;
    private javax.swing.JTextField jtxtActividadSucursal;
    private javax.swing.JTextField jtxtDireccionSucursal;
    private javax.swing.JTextField jtxtNitSucursal;
    private javax.swing.JTextField jtxtNombreSucursal;
    private javax.swing.JTextField jtxtTipoProductos;
    // End of variables declaration//GEN-END:variables

    private void guardarSucursal() {
        String actividadEconomica = null;
        String direccion = null;
        String nit = null;
        String nombreSucursal = null;
        String tipoProductos = null;
        byte idLugar = 0;
        byte estado = 0;
        String actividadSucursal = null;
        
        nit = jtxtNitSucursal.getText();
        nombreSucursal = jtxtNombreSucursal.getText().toUpperCase();
        direccion = jtxtDireccionSucursal.getText().toUpperCase();
        tipoProductos = jtxtTipoProductos.getText().toUpperCase();
        idLugar = Byte.valueOf(jlIdLugarSucursal.getText());
        actividadEconomica = jtxtActividadEconomica.getText().toUpperCase();
        if(jchEstado.isSelected()){
            estado = 1;
        }
        actividadSucursal = jtxtActividadSucursal.getText();
        
        Sucursal sucursal = new Sucursal();
        sucursal.setNitSucursal(nit);
        sucursal.setNombreSucursal(nombreSucursal);
        sucursal.setDireccion(direccion);
        sucursal.setTipoProductos(tipoProductos);
        sucursal.setIdLugar(idLugar);
        sucursal.setActividadEconomica(actividadEconomica);
        sucursal.setActividadSucursal(actividadSucursal);
        sucursal.setEstado(estado);
        
        SucursalDAO sucursalDAO = new SucursalDAOImpl(connectionDB);
        if(editar){
            sucursal.setId(idSucursalSeleccionado());
            sucursalDAO.editarSucursal(sucursal);
            editar = false;
        }else{
            sucursalDAO.insertarSucursal(sucursal);
        }
        llenarTablaSucursal();
    }

    private void selccionarSucursal() {
        idSucursal = idSucursalSeleccionado();
        SucursalDAO sucursalDAO = new SucursalDAOImpl(connectionDB);
        ArrayList<Sucursal> sucursal = sucursalDAO.getSucursal(idSucursal);
        
        jtxtActividadEconomica.setText(sucursal.get(0).getActividadEconomica());
        jtxtDireccionSucursal.setText(sucursal.get(0).getDireccion());
        jtxtNitSucursal.setText(sucursal.get(0).getNitSucursal());
        jtxtNombreSucursal.setText(sucursal.get(0).getNombreSucursal());
        jtxtTipoProductos.setText(sucursal.get(0).getTipoProductos());
        jtxtActividadSucursal.setText(sucursal.get(0).getActividadSucursal());
        jcLugarSucursal.setSelectedItem(sucursal.get(0).getNombreLugar());
        jlIdLugarSucursal.setText(String.valueOf(sucursal.get(0).getIdLugar()));
        if(sucursal.get(0).getEstado()==0){
            jchEstado.setSelected(false);
        }else{
            jchEstado.setSelected(true);
        }
        
    }
    private byte idSucursalSeleccionado(){
        int fila = jtSucursal.getSelectedRow();
        String id = jtSucursal.getValueAt(fila, 0).toString();
        return Byte.valueOf(id);
    }
}
