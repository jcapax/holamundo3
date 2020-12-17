/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package almacenes.model;

/**
 *
 * @author jcapax
 */
public class UnidadProducto {
    private int id ;
    private int idProdcuto;
    private int idUnidadMedida;
    private String nombreUnidadMedida;
    private int unidadPrincipal;
    private String nombreUnidadPrincipal;
    private double stockMinimo;
    private double precioVenta;
    private double precioVentaRebaja;
    private double precioVentaAumento;
    private double precioCompra;
    private int garantiaMeses;
    private int actualizacion;
    private String usuario;
    private String codigoAdjunto;
    private String nombreProducto;
    private int cantidad;
    private int descuento;
    private byte idLugar;

    public byte getIdLugar() {
        return idLugar;
    }

    public void setIdLugar(byte idLugar) {
        this.idLugar = idLugar;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getDescuento() {
        return descuento;
    }

    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }
    
    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }
    
    public String getCodigoAdjunto() {
        return codigoAdjunto;
    }

    public void setCodigoAdjunto(String codigoAdjunto) {
        this.codigoAdjunto = codigoAdjunto;
    }
    
    

    public int getGarantiaMeses() {
        return garantiaMeses;
    }

    public void setGarantiaMeses(int garantiaMeses) {
        this.garantiaMeses = garantiaMeses;
    }

    public UnidadProducto(int idProdcuto, int idUnidadMedida, int unidadPrincipal, double stockMinimo, double precioVenta, double precioVentaRebaja, double precioVentaAumento, double precioCompra, int actualizacion, String usuario) {
        this.idProdcuto = idProdcuto;
        this.idUnidadMedida = idUnidadMedida;
        this.unidadPrincipal = unidadPrincipal;
        this.stockMinimo = stockMinimo;
        this.precioVenta = precioVenta;
        this.precioVentaRebaja = precioVentaRebaja;
        this.precioVentaAumento = precioVentaAumento;
        this.precioCompra = precioCompra;
        this.actualizacion = actualizacion;
        this.usuario = usuario;
    }

    public UnidadProducto(int id, int idProdcuto, int idUnidadMedida, int unidadPrincipal, double stockMinimo, double precioVenta, double precioVentaRebaja, double precioVentaAumento, double precioCompra, String usuario) {
        this.id = id;
        this.idProdcuto = idProdcuto;
        this.idUnidadMedida = idUnidadMedida;
        this.unidadPrincipal = unidadPrincipal;
        this.stockMinimo = stockMinimo;
        this.precioVenta = precioVenta;
        this.precioVentaRebaja = precioVentaRebaja;
        this.precioVentaAumento = precioVentaAumento;
        this.precioCompra = precioCompra;
        this.usuario = usuario;
    }
    
    public UnidadProducto() {
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProdcuto() {
        return idProdcuto;
    }

    public void setIdProdcuto(int idProdcuto) {
        this.idProdcuto = idProdcuto;
    }

    public int getIdUnidadMedida() {
        return idUnidadMedida;
    }

    public void setIdUnidadMedida(int idUnidadMedida) {
        this.idUnidadMedida = idUnidadMedida;
    }

    public String getNombreUnidadMedida() {
        return nombreUnidadMedida;
    }

    public void setNombreUnidadMedida(String nombreUnidadMedida) {
        this.nombreUnidadMedida = nombreUnidadMedida;
    }

    public int getUnidadPrincipal() {
        return unidadPrincipal;
    }

    public void setUnidadPrincipal(int unidadPrincipal) {
        this.unidadPrincipal = unidadPrincipal;
    }

    public String getNombreUnidadPrincipal() {
        return nombreUnidadPrincipal;
    }

    public void setNombreUnidadPrincipal(String nombreUnidadPrincipal) {
        this.nombreUnidadPrincipal = nombreUnidadPrincipal;
    }

    public double getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(double stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public double getPrecioVentaRebaja() {
        return precioVentaRebaja;
    }

    public void setPrecioVentaRebaja(double precioVentaRebaja) {
        this.precioVentaRebaja = precioVentaRebaja;
    }

    public double getPrecioVentaAumento() {
        return precioVentaAumento;
    }

    public void setPrecioVentaAumento(double precioVentaAumento) {
        this.precioVentaAumento = precioVentaAumento;
    }

    public double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public int getActualizacion() {
        return actualizacion;
    }

    public void setActualizacion(int actualizacion) {
        this.actualizacion = actualizacion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    
    
    
    
}
