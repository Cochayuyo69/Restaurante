package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

public class PlatosDao {

    static Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public boolean Registrar(Platos pla) {
        String sql = "INSERT INTO platos (nombre, precio, fecha) VALUES (?,?,?)";
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, pla.getNombre());
            ps.setDouble(2, pla.getPrecio());
            ps.setString(3, pla.getFecha());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }finally {
            try {
                long fin = System.currentTimeMillis();
                System.out.println("Tiempo total de consulta SQL: " + (fin - Conexion.inicio) + " ms");
                long time = fin - Conexion.inicio;
            Conexion.guardarVelocidad((int)time);
                con.close();
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }
    }

    public List Listar(String valor) {
        List<Platos> Lista = new ArrayList();
        String sql = "SELECT * FROM platos";
        String consulta = "SELECT * FROM platos WHERE nombre LIKE '%"+valor+"%'";
        try {
            con = Conexion.getConnection();
            if(valor.equalsIgnoreCase("")){
                ps = con.prepareStatement(sql);
            }else{
                ps = con.prepareStatement(consulta);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                Platos pl = new Platos();
                pl.setId(rs.getInt("id"));
                pl.setNombre(rs.getString("nombre"));
                pl.setPrecio(rs.getDouble("precio"));
                Lista.add(pl);
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

    public boolean Eliminar(int id) {
        String sql = "DELETE FROM platos WHERE id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
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
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }
    }

    public boolean Modificar(Platos pla) {
        String sql = "UPDATE platos SET nombre=?, precio=? WHERE id=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, pla.getNombre());
            ps.setDouble(2, pla.getPrecio());
            ps.setInt(3, pla.getId());
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
    
    public static Integer obtenerIdPlatoPorNombre(String nombre){
        con = Conexion.getConnection();
        String sql = "SELECT id FROM platos WHERE nombre = ?";
        Integer id = null;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
            con.close();
            JOptionPane.showMessageDialog(null, "Éxito Plato Encontrado");
            return id;
        } catch (SQLException x) {
            System.out.println(x.toString());
            JOptionPane.showMessageDialog(null, "Error Plato No Encontrado");
        }
        return id;
    }
    
    public static DefaultComboBoxModel<String> obtenerModeloComboNombresPlatos() throws SQLException {
        Connection con = Conexion.getConnection();
        String sql = "SELECT nombre FROM platos ORDER BY nombre ASC";
        DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                modelo.addElement(rs.getString("nombre"));
            }
            con.close();
            return modelo;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return modelo;
        }
    }
    
    public static String obtenerNombrePlatoPorId(int idPlato) throws SQLException {
        Connection con = Conexion.getConnection();
        String sql = "SELECT nombre FROM platos WHERE id = ?";
        String nombre = null;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPlato);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nombre = rs.getString("nombre");
            }
            con.close();
            return nombre;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return null;
        }
    }
}
