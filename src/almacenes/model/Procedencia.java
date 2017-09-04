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
public class Procedencia {
    private int id;
    private String descripcion;
    private String usuario;

    public Procedencia(String descripcion, String usuario) {
        
        this.descripcion = descripcion;
        this.usuario = usuario;
    }

    public Procedencia(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }
    
    public Procedencia() {
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    
    
}
