/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.reportes;

/**
 *
 * @author jcapax
 */
public interface ReporteComprasDAO {
    public void vistaPreviaCompras(int idTransaccion);
    public void vistaPreviaAjusteStock(int idTransaccion);
}
