
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginDao {
    static Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    public login log(String correo, String pass){
        login l = new login();
        String sql = "SELECT * FROM usuarios WHERE correo = ? AND pass = ?";
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, correo);
            ps.setString(2, pass);
            rs= ps.executeQuery();
            if (rs.next()) {
                l.setId(rs.getInt("id"));
                l.setNombre(rs.getString("nombre"));
                l.setCorreo(rs.getString("correo"));
                l.setPass(rs.getString("pass"));
                l.setRol(rs.getString("rol"));
                
            }
            long fin = System.currentTimeMillis();
            System.out.println("Tiempo total de consulta SQL: " + (fin - Conexion.inicio) + " ms");
            long time = fin - Conexion.inicio;
            Conexion.guardarVelocidad((int)time);
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return l;
    }
    
    public boolean Registrar(login reg){
        String sql = "INSERT INTO usuarios (nombre, correo, pass, rol) VALUES (?,?,?,?)";
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, reg.getNombre());
            ps.setString(2, reg.getCorreo());
            ps.setString(3, reg.getPass());
            ps.setString(4, reg.getRol());
            ps.execute();
            long fin = System.currentTimeMillis();
            System.out.println("Tiempo total de consulta SQL: " + (fin - Conexion.inicio) + " ms");
            long time = fin - Conexion.inicio;
            Conexion.guardarVelocidad((int)time);
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }
    
    public List ListarUsuarios(){
       List<login> Lista = new ArrayList();
       String sql = "SELECT * FROM usuarios";
       try {
           con = Conexion.getConnection();
           ps = con.prepareStatement(sql);
           rs = ps.executeQuery();
           while (rs.next()) {               
               login lg = new login();
               lg.setId(rs.getInt("id"));
               lg.setNombre(rs.getString("nombre"));
               lg.setCorreo(rs.getString("correo"));
               lg.setRol(rs.getString("rol"));
               Lista.add(lg);
               long fin = System.currentTimeMillis();
               System.out.println("Tiempo total de consulta SQL: " + (fin - Conexion.inicio) + " ms");
               long time = fin - Conexion.inicio;
               Conexion.guardarVelocidad((int)time);
           }
       } catch (SQLException e) {
           System.out.println(e.toString());
       }
       return Lista;
   }
    
    public boolean ModificarDatos(Config conf){
        String sql = "UPDATE config SET ruc=?, nombre=?, telefono=?, direccion=?, mensaje=? WHERE id=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, conf.getRuc());
            ps.setString(2, conf.getNombre());
            ps.setString(3, conf.getTelefono());
            ps.setString(4, conf.getDireccion());
            ps.setString(5, conf.getMensaje());
            ps.setInt(6, conf.getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        } finally {
            try {
                long fin = System.currentTimeMillis();
                System.out.println("Tiempo total de consulta SQL: " + (fin - Conexion.inicio) + " ms");
                long time = fin - Conexion.inicio;
                Conexion.guardarVelocidad((int)time);
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
    
    public Config datosEmpresa(){
        Config conf = new Config();
        String sql = "SELECT * FROM config";
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);
            rs= ps.executeQuery();
            if (rs.next()) {
                conf.setId(rs.getInt("id"));
                conf.setRuc(rs.getString("ruc"));
                conf.setNombre(rs.getString("nombre"));
                conf.setTelefono(rs.getString("telefono"));
                conf.setDireccion(rs.getString("direccion"));
                conf.setMensaje(rs.getString("mensaje"));
                
            }
            long fin = System.currentTimeMillis();
            System.out.println("Tiempo total de consulta SQL: " + (fin - Conexion.inicio) + " ms");
            long time = fin - Conexion.inicio;
            Conexion.guardarVelocidad((int)time);
            
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return conf;
    }
}
