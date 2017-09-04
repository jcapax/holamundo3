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
public class UnidadMedida {
    private int id;
    private String descripcion;
    private String simbolo;
    private String usuario;

    public UnidadMedida(){
        
    }
    
    public UnidadMedida(String descripcion, String simbolo, String usuario) {
        this.descripcion = descripcion;
        this.simbolo = simbolo;
        this.usuario = usuario;
    }

    public UnidadMedida(int id, String simbolo) {
        this.id = id;
        this.simbolo = simbolo;
    }
    
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }
    
    
}
