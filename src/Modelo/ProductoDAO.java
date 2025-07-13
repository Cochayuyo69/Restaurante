package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

public class ProductoDAO {

    static Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public static void guardarProducto(Producto p) throws SQLException {
        con = Conexion.getConnection();
        String sql = "INSERT INTO Productos " +
                     "(nombre, descripcion, unidad_medida, stock, fecha_actualizacion) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString   (1, p.getNombre());
            ps.setString   (2, p.getDescripcion());
            ps.setString   (3, p.getUnidadMedida());
            ps.setInt      (4, p.getStock());
            ps.setTimestamp(5, p.getFechaActual());

            ps.executeUpdate();
            con.close();
            JOptionPane.showMessageDialog(null, "Producto Guardado Correctamente");
        }catch(SQLException x){
            System.out.println(x.toString());
            JOptionPane.showMessageDialog(null, "Error Producto No Guardado");
        }
    }

    public static void actualizarProducto(Producto p) throws SQLException {
        con = Conexion.getConnection();
        String sql = "UPDATE Productos SET " +
                     "nombre = ?, " +
                     "descripcion = ?, " +
                     "unidad_medida = ?, " +
                     "stock = ?, " +
                     "fecha_actualizacion = ? " +
                     "WHERE id_producto = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString   (1, p.getNombre());
            ps.setString   (2, p.getDescripcion());
            ps.setString   (3, p.getUnidadMedida());
            ps.setInt      (4, p.getStock());
            ps.setTimestamp(5, p.getFechaActual());
            ps.setInt      (6, p.getIdProducto());

            ps.executeUpdate();
            con.close();
            JOptionPane.showMessageDialog(null, "Producto Actualizado Correctamente");
        }catch(SQLException x){
            System.out.println(x.toString());
            JOptionPane.showMessageDialog(null, "Error Producto No Actualizado");
        }
    }
    
    public static void eliminarProductoPorId(int idProducto) throws SQLException {
        con = Conexion.getConnection();
        String sql = "DELETE FROM Productos WHERE id_producto = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idProducto);
            ps.executeUpdate();
            con.close();
            JOptionPane.showMessageDialog(null, "Producto Eliminado Correctamente");
        }catch(SQLException x){
            System.out.println(x.toString());
            JOptionPane.showMessageDialog(null, "Error Producto No Eliminado");
        }
    }
    
    public static Producto obtenerProductoPorId(int idProducto) throws SQLException {
        con = Conexion.getConnection();
        String sql = "SELECT id_producto, nombre, descripcion, unidad_medida, stock, fecha_actualizacion " +
                     "FROM Productos WHERE id_producto = ?";
        Producto p = null;
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idProducto);
            ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    p = new Producto(
                        rs.getInt("id_producto"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getString("unidad_medida"),
                        rs.getInt("stock"),
                        rs.getTimestamp("fecha_actualizacion")
                    );
                }
                con.close();
                JOptionPane.showMessageDialog(null, "Producto Encontrado Correctamente");
                return p;
        }catch(SQLException x){
            System.out.println(x.toString());
            JOptionPane.showMessageDialog(null, "Error Producto No Encontrado");
        }
        return p;
    }
    
    public static String[][] obtenerTodosLosProductos() throws SQLException {
        con = Conexion.getConnection();
        String sql = "SELECT id_producto, nombre, descripcion, unidad_medida, stock, fecha_actualizacion " +
                 "FROM Productos ORDER BY nombre ASC";

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            ArrayList<String[]> lista = new ArrayList<>();

            while (rs.next()) {
                String[] fila = new String[6];
                fila[0] = String.valueOf(rs.getInt("id_producto"));
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getString("descripcion");
                fila[3] = rs.getString("unidad_medida");
                fila[4] = String.valueOf(rs.getInt("stock"));
                fila[5] = rs.getTimestamp("fecha_actualizacion").toString();
                lista.add(fila);
            }

            // Convertir ArrayList a String[][]
            String[][] datos = new String[lista.size()][6];
            con.close();
            JOptionPane.showMessageDialog(null, "Exito Productos Cargados");
            return lista.toArray(datos);
        }catch(SQLException x){
           System.out.println(x.toString());
           JOptionPane.showMessageDialog(null, "Error Productos No Cargados");
        }
        return null;
    }
    
    public static Integer obtenerIdProductoPorNombre(String nombre) throws SQLException {
        con = Conexion.getConnection();
        String sql = "SELECT id_producto FROM Productos WHERE nombre = ?";
        Integer id = null;
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);

            ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    id = rs.getInt("id_producto");
                }
            con.close();
            JOptionPane.showMessageDialog(null, "Exito Producto Encontrado");
            return id;
        }catch(SQLException x){
           System.out.println(x.toString());
           JOptionPane.showMessageDialog(null, "Error Producto No Encontrado");
        }
        return id;
    }
    
    public static DefaultComboBoxModel<String> obtenerModeloComboNombresProductos() throws SQLException {
        con = Conexion.getConnection();
        String sql = "SELECT nombre FROM Productos ORDER BY nombre ASC";
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
            return modelo; // retorna aunque esté vacío
        }
    }
    
    public static String obtenerNombreProductoPorId(int idProducto) throws SQLException {
        con = Conexion.getConnection();
        String sql = "SELECT nombre FROM Productos WHERE id_producto = ?";
        String nombre = null;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idProducto);
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
