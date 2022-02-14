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
public class Producto {
    private int id;
    private int idUnidadProducto;
    private int idLaboratorio;
    private String nombreLaboratorio;
    private int idFamilia;
    private int idUnidadMedida;
    private String nombreFamilia;
    private String claseProducto;
    private String descripcion;
    private String principioActivo;
    private String indicaciones;
    private String simbolo;
    private String nombreUnidadMedida;
    private String tipoCuenta;
    private String estado;
    private int recargo;
    private int controlStock;
    private int caducidad;
    private String usuario;
    private Double precioCompra;
    private Double precioVenta;
    private int stock;

    public String getNombreUnidadMedida() {
        return nombreUnidadMedida;
    }

    public void setNombreUnidadMedida(String nombreUnidadMedida) {
        this.nombreUnidadMedida = nombreUnidadMedida;
    }

    public int getIdUnidadMedida() {
        return idUnidadMedida;
    }

    public void setIdUnidadMedida(int idUnidadMedida) {
        this.idUnidadMedida = idUnidadMedida;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    

    public int getIdUnidadProducto() {
        return idUnidadProducto;
    }

    public void setIdUnidadProducto(int idUnidadProducto) {
        this.idUnidadProducto = idUnidadProducto;
    }

    public Double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(Double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public Double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(Double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public Producto(int idLaboratorio, int idFamilia, String claseProducto, 
            String descripcion, String principioActivo, String indicaciones, 
            String tipoCuenta, String estado, int controlStock, 
            int caducidad, String usuario) {
        this.idLaboratorio = idLaboratorio;
        this.idFamilia = idFamilia;
        this.claseProducto = claseProducto;
        this.descripcion = descripcion;
        this.principioActivo = principioActivo;
        this.indicaciones = indicaciones;
        this.tipoCuenta = tipoCuenta;
        this.estado = estado;
        this.controlStock = controlStock;
        this.caducidad = caducidad;
        this.usuario = usuario;
    }
    
    

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public String getNombreLaboratorio() {
        return nombreLaboratorio;
    }

    public void setNombreLaboratorio(String nombreLaboratorio) {
        this.nombreLaboratorio = nombreLaboratorio;
    }

    public String getNombreFamilia() {
        return nombreFamilia;
    }

    public void setNombreFamilia(String nombreFamilia) {
        this.nombreFamilia = nombreFamilia;
    }

    public int getIdLaboratorio() {
        return idLaboratorio;
    }

    public void setIdLaboratorio(int idLaboratorio) {
        this.idLaboratorio = idLaboratorio;
    }

    public int getIdFamilia() {
        return idFamilia;
    }

    public void setIdFamilia(int idFamilia) {
        this.idFamilia = idFamilia;
    }

    public String getPrincipioActivo() {
        return principioActivo;
    }

    public void setPrincipioActivo(String principioActivo) {
        this.principioActivo = principioActivo;
    }

    public String getIndicaciones() {
        return indicaciones;
    }

    public void setIndicaciones(String indicaciones) {
        this.indicaciones = indicaciones;
    }

    public int getCaducidad() {
        return caducidad;
    }

    public void setCaducidad(int caducidad) {
        this.caducidad = caducidad;
    }

    public Producto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClaseProducto() {
        return claseProducto;
    }

    public void setClaseProducto(String claseProducto) {
        this.claseProducto = claseProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getRecargo() {
        return recargo;
    }

    public void setRecargo(int recargo) {
        this.recargo = recargo;
    }

    public int getControlStock() {
        return controlStock;
    }

    public void setControlStock(int controlStock) {
        this.controlStock = controlStock;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    
    
    
}
