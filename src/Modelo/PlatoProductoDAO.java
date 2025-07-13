package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class PlatoProductoDAO {

    static Connection con;

    public static void guardarPlatoProducto(PlatoProducto pp) throws SQLException {
        con = Conexion.getConnection();
        String sql = "INSERT INTO Plato_Producto (id_plato, id_producto, cantidad) VALUES (?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, pp.getIdPlato());
            ps.setInt(2, pp.getIdProducto());
            ps.setDouble(3, pp.getCantidad());
            ps.executeUpdate();
            con.close();
            JOptionPane.showMessageDialog(null, "Relación Plato-Producto Guardada Correctamente");
        } catch (SQLException x) {
            System.out.println(x.toString());
            JOptionPane.showMessageDialog(null, "Error al Guardar Plato-Producto");
        }
    }

    public static void actualizarPlatoProducto(PlatoProducto pp) throws SQLException {
        con = Conexion.getConnection();
        String sql = "UPDATE Plato_Producto SET id_plato = ?, id_producto = ?, cantidad = ? WHERE id_plato_producto = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, pp.getIdPlato());
            ps.setInt(2, pp.getIdProducto());
            ps.setDouble(3, pp.getCantidad());
            ps.setInt(4, pp.getIdPlatoProducto());
            ps.executeUpdate();
            con.close();
            JOptionPane.showMessageDialog(null, "Relación Actualizada Correctamente");
        } catch (SQLException x) {
            System.out.println(x.toString());
            JOptionPane.showMessageDialog(null, "Error al Actualizar Plato-Producto");
        }
    }

    public static void eliminarPlatoProductoPorId(int idPlatoProducto) throws SQLException {
        con = Conexion.getConnection();
        String sql = "DELETE FROM Plato_Producto WHERE id_plato_producto = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPlatoProducto);
            ps.executeUpdate();
            con.close();
            JOptionPane.showMessageDialog(null, "Relación Eliminada Correctamente");
        } catch (SQLException x) {
            System.out.println(x.toString());
            JOptionPane.showMessageDialog(null, "Error al Eliminar Plato-Producto");
        }
    }

    public static PlatoProducto obtenerPlatoProductoPorId(int idPlatoProducto) throws SQLException {
        con = Conexion.getConnection();
        String sql = "SELECT * FROM Plato_Producto WHERE id_plato_producto = ?";
        PlatoProducto pp = null;
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPlatoProducto);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                pp = new PlatoProducto(
                        rs.getInt("id_plato_producto"),
                        rs.getInt("id_plato"),
                        rs.getInt("id_producto"),
                        rs.getDouble("cantidad")
                );
            }
            con.close();
            JOptionPane.showMessageDialog(null, "Relación Encontrada");
            return pp;
        } catch (SQLException x) {
            System.out.println(x.toString());
            JOptionPane.showMessageDialog(null, "Error al Buscar Plato-Producto");
        }
        return pp;
    }

    public static String[][] obtenerTodosPlatoProducto() throws SQLException {
        con = Conexion.getConnection();
        String sql = "SELECT * FROM Plato_Producto";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            ArrayList<String[]> lista = new ArrayList<>();
            while (rs.next()) {
                String[] fila = new String[4];
                fila[0] = String.valueOf(rs.getInt("id_plato_producto"));
                fila[1] = String.valueOf(rs.getInt("id_plato"));
                fila[2] = String.valueOf(rs.getInt("id_producto"));
                fila[3] = String.valueOf(rs.getDouble("cantidad"));
                lista.add(fila);
            }

            String[][] datos = new String[lista.size()][4];
            con.close();
            JOptionPane.showMessageDialog(null, "Relaciones Cargadas Correctamente");
            return lista.toArray(datos);
        } catch (SQLException x) {
            System.out.println(x.toString());
            JOptionPane.showMessageDialog(null, "Error al Cargar Plato-Producto");
        }
        return null;
    }
}
