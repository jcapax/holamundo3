/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package almacenes.model;

import java.sql.Date;


/**
 *
 * @author jcapax
 */
public class FacturaVenta {
    private int idSucursal;
    private int especificacion;
    private int correlativoSucursal;
    private Date fechaFactura;
    private int nroFactura;
    private String nroAutorizacion;
    private String estado;
    private String nit;
    private String razonSocial;
    private double importeTotal;
    private double importeIce;
    private double importeExportaciones;
    private double importeVentasTasaCero;
    private double importeSubtotal;
    private double importeRebajas;
    private double importeBaseDebitoFiscal;
    private double debitoFiscal;
    private String codigoControl;
    private int idTransaccion;
    private Date fechaLimiteEmision;
    private int idDosificacion;

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public int getEspecificacion() {
        return especificacion;
    }

    public void setEspecificacion(int especificacion) {
        this.especificacion = especificacion;
    }

    public int getCorrelativoSucursal() {
        return correlativoSucursal;
    }

    public void setCorrelativoSucursal(int correlativoSucursal) {
        this.correlativoSucursal = correlativoSucursal;
    }

    public Date getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(Date fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public int getNroFactura() {
        return nroFactura;
    }

    public void setNroFactura(int nroFactura) {
        this.nroFactura = nroFactura;
    }

    public String getNroAutorizacion() {
        return nroAutorizacion;
    }

    public void setNroAutorizacion(String nroAutorizacion) {
        this.nroAutorizacion = nroAutorizacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public double getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(double importeTotal) {
        this.importeTotal = importeTotal;
    }

    public double getImporteIce() {
        return importeIce;
    }

    public void setImporteIce(double importeIce) {
        this.importeIce = importeIce;
    }

    public double getImporteExportaciones() {
        return importeExportaciones;
    }

    public void setImporteExportaciones(double importeExportaciones) {
        this.importeExportaciones = importeExportaciones;
    }

    public double getImporteVentasTasaCero() {
        return importeVentasTasaCero;
    }

    public void setImporteVentasTasaCero(double importeVentasTasaCero) {
        this.importeVentasTasaCero = importeVentasTasaCero;
    }

    public double getImporteSubtotal() {
        return importeSubtotal;
    }

    public void setImporteSubtotal(double importeSubtotal) {
        this.importeSubtotal = importeSubtotal;
    }

    public double getImporteRebajas() {
        return importeRebajas;
    }

    public void setImporteRebajas(double importeRebajas) {
        this.importeRebajas = importeRebajas;
    }

    public double getImporteBaseDebitoFiscal() {
        return importeBaseDebitoFiscal;
    }

    public void setImporteBaseDebitoFiscal(double importeBaseDebitoFiscal) {
        this.importeBaseDebitoFiscal = importeBaseDebitoFiscal;
    }

    public double getDebitoFiscal() {
        return debitoFiscal;
    }

    public void setDebitoFiscal(double debitoFiscal) {
        this.debitoFiscal = debitoFiscal;
    }

    public String getCodigoControl() {
        return codigoControl;
    }

    public void setCodigoControl(String codigoControl) {
        this.codigoControl = codigoControl;
    }

    public int getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(int idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public Date getFechaLimiteEmision() {
        return fechaLimiteEmision;
    }

    public void setFechaLimiteEmision(Date fechaLimiteEmision) {
        this.fechaLimiteEmision = fechaLimiteEmision;
    }

    public int getIdDosificacion() {
        return idDosificacion;
    }

    public void setIdDosificacion(int idDosificacion) {
        this.idDosificacion = idDosificacion;
    }
    
    
    
}
