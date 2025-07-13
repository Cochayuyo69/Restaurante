package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Conexion {

    public static Connection con;
    public static String ip = "";
    public static String puerto ="";
    public static String bd = "";
    public static String usuario = "";
    public static String passw = "";
    public static long inicio;
    public static Connection getConnection(){
        inicio = System.currentTimeMillis();
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://"+ip+":"+puerto+";databaseName="+bd+";user="+usuario+";password="+passw+";encrypt=false;");
//            Connection cn = DriverManager.getConnection("jdbc:sqlserver://192.168.1.10:1433;databaseName=bd_heladeria;user=sa;password=123456;");jdbc:sqlserver://localhost:1433;databaseName=bd_heladeria;encrypt=true;trustServerCertificate=true;
            return con;
        } catch (SQLException e) {
            System.out.println("Error en la conexion local " + e);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return null;
    }
    
    public static void guardarVelocidad(int tiempoMs) {
        String sql = "INSERT INTO velocidad (ms) VALUES (?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, tiempoMs);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al guardar tiempo en tabla velocidad: " + e.toString());
        }
    }
}