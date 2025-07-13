package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ProovedorDAO {

    static Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public static void guardarProveedor(Proveedor p) throws SQLException {
        con = Conexion.getConnection();
        String sql = "INSERT INTO Proveedores " +
                     "(nombre, telefono, email, direccion, fecha_registro, precio, id_producto) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getTelefono());
            ps.setString(3, p.getEmail());
            ps.setString(4, p.getDireccion());
            ps.setTimestamp(5, p.getFechaRegistro());
            ps.setDouble(6, p.getPrecio());
            ps.setInt(7, p.getIdProducto());
            ps.executeUpdate();
            con.close();
            JOptionPane.showMessageDialog(null, "Proveedor Guardado Correctamente");
        } catch (SQLException x) {
            System.out.println(x.toString());
            JOptionPane.showMessageDialog(null, "Error Proveedor No Guardado");
        }
    }

    public static void actualizarProveedor(Proveedor p) throws SQLException {
        con = Conexion.getConnection();
        String sql = "UPDATE Proveedores SET " +
                     "nombre = ?, telefono = ?, email = ?, direccion = ?, " +
                     "fecha_registro = ?, precio = ?, id_producto = ? " +
                     "WHERE id_proveedor = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getTelefono());
            ps.setString(3, p.getEmail());
            ps.setString(4, p.getDireccion());
            ps.setTimestamp(5, p.getFechaRegistro());
            ps.setDouble(6, p.getPrecio());
            ps.setInt(7, p.getIdProducto());
            ps.setInt(8, p.getIdProveedor());
            ps.executeUpdate();
            con.close();
            JOptionPane.showMessageDialog(null, "Proveedor Actualizado Correctamente");
        } catch (SQLException x) {
            System.out.println(x.toString());
            JOptionPane.showMessageDialog(null, "Error Proveedor No Actualizado");
        }
    }

    public static void eliminarProveedorPorId(int idProveedor) throws SQLException {
        con = Conexion.getConnection();
        String sql = "DELETE FROM Proveedores WHERE id_proveedor = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idProveedor);
            ps.executeUpdate();
            con.close();
            JOptionPane.showMessageDialog(null, "Proveedor Eliminado Correctamente");
        } catch (SQLException x) {
            System.out.println(x.toString());
            JOptionPane.showMessageDialog(null, "Error Proveedor No Eliminado");
        }
    }

    public static Proveedor obtenerProveedorPorId(int idProveedor) throws SQLException {
        con = Conexion.getConnection();
        String sql = "SELECT * FROM Proveedores WHERE id_proveedor = ?";
        Proveedor p = null;
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idProveedor);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                p = new Proveedor(
                        rs.getInt("id_proveedor"),
                        rs.getString("nombre"),
                        rs.getString("telefono"),
                        rs.getString("email"),
                        rs.getString("direccion"),
                        rs.getTimestamp("fecha_registro"),
                        rs.getDouble("precio"),
                        rs.getInt("id_producto")
                );
            }
            con.close();
            JOptionPane.showMessageDialog(null, "Proveedor Encontrado Correctamente");
            return p;
        } catch (SQLException x) {
            System.out.println(x.toString());
            JOptionPane.showMessageDialog(null, "Error Proveedor No Encontrado");
        }
        return p;
    }

    public static String[][] obtenerTodosLosProveedores() throws SQLException {
        con = Conexion.getConnection();
        String sql = "SELECT * FROM Proveedores ORDER BY nombre ASC";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            ArrayList<String[]> lista = new ArrayList<>();
            while (rs.next()) {
                String[] fila = new String[8];
                fila[0] = String.valueOf(rs.getInt("id_proveedor"));
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getString("telefono");
                fila[3] = rs.getString("email");
                fila[4] = rs.getString("direccion");
                fila[5] = rs.getTimestamp("fecha_registro").toString();
                fila[6] = String.valueOf(rs.getDouble("precio"));
                fila[7] = String.valueOf(rs.getInt("id_producto"));
                lista.add(fila);
            }

            String[][] datos = new String[lista.size()][8];
            con.close();
            JOptionPane.showMessageDialog(null, "Éxito Proveedores Cargados");
            return lista.toArray(datos);
        } catch (SQLException x) {
            System.out.println(x.toString());
            JOptionPane.showMessageDialog(null, "Error Proveedores No Cargados");
        }
        return null;
    }

    public static Integer obtenerIdProveedorPorNombre(String nombre) throws SQLException {
        con = Conexion.getConnection();
        String sql = "SELECT id_proveedor FROM Proveedores WHERE nombre = ?";
        Integer id = null;
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id_proveedor");
            }
            con.close();
            JOptionPane.showMessageDialog(null, "Éxito Proveedor Encontrado");
            return id;
        } catch (SQLException x) {
            System.out.println(x.toString());
            JOptionPane.showMessageDialog(null, "Error Proveedor No Encontrado");
        }
        return id;
    }
}
