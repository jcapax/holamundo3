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
    private String claseProducto;
    private int idRubroProducto;
    private int idMarca;
    private int idProcedencia;
    private String descripcion;
    private String tipoCuenta;
    private String estado;
    private int recargo;
    private int controlStock;
    private int caducidad;
    private String usuario;

    public int getCaducidad() {
        return caducidad;
    }

    public void setCaducidad(int caducidad) {
        this.caducidad = caducidad;
    }

    
    public Producto(String claseProducto, int idRubroProducto, int idMarca, int idProcedencia, 
                String descripcion, String tipoCuenta, String estado, 
                int recargo, int controlStock, int caducidad, String usuario) {
        this.claseProducto = claseProducto;
        this.idRubroProducto = idRubroProducto;
        this.idMarca = idMarca;
        this.idProcedencia = idProcedencia;
        this.descripcion = descripcion;
        this.tipoCuenta = tipoCuenta;
        this.estado = estado;
        this.recargo = recargo;
        this.controlStock = controlStock;
        this.caducidad = caducidad;
        this.usuario = usuario;
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

    public int getIdRubroProducto() {
        return idRubroProducto;
    }

    public void setIdRubroProducto(int idRubroProducto) {
        this.idRubroProducto = idRubroProducto;
    }

    public int getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }

    public int getIdProcedencia() {
        return idProcedencia;
    }

    public void setIdProcedencia(int idProcedencia) {
        this.idProcedencia = idProcedencia;
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
