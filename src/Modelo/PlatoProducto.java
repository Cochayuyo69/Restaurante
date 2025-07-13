/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Usuario
 */
public class PlatoProducto {
    private int idPlatoProducto;
    private int idPlato;
    private int idProducto;
    private double cantidad;

    // Constructor vacío
    public PlatoProducto() {
        this.idPlatoProducto = 0;
        this.idPlato = 0;
        this.idProducto = 0;
        this.cantidad = 0.0;
    }

    public PlatoProducto(int idPlatoProducto, int idPlato, int idProducto, double cantidad) {
        this.idPlatoProducto = idPlatoProducto;
        this.idPlato = idPlato;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
    }

    public int getIdPlatoProducto() {
        return idPlatoProducto;
    }

    public void setIdPlatoProducto(int idPlatoProducto) {
        this.idPlatoProducto = idPlatoProducto;
    }

    public int getIdPlato() {
        return idPlato;
    }

    public void setIdPlato(int idPlato) {
        this.idPlato = idPlato;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }
    
    
}
