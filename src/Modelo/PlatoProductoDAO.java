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

        // Verificar si ya existe la combinación plato-producto
        String verificarSql = "SELECT COUNT(*) FROM Plato_Producto WHERE id_plato = ? AND id_producto = ?";
        try (PreparedStatement verificarStmt = con.prepareStatement(verificarSql)) {
            verificarStmt.setInt(1, pp.getIdPlato());
            verificarStmt.setInt(2, pp.getIdProducto());

            ResultSet rs = verificarStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                con.close();
                System.out.println("La relación ya existe. No se insertó duplicado.");
                return;
            }
        }

        // Si no existe, insertar normalmente
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

    public static ArrayList<PlatoProducto> obtenerPlatoProductosPorIdPlato(int idPlato) {
        ArrayList<PlatoProducto> lista = new ArrayList<>();
        con = Conexion.getConnection();

        String sql = "SELECT pp.id_plato_producto, pp.id_plato, pp.id_producto, pp.cantidad " +
                     "FROM Plato_Producto pp " +
                     "WHERE pp.id_plato = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPlato);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PlatoProducto pp = new PlatoProducto(
                    rs.getInt("id_plato_producto"),
                    rs.getInt("id_plato"),
                    rs.getInt("id_producto"),
                    rs.getDouble("cantidad")
                );
                lista.add(pp);
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al obtener PlatoProducto por idPlato: " + e);
        }

        return lista;
    }
}
