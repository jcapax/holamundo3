/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import almacenes.conectorDB.DatabaseUtils;
import almacenes.model.DetalleFacturaFacil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jcarlos.porcel
 */
public class FacturaFacilDAOImpl implements FacturaFacilDAO{
    
    private DatabaseUtils databaseUtils;
    private Connection connectionDB;

    public FacturaFacilDAOImpl(Connection connectionDB) {
        this.connectionDB = connectionDB;
        this.databaseUtils = new DatabaseUtils();
    }

    @Override
    public List<String> getListaProductosAutocompletado() {
        List<String> list = new ArrayList<>();
        
        String sql = "Select detalle from detalle_factura_facil group by detalle order by detalle";
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                list.add(rs.getString("detalle"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturaFacilDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
    }   

    @Override
    public void insertarDetalleFacturaFacil(ArrayList<DetalleFacturaFacil> facil, int idFacturaFacil) {
        String sql = null;
        PreparedStatement ps;
        ResultSet rs;
        for(int i=0; i<facil.size(); i++){
            sql = "INSERT INTO detalle_factura_facil (id_factura_facil, detalle, cantidad, valor_unitario, valor_total) "
                        + "VALUES ("+idFacturaFacil
                        +", '"+facil.get(i).getDetalle()
                        +"', "+facil.get(i).getCantidad()
                        +", "+facil.get(i).getValorUnitario()
                        +", "+facil.get(i).getValorTotal()
                        +")";
            try {                
                ps = connectionDB.prepareStatement(sql);
                rs = ps.executeQuery();
            } catch (SQLException ex) {
                Logger.getLogger(FacturaFacilDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
    }

    @Override
    public int getIdFacturaUltima() {
        int id = 0;
        String sql = "Select id From factura_venta Order by id Desc Limit 1";
        try {
            PreparedStatement ps = connectionDB.prepareStatement(sql);            
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                id = rs.getInt("id");
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(SucursalDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
    
}
