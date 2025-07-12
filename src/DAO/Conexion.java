package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    
    Connection con;

    public Connection getConnection() {
        try {
            // Cargar el controlador JDBC para SQL Server
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            
            String conexionURL = "jdbc:sqlserver://localhost:1433;"
                                + "database=restaurante;"
                                + "user=sa;"
                                + "password=0;"
                                + "encrypt=true;"
                                + "trustServerCertificate=true;"
                                + "loginTimeout=30;";
            // Establecer la conexión sin usuario ni contraseña (autenticación integrada)
            con = DriverManager.getConnection(conexionURL);
            return con;
        } catch (ClassNotFoundException e) {
            System.out.println("Error: No se encontró el controlador JDBC.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos.");
            e.printStackTrace();
        }
        return null;
    }
}