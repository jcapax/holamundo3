/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package almacenes.vistas;

import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.ClienteProveedor;
import dao.ClienteProveedorDAO;
import dao.ClienteProveedorDAOImpl;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jcarlos.porcel
 */
public class FormClienteProveedor extends javax.swing.JFrame {
    
    private DatabaseUtils databaseUtils;
    private Connection connectionDB;
    DefaultTableModel dtm;
    ClienteProveedorDAO clienteProveedorDAO;
    String tipo;    
    
    /**
     * Creates new form FormClienteProveedor
     */
    public FormClienteProveedor(Connection connectionDB, String tipo) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.databaseUtils = new DatabaseUtils();
        this.connectionDB = connectionDB;
        clienteProveedorDAO = new ClienteProveedorDAOImpl(connectionDB);
        this.tipo = tipo;
        
        if(tipo.equals("C")){jlTituloFormulario.setText("CLIENTES");}else{jlTituloFormulario.setText("PROVEEDORES");}
        
        llenarTablaClienteProveedor();
        headerTabla();
        
        jlEdicion.setText("0");
        jlEdicion.setVisible(false);
        deshabilitarComponentes();
    }
    
    public void habilitarComponentes(){
        boolean aux = true;
        jtxtCedulaIdentidad.setEnabled(aux);
        jtxtDireccion.setEnabled(aux);
        jtxtNit.setEnabled(aux);
        jtxtNombreCompleto.setEnabled(aux);
        jtxtOtrosDatos.setEnabled(aux);
        jtxtRazonSocial.setEnabled(aux);
        jtxtTelefonos.setEnabled(aux);
        jcEstado.setEnabled(aux);
    }

    public void deshabilitarComponentes(){
        boolean aux = false;
        jtxtCedulaIdentidad.setEnabled(aux);
        jtxtDireccion.setEnabled(aux);
        jtxtNit.setEnabled(aux);
        jtxtNombreCompleto.setEnabled(aux);
        jtxtOtrosDatos.setEnabled(aux);
        jtxtRazonSocial.setEnabled(aux);
        jtxtTelefonos.setEnabled(aux);
        jcEstado.setEnabled(aux);
    }

    private FormClienteProveedor() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void headerTabla(){
        Font f = new Font("Times New Roman", Font.BOLD, 13);
        
        jtClienteProveedor.getTableHeader().setFont(f);
        jtClienteProveedor.getTableHeader().setBackground(Color.orange);
    }
    
    
    public void llenarTablaClienteProveedor(){
        ArrayList<ClienteProveedor> lista = new ArrayList<ClienteProveedor>();
        lista = clienteProveedorDAO.getListaClienteProveedor(tipo);

        dtm = (DefaultTableModel) this.jtClienteProveedor.getModel();
        dtm.setRowCount(0);

        jtClienteProveedor.setModel(dtm);

        Object[] fila = new Object[9];
        for (int i = 0; i < lista.size(); i++) {
            fila[0] = lista.get(i).getId();
            fila[1] = lista.get(i).getCedulaIdentidad();
            fila[2] = lista.get(i).getNombreCompleto();
            fila[3] = lista.get(i).getRazonSocial();
            fila[4] = lista.get(i).getNit();
            fila[5] = lista.get(i).getDireccion();
            fila[6] = lista.get(i).getTelefonos();
            fila[7] = lista.get(i).getOtrosDatos();
            if(lista.get(i).getEstado().equals("V")){
                fila[8] = true;
            }
            else{
                fila[8] = false;
            }
            
            
            dtm.addRow(fila);
        }

        jtClienteProveedor.setModel(dtm);
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
        jtxtCedulaIdentidad.setText("");
        jtxtDireccion.setText("");
        jtxtNit.setText("");
        jtxtNombreCompleto.setText("");
        jtxtOtrosDatos.setText("");
        jtxtTelefonos.setText("");
        jtxtRazonSocial.setText("");
        
        jcEstado.setSelected(false);
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
        jtClienteProveedor = new javax.swing.JTable();
        jlTituloFormulario = new javax.swing.JLabel();
        jlEdicion = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jtxtCedulaIdentidad = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jtxtNit = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jtxtNombreCompleto = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jtxtRazonSocial = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jtxtDireccion = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jtxtTelefonos = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtxtOtrosDatos = new javax.swing.JTextArea();
        jcEstado = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jbGuardar = new javax.swing.JButton();
        jbEditar = new javax.swing.JButton();
        jbCancelar = new javax.swing.JButton();
        jbEliminar = new javax.swing.JButton();
        jbSalir = new javax.swing.JButton();
        jbNuevo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jtClienteProveedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "id", "Cedula", "Nombre Completo", "Razon Social", "Nit", "Dirección", "Teléfonos", "Otros Datos", "Estado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtClienteProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtClienteProveedorMouseClicked(evt);
            }
        });
        jtClienteProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtClienteProveedorKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jtClienteProveedor);
        if (jtClienteProveedor.getColumnModel().getColumnCount() > 0) {
            jtClienteProveedor.getColumnModel().getColumn(0).setMinWidth(0);
            jtClienteProveedor.getColumnModel().getColumn(0).setPreferredWidth(0);
            jtClienteProveedor.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        jlTituloFormulario.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        jlTituloFormulario.setForeground(new java.awt.Color(153, 0, 51));
        jlTituloFormulario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlTituloFormulario.setText("titulo form");

        jLabel1.setForeground(new java.awt.Color(153, 0, 51));
        jLabel1.setText("Cedula de Identidad");

        jLabel4.setForeground(new java.awt.Color(153, 0, 51));
        jLabel4.setText("Nit");

        jLabel2.setForeground(new java.awt.Color(153, 0, 51));
        jLabel2.setText("Nombre Completo");

        jLabel3.setForeground(new java.awt.Color(153, 0, 51));
        jLabel3.setText("Razon Social");

        jLabel5.setForeground(new java.awt.Color(153, 0, 51));
        jLabel5.setText("Direccion");

        jLabel6.setForeground(new java.awt.Color(153, 0, 51));
        jLabel6.setText("Telefonos");

        jLabel7.setForeground(new java.awt.Color(153, 0, 51));
        jLabel7.setText("Otros Datos");

        jtxtOtrosDatos.setColumns(20);
        jtxtOtrosDatos.setRows(5);
        jScrollPane2.setViewportView(jtxtOtrosDatos);

        jcEstado.setForeground(new java.awt.Color(153, 0, 51));
        jcEstado.setText("Estado");
        jcEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcEstadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jtxtDireccion, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jtxtNombreCompleto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE))
                                .addComponent(jLabel1)
                                .addComponent(jLabel5)
                                .addComponent(jtxtCedulaIdentidad, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel6)
                            .addComponent(jtxtTelefonos, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxtRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jtxtNit, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcEstado)))
                    .addComponent(jLabel7))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxtCedulaIdentidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxtNit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtxtNombreCompleto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtxtRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxtTelefonos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jcEstado))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jbGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Save-icon.png"))); // NOI18N
        jbGuardar.setText("Guardar");
        jbGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbGuardarActionPerformed(evt);
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

        jbNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/folder-add-icon.png"))); // NOI18N
        jbNuevo.setText("Nuevo");
        jbNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbNuevoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jbNuevo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbGuardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbEliminar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbGuardar)
                    .addComponent(jbEliminar)
                    .addComponent(jbSalir)
                    .addComponent(jbNuevo)
                    .addComponent(jbEditar)
                    .addComponent(jbCancelar))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlEdicion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jlTituloFormulario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 183, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlTituloFormulario)
                    .addComponent(jlEdicion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jcEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcEstadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcEstadoActionPerformed

    private void jbNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbNuevoActionPerformed
        byte x = 1;
        botones(x);
        jlEdicion.setText("0");
    }//GEN-LAST:event_jbNuevoActionPerformed

    private void jbGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbGuardarActionPerformed
        byte x = 4;
        botones(x);
        guardar();
        jlEdicion.setText("0");
    }//GEN-LAST:event_jbGuardarActionPerformed

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

    private void jbEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbEliminarActionPerformed
        eliminar();
    }//GEN-LAST:event_jbEliminarActionPerformed

    private void jbSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSalirActionPerformed
        dispose();
    }//GEN-LAST:event_jbSalirActionPerformed

    private void jtClienteProveedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtClienteProveedorKeyReleased
        seleccionarClienteProveedor();
    }//GEN-LAST:event_jtClienteProveedorKeyReleased

    private void jtClienteProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtClienteProveedorMouseClicked
        seleccionarClienteProveedor();
    }//GEN-LAST:event_jtClienteProveedorMouseClicked

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
            java.util.logging.Logger.getLogger(FormClienteProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormClienteProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormClienteProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormClienteProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormClienteProveedor().setVisible(true);
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JButton jbEditar;
    private javax.swing.JButton jbEliminar;
    private javax.swing.JButton jbGuardar;
    private javax.swing.JButton jbNuevo;
    private javax.swing.JButton jbSalir;
    private javax.swing.JCheckBox jcEstado;
    private javax.swing.JLabel jlEdicion;
    private javax.swing.JLabel jlTituloFormulario;
    private javax.swing.JTable jtClienteProveedor;
    private javax.swing.JTextField jtxtCedulaIdentidad;
    private javax.swing.JTextField jtxtDireccion;
    private javax.swing.JTextField jtxtNit;
    private javax.swing.JTextField jtxtNombreCompleto;
    private javax.swing.JTextArea jtxtOtrosDatos;
    private javax.swing.JTextField jtxtRazonSocial;
    private javax.swing.JTextField jtxtTelefonos;
    // End of variables declaration//GEN-END:variables

    private void guardar() {
        ClienteProveedor cp = new ClienteProveedor();
                
        String cedulaIdentidad = null;
        String nombreCompleto = null;
        String razonSocial = null;
        String nit = null;
        String direccion = null;
        String telefonos = null;
        String otrosDatos = null;
        String estado = null;
        
        cedulaIdentidad = jtxtCedulaIdentidad.getText().toUpperCase();
        nombreCompleto = jtxtNombreCompleto.getText().toUpperCase();
        razonSocial = jtxtRazonSocial.getText().toUpperCase();
        nit = jtxtNit.getText();
        direccion = jtxtDireccion.getText().toUpperCase();
        telefonos = jtxtTelefonos.getText().toUpperCase();
        otrosDatos = jtxtOtrosDatos.getText().toUpperCase();
        estado = (jcEstado.isSelected()?"V":"N");
        
        cp.setCedulaIdentidad(cedulaIdentidad);
        cp.setDireccion(direccion);
        cp.setEstado(estado);
        cp.setNit(nit);
        cp.setNombreCompleto(nombreCompleto);
        cp.setOtrosDatos(otrosDatos);
        cp.setRazonSocial(razonSocial);
        cp.setTelefonos(telefonos);
        cp.setTipo(tipo);
        
        if(jlEdicion.getText().equals("1")){
            cp.setId(getIdClienteProveedorSeleeccionado());
            clienteProveedorDAO.actualizarClienteProveedor(cp);
        }
        else{
            clienteProveedorDAO.insertarClienteProveedor(cp);
        }
        
        llenarTablaClienteProveedor();
        
    }

    private void eliminar() {        
        clienteProveedorDAO.eliminarClienteProveedor(getIdClienteProveedorSeleeccionado());
        llenarTablaClienteProveedor();
    }
    
    public int getIdClienteProveedorSeleeccionado(){
        
        int idClienteProveedor;
        
        int fila = jtClienteProveedor.getSelectedRow();
        idClienteProveedor = Integer.parseInt(jtClienteProveedor.getValueAt(fila, 0).toString());
        
        return idClienteProveedor;                
    }
    
    public void seleccionarClienteProveedor(){
        boolean aux;
        int idClienteProveedor;
        int fila = jtClienteProveedor.getSelectedRow();
        
        idClienteProveedor = Integer.parseInt(jtClienteProveedor.getValueAt(fila, 0).toString());
        jtxtCedulaIdentidad.setText(jtClienteProveedor.getValueAt(fila, 1).toString());
        jtxtNombreCompleto.setText(jtClienteProveedor.getValueAt(fila, 2).toString());
        jtxtRazonSocial.setText(jtClienteProveedor.getValueAt(fila, 3).toString());
        jtxtNit.setText(jtClienteProveedor.getValueAt(fila, 4).toString());
        jtxtDireccion.setText(jtClienteProveedor.getValueAt(fila, 5).toString());
        jtxtTelefonos.setText(jtClienteProveedor.getValueAt(fila, 6).toString());
        jtxtOtrosDatos.setText(jtClienteProveedor.getValueAt(fila, 7).toString());
        
        aux = ((Boolean)jtClienteProveedor.getValueAt(fila, 8)?true:false);
        if(aux){jcEstado.setSelected(aux);}else{jcEstado.setSelected(false);}
        
        byte x = 4;
        botones(x);
        jlEdicion.setText("0");
    }
}
