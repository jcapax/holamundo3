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
public class ListaProductos {
   private int id; 
   private int idRubroProducto;
   private String rubro;
   private int idMarca;
   private String marca;
   private int idProcedencia;
   private String procedencia;
   private String descripcion;
   private String estado;
   private int controlStock;
   private int IDUNIDADMEDIDA;
   private String nombreUnidadMedida;
   private int UNIDADPRINCIPAL;
   private double STOCKMINIMO;
   private double PRECIOVENTA;
   private double PRECIOVENTAREBAJA;
   private double PRECIOVENTAAUMENTO;
   private double PRECIOCOMPRA;
   private int ACTUALIZACION;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdRubroProducto() {
        return idRubroProducto;
    }

    public void setIdRubroProducto(int idRubroProducto) {
        this.idRubroProducto = idRubroProducto;
    }

    public String getRubro() {
        return rubro;
    }

    public void setRubro(String rubro) {
        this.rubro = rubro;
    }

    public int getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getIdProcedencia() {
        return idProcedencia;
    }

    public void setIdProcedencia(int idProcedencia) {
        this.idProcedencia = idProcedencia;
    }

    public String getProcedencia() {
        return procedencia;
    }

    public void setProcedencia(String procedencia) {
        this.procedencia = procedencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getControlStock() {
        return controlStock;
    }

    public void setControlStock(int controlStock) {
        this.controlStock = controlStock;
    }

    public int getIDUNIDADMEDIDA() {
        return IDUNIDADMEDIDA;
    }

    public void setIDUNIDADMEDIDA(int IDUNIDADMEDIDA) {
        this.IDUNIDADMEDIDA = IDUNIDADMEDIDA;
    }

    public String getNombreUnidadMedida() {
        return nombreUnidadMedida;
    }

    public void setNombreUnidadMedida(String nombreUnidadMedida) {
        this.nombreUnidadMedida = nombreUnidadMedida;
    }

    public int getUNIDADPRINCIPAL() {
        return UNIDADPRINCIPAL;
    }

    public void setUNIDADPRINCIPAL(int UNIDADPRINCIPAL) {
        this.UNIDADPRINCIPAL = UNIDADPRINCIPAL;
    }

    public double getSTOCKMINIMO() {
        return STOCKMINIMO;
    }

    public void setSTOCKMINIMO(double STOCKMINIMO) {
        this.STOCKMINIMO = STOCKMINIMO;
    }

    public double getPRECIOVENTA() {
        return PRECIOVENTA;
    }

    public void setPRECIOVENTA(double PRECIOVENTA) {
        this.PRECIOVENTA = PRECIOVENTA;
    }

    public double getPRECIOVENTAREBAJA() {
        return PRECIOVENTAREBAJA;
    }

    public void setPRECIOVENTAREBAJA(double PRECIOVENTAREBAJA) {
        this.PRECIOVENTAREBAJA = PRECIOVENTAREBAJA;
    }

    public double getPRECIOVENTAAUMENTO() {
        return PRECIOVENTAAUMENTO;
    }

    public void setPRECIOVENTAAUMENTO(double PRECIOVENTAAUMENTO) {
        this.PRECIOVENTAAUMENTO = PRECIOVENTAAUMENTO;
    }

    public double getPRECIOCOMPRA() {
        return PRECIOCOMPRA;
    }

    public void setPRECIOCOMPRA(double PRECIOCOMPRA) {
        this.PRECIOCOMPRA = PRECIOCOMPRA;
    }

    public int getACTUALIZACION() {
        return ACTUALIZACION;
    }

    public void setACTUALIZACION(int ACTUALIZACION) {
        this.ACTUALIZACION = ACTUALIZACION;
    }
   
   
}
