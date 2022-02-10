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
public class Laboratorio {
    private int id;
    private String descripcion;
    

    public Laboratorio(String descripcion, String usuario) {
        this.descripcion = descripcion;        
    }

    public Laboratorio(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }
    
    public Laboratorio() {
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
    
}
