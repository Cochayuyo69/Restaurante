/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
import java.sql.Timestamp;

public class Proveedor {
    // Atributos
    private int idProveedor;
    private String nombre;
    private String telefono;
    private String email;
    private String direccion;
    private Timestamp fechaRegistro;
    private double precio;
    private int idProducto;

    // Constructor por defecto
    public Proveedor() {
        this.idProveedor = 0;
        this.nombre = "";
        this.telefono = "";
        this.email = "";
        this.direccion = "";
        this.fechaRegistro = new Timestamp(System.currentTimeMillis());
        this.precio = 0.0;
        this.idProducto = 0;
    }

    public Proveedor(int idProveedor, String nombre, String telefono, String email, String direccion, Timestamp fechaRegistro, double precio, int idProducto) {
        this.idProveedor = idProveedor;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
        this.fechaRegistro = fechaRegistro;
        this.precio = precio;
        this.idProducto = idProducto;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }
    
}
