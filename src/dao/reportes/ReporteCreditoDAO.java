/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.reportes;

/**
 *
 * @author jcarlos.porcel
 */
public interface ReporteCreditoDAO {
    public void vistaPreviaPagoCredito(int idTransaccion, String detalle);
    public void vistaPreviaCredito(int idTransaccion);
    public void vistaPreviaEntregaPendiente(int idTransaccion);
    public void vistaPreviaEntregaProductosCredito(int idTransaccion, int idTransaccionEntrega);
    public void vistaPreviaEntregaProductosCredito();
            
}
