package Modelo;

import java.sql.Timestamp;
import java.util.Date;

public class Producto {
    
    //Atributos
    private int idProducto;
    private String nombre;
    private String descripcion;
    private String unidadMedida;
    private int stock;
    private Timestamp fechaActual;
    
    //Contructor
    public Producto(){
        this.idProducto = 0;
        this.nombre = "";
        this.stock = 0;
        this.descripcion = "";
        this.unidadMedida = "";
        this.fechaActual = new Timestamp(new Date().getTime());
    }
    //Contructor sobrecargado

    public Producto(int idProducto, String nombre, String descripcion, String unidadMedida, int stock, Timestamp fechaActual) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.unidadMedida = unidadMedida;
        this.stock = stock;
        this.fechaActual = fechaActual;
    }
    
    
    //Set and get

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Timestamp getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Timestamp fechaActual) {
        this.fechaActual = fechaActual;
    }
    

}
